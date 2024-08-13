import api from "../../api/api";
import { useNavigate } from "react-router";
import { useEffect, useState } from "react";
import { FaCalendar } from "react-icons/fa";
import { User } from "../../types/userType";
import 'bootstrap/dist/css/bootstrap.min.css';
import { loadStripe } from '@stripe/stripe-js';
import { FaLocationDot } from "react-icons/fa6";
import 'bootstrap/dist/js/bootstrap.bundle.min';
import { getTotalDays } from "../../util/getTotalDays";
import { formatDateTime } from '../../util/formatDateTime';
import { formatDate, formatTime } from '../../util/formatDate';
import { AdditionalType, CheckInType, GroupType } from '../../types/reservationTypes';
import { AccordionItem, Aside, AsideWrapper, CheckIn, CheckInText, CheckInTitle, CheckoutText, CheckoutTitle,
  Container, Content, Details, Group, GroupImage, GroupText,GroupTitle, TitleResume,Total,TotalReservation, Wrapper } from './style';

export function Checkout() {
  const stripePromise = loadStripe('pk_test_51OnPHdGl8SYs9ZebZBMFxlJO9t3PNuTLFod2Erfqq2mGaSs5Kn1QVnjLnARhOnPwvq3Y3zhFjEmN8Y3EFhErMqLF00dyPxnhWE');
  const [selectedAdditionals, setSelectedAdditionals] = useState<AdditionalType[]>([]);
  const [group, setGroup] = useState<GroupType | null>(null);
  const [checkin, setCheckin] = useState<CheckInType | null>(null);
  const [user, setUser] = useState<User | null>(null);
  const [token, setToken] = useState<string | null>(null);
  const navigate = useNavigate();

  const pickupDateTime = formatDateTime(checkin?.checkInDate || '', checkin?.checkInTime || '');
  const returnDateTime = formatDateTime(checkin?.checkOutDate || '', checkin?.checkOutTime || '');
  const totalDays = getTotalDays(pickupDateTime, returnDateTime);
  const totalAdditionals = selectedAdditionals.reduce((acc, item) => acc + item.dailyRate * item.quantity * totalDays, 0);
  const totalReservation = group ? group.dailyRate * totalDays + totalAdditionals : 0;

  const reservationData = {
    personId: user?.id,
    groupId: group?.id,
    accessoryIds: selectedAdditionals.map(additional => additional.id),
    pickupDateTime: pickupDateTime,
    returnDateTime: returnDateTime,
    totalAmount: totalReservation,
    totalDays: totalDays,
  };

  useEffect(() => {
    const sessionToken = localStorage.getItem('token');
    if (sessionToken) setToken(sessionToken);
    else navigate('/login');

    const storedUser = localStorage.getItem('user');
    if (storedUser) setUser(JSON.parse(storedUser));

    const storedCheckin = localStorage.getItem('checkin');
    if (storedCheckin) setCheckin(JSON.parse(storedCheckin));

    const storedAdditionals = localStorage.getItem('additional');
    if (storedAdditionals) setSelectedAdditionals(JSON.parse(storedAdditionals));

    const storedGroup = localStorage.getItem('group');
    if (storedGroup) setGroup(JSON.parse(storedGroup));
  }, [navigate]);

  const createReservationOnlinePayment = async () => {
    try {
      const response = await api.post('/reservation/payment/online', reservationData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      
      localStorage.setItem('newReservation', JSON.stringify(response.data));
      
      await stripePromise;

      localStorage.clear();
      window.location.assign(response.data.paymentUrl);
    } catch (error) {
      console.error("Falha ao criar reserva com pagamento online: ", error);
    }
  }

  const createReservationStorePayment = async () => {
    try {
      const response = await api.post('/reservation/payment/store', reservationData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      
      localStorage.setItem('newReservation', JSON.stringify(response.data));

      navigate('/dashboard');
    } catch (error) {
      console.error("Falha ao criar reserva com pagamento na loja: ", error);
    }
  }

  return (
    <Container>
      <Wrapper>
      <Content>
        <TitleResume>Pagamento</TitleResume>
        <div className="accordion" id="accordionExample">
          <AccordionItem>
            <h2 className="accordion-header">
              <button className="accordion-button" type="button" aria-expanded="true" aria-controls="collapseTwo">
                Pagar agora - Cartão de crédito
              </button>
            </h2>
            <div id="collapseTwo" className="accordion-collapse show" data-bs-parent="#accordionExample">
              <div className="accordion-body">
                <strong>Pagamento Online:</strong> Escolhendo esta opção, você pode realizar o pagamento de forma rápida e segura utilizando seu cartão de crédito. Insira os dados do seu cartão e conclua a transação online. Este método é conveniente e garante que sua reserva seja confirmada imediatamente.
                <br /><br />
                <button type="button" className="btn btn-primary btn-lg" onClick={() => createReservationOnlinePayment()}>
                  Pagar agora
                </button>
              </div>
            </div>
          </AccordionItem>
          <AccordionItem>
            <h2 className="accordion-header">
              <button className="accordion-button" type="button" aria-expanded="true" aria-controls="collapseThree">
                Pagar depois - No balcão da locadora
              </button>
            </h2>
            <div id="collapseThree" className="accordion-collapse show" data-bs-parent="#accordionExample">
              <div className="accordion-body">
                <strong>Pagamento Presencial na Agência:</strong> Escolhendo esta opção, você pode pagar pelo serviço diretamente na agência no momento da retirada do veículo. Este método permite que você faça a reserva agora e efetue o pagamento apenas na hora da retirada, proporcionando maior flexibilidade.
                <br /><br />
                <button type="button" className="btn btn-primary btn-lg" onClick={() => createReservationStorePayment()}>
                  Pagar depois
                </button>
              </div>
            </div>
          </AccordionItem>
        </div>
      </Content>
        <Aside>
          <TitleResume>Resumo</TitleResume>
          <AsideWrapper>
            <Group>    
              {group && (
                <>
                  <GroupImage src={group.imageURL} alt={group.name} />
                  <GroupTitle>{group.name}</GroupTitle>
                  <GroupText>{group.vehicles}</GroupText>
                </>
              )}
            </Group>
            <hr />
            <CheckIn>
              {checkin && (
                <>
                  <div>
                    <CheckInTitle>Data retirada</CheckInTitle>
                    <CheckInText>
                        <FaCalendar />{' '}{formatDate(checkin.checkInDate)} às {formatTime(checkin.checkInTime)}
                    </CheckInText>
                    <CheckInText><FaLocationDot />{' '}Aeroporto de Porto Seguro</CheckInText>
                    <hr />
                    <CheckoutTitle>Data devolução</CheckoutTitle>
                    <CheckoutText>
                        <FaCalendar />{' '}{formatDate(checkin.checkOutDate)} às {formatTime(checkin.checkOutTime)}
                    </CheckoutText>
                    <CheckoutText><FaLocationDot />{' '}Aeroporto de Porto Seguro</CheckoutText>
                  </div>
                  
                  <hr />

                  <Details>
                    <h3>Diárias</h3>
                    <ul>
                      <li>
                        <span>Total de dias: {totalDays}</span>
                      </li>
                      <li>
                        <span>Preço da diária: R$ {group?.dailyRate}</span>
                      </li>
                      <li>
                        <span>Total estimado das diárias: </span>
                        <span>R$ {(group?.dailyRate ?? 0) * totalDays}</span>
                      </li>
                    </ul>

                    <hr />

                    <CheckInTitle>Adicionais</CheckInTitle>
                    {selectedAdditionals.length > 0 ? (
                      <>
                        <ul>
                          {selectedAdditionals.map((additional) => (
                            <li key={additional.id}>
                              <span className="additional-name">{additional.name} x{additional.quantity}</span>
                              <span>R$ {additional.dailyRate * additional.quantity} x {totalDays}</span>
                              <span>R$ {(additional.dailyRate * totalDays * additional.quantity)}</span>
                            </li>
                          ))}
                        </ul>
                      </>
                    ) : (
                      <p>Nenhum adicional selecionado</p>
                    )}
                  </Details>
                </>
              )}
            </CheckIn>
            <TotalReservation>
              <Total>
                <span>Total</span>
                <span>R$ {totalReservation}</span>
              </Total>
            </TotalReservation>
          </AsideWrapper>
        </Aside>
      </Wrapper>
    </Container>
  );
}
