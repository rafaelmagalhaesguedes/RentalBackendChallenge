import styled from "styled-components";

export const Container = styled.section`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 80vh;
  padding: 0 5rem;

  background-color: #FFFFFF;
`;

export const Contact = styled.div`
  display: flex;
  flex-direction: column;
  padding: 2rem;
  border-radius: 1rem;
  width: 30%;

  gap: 2rem;

  div {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
  }

  span {
    width: 10%;
    height: 3px;
    background-color: #333;
    margin-top: -1rem;
    margin-bottom: 0.5rem;
  }

  h1 {
    font-size: 1.5rem;
    margin-bottom: 1rem;
    color: #333;
  }

  p {
    font-size: 1rem;
    margin: 0.5rem;
    color: #333;
    
    display: flex;
    align-items: center;
    gap: 0.5rem;
  }
`;

export const Map = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.1);
  width: 50%;

  iframe {
    width: 100%;
    height: 70vh;
  }
`;