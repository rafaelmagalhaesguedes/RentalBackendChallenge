import BannerImage from '../../assets/images/banner-home.jpg';
import { Container, Image } from './style';

export function Banner() {
  return (
    <Container>
      <Image src={ BannerImage } alt="Banner" />
    </Container>
  );
}