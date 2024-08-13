import styled from 'styled-components';

export const Container = styled.header`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 10rem;
  background-color: #151515;
  width: 100%;
  height: 10vh;

  position: fixed;
  top: 0;
  z-index: 100;
`;

export const Logo = styled.h1`
  color: #fff;
  font-size: 2.3rem;
  font-family: 'Lexend Deca', sans-serif;

  display: flex;
  align-items: center;
  gap: 1rem;

  a {
    color: #fff;
    text-decoration: none;
  }
`;

export const NavLinks = styled.div`
  display: flex;
  align-items: center;
  gap: 2rem;
  color: #fff;
`;

export const NavLink = styled.a`
  color: #fff;
  text-decoration: none;
  font-size: 0.9rem;
  font-family: 'Lexend Deca', sans-serif;
  display: flex;
  gap: 0.5rem;

  svg {
    font-size: 1.1rem;
  }

  &:hover {
    color: #ddd;
  }
`;

export const Main = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  margin-top: 10vh;
`;