import React, {useCallback, useEffect, useState} from "react";
import {Box, Button, Grid, Typography} from '@mui/material';
import {DataGrid} from '@mui/x-data-grid';
import WorkerFilterCollection from './WorkerFilterCollection.js';
import CreateWorkerDialog from './CreateWorkerDialog.js';
import EditWorkerDialog from './EditWorkerDialog.js';
import NumberInput from '../NumberInput.js';
import {useSnackbar} from 'notistack';

export default function WorkerTabPanel() {
    const {enqueueSnackbar} = useSnackbar();
    const axios = require('axios').default;

    const [openCreateWorkerDialog, setOpenCreateWorkerDialog] = useState(false);
    const [isSalaryLoaded, setIsSalaryLoaded] = useState(false);
    const [isGridLoaded, setIsGridLoaded] = useState(false);
    const [isTotalLoaded, setIsTotalLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [selectedItem, setSelectedItem] = useState(null);
    const [salarySum, setSalarySum] = useState(0);
    const [displayedNow, setDisplayedNow] = useState(0);
    const [totalForFilter, setTotalForFilter] = useState(0);
    const [page, setPage] = useState(null);
    const [pageSize, setPageSize] = useState(null);
    const [filterCombination, setFilterCombination] = useState([]);
    const [sortOrder, setSortOrder] = useState({field: 'id', order: 'ASC'});

    const updateSelectedItem = useCallback((selectionModel: GridSelectionModel, details: GridCallbackDetails) => {
        if(isGridLoaded) {
            const newSelected = items.find(function (e) {
                return e.id === selectionModel[0]
            })
            setSelectedItem(newSelected)
        }
        else {
            setSelectedItem(null)
        }
    }, [items, setSelectedItem, isGridLoaded])

    const setProperError = useCallback((requestError) => {
        var newError = requestError;
        if (requestError) {
            if (requestError.response) {
                newError = requestError.response.data.message;
            } else if (requestError.request) {
                newError = requestError.request
            } else {
                newError = requestError.message;
            }
            enqueueSnackbar(newError, {variant: 'error'});
        }
    }, [enqueueSnackbar]);

    const updateDataGrid = useCallback(() => {
        axios.get('/app/api/workers/salary/sum').then(function (response) {
            console.log(response.data);
            setSalarySum(response.data);
            setIsSalaryLoaded(true);
        })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsSalaryLoaded(true);
            });

        axios.get('/app/api/workers/count')
            .then(function (response) {
                console.log(response.data);
                setTotalForFilter(response.data);
                setIsTotalLoaded(true);
            })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsTotalLoaded(true);
            });

        axios.get('/app/api/workers', {
            params: {
                page: page,
                pageSize: pageSize,
                sort: JSON.stringify({type: sortOrder.order, fieldName: sortOrder.field})
            }
        })
            .then(function (response) {
                console.log(response.data);
                var newData = response.data.map(function (current) {
                    var newCurrent = current;
                    newCurrent.organization = JSON.stringify(current.organization, null, '\t');
                    newCurrent.coordinates = JSON.stringify(current.coordinates, null, '\t');

                    return newCurrent;
                });
                setItems(newData);
                setDisplayedNow(newData.length);
                setIsGridLoaded(true);
                setProperError(null);
            })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsGridLoaded(true);
            });
    }, [axios, setProperError, page, pageSize, sortOrder, setSalarySum, setTotalForFilter]);

    const updateDataGridWithFilter = useCallback(() => {
        var params = filterCombination.map((filter) => {
            return {fieldName: filter.field, predicateType: filter.predicate, fieldValue: filter.value};
        });

        axios.get('/app/api/workers/salary/sum').then(function (response) {
            console.log(response.data);
            setSalarySum(response.data);
            setIsSalaryLoaded(true);
        })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsSalaryLoaded(true);
            });

        axios.get('/app/api/workers/count')
            .then(function (response) {
                console.log(response.data);
                setTotalForFilter(response.data);
                setIsTotalLoaded(true);
            })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsTotalLoaded(true);
            });

        return axios({
            method: 'get',
            url: '/app/api/workers',
            responseType: 'json',
            params: {
                filters: JSON.stringify(params),
                page: page,
                pageSize: pageSize,
                sort: JSON.stringify({type: sortOrder.order, fieldName: sortOrder.field})
            }
        }).then(function (response) {
            console.log(response.data);
            var newData = response.data.map(function (current) {
                var newCurrent = current;
                newCurrent.organization = JSON.stringify(current.organization, null, '\t');
                newCurrent.coordinates = JSON.stringify(current.coordinates, null, '\t');

                return newCurrent;
            });
            setItems(newData);
            setDisplayedNow(newData.length);
            setIsGridLoaded(true);
            setProperError(null);
        })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsGridLoaded(true);
            });
    }, [axios, filterCombination, setProperError, pageSize, page, sortOrder, setSalarySum, setTotalForFilter]);

    useEffect(() => {
        if (items.length === 0) {
            updateDataGrid();
        }
    }, [items.length, updateDataGrid])

    const columns: GridColDef[] = [
        {field: 'id', headerName: 'Id', width: 50, sortable: false},
        {field: 'name', headerName: 'Name', width: 150, sortable: false},
        {field: 'position', headerName: 'Position', width: 150, sortable: false},
        {field: 'salary', headerName: 'Salary', width: 150, sortable: false},
        {field: 'coordinates', headerName: 'Coordinates', width: 250, sortable: false},
        {field: 'organization', headerName: 'Organization', width: 400, sortable: false},
        {field: 'creationDate', headerName: 'Created at', width: 220, sortable: false},
        {field: 'startDate', headerName: 'Started at', width: 200, sortable: false},
        {field: 'endDate', headerName: 'Ended at', width: 350, sortable: false},
    ];

    if (isGridLoaded || isSalaryLoaded || isTotalLoaded) {
        return <Grid container spacing={4}>
            <Grid item xl={8}>
                {
                    isGridLoaded ?
                        <div>
                            <Box sx={{height: Math.max(Math.min(56 * (displayedNow + 1), 1000), 620) + 'px'}}>
                                <DataGrid disableColumnFilter rows={items} columns={columns} hideFooter={true}
                                          onSelectionModelChange={updateSelectedItem}/>
                            </Box>
                            <Typography sx={{mb: 2}}>Displayed: {displayedNow}</Typography>
                            <NumberInput size='small' label='Page' placeholder='0' value={page} onChange={(e) => {
                                setPage(e.target.value)
                            }}/>
                            <NumberInput size='small' label='Page size' placeholder='10' value={pageSize}
                                         onChange={(e) => {
                                             setPageSize(e.target.value)
                                         }}/>
                        </div> :
                        <Box sx={{height: '100%', width: '50%'}}>
                            Grid is loading...
                        </Box>
                }
                <Box sx={{mt: 1, mb: 2}}>
                    {
                        isTotalLoaded ?
                            <Typography>Total: {totalForFilter}</Typography> :
                            <Typography>Total: Loading...</Typography>
                    }
                    {
                        isSalaryLoaded ?
                            <Typography>Salary sum: {salarySum}</Typography> :
                            <Typography>Salary sum: Loading</Typography>
                    }
                </Box>
                <Button variant="contained" onClick={() => {
                    setOpenCreateWorkerDialog(true)
                }}>Add element</Button>
            </Grid>
            <Grid item xl={4}>
                <Box sx={{height: '100%', width: '50%'}}>
                    <WorkerFilterCollection filters={filterCombination} updateFilters={setFilterCombination}
                                            sortOrder={sortOrder} setSortOrder={setSortOrder}/>
                    <Button sx={{mt: 3}} variant="contained" onClick={updateDataGridWithFilter}>Apply filter</Button>
                </Box>
            </Grid>
            {openCreateWorkerDialog ? <CreateWorkerDialog open={openCreateWorkerDialog} handleClose={() => {
                    setOpenCreateWorkerDialog(false);
                    updateDataGrid()
                }} setProperError={setProperError}/>
                : ''}
            {selectedItem ? <EditWorkerDialog open={selectedItem} handleClose={() => {
                    setSelectedItem(null);
                    updateDataGrid()
                }} item={selectedItem} setProperError={setProperError}/>
                : ''}
        </Grid>
    } else {
        return <Box sx={{height: '100%', width: '50%'}}>
            Loading...
        </Box>
    }


}