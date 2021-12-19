import React, {useCallback, useState} from "react";
import {Box, Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField, Typography} from '@mui/material';
import {LoadingButton} from '@mui/lab';
import axios from 'axios'
import teamImg from './team.jpg';
import NumberInput from '../NumberInput.js';

export default function CreateOrganizationDialog(props) {
    const [organization, setOrganization] = useState({fullName: null, annualTurnover: 0});
    const [errors, setErrors] = useState({});
    const [submitting, setSubmitting] = useState(false);

    const validateOrganizationOnSubmit = useCallback(() => {
        var newErrors = {};
        if (!organization.fullName || organization.fullName.length === 0) {
            newErrors['FullName'] = 'Full Name should not be empty';
        }
        if (!organization.annualTurnover || organization.annualTurnover <= 0) {
            newErrors['AnnualTurnover'] = 'Annual turnover should be positive';
        }

        if (Object.keys(newErrors).length === 0) {
            var organizationDto = {
                fullName: organization.fullName,
                annualTurnover: organization.annualTurnover,
            };

            setSubmitting(true);
            axios.post('/app/api/organizations', organizationDto)
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
    }, [organization, props, setSubmitting])

    return (
        <div>
            <Dialog open={props.open} onClose={props.handleClose} maxWidth='xl'>
                <DialogTitle>Create organization</DialogTitle>
                <DialogContent>
                    <Box display="grid" gap={2} sx={{
                        backgroundImage: `linear-gradient(rgba(255,255,255,0.9), rgba(255,255,255,0.9)), url(${teamImg})`,
                        backgroundSize: 'contain',
                        backgroundPosition: 'center'
                    }}>
                        <Typography>
              <pre style={{fontFamily: 'inherit'}}>
                {`public class Organization {
    private String fullName; //Поле не может быть null
    private int annualTurnover; //Значение поля должно быть больше 0
}`}
              </pre>
                        </Typography>
                        <Typography>
                            <b>Please, fill the fields according to the description to create new organization
                                entity.</b>
                        </Typography>
                        <Box display="grid" gap={2}>
                            <TextField size='small' label='FullName' value={organization.fullName} onChange={(e) => {
                                organization.fullName = e.target.value;
                                setOrganization(Object.assign({}, organization));
                            }} {...(errors['FullName'] && {error: true, helperText: errors['FullName']})}/>
                            <NumberInput size='small' label='Annual Turnover' value={organization.annualTurnover}
                                         onChange={(e) => {
                                             organization.annualTurnover = e.target.value;
                                             setOrganization(Object.assign({}, organization));
                                         }} {...(errors['AnnualTurnover'] && {
                                error: true,
                                helperText: errors['AnnualTurnover']
                            })}/>
                        </Box>
                    </Box>
                </DialogContent>
                {
                    submitting ? <DialogActions>
                        <LoadingButton color='secondary'>Cancel</LoadingButton>
                        <LoadingButton>Create</LoadingButton>
                    </DialogActions> : <DialogActions>
                        <Button onClick={() => {
                            setOrganization({position: null, startDate: null, endDate: null});
                            props.handleClose()
                        }} color='secondary'>Cancel</Button>
                        <Button onClick={() => {
                            validateOrganizationOnSubmit();
                        }}>Create</Button>
                    </DialogActions>
                }
            </Dialog>
        </div>
    );
}