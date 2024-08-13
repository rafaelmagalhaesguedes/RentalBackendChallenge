import styled from 'styled-components';
import { Card as BootstrapCard } from 'react-bootstrap';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 0 auto;
  width: 80%;
`;

export const Title = styled.h1`
  text-align: center;
  margin-top: 6rem;
  color: #343a40;
`;

export const CardGroup = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-evenly;

  margin-top: 3rem;
`;

export const Card = styled(BootstrapCard)`
  background-color: #f8f9fa;
  padding: 20px;
  margin: 20px;
  width: 300px;
`;

export const CardBody = styled(BootstrapCard.Body)`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  height: 100%;
`;

export const CardTitle = styled(BootstrapCard.Title)`
  font-size: 24px;
  font-weight: bold;
  color: #343a40;
`;

export const CardText = styled(BootstrapCard.Text)`
  font-size: 16px;
  text-align: center;
  padding-top: 7px;
  color: #6c757d;
`;

export const CardImage = styled(BootstrapCard.Img)`
  margin-top: 10px;
  width: 100%;
`;

export const CardPrice = styled.p`
  font-size: 20px;
  font-weight: bold;
  margin: 1rem 0;
  color: #007bff;
`;

export const CardButton = styled(BootstrapCard.Link)`
  margin-top: 10px;
  background-color: #007bff;
  color: white;
  padding: 10px;
  border-radius: 5px;
  text-align: center;
  text-decoration: none;
  width: 100%;
  display: block;
  text-transform: uppercase;
  cursor: pointer;

  &:hover {
    text-decoration: none;
    color: white;
  }

  &:active {
    color: white;
  }
`;