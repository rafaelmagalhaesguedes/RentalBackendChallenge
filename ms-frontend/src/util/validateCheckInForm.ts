import { validateReserveDates } from './validateReserveDates';

export function validateCheckInForm(checkInData: any) {
  const { checkInDate, checkInTime, checkOutDate, checkOutTime } = checkInData;
  
  const validate = validateReserveDates(String(checkInDate), String(checkOutDate));

  const validations = [
    { condition: validate !== 'Valid reservation.', message: validate },
    { condition: checkInDate === null, message: 'Por favor, informe a data de retirada' },
    { condition: checkInTime === null, message: 'Por favor, informe a hora de retirada' },
    { condition: checkOutDate === null, message: 'Por favor, informe a data de devolução' },
    { condition: checkOutTime === null, message: 'Por favor, informe a hora de devolução' },
  ];

  for (const { condition, message } of validations) {
    if (condition) {
      return message;
    }
  }

  return null;
};