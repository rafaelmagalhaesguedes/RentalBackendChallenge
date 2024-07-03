
# Rental Service Application

Este projeto engloba uma API robusta para gerenciamento de clientes, reservas, aluguel de acessórios e
grupos de carros dentro de uma locadora de veículos. Possui integração perfeita com o gateway de pagamento
Stripe para transações online seguras. Além disso, o sistema inclui uma arquitetura de microsserviços para
comunicação eficiente por e-mail, aproveitando filas de mensagens para maior confiabilidade e escalabilidade.

## Funcionalidades Principais

- Autenticação e Autorização: Autenticação de usuários e autorização baseada em papéis (ADMIN, MANAGER).

- Gerenciamento de Reservas: Registro e gerenciamento de reservas de acessórios para pessoas ou grupos de veículos.

- Gerenciamento de Pessoas: Cadastro, listagem, atualização e exclusão de informações de pessoas, incluindo clientes e funcionários.

- Gerenciamento de Acessórios: Cadastro, listagem, atualização e exclusão de acessórios disponíveis para aluguel.

- Gerenciamento de Grupos de veículos: Criação, listagem, atualização e exclusão de grupos de veículos.

- Integração com o Stripe: Permite que os usuários façam o pagamento das reservas utilizando cartão de crédito/débito através do Stripe.

## Microserviço para Envio de E-mails

O sistema inclui um microserviço para envio de e-mails, utilizando filas para processamento assíncrono
e garantir a entrega dos e-mails. O microserviço é responsável por:

- Envio de E-mails Assíncrono: Envio assíncrono de e-mails para evitar bloqueios durante a execução de outras tarefas.

- Tratamento de Falhas: Lidar com falhas de envio de e-mails de forma robusta e eficiente.

- Configuração com RabbitMQ: Utilização do RabbitMQ para gerenciamento de filas e garantia de entrega dos e-mails.

- Utiliza a Cloud AMQP para fornecer uma infraestrutura de mensageria hospedada na nuvem, garantindo alta disponibilidade, escalabilidade e desempenho para o sistema de filas do RabbitMQ.

## Tecnologias Utilizadas

- Spring Boot: Framework para desenvolvimento de aplicativos Java.

- Spring Security: Gerenciamento de autenticação e autorização.

- Spring Data JPA: Acesso a dados utilizando o padrão JPA.

- Spring AMQP (RabbitMQ): Integração com RabbitMQ para envio de e-mails assíncronos.

- Hibernate Validator: Validação de entrada de dados.

- JavaMail Sender: Envio de e-mails utilizando o protocolo SMTP.

- Jakarta Validation: Validação de dados no lado do servidor.

- Stripe API: Integração com a API do Stripe para processamento de pagamentos.

- Springdoc OpenAPI: API documentada com o Springdoc OpenAPI Swagger

- Docker: Os serviços usam container docker para seu ciclo de vida.

## Integração com o Stripe

O sistema utiliza a tecnologia de integração de pagamentos Stripe para permitir que os usuários façam o pagamento das reservas de forma segura e conveniente. A integração com o Stripe inclui:

- Configuração da API do Stripe: Configuração das chaves de API do Stripe no sistema para autenticação e comunicação com o serviço.

- Implementação dos Endpoints de Pagamento: Desenvolvimento de endpoints no sistema para iniciar e processar transações de pagamento utilizando a API do Stripe.

- Segurança e Criptografia: Utilização de protocolos de segurança e criptografia para garantir a segurança das transações de pagamento e dos dados do usuário.

- Tratamento de Eventos: Implementação de mecanismos para lidar com eventos e notificações do Stripe, como confirmações de pagamento e atualizações de status.

## Rodando a Aplicação com Docker

### Pré-requisitos

Certifique-se de ter o Docker instalado em sua máquina.

1. Clone o Repositório
2. Configure as variáveis de ambiente se necessário
3. Construa as imagens e suba os containers:
  ```
  docker-compose up -d
  ```
4. Acesse a aplicação em http://localhost:8080

## Documentação do Swagger

Este projeto utiliza Swagger para documentar seus endpoints de API, facilitando o entendimento e teste das funcionalidades disponíveis.

### Acessando o Swagger

Para acessar a documentação do Swagger, navegue até [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) após executar o aplicativo.

### Endpoints Principais

#### Autenticação

- **Login:**
  - **POST /auth/login**
  - Request Body:
    ```json
    {
      "email": "user@email.com",
      "password": "password123"
    }
    ```
  - Response:
    ```json
    {
      "token": "jwt-token"
    }
    ```

#### Usuários

- **Criar Usuário:**
  - **POST /persons**
  - Request Body:
    ```json
    {
      "fullName": "User Test", 
      "username": "user123",
      "email": "user@example.com",
      "password": "password123",
      "role": "USER"
    }
    ```
  - Response:
    ```json
    {
      "id": "uuid",
      "fullName": "User Test",
      "username": "user123",
      "email": "user@example.com",
      "role": "USER"
    }
    ```

#### Reservas

- **Listar Reservas:**
  - **GET /reservations**
  - Query Parameters:
    - `pageNumber`: Número da página (padrão: 0)
    - `pageSize`: Tamanho da página (padrão: 10)
    - Response:
    ```json
     [
      {
        "id": "uuid",
        "person": {
          "id": "uuid",
          "fullName": "Rafael Magalhães Guedes",
          "username": "rafaguedes",
          "email": "user@example.com",
          "role": "USER"
        },
        "group": {
          "id": "uuid",
          "name": "Group GX",
          "vehicles": "Renegade, Creta, Duster, Toro ou similares",
          "dailyRate": 250.0
        },
        "accessories": [
          {
            "id": "uuid",
            "name": "GPS",
            "description": "SmartPhone com GPS",
            "quantity": 1,
            "dailyRate": 100.0
          },
          {
            "id": "uuid",
            "name": "Bebê conforto",
            "description": "Comfort plus ergonomic top",
            "quantity": 1,
            "dailyRate": 100.0
          }
        ],
        "pickupDateTime": "2024-10-01T08:00:00",
        "returnDateTime": "2024-11-05T10:00:00",
        "totalAmount": 15750.0,
        "totalDays": 35,
        "reservationStatus": "PENDING",
        "paymentType": "Online",
        "createdDate": "2024-06-30T20:28:30.783332566",
      }
     ]
    ```

- **Criar Reserva:**
  - **POST /reservations**
  - Request Body:
    ```json
    {
      "personId": "uuid",
      "groupId": "uuid",
      "accessoryIds": ["uuid", "uuid"],
      "pickupDateTime": "2024-10-01T08:00:00",
      "returnDateTime": "2024-11-05T10:00:00",
      "totalAmount": 15750.00,
      "paymentType": "Online"
    }
    ```
  - Response:
    ```json
    {
      "id": "uuid",
      "person": {
        "id": "uuid",
        "fullName": "Rafael Magalhães Guedes",
        "username": "rafaguedes",
        "email": "user@example.com",
        "role": "USER"
      },
      "group": {
        "id": "uuid",
        "name": "Group GX",
        "vehicles": "Renegade, Creta, Duster, Toro ou similares",
        "dailyRate": 250.0
      },
      "accessories": [
        {
          "id": "uuid",
          "name": "GPS",
          "description": "SmartPhone com GPS",
          "quantity": 1,
          "dailyRate": 100.0
        },
        {
          "id": "uuid",
          "name": "Bebê conforto",
          "description": "Comfort plus ergonomic top",
          "quantity": 1,
          "dailyRate": 100.0
        }
      ],
      "pickupDateTime": "2024-10-01T08:00:00",
      "returnDateTime": "2024-11-05T10:00:00",
      "totalAmount": 15750.0,
      "totalDays": 35,
      "reservationStatus": "PENDING",
      "paymentType": "Online",
      "createdDate": "2024-06-30T20:28:30.783332566",
      "paymentUrl": "https://checkout.stripe.com/c/pay/cs_test_a1DWuVtXutyIcEyCPFOK2XiLoixgdRP7snfmN5fSmbsGleR0aWIwJIvseY#fidkdWxOYHwnPyd1blpxYHZxWjA0SmtVTWFCaT1WXHY8X2BnX0dIQ31pT0o8cTZVS3BRSUNqYTdAd2N0dDdoQmRWdjBOazRUU2tvSWtEV21Ka1Vyc3Q2XDZ%2FbUNvQGhLPVw2QENtQHdIdElDNTVhfFV9a21SQCcpJ2N3amhWYHdzYHcnP3F3cGApJ2lkfGpwcVF8dWAnPyd2bGtiaWBabHFgaCcpJ2BrZGdpYFVpZGZgbWppYWB3dic%2FcXdwYHgl"
    }
    ```