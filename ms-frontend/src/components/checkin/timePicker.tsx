import { TimePicker } from '@mui/x-date-pickers/TimePicker';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';

type TimePickerCustomProps = {
  label: string;
  value: any;
  onChange: (newValue: any) => void;
}

export const TimePickerCustom = ({ label, value, onChange }: TimePickerCustomProps) => (
  <LocalizationProvider dateAdapter={AdapterDayjs}>
    <TimePicker
      label={label}
      value={value}
      onChange={onChange}
      ampm={false}
      slotProps={{
        textField: {
            variant: 'outlined',
            style: { backgroundColor: '#f5f5f5' }
        },
        toolbar: {
          hidden: false,
        }
      }}
    />
  </LocalizationProvider>
);