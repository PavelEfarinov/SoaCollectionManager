import * as React from 'react';
import {Box} from '@mui/material';

interface
TabPanelProps
{
    children ? : React.ReactNode;
    value : number;
    index : number;
}

export default function TabPanel(props: TabPanelProps) {
    const {children, value, index, ...other} = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{p: 3, mt: 8}}>
                    {children}
                </Box>
            )}
        </div>
    );
}