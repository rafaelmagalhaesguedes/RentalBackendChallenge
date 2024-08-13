export function formatDateTime(dateString: string, timeString: string) {
  const date = new Date(dateString);
  const time = new Date(timeString);
  
  const hours = time.getUTCHours().toString().padStart(2, '0');
  const minutes = time.getUTCMinutes().toString().padStart(2, '0');
  
  date.setUTCHours(Number(hours));
  date.setUTCMinutes(Number(minutes));
  
  const year = date.getUTCFullYear();
  const month = (date.getUTCMonth() + 1).toString().padStart(2, '0');
  const day = date.getUTCDate().toString().padStart(2, '0');
  const formattedDate = `${year}-${month}-${day}`;

  return `${formattedDate}T${hours}:${minutes}:00`;
}
