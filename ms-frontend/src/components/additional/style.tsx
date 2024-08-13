import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 80%;
`;

export const Wrapper = styled.div`
  display: flex;
  justify-content: space-around;
  width: 100%;
  height: 100%;
  margin: 6rem 0;
  gap: 2rem;
`;

export const Content = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  width: 65%;
`;

export const Title = styled.h1`
  width: 100%;
  text-align: left;
  background-color: #007bff;
  padding: 10px;
  font-size: 1.5rem;
  color: #f1f1f1;
  border-radius: 5px;
`;

export const CardAdditional = styled.div`
  display: flex;
  flex-direction: column;
  
  width: 100%;
`;

export const CardAdditionalBody = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  margin: 10px 0;
  border: 1px solid #ddd;
  padding: 1rem;
  border-radius: 5px;
`;

export const CardAdditionalBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 60%;
  gap: 5px;

  span {
    font-size: 0.8rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #666;
    font-weight: 500;
    
  }
`;

export const CardAdditionalTitle = styled.h2`
  font-size: 1rem;
  font-weight: bold;
  width: 60%;
  color: #0F2167;
  font-family: 'Lexend Deca', sans-serif;
`;

export const CardAdditionalText = styled.p`
  font-size: 16px;
  font-family: 'Lexend Deca', sans-serif;
  margin-bottom: 0;
`;

export const CardAdditionalTotal = styled.div`
  display: flex;
  align-items: center;
  gap: 10px;

  select {
    padding: 5px 10px;
    border-radius: 5px;
    border: 1px solid #ddd;

    font-family: 'Lexend Deca', sans-serif;

    option {
      padding: 5px 10px;
      border-radius: 5px;
      border: 1px solid #ddd;
    }
  }

  select:focus {
    outline: none;
  }

  select:hover {
    cursor: pointer;
  }

  span {
    font-family: 'Lexend Deca', sans-serif;
    font-size: 0.8rem;
  }

`;

export const CardProtection = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  border-radius: 5px;
  background-color: #f8f9fa;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

export const CardProtectionTitle = styled.h2`
  font-size: 1.8rem;
  font-weight: bold;
  color: #007bff;
  font-family: 'Lexend Deca', sans-serif;
`;

export const CardProtectionBody = styled.div`
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #ddd;

  div {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem 0;
    border-radius: 5px;
    background-color: #f8f9fa;

    h5 {
      font-size: 1rem;
      font-weight: bold;
      color: #38419D;
      width: 70%;
      font-family: 'Lexend Deca', sans-serif;
    }

    span {
      font-size: 0.9rem;
      font-family: 'Lexend Deca', sans-serif;
      color: #38419D;
      font-weight: 500;
    }
  }
`;

export const CardProtectionText = styled.p`
  font-size: 0.8rem;
  font-family: 'Lexend Deca', sans-serif;
  color: #200E3A;
  font-weight: 500;
  text-align: justify;
  letter-spacing: 0.2px;
`;

export const CardLink = styled.button`
  background-color: #007bff;
  color: white;
  border: none;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 8px;

  font-family: 'Lexend Deca', sans-serif;
`;

export const Aside = styled.div`
  width: 30%;
  display: flex;
  flex-direction: column;
  border-radius: 5px;
  background-color: #f8f9fa;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);

  hr {
    border: 1px solid #ddd;
  }
`;

export const TitleResume = styled.h2`
  text-align: left;
  background-color: #007bff;
  padding: 10px;
  border-radius: 5px;
  font-size: 1.5rem;
  color: #f1f1f1;
`;

export const AsideBody = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1rem;
`;

export const CardTotalResume = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 10px;
  border-radius: 5px;
`;

export const Group = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 1rem;
`;

export const GroupImage = styled.img`
  width: 100%;
  border-radius: 5px;
`;

export const GroupTitle = styled.h3`
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
`;

export const GroupText = styled.p`
  font-size: 0.9rem;
  font-family: 'Lexend Deca', sans-serif;
  color: #222;
  font-weight: 500;
`;

export const CheckIn = styled.div`
  display: flex;
  flex-direction: column;
  padding: 0 1rem;
  width: 100%;
  margin-bottom: 1rem;
`;

export const CheckInTitle = styled.h3`
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
`;

export const CheckInText = styled.p`
  font-size: 0.9rem;
  font-family: 'Lexend Deca', sans-serif;
  color: #222;
  font-weight: 500;
`;

export const CheckoutTitle = styled.h3`
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
`;

export const CheckoutText = styled.p`
  font-size: 0.9rem;
  font-family: 'Lexend Deca', sans-serif;
  color: #222;
  font-weight: 500;
`;

export const Details = styled.div`
  display: flex;
  flex-direction: column;

  h3 {
    font-size: 1.2rem;
    font-weight: bold;
    color: #007bff;
  }

  ul {
    list-style: none;
    padding: 0;
  }

  li {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 0.5rem 0;
    border-radius: 5px;
    background-color: #f8f9fa;
  }

  .additional-box {
    display: flex;
    flex-direction: column;
    gap: 5px;
  }

  .additional-name-title {
    font-size: 0.9rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #222;
    font-weight: 600;
    width: 100%;
  }

  .additional-name {
    font-size: 0.9rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #222;
    font-weight: 500;
    width: 80%;
  }

  .protection-name {
    font-size: 0.9rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #222;
    font-weight: 500;
    width: 50%;
  }

  span {
    font-size: 0.9rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #222;
    font-weight: 500;
  }

  button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 8px;
  }
`;

export const AdditionalsBody = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
`;

export const Total = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 10px;
  border-radius: 5px;
  background-color: #007bff;

  h2 {
    font-size: 1.6rem;
    font-weight: bold;
    margin: 0 0 0 0.9rem;
    color: white;
  }

  h4 {
    font-size: 0.8rem;
    font-weight: bold;
    color: #ddd;
  }
`;

export const TotalDaily = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  gap: 1rem;
  padding: 1rem;
`;

export const Box = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;

  h4 {
    font-size: 0.8rem;
    font-weight: bold;
    color: blue;
  }

  span {
    font-size: 16px;
    font-family: 'Lexend Deca', sans-serif;
    color: white;
  }
`;

export const TotalAdditionals = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  background-color: #007bff;

  h4 {
    font-size: 0.8rem;
    font-weight: bold;
  }

  .card {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
  }

  .bodyCard {
    display: flex;
    flex-direction: column;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
  }

  .bodyCardTotal {
    display: flex;
    flex-direction: row;
    border-radius: 5px;
    background-color: #007bff;
    color: white;
    gap: 1rem;
  }

  .totalSum {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }

  .buttonDelete {
    display: flex;
    justify-content: center;
  }

  span {
    font-size: 16px;
    font-family: 'Lexend Deca', sans-serif;
  }

  button {
    background-color: #007bff;
    color: white;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 8px;
  }
`;

export const TotalReservation = styled.div`
  display: flex;
  flex-direction: column;

  h2 {
    font-size: 1rem;
    font-weight: bold;
    color: white;
  }

  span {
    font-size: 2.5rem;
    font-weight: bold;
    color: white;
  }
`;

export const Continue = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 1rem;
  align-items: center;

  button {
    background-color: #fff;
    color: #000;
    border: none;
    padding: 5px 10px;
    cursor: pointer;
    border-radius: 8px;

    font-family: 'Lexend Deca', sans-serif;
    font-size: 1rem;

    height: 50px;
    width: 150px;

    display: flex;
    align-items: center;
    gap: 5px;
    justify-content: center;
  }
`;