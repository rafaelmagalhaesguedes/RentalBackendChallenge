import { useContext, useEffect, useState } from 'react';
import { DashboardContainer, DashboardHeader, DashboardContent, Card, DashboardSidebar, DashboardMain } from './style';
import { User } from '../../types/userType';
import { GroupType, ReservationType } from '../../types/reservationTypes';
import { FaArrowRight } from 'react-icons/fa';
import { FaLocationDot } from 'react-icons/fa6';
import { formatDate, formatTime } from '../../util/formatDate';
import { getTotalDays } from '../../util/getTotalDays';
import { AuthContext } from '../../context/authContext';

export function UserDashboard() {
  const { Logout } = useContext(AuthContext);
  const [reservation, setReservation] = useState<ReservationType | null>(null);
  const [person, setPerson] = useState<User | null>(null);
  const [group, setGroup] = useState<GroupType | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const storedReservation = localStorage.getItem('reservation');
        if (storedReservation) {
          const reservationData = JSON.parse(storedReservation);
          setReservation(reservationData);
        }
        const storedPerson = localStorage.getItem('user');
        if (storedPerson) {
          const personData = JSON.parse(storedPerson);
          setPerson(personData);
        }
        const storedGroup = localStorage.getItem('group');
        if (storedGroup) {
          const groupData = JSON.parse(storedGroup);
          setGroup(groupData);
        }
      } catch (error) {
        console.error('Erro ao carregar dados:', error);
      }
    };

    fetchData();
  }, []);

  const handleLogout = () => {
    Logout();
  }

  return (
    <DashboardContainer>
      <DashboardHeader>
        <h1>Resumo da Reserva</h1>
        <p>Enviamos um voucher com todos os detalhes da reserva para seu email!</p>
      </DashboardHeader>
      <DashboardMain>
        <DashboardContent>
          <Card>
            <h2>Pagamento</h2>
            <p>O pagamento será feito no momento da retirada do veículo. As formas de pagamento são dinheiro, pix, cartão de crédito ou débito.</p>
          </Card>
          {reservation && (
            <Card>
              <h2>Detalhes</h2>
              <div>
                <p><strong>Data de retirada: </strong>{formatDate(reservation.pickupDateTime)} às {formatTime(reservation.pickupDateTime)}h</p>
                <p><FaLocationDot /> Aeroporto de Porto Seguro</p>
              </div>
              <hr />
              <div>
                <p><strong>Data de devolução: </strong> {formatDate(reservation.returnDateTime)} às {formatTime(reservation.returnDateTime)}h</p>
                <p><FaLocationDot /> Aeroporto de Porto Seguro</p>
              </div>
            </Card>
          )}

          <Card>
            <h2>Responsável</h2>
            <p><strong>Nome: </strong>{person?.fullName}</p>
            <p><strong>Email:</strong> {person?.email}</p>
          </Card>
        </DashboardContent>
        <DashboardSidebar>

          {group && (
            <Card>
              <h3><strong>{group.name}</strong></h3>
              <img src={group.imageURL} alt={group.name} />
              <p>{group.vehicles}</p>
            </Card>
          )}

          <Card>
            <h2>Resumo</h2>
            <div className="total">
              <p><strong>Total de diárias:</strong> {getTotalDays(reservation?.pickupDateTime, reservation?.returnDateTime)}</p>
              <p><strong>Valor da diária:</strong> R$ {group?.dailyRate}</p>
              <p><strong>Valor total:</strong> R$ {reservation?.totalAmount}</p>
            </div>
          </Card>

          <Card>
            <button onClick={handleLogout}><FaArrowRight /> Finalizar Reserva</button>
          </Card>
        </DashboardSidebar>
      </DashboardMain>
    </DashboardContainer>
  );
}
