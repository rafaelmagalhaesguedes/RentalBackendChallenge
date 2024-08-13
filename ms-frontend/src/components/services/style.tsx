import styled from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

export const SectionServices = styled.section`
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 2rem;
  align-items: center;
  width: 100%;
  height: 80vh;
`;

export const Title = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  margin-bottom: 5rem;

  h1 {
    font-size: 2rem;
    color: #ddd;
    margin-bottom: 1rem;
  }

  p {
    font-size: 1rem;
    color: #ddd;
  }
`;

export const Image = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;

  img {
    width: 100%;
  }
`;
