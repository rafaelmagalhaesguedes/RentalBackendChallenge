import { Container, Image, SectionServices, Title } from '../services/style';

export function Services() {
  return (
    <Container>
      <SectionServices>
        <Title>
          <h1>Conheça nossa Frota</h1>
          <p>As melhores condições para você reservar e aproveitar</p>
        </Title>
        <Image>
          <img src={`https://raw.githubusercontent.com/rafaelmagalhaesguedes/CDN-GitHub/main/images/cars.png`} alt="Services" />
        </Image>
      </SectionServices>
    </Container>
  );
}