/**
 * Function to format date
 * 
 * @param date
 * @returns formatted date
 * 
 * @example
 * formatDate('2024-05-08') => 'May 8, 2024'
 * formatDate('2024-05-08T12:30:00') => 'May 8, 2024'
*/
export const formatDate = (date: string) => {
  const [year, month, day] = date.split('-');
  return `${new Date(`${year}-${month}-${day}`).toLocaleDateString('pt-BR', {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
  })}`;
};

export const formatTime = (date: string) => {
  const [year, month, day] = date.split('-');
  return `${new Date(`${year}-${month}-${day}`).toLocaleTimeString('pt-BR', {
    hour: '2-digit',
    minute: '2-digit',
  })}`;
}