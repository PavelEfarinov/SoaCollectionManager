import React, {useCallback, useEffect, useState} from "react";
import {Box, Button, Grid, Typography} from '@mui/material';
import {DataGrid} from '@mui/x-data-grid';
import OrganizationFilterCollection from './OrganizationFilterCollection.js';
import CreateOrganizationDialog from './CreateOrganizationDialog.js';
import EditOrganizationDialog from './EditOrganizationDialog.js';
import NumberInput from '../NumberInput.js';
import {useSnackbar} from 'notistack';

export default function OrganizationTabPanel() {
    const {enqueueSnackbar} = useSnackbar();
    const axios = require('axios').default;

    const [openCreateWorkerDialog, setOpenCreateWorkerDialog] = useState(false);
    const [isGridLoaded, setIsGridLoaded] = useState(false);
    const [isTotalLoaded, setIsTotalLoaded] = useState(false);
    const [items, setItems] = useState([]);
    const [selectedItem, setSelectedItem] = useState(null);
    const [displayedNow, setDisplayedNow] = useState(0);
    const [totalForFilter, setTotalForFilter] = useState(0);
    const [page, setPage] = useState(null);
    const [pageSize, setPageSize] = useState(null);
    const [filterCombination, setFilterCombination] = useState([]);
    const [sortOrder, setSortOrder] = useState({field: 'id', order: 'ASC'});

    const updateSelectedItem = useCallback((selectionModel: GridSelectionModel, details: GridCallbackDetails) => {
        const newSelected = items.find(function (e) {
            return e.id === selectionModel[0]
        })
        setSelectedItem(newSelected)
    }, [items, setSelectedItem])

    const setProperError = useCallback((requestError) => {
        var newError = requestError;
        if (requestError) {
            if (requestError.response) {
                newError = requestError.response.data.Message;
            } else if (requestError.request) {
                newError = requestError.request
            } else {
                newError = requestError.message;
            }
            enqueueSnackbar(newError, {variant: 'error'});
        }
    }, [enqueueSnackbar]);

    const updateDataGrid = useCallback(() => {
        return axios.get('/organizations', {
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
    }, [axios, setProperError, page, pageSize, sortOrder]);

    const updateDataGridWithFilter = useCallback(() => {
        var params = filterCombination.map((filter) => {
            return {fieldName: filter.field, predicateType: filter.predicate, fieldValue: filter.value};
        });

        axios.get('/organizations/count')
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
            url: '/organizations',
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
    }, [axios, filterCombination, setProperError, pageSize, page, sortOrder]);

    useEffect(() => {
        if (items.length === 0) {
            updateDataGrid();
        }

        axios.get('/organizations/count')
            .then(function (response) {
                console.log(response.data);
                setTotalForFilter(response.data);
                setIsTotalLoaded(true);
            })
            .catch(function (requestError) {
                setProperError(requestError);
                setIsTotalLoaded(true);
            });
    }, [])

    const columns: GridColDef[] = [
        {field: 'id', headerName: 'Id', width: 50, sortable: false},
        {field: 'fullName', headerName: 'Full Name', width: 150, sortable: false},
        {field: 'annualTurnover', headerName: 'Annual Turnover', width: 150, sortable: false},
    ];

    if (isGridLoaded || isTotalLoaded) {
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
                </Box>
                <Button variant="contained" onClick={() => {
                    setOpenCreateWorkerDialog(true)
                }}>Add element</Button>
            </Grid>
            <Grid item xl={4}>
                <Box sx={{height: '100%', width: '50%'}}>
                    <OrganizationFilterCollection filters={filterCombination} updateFilters={setFilterCombination}
                                                  sortOrder={sortOrder} setSortOrder={setSortOrder}/>
                    <Button sx={{mt: 3}} variant="contained" onClick={updateDataGridWithFilter}>Apply filter</Button>
                </Box>
            </Grid>
            {openCreateWorkerDialog ? <CreateOrganizationDialog open={openCreateWorkerDialog} handleClose={() => {
                    setOpenCreateWorkerDialog(false);
                    updateDataGrid()
                }} setProperError={setProperError}/>
                : ''}
            {selectedItem ? <EditOrganizationDialog open={selectedItem} handleClose={() => {
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