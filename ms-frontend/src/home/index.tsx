import { Banner } from '../components/banner';
import { Checkin } from '../components/checkin';
import { Contacts } from '../components/contact';
import { Services } from '../components/services';
import { Header } from '../components/header';
import { Footer } from '../components/footer';
import { Container, Main, Wrapper } from './style';

export function Home() {
  return (
    <Container>
      <Main>
        <Wrapper>
          <Header />
          <Checkin />
          <Banner />
          <Services />
          <Contacts />
          <Footer />
        </Wrapper>
      </Main>
    </Container>
  );
}