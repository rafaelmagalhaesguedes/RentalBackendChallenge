package com.rental.producer;

import com.rental.controller.dto.auth.EmailDto;
import com.rental.entity.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The type User producer.
 */
@Component
public class PersonProducer {

  final RabbitTemplate rabbitTemplate;

  /**
   * Instantiates a new User producer.
   *
   * @param rabbitTemplate the rabbit template
   */
  public PersonProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  /**
   * Publish message email.
   *
   * @param person the person
   */
  public void publishMessageEmail(Person person) {
    EmailDto emailDto = new EmailDto();
    // emailDto.setUserName(person.getId());
    emailDto.setEmailTo(person.getEmail());
    emailDto.setSubject("Cadastro concluído com sucesso!");

    String emailText = String.format(
        "Querido %s,\n\n" +
            "Estamos entusiasmados em recebê-lo na Rental Cars! Seu registro foi concluído com sucesso e estamos entusiasmados por tê-lo como um membro valioso de nossa comunidade.\n\n" +
            "Aqui está o que esperar a seguir:\n\n" +
            "1. Ativação da conta:\n" +
            "Sua conta agora está ativa e pronta para uso. Você pode fazer login usando seu e-mail cadastrado e a senha que você criou durante o processo de registro.\n\n" +
            "2. Personalize sua experiência:\n" +
            "Reserve um momento para completar seu perfil adicionando mais detalhes sobre suas preferências e necessidades de viagem. Isso nos ajuda a personalizar a experiência para melhor atendê-lo.\n\n" +
            "3. Explore nossos serviços:\n" +
            "Como membro da Rental Cars, você tem acesso a uma ampla gama de serviços projetados para aprimorar sua experiência de aluguel de automóveis. Alguns deles incluem:\n" +
            "- Opções de reserva flexíveis: escolha entre uma variedade de veículos que atendem às suas necessidades, desde carros econômicos até modelos de luxo.\n" +
            "- Retirada e entrega convenientes: vários locais para tornar sua experiência de aluguel perfeita.\n" +
            "- Ofertas Especiais: Aproveite descontos e ofertas exclusivas disponíveis apenas para nossos membros registrados.\n\n" +
            "4. Fique atualizado:\n" +
            "Fique de olho em sua caixa de entrada para receber nossos boletins informativos e atualizações. Compartilharemos dicas, novidades e ofertas especiais para ajudar você a aproveitar ao máximo nossos serviços.\n\n" +
            "5. Obtenha suporte:\n" +
            "Se você tiver alguma dúvida ou precisar de ajuda, nossa equipe de suporte está aqui para ajudar. Você pode entrar em contato conosco pelo email rentalcars@email.com ou visitar o link helper da nossa Central de Ajuda.\n\n" +
            "Pronto para pegar a estrada?\n\n" +
            "Faça login agora para começar a explorar nossa frota e fazer sua primeira reserva. Obrigado por escolher a [Sua locadora de veículos]. Estamos ansiosos para atendê-lo!\n\n" +
            "Boa viagem,\n\n" +
            "Equipe de Aluguel de Carros",
        person.getFullName()
    );

    emailDto.setText(emailText);

    rabbitTemplate.convertAndSend("", routingKey, emailDto);
  }
}
