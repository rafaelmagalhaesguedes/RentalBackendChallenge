import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100vh;

  @media (max-width: 968px) {
    background-image: none;
    flex-direction: column;
  }
`;

export const Wrapper = styled.div`
  display: flex;
  gap: 1rem;
  width: 27%;
  align-items: center;
  justify-content: center;
  background-color: #fff;
  border-radius: 10px;

  @media (max-width: 968px) {
    width: 90%;
    margin-left: 0;
  }
`;

export const Content = styled.div`
  width: 100%;
  padding: 1rem;
  margin: 2rem;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #fff;

  @media (max-width: 968px) {
    margin: 1rem;
  }
`;

export const Title = styled.h1`
  color: #333;
  
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-size: 2rem;
  font-family: 'Lexend Deca', sans-serif;
  font-weight: 700;

  margin-left: 1rem;

  img {
    width: 30px;
    height: 30px;
  }

  @media (max-width: 968px) {
    font-size: 1.5rem;
    margin-bottom: 1rem;
  }
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1rem;
  border-radius: 5px;
  background-color: #fff;

  @media (max-width: 968px) {
    padding: 0;
  }

  label {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    font-family: 'Lexend Deca', sans-serif;
  }

  .password {
    display: flex;
    justify-content: space-between;
    width: 100%;

    input {
      width: 100%;
    }

    svg {
      position: absolute;
      align-self: end;
      margin-top: 1rem;
      margin-right: 0.5rem;
      cursor: pointer;
    }
  }

  input {
    padding: 0.8rem 0.8rem;
    border: 1px solid #ddd;
    border-radius: 8px;
  }

  input:focus {
    outline: none;
    border-color: #007bff;
  }

  input::placeholder {
    color: #666;
    font-size: 0.8rem;
    font-weight: 300;
    font-family: 'Lexend Deca', sans-serif;
  }

  div {
    display: flex;
    flex-direction: column;
    padding: 0 0.1rem;
    justify-content: center;
    align-items: center;

    label {
      display: flex;
      flex-direction: row;
      align-items: center;
      gap: 0.3rem;
      font-size: 0.7rem;
      font-family: 'Lexend Deca', sans-serif;
      color: #222;
      cursor: pointer;
      margin-top: 0.5rem;
    }

    a {
      color: black;
      font-size: 0.8rem;
      text-decoration: none;
      text-align: right;
      font-family: 'Lexend Deca', sans-serif;
    }

    a:hover {
      text-decoration: underline;
    }

    span {
      font-weight: 500;
      color: #007bff;
      font-family: 'Lexend Deca', sans-serif;
    }

    .forgot {
      margin-top: 0.5rem;
      color: #666;
      font-size: 0.8rem;
      font-family: 'Lexend Deca', sans-serif;
    }
  }

  @media (max-width: 968px) {
    width: 100%;

    div {
      flex-direction: column;
      gap: 0.5rem;
      justify-content: space-between;
      align-items: center;

      .checkbox {
        display: none;
      }
    }
  }

  button {
    padding: 0.6rem 0.6rem;
    border: none;
    border-radius: 10px;
    background-color: #007bff;
    color: #fff;
    cursor: pointer;
    font-size: 17px;
    font-weight: 700;
    letter-spacing: 0.8px;
    font-family: 'Lexend Deca', sans-serif;
  }

  button:hover {
    background-color: #0056b3;
  }
`;

export const Error = styled.p`
  color: red;
  font-size: 0.8rem;
  font-family: 'Lexend Deca', sans-serif;
  text-align: center;
  margin-top: 1rem;
`;
