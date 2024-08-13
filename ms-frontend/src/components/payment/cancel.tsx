import { useNavigate } from 'react-router';

export function Cancel() {
  const navigate = useNavigate();

  return (
    <div>
        <h1>Pagamento Cancelado</h1>
        <p>Você cancelou o pagamento. Se você tiver alguma dúvida, entre em contato conosco.</p>
        <button onClick={() => navigate('/')}>Voltar para a Home</button>
    </div>
  );
}
