# Documentação da API

Esta documentação descreve a API de Controle de Gastos, que permite gerenciar pessoas, transações e transferências financeiras. A API é construída utilizando o framework Spring e segue o padrão REST.

---

## Pré-requisitos

Antes de começar, você precisará ter algumas ferramentas instaladas em sua máquina:

1. **Java Development Kit (JDK)**: A aplicação é construída em Java, então você precisará do JDK instalado. Recomenda-se a versão 11 ou superior.

- [Download do JDK](https://www.oracle.com/java/technologies/downloads/)
2. **Apache Maven**: O Maven é usado para gerenciar as dependências do projeto e construir a aplicação.

- [Download do Maven](https://maven.apache.org/download.cgi)
3. **IDE (opcional)**: Uma IDE como IntelliJ IDEA, Eclipse ou Spring Tool Suite pode facilitar o desenvolvimento e a execução da aplicação.

- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)
- [Eclipse](https://www.eclipse.org/downloads/)
- [Spring Tool Suite](https://spring.io/tools)
4. **Banco de Dados**: A aplicação pode usar um banco de dados como MySQL, PostgreSQL ou H2 (para desenvolvimento). Certifique-se de ter um banco de dados configurado e acessível.


### Passo 1: Navegar até o Diretório do Projeto

Navegue até o diretório do projeto:

```bash
cd controle_gastos/controle_gastos_backend
```

### Passo 2: Configurar o Banco de Dados

1. **Configuração do Banco de Dados**: Abra o arquivo `application.properties` (localizado em `src/main/resources`) e configure as propriedades do banco de dados. Por exemplo, para PostgreSQL:

  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/controle_gastos_db
  spring.datasource.username=seu_usuario
  spring.datasource.password=sua_senha
  spring.jpa.hibernate.ddl-auto=update
  ```

2. **Criar o Banco de Dados**:Crie um banco de dados com o nome especificado na URL de conexão.


### Passo 3: Construir o Projeto

Com o Maven instalado, você pode construir o projeto. Execute o seguinte comando no terminal:

```bash
mvn clean install
```

Esse comando irá baixar todas as dependências necessárias e compilar o projeto.

### Passo 4: Executar a Aplicação

Após a construção bem-sucedida do projeto, você pode executar a aplicação. Use o seguinte comando:

```bash
mvn spring-boot:run
```

A aplicação será iniciada e você verá logs no terminal indicando que o servidor está rodando.

### Passo 5: Acessar a API

A API estará disponível em `http://localhost:8081`. Você pode usar ferramentas como Postman ou cURL para testar os endpoints da API.

### Passo 6: Testar a Aplicação

Utilize os exemplos de requisições fornecidos abaixo para interagir com a API e verificar se tudo está funcionando corretamente.

---

# Estrutura da API

A API é dividida em três principais controladores:

1. **PessoaController**: Gerencia as operações relacionadas a pessoas.
2. **TransacaoController**: Gerencia as operações relacionadas a transações financeiras.
3. **TransferenciaController**: Gerencia as operações relacionadas a transferências entre pessoas.

---

## 1. PessoaController

### Endpoints

#### 1.1 Criar uma nova pessoa

- **POST** `/pessoa`
- **Request Body**: `PessoaSaveRequestDTO`
- **Response**: `PessoaResponseDTO`
- **Descrição**: Cria uma nova pessoa e retorna os dados da pessoa criada.

#### 1.2 Obter uma pessoa por ID

- **GET** `/pessoa/{id}`
- **Response**: `PessoaResponseDTO`
- **Descrição**: Retorna os dados de uma pessoa com base no ID fornecido.

#### 1.3 Atualizar uma pessoa

- **PUT** `/pessoa/{pessoaId}/transacoes`
- **Request Body**: `Transacao`
- **Response**: `Pessoa`
- **Descrição**: Atualiza os dados de uma pessoa com base no ID fornecido e a transação associada.

#### 1.4 Obter todas as pessoas

- **GET** `/pessoa`
- **Query Parameters**: `page`, `size`, `sort`
- **Response**: `Page<PessoaComTotaisDTO>`
- **Descrição**: Retorna uma lista paginada de todas as pessoas, incluindo totais de despesas e saldo.

#### 1.5 Deletar uma pessoa

- **DELETE** `/pessoa/{id}`
- **Response**: `204 No Content`
- **Descrição**: Deleta uma pessoa com base no ID fornecido.

#### 1.6 Obter todas as pessoas excluindo uma específica

- **GET** `/pessoa/{remetenteId}/transferencia`
- **Query Parameters**: `page`, `size`, `sort`
- **Response**: `Page<PessoaComTotaisDTO>`
- **Descrição**: Retorna uma lista paginada de todas as pessoas, excluindo a pessoa com o ID fornecido.

---

## 2. TransacaoController

### Endpoints

#### 2.1 Criar uma nova transação

- **POST** `/transacao/{pessoaId}`
- **Request Body**: `TransacaoRequestDTO`
- **Response**: `TransacaoResponseDTO`
- **Descrição**: Cria uma nova transação associada a uma pessoa e retorna os dados da transação criada.

#### 2.2 Obter todas as transações

- **GET** `/transacao`
- **Query Parameters**: `page`, `size`, `sort`
- **Response**: `Page<TransacaoResponseDTO>`
- **Descrição**: Retorna uma lista paginada de todas as transações.

#### 2.3 Deletar uma transação

- **DELETE** `/transacao/{id}`
- **Response**: `204 No Content`
- **Descrição**: Deleta uma transação com base no ID fornecido.

---

## 3. TransferenciaController

### Endpoints

#### 3.1 Realizar uma transferência

- **POST** `/transferencia/{remetenteId}/{destinatarioId}`
- **Request Body**: `TransferenciaRequestDTO`
- **Response**: `List<TransferenciaResponseDTO>`
- **Descrição**: Realiza uma transferência de valor entre duas pessoas e retorna os detalhes da transferência realizada.

---

# Exemplos de Requisições

---

## 1. PessoaController

### 1.1 Criar uma nova pessoa

- **Método**: `POST`

- **URL**: `/pessoa`

- **Cabeçalhos**:

  ```
  Content-Type: application/json
  ```

- **Corpo da Requisição**:

  ```json
  {
    "nome": "João Silva",
    "idade": 25,
    "mesada": 1500.00,
    "despesas": 500.00
  }
  ```


### 1.2 Obter uma pessoa por ID

- **Método**: `GET`
- **URL**: `/pessoa/1`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

### 1.3 Obter todas as pessoas

- **Método**: `GET`
- **URL**: `/pessoa?page=0&size=5&sort=id`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

### 1.4 Deletar uma pessoa

- **Método**: `DELETE`
- **URL**: `/pessoa/1`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

### 1.5 Obter todas as pessoas excluindo uma específica

- **Método**: `GET`
- **URL**: `/pessoa/1/transferencia?page=0&size=5&sort=id`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

---

## 2. TransacaoController

### 2.1 Criar uma nova transação

- **Método**: `POST`

- **URL**: `/transacao/1`

- **Cabeçalhos**:

  ```
  Content-Type: application/json
  ```

- **Corpo da Requisição**:

  ```json
  {
    "descricao": "Pagamento de conta de luz",
    "valor": 100.00,
    "data": "2023-10-01T10:00:00Z"
  }
  ```


### 2.2 Obter todas as transações

- **Método**: `GET`
- **URL**: `/transacao?page=0&size=5&sort=id`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

### 2.3 Deletar uma transação

- **Método**: `DELETE`
- **URL**: `/transacao/1`
- **Cabeçalhos**: Nenhum
- **Corpo da Requisição**: Nenhum

---

## 3. TransferenciaController

### 3.1 Realizar uma transferência

- **Método**: `POST`

- **URL**: `/transferencia/1/2`

- **Cabeçalhos**:

  ```
  Content-Type: application/json
  ```

- **Corpo da Requisição**:

  ```json
  {
    "valor": 300.00,
    "descricao": "Transferência para João"
  }
  ```


---