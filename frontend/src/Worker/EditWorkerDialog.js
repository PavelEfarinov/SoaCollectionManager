import React, {useCallback, useEffect, useState} from "react";
import {
    Autocomplete,
    Box,
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogTitle,
    Grid,
    TextField,
    Typography
} from '@mui/material';
import {LoadingButton} from '@mui/lab';
import DateTimePicker from '@mui/lab/DateTimePicker';
import axios from 'axios'
import dayjz from 'dayjs';
import workerImg from './worker.jpg';
import NumberInput from "../NumberInput";

export default function EditWorkerDialog(props) {
    const positions = ['DIRECTOR', 'HEAD_OF_DIVISION', 'HEAD_OF_DEPARTMENT', 'LEAD_DEVELOPER', 'BAKER'];
    const [oldOrganizationId, setOldOrganizationId] = useState(props.item.organization != null ? JSON.parse(props.item.organization).id : null);
    const [organizations, setOrganizations] = useState([]);
    const [index, setIndex] = useState(1.0);
    const [coordinates, setCoordinates] = useState([]);
    const [worker, setWorker] = useState({
        id: props.item.id,
        name: props.item.name,
        position: props.item.position,
        salary: props.item.salary,
        startDate: dayjz(props.item.startDate),
        endDate: props.item.endDate ? dayjz(props.item.endDate) : null,
        coordinates: JSON.parse(props.item.coordinates),
        organization: props.item.organization != null ? JSON.parse(props.item.organization) : null,
    });

    const [errors, setErrors] = useState({});
    const [submitting, setSubmitting] = useState(false);

    useEffect(() => {
        axios.get('/app/api/organizations?pageSize=1000').then(function (response) {
            console.log(response.data);
            setOrganizations(response.data);
        })
            .catch(function (requestError) {
                props.setProperError(requestError);
            });

        axios.get('/app/api/coordinates?pageSize=1000')
            .then(function (response) {
                console.log(response.data);
                setCoordinates(response.data);
            })
            .catch(function (requestError) {
                props.setProperError(requestError);
            });
    }, [setOrganizations, setCoordinates, props]);

    const deleteWorker = useCallback(() => {
        setSubmitting(true);
        axios.delete('/app/api/workers/' + worker.id)
            .then(function (response) {
                props.handleClose();
                setSubmitting(false);
            })
            .catch(function (requestError) {
                props.setProperError(requestError);
                setSubmitting(false);
            })
    }, [worker, props])

    const updateOnlyOrg = useCallback((newOrg) => {
        setSubmitting(true);
        axios.post(`/api/move/${worker.id}/${oldOrganizationId}/${newOrg.id}`)
            .then(function (response) {
                setOldOrganizationId(newOrg.id)
                worker.organization = newOrg;
                setWorker(Object.assign({}, worker));
                setSubmitting(false);
            })
            .catch(function (requestError) {
                props.setProperError(requestError);
                setSubmitting(false);
            })
    }, [worker, oldOrganizationId, props])

    const updateOnlySalary = useCallback(() => {
        setSubmitting(true);
        axios.post(`/api/index/${worker.id}/${index}`)
            .then(function (response) {
                worker.salary = response.data;
                setWorker(Object.assign({}, worker));
                setSubmitting(false);
            })
            .catch(function (requestError) {
                props.setProperError(requestError);
                setSubmitting(false);
            })
    }, [worker, index, props])

    const validateWorkerOnSubmit = useCallback(() => {
        var newErrors = {};
        if (!worker.name || worker.name.length === 0) {
            newErrors['Name'] = 'Name should not be empty';
        }
        if (!worker.salary || worker.salary < 0) {
            newErrors['Salary'] = 'Salary should be positive';
        }
        if (!worker.position) {
            newErrors['Position'] = 'Position should be initialized';
        }
        if (!worker.startDate) {
            newErrors['StartDate'] = 'Start date should be initialized';
        }
        if (!worker.coordinates) {
            newErrors['Coordinates'] = 'Coordinates should be initialized';
        }

        if (Object.keys(newErrors).length === 0) {
            var workerDto = {
                name: worker.name,
                salary: worker.salary,
                position: worker.position,
                coordinatesId: worker.coordinates.id,
                startDate: worker.startDate.format('YYYY-MM-DDTHH:mm:ss'),
            };
            if (worker.organization) {
                workerDto.organizationId = worker.organization.id;
            }
            if (worker.endDate) {
                workerDto.endDate = worker.endDate.format('YYYY-MM-DDTHH:mm:ss');
            }
            setSubmitting(true);
            axios.put('/app/api/workers/' + worker.id, workerDto)
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
    }, [worker, props, setSubmitting])

    if(props.item != null) {
        return (
            <div>
                <Dialog open={props.open} onClose={props.handleClose} maxWidth='xl'>
                    <DialogTitle>Update worker</DialogTitle>
                    <DialogContent>
                        <Box display="grid" gap={2} sx={{
                            backgroundImage: `linear-gradient(rgba(255,255,255,0.9), rgba(255,255,255,0.9)), url(${workerImg})`,
                            backgroundSize: 'contain',
                            backgroundPosition: 'center'
                        }}>
                            <Typography>
              <pre style={{fontFamily: 'inherit'}}>
                {`public class Worker {
    private int id; //???????????????? ???????? ???????????? ???????? ???????????? 0, ???????????????? ?????????? ???????? ???????????? ???????? ????????????????????, ???????????????? ?????????? ???????? ???????????? ???????????????????????????? ??????????????????????????
    private String name; //???????? ???? ?????????? ???????? null, ???????????? ???? ?????????? ???????? ????????????
    private Coordinates coordinates; //???????? ???? ?????????? ???????? null
    private java.time.LocalDateTime creationDate; //???????? ???? ?????????? ???????? null, ???????????????? ?????????? ???????? ???????????? ???????????????????????????? ??????????????????????????
    private float salary; //???????????????? ???????? ???????????? ???????? ???????????? 0
    private java.time.LocalDateTime startDate; //???????? ???? ?????????? ???????? null
    private java.time.ZonedDateTime endDate; //???????? ?????????? ???????? null
    private Position position; //???????? ???? ?????????? ???????? null
    private Organization organization; //???????? ?????????? ???????? null
}`}
              </pre>
                            </Typography>
                            <Typography>
                                <b>Please, fill the fields according to the description to create new worker entity.</b>
                            </Typography>
                            {
                                coordinates.length > 0 && organizations.length > 0 ? <Box display="grid" gap={2}>
                                    <TextField size='small' label='Name' value={worker.name} onChange={(e) => {
                                        worker.name = e.target.value;
                                        setWorker(Object.assign({}, worker));
                                    }} {...(errors['Name'] && {error: true, helperText: errors['Name']})}/>
                                    <Grid container spacing={1}>
                                        <Grid item xl={5}>
                                            <TextField size='small' label='Salary' value={worker.salary}
                                                       onChange={(e) => {
                                                           worker.salary = e.target.value;
                                                           setWorker(Object.assign({}, worker));
                                                       }} {...(errors['Salary'] && {
                                                error: true,
                                                helperText: errors['Salary']
                                            })}/>
                                        </Grid>
                                        <Grid item xl={5}>
                                            <NumberInput xl={5} size='small' label='Index' value={index}
                                                         onChange={(e) => {
                                                             setIndex(e.target.value)
                                                         }}/>
                                        </Grid>
                                        <Grid item xl={1}>
                                            <Button xl={2} onClick={updateOnlySalary}>Update</Button>
                                        </Grid>
                                    </Grid>
                                    <Autocomplete value={worker.position} onChange={(e, value) => {
                                        worker.position = value;
                                        setWorker(Object.assign({}, worker));
                                    }} options={positions}
                                                  renderInput={(params) => <TextField {...params} label='Position'
                                                                                      size='small' {...(errors['Position'] && {
                                                      error: true,
                                                      helperText: errors['Position']
                                                  })}/>}/>
                                    <Autocomplete value={worker.coordinates} onChange={(e, value) => {
                                        worker.coordinates = value;
                                        setWorker(Object.assign({}, worker));
                                    }} options={coordinates} getOptionLabel={(option) => {
                                        return JSON.stringify(option)
                                    }} renderInput={(params) => <TextField {...params} label='Coordinates'
                                                                           size='small' {...(errors['Coordinates'] && {
                                        error: true,
                                        helperText: errors['Coordinates']
                                    })}/>}/>
                                    <Autocomplete value={worker.organization} onChange={(e, value) => {
                                        if (oldOrganizationId) {
                                            updateOnlyOrg(value);
                                        } else {
                                            worker.organization = value;
                                            setWorker(Object.assign({}, worker));
                                        }
                                    }} options={organizations} getOptionLabel={(option) => {
                                        return option.fullName + '_' + option.id
                                    }} renderInput={(params) => <TextField {...params} label='Organization'
                                                                           size='small'/>}/>
                                    <DateTimePicker
                                        renderInput={(props) => <TextField {...props}
                                                                           size='small' {...(errors['StartDate'] && {
                                            error: true,
                                            helperText: errors['StartDate']
                                        })}/>}
                                        label="Start date"
                                        value={worker.startDate}
                                        onChange={(newValue) => {
                                            worker.startDate = newValue;
                                            setWorker(Object.assign({}, worker));
                                        }}
                                    />
                                    <DateTimePicker
                                        renderInput={(props) => <TextField {...props} size='small'/>}
                                        label="End date"
                                        value={worker.endDate}
                                        onChange={(newValue) => {
                                            worker.endDate = newValue;
                                            setWorker(Object.assign({}, worker));
                                        }}
                                    />
                                </Box> : ''
                            }
                        </Box>
                    </DialogContent>
                    {
                        submitting ? <DialogActions>
                            <LoadingButton color='secondary'>Cancel</LoadingButton>
                            <LoadingButton>Update</LoadingButton>
                        </DialogActions> : <DialogActions>
                            <Button onClick={() => {
                                setWorker({position: null, startDate: null, endDate: null});
                                props.handleClose()
                            }} variant='contained' color='secondary'>Cancel</Button>
                            <Button onClick={() => {
                                deleteWorker();
                            }} color='error'>Delete</Button>
                            <Button onClick={() => {
                                validateWorkerOnSubmit();
                            }}>Update</Button>
                        </DialogActions>
                    }
                </Dialog>
            </div>
        );
    }
    else {
        return <div></div>
    }
}