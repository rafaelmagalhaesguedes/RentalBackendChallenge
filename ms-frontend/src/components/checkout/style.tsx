import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;

  width: 80%;
`;

export const Wrapper = styled.div`
  display: flex;
  width: 100%;
  margin: 6rem 0;

  gap: 3rem;
  justify-content: space-between;
`;

export const Content = styled.div`
  width: 70%;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  border-radius: 5px;
  background-color: #f8f9fa;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
`;

export const InputBox = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: space-between;
  gap: 1rem;

  div {
    display: flex;
    flex-direction: column;
    width: 50%;
  }
`;

export const Label = styled.label`
  font-size: 0.9rem;
  font-weight: bold;
  color: #007bff;
`;

export const Input = styled.input`
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ddd;
  font-size: 0.8rem;
`;

export const AccordionItem = styled.div`
  margin: 2rem 0;

  .accordion-header {
    button.accordion-button {
      &:not(.collapsed) {
        color: #495057;
        background-color: #e9ecef;
        box-shadow: inset 0 -0.25rem 0.25rem rgba(0, 0, 0, 0.075);
      }
    }
  }

  .accordion-collapse {
    .accordion-body {
      strong {
        font-weight: bold;
      }
    }
  }
`;

export const Aside = styled.div`
  width: 30%;
  display: flex;
  flex-direction: column;

  hr {
    border: 1px solid #ddd;
  }
`;

export const TitleResume = styled.h2`
  text-align: left;
  background-color: #007bff;
  padding: 20px;
  border-radius: 5px;
  font-size: 1.3rem;
  color: #fff;
  text-transform: uppercase;
  margin-top: 1.5rem;
`;

export const AsideWrapper = styled.div`
  display: flex;
  flex-direction: column;
  padding: 1rem;
  border-radius: 5px;
  background-color: #f8f9fa;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
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
  margin: 1rem 0;
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
  margin-bottom: 1.5rem;
`;

export const CheckInTitle = styled.h3`
  font-size: 1.2rem;
  font-weight: bold;
  color: #007bff;
  margin-bottom: 1rem;
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
  margin-bottom: 1rem;
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

  .additional-name {
    font-size: 0.9rem;
    font-family: 'Lexend Deca', sans-serif;
    color: #222;
    font-weight: 500;
    width: 50%;
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
    padding: 10px 10px;
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
    padding: 10px 10px;
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