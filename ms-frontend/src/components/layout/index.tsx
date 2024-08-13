import { Outlet } from 'react-router-dom';
import { Container } from './style';
import { Header } from '../header';
import { Footer } from '../footer';

export function Layout() {
  return (
    <Container>
      <Header />
      <Outlet />
      <Footer />
    </Container>
  )
}