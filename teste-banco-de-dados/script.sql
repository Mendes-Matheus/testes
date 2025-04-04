-- Cria a tabela demonstracoes_contabeis para armazenar informações contábeis.
CREATE TABLE demonstracoes_contabeis (
    id SERIAL PRIMARY KEY,
    data_ref DATE,
    reg_ans INT NOT NULL,
    cd_conta_contabil VARCHAR(30),
    descricao VARCHAR(250),
    vl_saldo_inicial NUMERIC,
    vl_saldo_final NUMERIC
);

-- Cria a tabela demonstracoes_contabeis_auxiliar, que serve como uma tabela temporária 
-- para armazenar dados antes da transformaçãoos
CREATE TABLE demonstracoes_contabeis_auxiliar (
    data_ref DATE,
    reg_ans INT NOT NULL,
    cd_conta_contabil VARCHAR(30),
    descricao VARCHAR(250),
    vl_saldo_inicial VARCHAR(30),
    vl_saldo_final VARCHAR(30)
);

-- Importa os dados de demonstrações contábeis de 2023 e 2024 para a tabela demonstracoes_contabeis_auxiliar
COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/1T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/2T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/3T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/4T2023.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/1T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/2T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/3T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

COPY demonstracoes_contabeis_auxiliar(data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final) 
FROM '/tmp/postgres/teste-banco-de-dados/demonstracoes-contabeis/4T2024.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

-- As colunas vl_saldo_inicial e vl_saldo_final da tabela auxiliar 
-- contêm números no formato brasileiro (usando vírgula como separador decimal). 
-- Estes comandos substituem a vírgula por ponto, convertendo as colunas para o 
-- formato numérico adequado.
UPDATE demonstracoes_contabeis_auxiliar SET vl_saldo_inicial = REPLACE(vl_saldo_inicial, ',', '.');

UPDATE demonstracoes_contabeis_auxiliar SET vl_saldo_final = REPLACE(vl_saldo_final, ',', '.');

-- Insere os dados da tabela auxiliar na tabela demonstracoes_contabeis, 
-- fazendo a conversão de vl_saldo_inicial e vl_saldo_final de VARCHAR para NUMERIC.
INSERT INTO demonstracoes_contabeis (data_ref, reg_ans, cd_conta_contabil, descricao, vl_saldo_inicial, vl_saldo_final)
SELECT data_ref, reg_ans, cd_conta_contabil, descricao, CAST(vl_saldo_inicial AS NUMERIC), CAST(vl_saldo_final AS NUMERIC)
FROM demonstracoes_contabeis_auxiliar;

-- Adiciona a coluna despesas à tabela demonstracoes_contabeis 
-- para armazenar o cálculo de despesas.
ALTER TABLE demonstracoes_contabeis ADD COLUMN despesas NUMERIC;

-- Calcula o valor de despesas como a diferença entre o saldo final
-- e o saldo inicial, e atualiza a coluna despesas da tabela demonstracoes_contabeis.
UPDATE demonstracoes_contabeis SET despesas = vl_saldo_final - vl_saldo_inicial;

-- Cria a tabela relatorio_cadop, que contém informações cadastrais sobre empresas.
-- Cada linha é identificada por registro_ans e inclui dados como CNPJ, 
-- razão social, nome fantasia, endereço e dados de contato.
CREATE TABLE relatorio_cadop (
    registro_ans INT PRIMARY KEY,
    cnpj VARCHAR(15),
    razao_social VARCHAR(300),
    nome_fantasia VARCHAR(200),
    modalidade VARCHAR(50),
    logradouro VARCHAR(200),
    numero VARCHAR(50),
    complemento VARCHAR(200),
    bairro VARCHAR(100),
    cidade VARCHAR(50),
    uf VARCHAR(5),
    cep VARCHAR(10),
    ddd VARCHAR(5),
    telefone VARCHAR(50),
    fax VARCHAR(15),
    endereco_eletronico VARCHAR(100),
    representante VARCHAR(180),
    cargo_representante VARCHAR(100),
    regiao_de_comercializacao VARCHAR(5),
    data_registro_ans DATE
);

-- Importa os dados do arquivo Relatorio_cadop.csv para a tabela relatorio_cadop 
COPY relatorio_cadop FROM '/tmp/postgres/teste-banco-de-dados/operadoras-ativas/Relatorio_cadop.csv' DELIMITER ';' CSV HEADER ENCODING 'UTF-8';

-- 10 operadoras com maiores despesas  em "EVENTOS/ SINISTROS CONHECIDOS OU
-- AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" no último trimestre de 2024
SELECT rc.razao_social, SUM(dc.despesas) AS total_despesas
FROM relatorio_cadop rc
INNER JOIN demonstracoes_contabeis dc ON rc.registro_ans = dc.reg_ans
WHERE dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
AND dc.data_ref BETWEEN '2024-10-01' AND '2024-12-31'
GROUP BY rc.razao_social
ORDER BY total_despesas DESC
LIMIT 10;

-- 10 operadoras com maiores despesas em "EVENTOS/ SINISTROS CONHECIDOS OU
-- AVISADOS DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR" de 2024
SELECT rc.razao_social, SUM(dc.despesas) AS total_despesas
FROM relatorio_cadop rc
INNER JOIN demonstracoes_contabeis dc ON rc.registro_ans = dc.reg_ans
WHERE dc.descricao ILIKE '%EVENTOS/ SINISTROS CONHECIDOS OU AVISADOS  DE ASSISTÊNCIA A SAÚDE MEDICO HOSPITALAR%'
AND dc.data_ref BETWEEN '2024-01-01' AND '2024-12-31'
GROUP BY rc.razao_social
ORDER BY total_despesas DESC
LIMIT 10;

