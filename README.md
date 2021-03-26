# Payment System

Este é um simples sistema de pagamento de fatura. 

A aplicação conta com um sistema de autenticação e autorização que utiliza JWT e baseia as permissões em Roles de usuário. 

Junto a aplicação estão as collections do Postman que serão úteis para testar a aplicação. 
Para facilitar o uso da API o Swagger foi configurado para gerar a documentação.

O Docker foi utilizado para prover as aplicações necessárias para executar a aplicação, nesse caso, apenas o banco de dados (Postgresql).

Para o versionamento da base de dados foi utilizado o Liquibase, ele é responsável por criar a estrutura inicial da base de dados e gerenciar toda a sua evolução.

## Requisitos

* Java JDK 11
* Maven
* Docker
* Docker Compose
* Git

## Como Executar

Utilizando de preferência o sistema operacional Linux, abra o Terminal. 
Na raiz do projeto (onde se encontra o arquivo pom.xml), siga os passos:

1) Execute o docker-compose para que todas as aplicações que o projeto depende fiquem operantes:

```bash
docker-compose up -d --build
```

2) Faça o build do projeto utilizando o Maven:

```bash
mvn clean install
```

3) Execute o projeto utilizando o Maven:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em: [http://localhost:8080](http://localhost:8080/)

## Instruções de Uso

Com a aplicação em execução, a documentação da API estará disponível em: http://localhost:8080/swagger-ui.html.

Para facilitar o teste da API, um arquivo de Collections e Environment do Postman está disponível no diretório "postman" na raiz do projeto. 
Faça a importação destes arquivos no Postman e execute cada Item da collection conforme os passos a seguir:

#### 1) Collection - Login ADMIN User
Esta aplicação implementa autenticação e autorização, dessa forma é necessário autenticar-se com o usuário administrador para criar as faturas;

#### 2) Collection - Create User USER
Crie um ou mais usuários USER para atribuir faturas a eles. Para criar mais de um usuário será necessário alterar os valores únicos de cada usuário: CPF, email e username;

#### 3) Collection - Create Bill
Crie uma ou mais faturas relacionada ao usuário do tipo USER criado, somente este usuário poderá pagar a fatura.

#### 4) Collection - Login User USER
Faça login com o usuário que você criou para fazer o pagamento, alterando, caso necessário, os campos de usuário e senha caso tenha utilizado outros valores;
   
#### 5) Collection - Get my Bills
Utilize o usuário logado para buscar suas faturas.

#### 6) Collection - Pay Bill
Utilize o usuário logado para pagar uma fatura. O Id da fatura que deseja pagar deverá ser informada na URL como parâmetro e só será possível pagar se a fatura esta associada ao usuário logado.
   
#### 7) Repita os passos 5 e 6 para pagar quantas faturas quiser, mas lembre-se que não é possível pagar a mesma fatura mais de uma vez.

#### 8) Collection - Login Admin User 
Faça login como Administrador novamente para verificar a lista de faturas;

#### 9) Collection - List all Bills
Verifique a lista de faturas.






