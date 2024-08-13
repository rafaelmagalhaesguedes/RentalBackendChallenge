import { useNavigate } from 'react-router';
import { ContainerSuccess } from './style';

export function Success() {
  const navigate = useNavigate();
  return (
    <ContainerSuccess>
      <h1>Pagamento realizado com sucesso!</h1>
      <p>
        Os dados do seu pagamento foram enviados ao seu email,
        assim como o comprovante de pagamento e dados da reserva.
      </p>

      <button type="button" className="btn btn-primary btn-lg" onClick={() =>navigate('/dashboard')}>
        Minhas reservas
      </button>
    </ContainerSuccess>
  );
}