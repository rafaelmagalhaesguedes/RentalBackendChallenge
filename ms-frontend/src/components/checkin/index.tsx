import dayjs from 'dayjs';
import 'dayjs/locale/pt-br';
import Swal from 'sweetalert2';
import * as React from 'react';
import { useState } from 'react';
import { CheckinBar } from './style';
import { useNavigate } from 'react-router-dom';
import { DatePickerCustom } from './datePicker';
import { TimePickerCustom } from './timePicker';
import { validateCheckInForm } from '../../util/validateCheckInForm';

dayjs.locale('pt-br');

export function Checkin() {
  const navigate = useNavigate();
  const [checkInDate, setCheckInDate] = useState(null);
  const [checkInTime, setCheckInTime] = useState(null);
  const [checkOutDate, setCheckOutDate] = useState(null);
  const [checkOutTime, setCheckOutTime] = useState(null);

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    const checkInData = { checkInDate, checkInTime, checkOutDate, checkOutTime };

    const errorMessage = validateCheckInForm(checkInData);

    if (errorMessage) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: errorMessage,
      });
      return;
    }

    try {
      localStorage.setItem('checkin', JSON.stringify(checkInData));

      navigate('/reservation/group');
    } catch (error) {
      console.error(error);
    }
  }

  return (
    <CheckinBar>
      <div className="date">
        <DatePickerCustom label="Data retirada" value={checkInDate} onChange={setCheckInDate} />
        <TimePickerCustom label="Hora retirada" value={checkInTime} onChange={setCheckInTime} />
      </div>
      <div className="date">
        <DatePickerCustom label="Data devolução" value={checkOutDate} onChange={setCheckOutDate} />
        <TimePickerCustom label="Hora devolução" value={checkOutTime} onChange={setCheckOutTime} />
      </div>
      <input type="submit" onClick={handleSubmit} value="Continuar" />
    </CheckinBar>
  );
}