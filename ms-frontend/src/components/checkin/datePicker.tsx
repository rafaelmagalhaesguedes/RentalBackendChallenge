import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { Dayjs } from 'dayjs';

type DatePickerCustomProps = {
  label: string;
  value: Dayjs | null;
  onChange: (newValue: any | null) => void;
};

export const DatePickerCustom = ({ label, value, onChange }: DatePickerCustomProps) => (
  <LocalizationProvider dateAdapter={AdapterDayjs}>
    <DatePicker
        label={label}
        value={value}
        onChange={onChange}
        format="DD / MM / YYYY"
        slotProps={{
          textField: {
            variant: 'outlined', 
            style: { backgroundColor: '#f5f5f5' }
          },
        }}
    />
  </LocalizationProvider>
);