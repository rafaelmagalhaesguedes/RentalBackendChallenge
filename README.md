
# Rental Service Application

Sistema para gerenciar clientes, reservas, aluguéis de acessórios e grupos de carros em uma locadora de veículos.
O sistema também inclui um microserviço para o envio de e-mails utilizando filas.

## Funcionalidades Principais

- Autenticação e Autorização: Autenticação de usuários e autorização baseada em papéis (ADMIN, MANAGER).

- Gerenciamento de Reservas: Registre e gerencie reservas de acessórios para pessoas ou grupos de veículos.

- Gerenciamento de Pessoas: Cadastre, liste, atualize e exclua informações de pessoas, incluindo clientes e funcionários.

- Gerenciamento de Acessórios: Cadastre, liste, atualize e exclua acessórios disponíveis para aluguel.

- Gerenciamento de Grupos de veículos: Crie, liste, atualize e exclua grupos de veículos.

- Integração com o Stripe: Permita que os usuários façam o pagamento das reservas utilizando cartão de crédito/débito através do Stripe.

## Microserviço de Envio de E-mails

O sistema inclui um microserviço para envio de e-mails, utilizando filas para processamento assíncrono
e garantir a entrega dos e-mails. O microserviço é responsável por:

- Envio de E-mails Assíncrono: Envio assíncrono de e-mails para evitar bloqueios durante a execução de outras tarefas.

- Tratamento de Falhas: Lidar com falhas de envio de e-mails de forma robusta e eficiente.

- Configuração com RabbitMQ: Utilização do RabbitMQ para gerenciamento de filas e garantia de entrega dos e-mails.

## Tecnologias Utilizadas

- Spring Boot: Framework para desenvolvimento de aplicativos Java.

- Spring Security: Gerenciamento de autenticação e autorização.

- Spring Data JPA: Acesso a dados utilizando o padrão JPA.

- Spring AMQP (RabbitMQ): Integração com RabbitMQ para envio de e-mails assíncronos.

- Hibernate Validator: Validação de entrada de dados.

- JavaMail Sender: Envio de e-mails utilizando o protocolo SMTP.

- Jakarta Validation: Validação de dados no lado do servidor.

- Stripe API: Integração com a API do Stripe para processamento de pagamentos.

## Configuração do Microserviço de E-mails

### Para configurar o microserviço de envio de e-mails, siga os passos abaixo:

- Configuração do RabbitMQ: Certifique-se de ter o RabbitMQ instalado e configurado corretamente.

- Configuração do Spring Boot: Edite o arquivo application.properties para configurar as propriedades do RabbitMQ, incluindo o host, porta, nome da fila, etc.

- Execução do Microserviço: Execute o aplicativo Spring Boot que contém o microserviço de envio de e-mails. Certifique-se de que o aplicativo esteja conectado ao RabbitMQ e pronto para processar as mensagens na fila.

## Integração com o Stripe

O sistema utiliza a tecnologia de integração de pagamentos Stripe para permitir que os usuários façam o pagamento das reservas de forma segura e conveniente. A integração com o Stripe inclui:

- Configuração da API do Stripe: Configuração das chaves de API do Stripe no sistema para autenticação e comunicação com o serviço.

- Implementação dos Endpoints de Pagamento: Desenvolvimento de endpoints no sistema para iniciar e processar transações de pagamento utilizando a API do Stripe.

- Segurança e Criptografia: Utilização de protocolos de segurança e criptografia para garantir a segurança das transações de pagamento e dos dados do usuário.

- Tratamento de Eventos: Implementação de mecanismos para lidar com eventos e notificações do Stripe, como confirmações de pagamento e atualizações de status.

## Endpoints

### Autenticação

- POST /auth/login: Autentica um usuário e gera um token de acesso.
    - Corpo da solicitação: { "username": "example", "password": "example" }
    - Resposta bem-sucedida: Retorna um token de acesso válido.
    - Resposta de erro: Retorna uma mensagem de erro se as credenciais forem inválidas.

### Gerenciamento de Acessórios

- GET /accessory/{id}: Obtém detalhes de um acessório específico.
    - Parâmetros de URL: id - ID único do acessório.
    - Autorização: Requer autorização de ADMIN ou MANAGER.


- GET /accessory: Obtém todos os acessórios com paginação.
    - Parâmetros de consulta: pageNumber, pageSize.
    - POST /accessory: Cria um novo acessório.
    - Corpo da solicitação: Objeto JSON com os detalhes do acessório.


- PUT /accessory/{id}: Atualiza os detalhes de um acessório existente.
    - Parâmetros de URL: id - ID único do acessório.
    - Corpo da solicitação: Objeto JSON com os novos detalhes do acessório.


- DELETE /accessory/{id}: Exclui um acessório existente.
    - Parâmetros de URL: id - ID único do acessório.

### Gerenciamento de Grupos

- GET /group/{id}: Obtém detalhes de um grupo específico.
    - Parâmetros de URL: id - ID único do grupo.
    - Autorização: Requer autorização de MANAGER.


- GET /group: Obtém todos os grupos com paginação.
    - Parâmetros de consulta: pageNumber, pageSize. 


- POST /group: Cria um novo grupo.
    - Corpo da solicitação: Objeto JSON com os detalhes do grupo.
  

- PUT /group/{id}: Atualiza os detalhes de um grupo existente.
    - Parâmetros de URL: id - ID único do grupo.
    - Corpo da solicitação: Objeto JSON com os novos detalhes do grupo.


- DELETE /group/{id}: Exclui um grupo existente.
    - Parâmetros de URL: id - ID único do grupo.

### Gerenciamento de Pessoas

- GET /persons/{id}: Obtém detalhes de uma pessoa específica.
    - Parâmetros de URL: id - ID único da pessoa.
    - Autorização: Requer autorização de ADMIN ou MANAGER.


- GET /persons: Obtém todas as pessoas com paginação.
    - Parâmetros de consulta: pageNumber, pageSize.


- POST /persons: Cria uma nova pessoa.
    - Corpo da solicitação: Objeto JSON com os detalhes da pessoa.


- PUT /persons/{id}: Atualiza os detalhes de uma pessoa existente.
    - Parâmetros de URL: id - ID único da pessoa.
    - Corpo da solicitação: Objeto JSON com os novos detalhes da pessoa.


- DELETE /persons/{id}: Exclui uma pessoa existente.
    - Parâmetros de URL: id - ID único da pessoa.

### Gerenciamento de Reservas

- GET /reservations/{id}: Obtém detalhes de uma reserva específica.
    - Parâmetros de URL: id - ID único da reserva.
    - Autorização: Requer autorização de ADMIN ou MANAGER.


- GET /reservations: Obtém todas as reservas com paginação.
    - Parâmetros de consulta: pageNumber, pageSize.


- POST /reservations: Cria uma nova reserva.
    - Corpo da solicitação: Objeto JSON com os detalhes da reserva.


- PUT /reservations/{id}: Atualiza os detalhes de uma reserva existente.
    - Parâmetros de URL: id - ID único da reserva.
    - Corpo da solicitação: Objeto JSON com os novos detalhes da reserva.


- DELETE /reservations/{id}: Exclui uma reserva existente.
    - Parâmetros de URL: id - ID único da reserva.

### Integração com o Stripe

- POST /payment: Inicia uma transação de pagamento para uma reserva específica.


- GET /payment/{id}: Retorna o status atual da transação de pagamento com o ID especificado.


- POST /success: Confirma e finaliza a transação de pagamento após a aprovação do usuário.


- POST /cancel: Cancela uma transação de pagamento em andamento.

## Screenshots

### Cria uma reserva com pagamento online

![Alt text](screenshots/createReservationOnlinePayment.png)

### Tela de pagamento Stripe

![Alt text](screenshots/stripePaymentPanel.png)

### Tela de succeso após o pagamento

![Alt text](screenshots/paymentSucces.png)

### Tela de falha caso o cliente cancele o pagamento

![Alt text](screenshots/paymentCancel.png)