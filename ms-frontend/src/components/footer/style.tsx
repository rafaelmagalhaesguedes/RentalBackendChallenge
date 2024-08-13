import styled from 'styled-components';

export const Container = styled.section`
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  align-items: center;
  width: 100%;
  height: 10vh;

  background-color: #111;

  font-size: 0.8rem;
  color: #888;

  div {
    display: flex;
    justify-content: center;
    align-items: center;

    p {
      margin: 0 1rem;

      strong {
        color: #fff;
        font-weight: 300;
      }
    }
  }
`;
