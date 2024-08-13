export function getTotalDays(pickupDateTime: string | undefined, returnDateTime: string | undefined): number {
  if (!pickupDateTime || !returnDateTime) return 0; // Return 0 if either date is missing

  const pickupDate = new Date(pickupDateTime);
  const returnDate = new Date(returnDateTime);
  const millisecondsPerDay = 1000 * 60 * 60 * 24;

  const differenceInMilliseconds = returnDate.getTime() - pickupDate.getTime();
  const differenceInDays = Math.ceil(differenceInMilliseconds / millisecondsPerDay);

  return differenceInDays;
}