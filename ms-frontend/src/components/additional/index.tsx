import api from '../../api/api';
import { Timeline } from '../timeLine';
import { useNavigate } from 'react-router-dom';
import { FaLocationDot } from 'react-icons/fa6';
import { getTotalDays } from '../../util/getTotalDays';
import { AuthContext } from '../../context/authContext';
import { useContext, useEffect, useState } from 'react';
import { formatDate, formatTime } from '../../util/formatDate';
import { FaArrowCircleRight, FaCalendar, FaTrash } from 'react-icons/fa';
import { AdditionalType, CheckInType, GroupType } from '../../types/reservationTypes';
import { Aside, CardAdditional, CardAdditionalBody, CardAdditionalBox, CardAdditionalText, CardAdditionalTitle,
  CardAdditionalTotal, CardLink, CheckIn, CheckInText, CheckInTitle, CheckoutText, CheckoutTitle, Container,
  Content, Continue, Details, Group, GroupImage, GroupText, GroupTitle, Title, TitleResume, Total, TotalReservation, Wrapper
} from './style';

export function Additional() {
  const { signed } = useContext(AuthContext);
  const [additionals, setAdditionals] = useState<AdditionalType[]>([]);
  const [selectedAdditionals, setSelectedAdditionals] = useState<AdditionalType[]>([]);
  const [group, setGroup] = useState<GroupType | null>(null);
  const [checkin, setCheckin] = useState<CheckInType | null>(null);
  const [quantities, setQuantities] = useState<{ [key: number]: number }>({});
  const navigate = useNavigate();

  const totalDays = getTotalDays(checkin?.checkInDate, checkin?.checkOutDate);

  const totalAdditionals = () => {
    return selectedAdditionals.reduce((acc, item) => {
      const totalCost = item.dailyRate * item.quantity * totalDays;
      return acc + totalCost;
    }, 0);
  };
  
  const totalAmount = group ? group.dailyRate * totalDays + totalAdditionals() : 0;

  const fetchAdditionals = async () => {
    try {
      const response = await api.get('/accessory');
      setAdditionals(response.data);
    } catch(error) {
      console.error(error);
    }
  };

  useEffect(() => {
    const storedCheckin = localStorage.getItem('checkin');
    if (storedCheckin) setCheckin(JSON.parse(storedCheckin));

    const storedGroup = localStorage.getItem('group');
    if (storedGroup) setGroup(JSON.parse(storedGroup));

    fetchAdditionals();
  }, []);

  const handleAdditionalSelection = (additional: AdditionalType) => {
    const selectedQuantity = quantities[additional.id] || 1;
  
    const existingAdditional = selectedAdditionals.find(item => item.id === additional.id);
  
    if (!existingAdditional) {
      const updatedAdditionals = [...selectedAdditionals, { ...additional, quantity: selectedQuantity }];
      setSelectedAdditionals(updatedAdditionals);
      localStorage.setItem('additional', JSON.stringify(updatedAdditionals));
    } else {
      const updatedAdditionals = selectedAdditionals.map(item =>
        item.id === additional.id ? { ...item, quantity: selectedQuantity } : item
      );
      setSelectedAdditionals(updatedAdditionals);
      localStorage.setItem('additional', JSON.stringify(updatedAdditionals));
    }
  };  

  const handleRemoveAdditional = (id: number) => {
    const updatedAdditionals = selectedAdditionals.filter(item => item.id !== id);
    setSelectedAdditionals(updatedAdditionals);
    localStorage.setItem('additional', JSON.stringify(updatedAdditionals));
  };

  const handleQuantityChange = (event: React.ChangeEvent<HTMLSelectElement>, additionalId: number) => {
    const newQuantity = Number(event.target.value);
    setQuantities((prevQuantities) => ({
      ...prevQuantities,
      [additionalId]: newQuantity,
    }));

    const existingAdditional = selectedAdditionals.find(item => item.id === additionalId);
    if (existingAdditional) {
      handleAdditionalSelection(existingAdditional);
    }
  };

  const redirectToCheckout = (isLoggedIn: boolean, navigate: Function) => {
    if (isLoggedIn) {
      window.location.href = '/checkout';
    } else {
      navigate('/login');
    }
  };

  return (
    <Container>
      <Timeline currentStep={2} />
      <Wrapper>
        <Content>
          <CardAdditional>
            <Title>Adicionais</Title>
            {additionals.map((additional) => (
              <CardAdditionalBody key={additional.id}>
                <CardAdditionalBox>
                  <CardAdditionalTitle>{additional.name}</CardAdditionalTitle>
                  <span>{additional.description}</span>
                </CardAdditionalBox>
                <CardAdditionalText>R$ {additional.dailyRate} / dia</CardAdditionalText>
                {additional.name !== 'Additional Driver' && (
                  <CardAdditionalTotal>
                    <span>Quantidade:</span>
                    <select value={quantities[additional.id] || 1} onChange={(e) => handleQuantityChange(e, additional.id)}>
                      <option value="1">1</option>
                      <option value="2">2</option>
                    </select>
                  </CardAdditionalTotal>
                )}
                <CardLink onClick={() => handleAdditionalSelection(additional)}>Adicionar</CardLink>
              </CardAdditionalBody>
            ))}
          </CardAdditional>
          <CardAdditional>
            <Title style={ { textAlign: 'center' } }>As proteções do veículo já estão inclusas no valor da diária</Title>
            </CardAdditional>
        </Content>

        <Aside>
          <TitleResume>Resumo</TitleResume>

          <Group>
            {group && (
              <>
                <GroupImage src={group.imageURL} alt={group.name} />
                <GroupTitle>{group.name}</GroupTitle>
                <GroupText>{group.vehicles}</GroupText>
              </>
            )}
          </Group>

          <CheckIn>
            <hr />
            {checkin && (
              <>
                <CheckInTitle>Retirada</CheckInTitle>
                <CheckInText>
                  <FaCalendar />{' '}{formatDate(checkin.checkInDate)}, às {formatTime(checkin.checkOutTime)}
                </CheckInText>
                <CheckInText><FaLocationDot />{' '}Aeroporto de Porto Seguro</CheckInText>
                <hr />
                <CheckoutTitle>Devolução</CheckoutTitle>
                <CheckoutText>
                  <FaCalendar />{' '}{formatDate(checkin.checkOutDate)}, às {formatTime(checkin.checkOutTime)}
                </CheckoutText>
                <CheckoutText><FaLocationDot />{' '}Aeroporto de Porto Seguro</CheckoutText>
                <hr />
                <Details>
                  <h3>Detalhes</h3>
                  <ul>
                    <li>
                      <span><strong>Total de diárias: </strong>{totalDays}</span>
                    </li>
                    <li>
                      <span><strong>Preço do grupo por dia: </strong>R$ {group?.dailyRate}</span>
                    </li>
                    <li>
                      <span><strong>Total: </strong>R$ {(group?.dailyRate ?? 0) * totalDays}</span>
                    </li>
                  </ul>
                  <hr />

                  <CheckInTitle>Adicionais</CheckInTitle>
                  {selectedAdditionals.length > 0 ? (
                    <>
                      <ul>
                        {selectedAdditionals.map((additional) => {
                          const totalCost = additional.dailyRate * additional.quantity * totalDays;
                          return (
                            <li key={additional.id}>
                              <div className="additional-box">
                                <span className="additional-name-title">{additional.name} x{additional.quantity}</span>
                                <span className="additional-name">R$ {additional.dailyRate} /dia</span>
                              </div>
                              <div className="additional-box">
                                <span className="additional-total">R$ {totalCost}</span>
                              </div>
                              <div className="additional-box">
                                <span className="additional-button"><button onClick={() => handleRemoveAdditional(additional.id)}><FaTrash /></button></span>
                              </div>
                            </li>
                          );
                        })}
                      </ul>
                    </>
                  ) : (
                    <CheckInText>Sem adicionais</CheckInText>
                  )}
                </Details>
              </>
            )}
          </CheckIn>

          <Total>
            <TotalReservation>
              <h2>Total estimado</h2>
              <span>R$ {totalAmount}</span>
            </TotalReservation>
            <Continue>
              <button onClick={() => redirectToCheckout(signed, navigate)}>
                Continuar {' '} <FaArrowCircleRight />
              </button>
            </Continue>
          </Total>
        </Aside>
      </Wrapper>
    </Container>
  );
}
