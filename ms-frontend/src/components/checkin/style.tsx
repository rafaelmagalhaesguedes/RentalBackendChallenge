import styled from 'styled-components';
import { FaMapMarkerAlt } from 'react-icons/fa';

export const CheckinBar = styled.div`
  display: flex;
  width: 80%;
  justify-content: space-between;
  align-items: center;
  background-color: #ddd;
  padding: 1rem;
  border-radius: 0.5rem;
  gap: 1rem;

  span {
    font-family: 'Lexend Deca', sans-serif;
    font-size: 1rem;
    font-weight: 700;
    text-align: center;
    margin: 0.4rem 0;
    color: #fff;
  }
    
  .date {
    width: 100%;
    display: flex;
    align-items: center;
    gap: 1rem;
    justify-content: center;
    border-radius: 0.5rem;
  }

  input[type="text"], input[type="date"], input[type="time"] {
    border: none;
    outline: none;
    font-family: 'Lexend Deca', sans-serif;
  }

  input[type="date"] {
    border: none;
    border-radius: 0.5rem;
  }

  input[type="time"] {
    border: none;
    border-radius: 0.5rem;
  }

  input[type="submit"] {
    background-color: #151515;
    color: #fff;
    border: none;
    border-radius: 0.2rem;
    padding: 0.8rem;
    font-family: 'Lexend Deca', sans-serif;
    font-size: 1rem;
    letter-spacing: 0.5px;
    cursor: pointer;
    font-weight: 400;
  }
  
`;

export const Icon = styled(FaMapMarkerAlt)`
  position: relative;
  left: 1.8rem;
`;