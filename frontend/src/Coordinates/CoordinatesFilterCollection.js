import React, {useState} from "react";
import {Autocomplete, Box, Button, TextField, Typography} from '@mui/material';

export default function CoordinatesFilterCollection(props) {
    function addNewFilter() {
        props.updateFilters(props.filters.concat([{
            field: filterField,
            predicate: filterPredicate,
            value: filterValue
        }]));
    }

    function updateSortField(value) {
        var newSort = {};
        newSort.order = props.sortOrder.order;
        newSort.field = value;
        props.setSortOrder(newSort);
    }

    function updateSortOrder(value) {
        var newSort = {};
        newSort.field = props.sortOrder.field;
        newSort.order = value;
        props.setSortOrder(newSort);
    }

    const fieldOptions = ['id', 'x', 'y'];
    const predicateOptions = ['EQ', 'GT', 'LT', 'LIKE'];
    const sortOptions = ['ASC', 'DESC'];

    const [filterField, setFilterField] = useState(fieldOptions[0]);
    const [filterPredicate, setFilterPredicate] = useState(predicateOptions[0]);
    const [filterValue, setFilterValue] = useState('');
    return <Box display="grid" gap={2}>
        <Autocomplete size='small' value={filterField} onChange={(e, value) => {
            setFilterField(value);
        }} options={fieldOptions} renderInput={(params) => <TextField {...params} label="Field name"/>}/>
        <Autocomplete size='small' value={filterPredicate} onChange={(e, value) => {
            setFilterPredicate(value);
        }} options={predicateOptions} renderInput={(params) => <TextField {...params} label="Predicate"/>}/>
        <TextField size='small' label={filterField + ' value'} value={filterValue} onChange={(e) => {
            setFilterValue(e.target.value);
        }}/>
        <Button variant="contained" onClick={addNewFilter}>Add filter</Button>
        <Button variant="contained" color="secondary" onClick={() => {
            props.updateFilters([])
        }}>Drop Filters</Button>
        <Autocomplete size='small' value={props.sortOrder.field} onChange={(e, value) => {
            updateSortField(value);
        }} options={fieldOptions} renderInput={(params) => <TextField {...params} label="Sort field"/>}/>
        <Autocomplete size='small' value={props.sortOrder.order} onChange={(e, value) => {
            updateSortOrder(value);
        }} options={sortOptions} renderInput={(params) => <TextField {...params} label="Sort order"/>}/>
        {props.filters.map((filter, i) => {
            return (<Typography key={'Typo_' + i}>{filter.field} {filter.predicate} {filter.value}</Typography>)
        })}
    </Box>
}
