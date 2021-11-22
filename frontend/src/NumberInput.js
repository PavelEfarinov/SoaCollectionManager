import NumberFormat from 'react-number-format';
import * as React from 'react';
import TextField from '@mui/material/TextField';

const NumberFormatCustom = React.forwardRef(function NumberFormatCustom(props) {
    const {onChange, ...other} = props;

    return (
        <NumberFormat
            {...other}
            onValueChange={(values) => {
                onChange({
                    target: {
                        name: props.name,
                        value: values.value,
                    },
                });
            }}
            isNumericString
        />
    );
});

export default function NumberInput(props) {
    return (
        <TextField
            label={props.label}
            placeholder={props.placeholder}
            value={props.value}
            onChange={props.onChange}
            error={props.error}
            helperText={props.helperText}
            id={"formatted-numberformat-input-" + props.label}
            InputProps={{
                inputComponent: NumberFormatCustom,
            }}
            variant="standard"
        />
    );
}