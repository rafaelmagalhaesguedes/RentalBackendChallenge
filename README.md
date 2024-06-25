
# Rental Service Application

Sistema para gerenciar clientes, reservas, aluguéis de acessórios e grupos de carros em uma locadora de veículos.
O sistema também inclui um microserviço para o envio de e-mails utilizando filas.

## Funcionalidades Principais

- Autenticação e Autorização: Autenticação de usuários e autorização baseada em papéis (ADMIN, MANAGER).

- Gerenciamento de Reservas: Registre e gerencie reservas de acessórios para pessoas ou grupos de veículos.

- Gerenciamento de Pessoas: Cadastre, liste, atualize e exclua informações de pessoas, incluindo clientes e funcionários.

- Gerenciamento de Acessórios: Cadastre, liste, atualize e exclua acessórios disponíveis para aluguel.

- Gerenciamento de Grupos de veículos: Crie, liste, atualize e exclua grupos de veículos.

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

## Configuração do Microserviço de E-mails

### Para configurar o microserviço de envio de e-mails, siga os passos abaixo:

- Configuração do RabbitMQ: Certifique-se de ter o RabbitMQ instalado e configurado corretamente.

- Configuração do Spring Boot: Edite o arquivo application.properties para configurar as propriedades do RabbitMQ, incluindo o host, porta, nome da fila, etc.

- Execução do Microserviço: Execute o aplicativo Spring Boot que contém o microserviço de envio de e-mails. Certifique-se de que o aplicativo esteja conectado ao RabbitMQ e pronto para processar as mensagens na fila.