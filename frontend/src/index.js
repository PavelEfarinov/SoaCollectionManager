import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import {Button, Typography} from '@mui/material';
import {SnackbarProvider} from 'notistack';
import DateAdapter from '@mui/lab/AdapterDayjs';
import LocalizationProvider from '@mui/lab/LocalizationProvider';
import dayjz from 'dayjs';

const notistackRef = React.createRef();
const onClickDismiss = key => () => {
    notistackRef.current.closeSnackbar(key);
}
var utc = require('dayjs/plugin/utc')
var timezone = require('dayjs/plugin/timezone') // dependent on utc plugin
dayjz.extend(utc)
dayjz.extend(timezone)
document.title = "SOA 1";

ReactDOM.render(
    <React.StrictMode>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"/>
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
        <SnackbarProvider
            maxSnack={2}
            hideIconVariant={false}
            ref={notistackRef}
            action={(key) => (
                <Button onClick={onClickDismiss(key)}>
                    <Typography color='white'>CLOSE</Typography>
                </Button>
            )}>
            <LocalizationProvider dateAdapter={DateAdapter}>
                <App/>
            </LocalizationProvider>
        </SnackbarProvider>
    </React.StrictMode>,
    document.getElementById('root')
);
