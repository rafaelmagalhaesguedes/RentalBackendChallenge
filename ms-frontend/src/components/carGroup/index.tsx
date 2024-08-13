import api from '../../api/api';
import { Timeline } from '../timeLine';
import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { GroupType } from '../../types/reservationTypes';
import { Card, CardBody, CardButton, CardGroup, CardImage, CardPrice, CardText, CardTitle, Container, Title } from './style';

export function CarGroup() {
  const navigate = useNavigate();
  const [groups, setGroups] = useState<GroupType[]>([]);

  useEffect(() => {
    const fetchGroups = async () => {
      try {
        const response = await api.get('/group');
        const sortedGroups = response.data.sort((a: GroupType, b: GroupType) => {
          const nameA = a.name.split(' ')[1];
          const nameB = b.name.split(' ')[1];
          return nameA.localeCompare(nameB);
        });
        setGroups(sortedGroups);
      } catch (error) {
        console.error('Error fetching groups:', error);
      }
    };
    fetchGroups();
  }, []);

  const groupSelection = (group: GroupType) => {
    try {
      localStorage.setItem('group', JSON.stringify(group));
      navigate('/reservation/additional');
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <Container>
      <Timeline currentStep={1} />
      <Title>Escolha o grupo de carros que melhor combina com você</Title>
      <CardGroup>
        {groups.map((group, index) => (
          <Card key={index}>
            <CardBody>
              <CardTitle>{group.name}</CardTitle>
              <CardText>{group.vehicles}</CardText>
              <CardImage src={group.imageURL} alt={group.name} />
              <CardPrice>R$ {group.dailyRate} / dia</CardPrice>
              <CardButton onClick={() => groupSelection(group)}>
                Escolha o grupo
              </CardButton>
            </CardBody>
          </Card>
        ))}
      </CardGroup>
    </Container>
  );
}