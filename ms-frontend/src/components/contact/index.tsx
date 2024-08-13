import { FaLocationPin } from 'react-icons/fa6';
import { Contact, Container, Map } from './style';
import { FaClock, FaMailBulk, FaPhone, FaWhatsapp } from "react-icons/fa";

export function Contacts() {
  return (
    <Container>
      <Contact>
        <div>
          <h1>Contatos</h1>
          <span />
          <p>
            <FaPhone />
            (xx) 9999-99999
          </p>
          <p>
            <FaWhatsapp />
            (xx) 9999-99999
          </p>
          <p>
            <FaMailBulk />
            rental@atendimento.com
          </p>
          <p>
            <FaLocationPin/>
            Avenida Antônio Carlos, Belo Horizonte - MG
          </p>
        </div>
        
        <div>
          <h1>Horários de funcionamento</h1>
          <span />
          <p><FaClock />24h por dia</p>
          <p><FaClock />Todos os dias da semana</p>
        </div>
      </Contact>

      <Map>
        <iframe
          title="Map"
          src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3752.093964819761!2d-43.95505472477414!3d-19.878253781498543!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0xa6909a85432e93%3A0x289190bf448a546f!2sAv.%20Pres.%20Ant%C3%B4nio%20Carlos%20-%20Belo%20Horizonte%2C%20MG!5e0!3m2!1spt-BR!2sbr!4v1721172895480!5m2!1spt-BR!2sbr"
          allowFullScreen={false}
          loading="lazy"
        ></iframe>
      </Map>

    </Container>
  );
}