# Teste de API

Este projeto fornece uma API para busca de empresas a partir de um arquivo CSV. Ele permite listar todas as empresas cadastradas e buscar empresas pelo nome de forma dinâmica.

## 📌 **Tecnologias Utilizadas**

### Backend:
- Python 3.x
- Flask
- Pandas

### Frontend:
- Vite
- Vue.js
- Axios

---

## 📂 **Estrutura do Projeto**

```
backend/
│── app/
│ ├── __init__.py
│ ├── routes/
│ │ ├── empresa_routes.py
│ ├── services/
│ │ ├── empresa_service.py
│ ├── data/
│ │ ├── Relatorio_cadop.csv
│── run.py
│── venv/
│── requirements.txt
frontend/
│── src/
│ ├── components/
│ │ ├── Search.vue
│ ├── App.vue
│── package.json
```

---

## 🚀 **Instalação e Execução**

### 📌 **1. Configurar o ambiente backend**

1. Clone o repositório:
   
   ```bash
   git clone https://github.com/seu-usuario/teste-api.git
   cd teste-api/backend
   ```

2. Crie um ambiente virtual e ative-o:
   
   ```bash
   python3 -m venv venv
   source venv/bin/activate  
   # No Windows: venv\Scripts\activate
   ```

3. Instale as dependências:
   
   ```bash
   pip install -r requirements.txt
   ```

4. Certifique-se de que o arquivo `Relatorio_cadop.csv` está dentro da pasta `data/`.

5. Execute a API:
   
   ```bash
   python run.py
   ```

A API estará disponível em **[http://127.0.0.1:5000](http://127.0.0.1:5000/)**.

---

### 📌 **2. Configurar o ambiente frontend**

1. Vá para a pasta do frontend:
   
   ```bash
   cd ../frontend
   ```

2. Instale as dependências do projeto Vue:
   
   ```bash
   npm install
   ```

3. Inicie o servidor frontend:
   
   ```bash
   npm run dev
   ```

O frontend estará disponível em **[http://localhost:5173](http://localhost:5173/)**.

---

## 📡 **Endpoints da API**

### 🔍 **Buscar empresas pelo nome**

```http
GET /search?query={nome}
```

- **Exemplo de requisição:**
  
  ```bash
  curl -X GET "http://127.0.0.1:5000/search?query=brad"
  ```

- **Resposta JSON:**
  
  ```json
  [
    {
      "Razao_Social": "BRADESCO S.A.",
      "CNPJ": "00.000.000/0001-91",
      "Endereço": "Rua Exemplo, 123"
    }
  ]
  ```

### 📜 **Listar todas as empresas**

```http
GET /empresas
```

- **Exemplo de requisição:**
  
  ```bash
  curl -X GET http://127.0.0.1:5000/empresas
  ```

- **Resposta JSON:**
  
  ```json
  [
    {
      "Razao_Social": "Empresa A",
      "CNPJ": "11.111.111/0001-11",
      "Endereço": "Rua ABC, 100"
    },
    {
      "Razao_Social": "Empresa B",
      "CNPJ": "22.222.222/0001-22",
      "Endereço": "Rua XYZ, 200"
    }
  ]
  ```

---

## 🛠 **Testando com Postman**

Você pode importar a coleção do Postman para testar os endpoints rapidamente.

1. Abra o Postman e vá em **Importar**.
2. Selecione o arquivo `Teste_API.postman_collection.json` que está no repositório.
3. Utilize os endpoints configurados para testar as requisições.

---

