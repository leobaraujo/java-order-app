![Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/img/banner-logo.svg)

# Java order app

> Em desenvolvimento

_java-order-app_ é uma __API REST__ criada com a linguagem de programação Java e com o framework Spring Boot 3.

A API permite que o usuário crie outros usuários, cadastro de produtos, pedidos e pagamentos parciais. O app foi imaginado para o cenário de bares e restaurantes.

[![License](https://img.shields.io/npm/l/react)](https://github.com/leobaraujo/java-order-app/blob/main/LICENSE) 

## Tecnologias

- Java 17
- Spring Boot 3
- Banco de dados H2 e MySQL
- JPA & Hibernate
- Swagger (OpenAI)
- Spring Security
- Docker

## Funcionalidades

- Autenticação
- Autorização
- JWT
- CRUD

## Arquitetura

![arquitetura](https://i.ibb.co/mbdhgMk/arquitetura.jpg)

## Diagrama Entidade-Relacionamento

![ER](https://i.ibb.co/1KH0rDh/MER.jpg)

## Execução

> Observação: Por padrão o perfil ativo é __dev__. Altere a propriedade _spring.profiles.active_ do arquivo _application.properties_ para __prod__ caso queira sair do ambiente de desenvolvimento.

### Modificando application-prod.properties para o ambiente de produção
```
# DataSource
spring.datasource.url=URL_DO_BANCO_DE_DADOS
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=USUÁRIO_DO_BANCO_DE_DADOS
spring.datasource.password=SENHA_DO_BANCO_DE_DADOS

# H2
spring.h2.console.enabled=false

# JPA
spring.jpa.hibernate.ddl-auto=update

# Security
security.jwt.secret-key=SENHA_PRIVADA_PARA_AUTENTICAÇÂO
security.admin.username=USÁRIO_ADMINISTRATIVO_PADRÂO
security.admin.password=SENHA_DO_USUÁRIO_ADMINISTRATIVO_PADRÂO
```

### Docker

> Requisitos: Docker

```shell
# Baixar a imagem do Docker Hub
docker pull leobaraujo/java-order-app:latest

# Criar um container a partir da imagem, expôr a porta 8080 e nomear o container
docker run -p 8080:8080 --name java-order-app leobaraujo/java-order-app:latest

# A aplicação já está em execução neste ponto, utilize os comandos abaixo para desligar a aplicação

# Parar o container
docker stop java-order-app

# Iniciar um container que está parado
docker start java-order-app

# Remover container
docker rm java-order-app

# Remover imagem
docker rmi leobaraujo/java-order-app:latest
```

### Git

> Requisitos: Git e Java17

```shell
# Clonar o repositório git
git clone https://github.com/leobaraujo/java-order-app.git

# Entrar na pasta do repositório
cd java-order-app

# Iniciar a aplicação
./mvnw spring-boot:run

# Parar a aplicação
Pressione a tecla CTRL+C no console
```

## Endpoints
Com a API em funcionamento, entre no endereço _http://localhost:8080/swagger-ui/index.html_
