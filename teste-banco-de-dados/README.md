# Teste - Banco de Dados

Este repositório contém um script SQL utilizado para criar e manipular tabelas relacionadas a **demonstrações contábeis** e informações **cadastrais de operadoras de saúde**. O objetivo deste script é processar e importar dados financeiros e cadastrais para análise e geração de relatórios relacionados a despesas de operadoras de saúde.

## Estrutura do Script

O script está dividido em várias seções:

1. **Criação das Tabelas**  
   O script começa criando duas tabelas principais:
   
   - **demonstracoes_contabeis**: Armazena informações contábeis, como saldos iniciais e finais, e cálculos de despesas.
   
   - **demonstracoes_contabeis_auxiliar**: Tabela temporária usada para armazenar dados antes da transformação dos valores numéricos.
   
   - **relatorio_cadop**: Tabela que contém informações cadastrais sobre operadoras de saúde, como CNPJ, razão social, endereço e dados de contato.

2. **Importação de Dados**
   
   - São importados dados contábeis de arquivos CSV (representando os trimestres de 2023 e 2024) para a tabela auxiliar `demonstracoes_contabeis_auxiliar`.
   
   - O comando `COPY` é utilizado para importar dados para a tabela `relatorio_cadop` a partir de um arquivo CSV contendo informações cadastrais de operadoras.

3. **Transformação dos Dados**
   
   - Após a importação dos dados, as colunas `vl_saldo_inicial` e `vl_saldo_final` da tabela auxiliar são convertidas de formato string para numérico, substituindo a vírgula (utilizada no formato brasileiro) por ponto.
   
   - Os dados são então inseridos na tabela principal `demonstracoes_contabeis`, com os valores de saldo convertidos adequadamente para o tipo numérico.

4. **Cálculo de Despesas**
   
   - Uma nova coluna chamada `despesas` é adicionada à tabela `demonstracoes_contabeis` para armazenar o cálculo das despesas, que é a diferença entre o saldo final e o saldo inicial.
   
   - O cálculo de despesas é realizado por meio de um comando `UPDATE`.

5. **Consultas de Análise**
   
   - São realizadas consultas SQL para identificar as **10 operadoras com maiores despesas** em um determinado tipo de evento ("EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR") no último trimestre de 2024 e para o ano de 2024 como um todo.
   
   - A consulta faz um **JOIN** entre a tabela `relatorio_cadop` e a tabela `demonstracoes_contabeis` para agrupar os dados de despesas por operadora.

## Detalhamento das Tabelas

### Tabela: `demonstracoes_contabeis`

Armazena informações financeiras contábeis das operadoras de saúde.

| Coluna              | Tipo    | Descrição                                                    |
| ------------------- | ------- | ------------------------------------------------------------ |
| `id`                | SERIAL  | Identificador único da linha (chave primária).               |
| `data_ref`          | DATE    | Data de referência (ano e trimestre).                        |
| `reg_ans`           | INT     | Número de registro da operadora na ANS.                      |
| `cd_conta_contabil` | VARCHAR | Código da conta contábil.                                    |
| `descricao`         | VARCHAR | Descrição da conta contábil.                                 |
| `vl_saldo_inicial`  | NUMERIC | Valor do saldo inicial.                                      |
| `vl_saldo_final`    | NUMERIC | Valor do saldo final.                                        |
| `despesas`          | NUMERIC | Despesas calculadas (diferenca entre saldo final e inicial). |

### Tabela: `demonstracoes_contabeis_auxiliar`

Tabela temporária utilizada para armazenar dados antes da transformação.

| Coluna              | Tipo    | Descrição                                   |
| ------------------- | ------- | ------------------------------------------- |
| `data_ref`          | DATE    | Data de referência (ano e trimestre).       |
| `reg_ans`           | INT     | Número de registro da operadora na ANS.     |
| `cd_conta_contabil` | VARCHAR | Código da conta contábil.                   |
| `descricao`         | VARCHAR | Descrição da conta contábil.                |
| `vl_saldo_inicial`  | VARCHAR | Valor do saldo inicial (em formato string). |
| `vl_saldo_final`    | VARCHAR | Valor do saldo final (em formato string).   |

### Tabela: `relatorio_cadop`

Armazena as informações cadastrais das operadoras de saúde.

| Coluna                      | Tipo    | Descrição                                                |
| --------------------------- | ------- | -------------------------------------------------------- |
| `registro_ans`              | INT     | Número de registro da operadora na ANS (chave primária). |
| `cnpj`                      | VARCHAR | CNPJ da operadora.                                       |
| `razao_social`              | VARCHAR | Razão social da operadora.                               |
| `nome_fantasia`             | VARCHAR | Nome fantasia da operadora.                              |
| `modalidade`                | VARCHAR | Modalidade da operadora.                                 |
| `logradouro`                | VARCHAR | Logradouro do endereço.                                  |
| `numero`                    | VARCHAR | Número do endereço.                                      |
| `complemento`               | VARCHAR | Complemento do endereço.                                 |
| `bairro`                    | VARCHAR | Bairro do endereço.                                      |
| `cidade`                    | VARCHAR | Cidade do endereço.                                      |
| `uf`                        | VARCHAR | Unidade federativa (estado) do endereço.                 |
| `cep`                       | VARCHAR | Código postal (CEP) do endereço.                         |
| `ddd`                       | VARCHAR | DDD do telefone.                                         |
| `telefone`                  | VARCHAR | Número de telefone.                                      |
| `fax`                       | VARCHAR | Número de fax.                                           |
| `endereco_eletronico`       | VARCHAR | Endereço eletrônico (email).                             |
| `representante`             | VARCHAR | Nome do representante legal da operadora.                |
| `cargo_representante`       | VARCHAR | Cargo do representante.                                  |
| `regiao_de_comercializacao` | VARCHAR | Região de comercialização da operadora.                  |
| `data_registro_ans`         | DATE    | Data do registro da operadora na ANS.                    |

---

## Como Usar

1. **Pré-requisitos**:
   
   - Banco de dados PostgreSQL.
   
   - Arquivos CSV com os dados de demonstrações contábeis e relatórios CADOP.

2- **Tarefas de Preparação:**

- Baixe os arquivos dos últimos 2 anos do repositório
  público [Index of /FTP/PDA/demonstracoes_contabeis](https://dadosabertos.ans.gov.br/FTP/PDA/demonstracoes_contabeis/) 

- Baixe os Dados cadastrais das Operadoras Ativas na ANS no formato CSV em [Index of /FTP/PDA/operadoras_de_plano_de_saude_ativas](https://dadosabertos.ans.gov.br/FTP/PDA/operadoras_de_plano_de_saude_ativas/)
3. **Execução do Script**:
   
   - **Passo 1: Copiar os arquivos CSV para o diretório apropriado**
     
     Como os arquivos CSV estão anexados ao projeto, copie-os para o diretório `/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/` para os dados contábeis e `/tmp/postgres/teste-banco-de-dados/operadoras-ativas/` para os dados cadastrais.
     
     **Para sistemas Unix (Linux/macOS), utilize o comando:**
     
     ```bash
     cp arquivos/*.csv /tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/
     cp arquivos/Relatorio_cadop.csv /tmp/postgres/teste-banco-de-dados/operadoras-ativas/
     ```
     
     **Para sistemas Windows, copie os arquivos para o diretório e modifique o caminho dos arquivos na query**
     
     **Importante**: Certifique-se de que o caminho do diretório de destino seja o mesmo indicado no script. Se necessário, ajuste os caminhos de origem e destino conforme a estrutura do seu projeto.
   
   - **Passo 2: Execute o script SQL**
     
     Após copiar os arquivos, execute o script SQL em seu ambiente PostgreSQL para criar as tabelas e importar os dados.
     
     - O script irá criar as tabelas, importar os arquivos CSV, realizar as transformações de dados e gerar os relatórios.

4. **Consultas**:
   
   - O script inclui consultas para gerar relatórios de operadoras com as maiores despesas no último trimestre de 2024 e em todo o ano de 2024.
