# Transformação de Dados

Este projeto permite extrair tabelas de um arquivo PDF, processar os dados, salvá-los em um arquivo CSV e compactá-los em um arquivo ZIP.

## Tecnologias Utilizadas

- **Python 3.x**
- **tabula-py** (para extração de tabelas do PDF)
- **pandas** (para manipulação de dados)
- **zipfile** (para compactação de arquivos)

## Estrutura do Projeto

```
/transformacao-de-dados
│
├── data/                          # Diretório para arquivos de dados
│   ├── Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf
│   ├── Anexo.csv                  # Será gerado após a execução do código
│   └── Teste_Matheus_Mendes.zip   # Será gerado após a execução do código
│
├── src/                           # Código fonte
|   ├── transform_data
│   |    ├── data_extractor.py      # Módulo para extração de dados do PDF
│   |    ├── data_saver.py          # Módulo para salvar os dados em CSV
│   |    ├── data_processing.py     # Módulo para processamento dos dados
│   |    └── zip_creator.py         # Módulo para criar arquivos ZIP
│   └── main.py                     # Arquivo principal para executar o código
└── .gitignore                       
```

## Instalando o Python

Caso você ainda não tenha o Python instalado no seu sistema, siga as instruções abaixo:

### Windows

1. Acesse a página de download do Python: [python.org/downloads](https://www.python.org/downloads/)
2. Baixe o instalador do Python para Windows.
3. Execute o instalador e siga as instruções na tela.
4. Após a instalação, abra o **Prompt de Comando** e digite `python --version` para verificar se a instalação foi concluída corretamente.

### macOS

1. Acesse a página de download do Python: [python.org/downloads](https://www.python.org/downloads/)
2. Baixe o instalador do Python para macOS.
3. Execute o instalador e siga as instruções na tela.
4. Após a instalação, abra o **Terminal** e digite `python3 --version` para verificar se a instalação foi concluída corretamente.

### Linux

Em sistemas Linux, o Python geralmente já está instalado. Caso não esteja, você pode instalar o Python através do terminal utilizando o seguinte comando:

```bash
sudo apt update
sudo apt install python3
```

Após a instalação, verifique se o Python foi instalado corretamente com:

```bash
python3 --version
```

## Configuração do Ambiente Virtual

Para garantir que o projeto utilize dependências isoladas, siga os passos abaixo para criar e ativar um ambiente virtual.

### Criando o ambiente virtual

No terminal, navegue até a pasta do projeto e execute:

```bash
python -m venv venv
```

ou

```bash
python3 -m venv venv
```

Isso criará um diretório `venv/` contendo o ambiente virtual.

### Ativando o ambiente virtual

- **Windows** (Prompt de Comando ou PowerShell):
  
  ```bash
  venv\Scripts\activate
  ```

- **Mac/Linux**:
  
  ```bash
  source venv/bin/activate
  ```

Se ativado corretamente, o terminal exibirá `(venv)` antes do caminho do diretório.

### Instalando as dependências

Com o ambiente virtual ativado, instale os pacotes necessários:

```bash
pip install tabula-py pandas
```

Pode ser necessário instalar o `jpype1`:

```bash
pip install jpype1
```

## Configuração do VS Code

Caso esteja usando o VS Code, siga esses passos para garantir que a IDE reconheça o ambiente virtual:

### Instale a extensão Python

Certifique-se de que a extensão oficial do Python para VS Code está instalada.

### Selecione o ambiente virtual

- Certifique-se de que está dentro do diretório do projeto e com o ambiente virtual ativo, então abra o **VS Code** pelo terminal com o comando:
```bash
code .
```
- Pressione `Ctrl+Shift+P` (Windows/Linux) ou `Cmd+Shift+P` (Mac) para abrir a paleta de comandos.
- Digite `Python: Select Interpreter` e selecione o caminho do ambiente virtual:
  - **Windows:** `venv\Scripts\python.exe`
  - **Mac/Linux:** `venv/bin/python`

## Executando o Projeto

Após configurar o ambiente virtual e instalar as dependências, navegue até o diretório /src e execute o script principal:

```bash
python main.py
```

ou

```bash
python3 main.py
```

Caso ocorra algum erro, verifique se o ambiente virtual está ativado e se todas as dependências foram instaladas corretamente.

### Desativar o Ambiente Virtual

Após concluir, desative o ambiente virtual:

```bash
deactivate
```

---