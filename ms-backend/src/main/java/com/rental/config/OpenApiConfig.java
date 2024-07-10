package com.rental.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * Class to configure the OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig implements OpenApiCustomizer {

  @Override
  public void customise(OpenAPI openApi) {
    Info info = new Info()
        .title("Rental Service")
        .title("Rental Service")
        .description("Este projeto apresenta uma API RESTful que facilita"
            + " a gestão de um serviço de aluguel de veículos, possibilitando"
            + " aos usuários criar, visualizar, atualizar e excluir reservas de"
            + " veículos de forma intuitiva e prática. A API oferece endpoints"
            + " específicos para operações CRUD (Create, Read, Update, Delete) em veículos,"
            + " grupos de veículos, clientes e reservas, visando proporcionar uma experiência"
            + " consistente e confiável. Além de endpoints para realizar pagamentos online com"
            + " o gateway de pagamento Stripe")

        .version("1.0.0");

    openApi.info(info);
  }
}
