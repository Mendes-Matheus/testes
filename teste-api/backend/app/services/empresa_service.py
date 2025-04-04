import pandas as pd
import os
import logging

# Configuração do logging
logging.basicConfig(level=logging.INFO, format="%(asctime)s - %(levelname)s - %(message)s")

# Caminho do arquivo de dados
BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
DATA_PATH = os.path.join(BASE_DIR, '../data', 'Relatorio_cadop.csv')

# Verifica se o arquivo existe antes de prosseguir
if not os.path.exists(DATA_PATH):
    logging.error(f"Arquivo não encontrado: {DATA_PATH}")
    raise FileNotFoundError(f"Arquivo não encontrado: {DATA_PATH}")

def load_data():
    """Carrega os dados do CSV"""
    try:
        df = pd.read_csv(DATA_PATH, delimiter=';', dtype=str)
        df = df.applymap(lambda x: x.strip() if isinstance(x, str) else x)  # Remover espaços extras
        return df
    except Exception as e:
        logging.error(f"Erro ao carregar o CSV: {e}")
        return pd.DataFrame()

def get_all_companies():
    """Retorna todas as empresas ordenadas por Razão Social"""
    data = load_data()
    if data.empty:
        return []

    sorted_data = data.sort_values(by='Razao_Social')
    sorted_data = sorted_data.where(pd.notna(sorted_data), None)
    return sorted_data.to_dict(orient='records')

def search_companies(query):
    """Filtra empresas pelo nome"""
    if not query:
        return []

    data = load_data()
    if data.empty:
        return []

    logging.info(f"Buscando por: {query}")
    filtered_data = data[data['Razao_Social'].str.contains(query, case=False, na=False)]
    
    logging.info(f"Resultados encontrados: {len(filtered_data)}")
    filtered_data = filtered_data.where(pd.notna(filtered_data), None)
    return filtered_data.to_dict(orient='records')
