export function validateReserveDates(dateStart: string, dateEnd: string) {
  const now = new Date();
  const start = new Date(dateStart);
  const end = new Date(dateEnd);
  
  // Checks if the start data is before the current data
  if (start < now) {
    return "A data de início deve estar no futuro da data e hora atuais.";
  }
  
  // Calculates the difference in days between the start data and the end data
  const differenceTime = end.getTime() - start.getTime();
  const differenceDays = differenceTime / (1000 * 3600 * 24);
  
  // Check if the reservation is for at least 1 day
  if (differenceDays < 1) {
    return "A reserva deve ser de pelo menos 1 dia.";
  }
  
  return "Valid reservation.";
}