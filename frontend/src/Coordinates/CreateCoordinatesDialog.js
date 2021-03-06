import React, {useCallback, useState} from "react"
import {Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, Typography} from '@mui/material'
import {LoadingButton} from '@mui/lab'
import axios from 'axios'
import mapImg from './map.jpg'
import NumberInput from '../NumberInput.js'

export default function CreateCoordinatesDialog(props) {
    const [coordinates, setCoordinates] = useState({x: null, y: null});
    const [errors, setErrors] = useState({});
    const [submitting, setSubmitting] = useState(false);

    const validateCoordinatesOnSubmit = useCallback(() => {
        var newErrors = {};
        if (!coordinates.x || coordinates.x <= -485) {
            newErrors['X'] = 'X should be bigger then -485';
        }
        if (!coordinates.y) {
            newErrors['Y'] = 'Y should be given';
        }

        if (Object.keys(newErrors).length === 0) {
            var coordinatesDto = {
                x: coordinates.x,
                y: coordinates.y,
            };

            setSubmitting(true);
            axios.post('/app/api/coordinates', coordinatesDto)
                .then(function (response) {
                    props.handleClose();
                    setSubmitting(false);
                })
                .catch(function (requestError) {
                    props.setProperError(requestError);
                    setSubmitting(false);
                });
        }

        setErrors(newErrors);
    }, [coordinates, props, setSubmitting])

    return (
        <div>
            <Dialog open={props.open} onClose={props.handleClose} maxWidth='xl'>
                <DialogTitle>Create coordinates</DialogTitle>
                <DialogContent>
                    <Box display="grid" gap={2} sx={{
                        backgroundImage: `linear-gradient(rgba(255,255,255,0.5), rgba(255,255,255,0.5)), url(${mapImg})`,
                        backgroundSize: 'contain',
                        backgroundPosition: 'center'
                    }}>
                        <Typography>
              <pre style={{fontFamily: 'inherit'}}>
                {`public class Coordinates {
    private Integer x; //???????????????? ???????? ???????????? ???????? ???????????? -485, ???????? ???? ?????????? ???????? null
    private int y;
}`}
              </pre>
                        </Typography>
                        <Typography>
                            <b>Please, fill the fields according to the description to create new coordinates
                                entity.</b>
                        </Typography>
                        <Box display="grid" gap={2}>
                            <NumberInput size='small' label='X' value={coordinates.x} onChange={(e) => {
                                coordinates.x = e.target.value;
                                setCoordinates(Object.assign({}, coordinates));
                            }} {...(errors['X'] && {error: true, helperText: errors['X']})}/>
                            <NumberInput size='small' label='Y' value={coordinates.y} onChange={(e) => {
                                coordinates.y = e.target.value;
                                setCoordinates(Object.assign({}, coordinates));
                            }} {...(errors['Y'] && {error: true, helperText: errors['Y']})}/>
                        </Box>
                    </Box>
                </DialogContent>
                {
                    submitting ? <DialogActions>
                        <LoadingButton color='secondary'>Cancel</LoadingButton>
                        <LoadingButton>Create</LoadingButton>
                    </DialogActions> : <DialogActions>
                        <Button onClick={() => {
                            setCoordinates({position: null, startDate: null, endDate: null});
                            props.handleClose()
                        }} color='secondary'>Cancel</Button>
                        <Button onClick={() => {
                            validateCoordinatesOnSubmit();
                        }}>Create</Button>
                    </DialogActions>
                }
            </Dialog>
        </div>
    );
}