import * as React from 'react';
import {Box, Tab, Tabs} from '@mui/material';
import TabPanel from './TabPanel.js';
import WorkerTabPanel from './Worker/WorkerTabPanel.js';
import OrganizationTabPanel from './Organizations/OrganizationTabPanel.js';
import CoordinatesTabPanel from './Coordinates/CoordinatesTabPanel.js';
import axios from 'axios'
import queryString from 'query-string'

// Add a request interceptor
axios.interceptors.request.use(function (config) {
    // Serialize the parameteters
    config.paramsSerializer = params => queryString.stringify(params, {arrayFormat: 'brackets'})
    return config;
});

function a11yProps(index: number) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`,
    };
}

export default function App() {
    const [value, setValue] = React.useState(0);

    const handleChange = (event: React.SyntheticEvent, newValue: number) => {
        setValue(newValue);
    };

    return (
        <Box sx={{width: '100%', backgroundColor: '#F6F6F6'}}>
            <Box>
                <Tabs
                    value={value}
                    onChange={handleChange}
                    centered
                    indicatorColor='primary'>
                    <Tab label="Workers" {...a11yProps(0)} />
                    <Tab label="Coordinates" {...a11yProps(1)} />
                    <Tab label="Organizations" {...a11yProps(2)} />
                </Tabs>
            </Box>
            <TabPanel value={value} index={0}>
                <WorkerTabPanel/>
            </TabPanel>
            <TabPanel value={value} index={1}>
                <CoordinatesTabPanel/>
            </TabPanel>
            <TabPanel value={value} index={2}>
                <OrganizationTabPanel/>
            </TabPanel>
        </Box>
    );
}