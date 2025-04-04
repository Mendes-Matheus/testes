import os
import tabula
import pandas as pd

# Define uma interface IDataExtractor
class IDataExtractor:
    # Método abstrato para extrair dados
    def extract_data(self):
        pass
# Classe PDFDataExtractor que implementa a interface IDataExtractor
class PDFDataExtractor(IDataExtractor):

    """
        Construtor da classe PDFDataExtractor
        Inicializa a classe com o caminho do arquivo PDF a ser processado

        :param file_path: Caminho do arquivo PDF a ser processado.
    """
    def __init__(self, file_path):
        
        self.file_path = file_path  # Armazena o caminho do arquivo PDF
        self.data = None  # Inicializa a variável que armazenará os dados extraídos

    # --------------------------------------------------------------------------------------------------- #
    
    """
        Método que extrai dados de um arquivo PDF.

        :return: Um DataFrame contendo os dados extraídos do PDF.
        :raises FileNotFoundError: Se o arquivo PDF não for encontrado.
        :raises ValueError: Se nenhum dado for extraído da tabela.
    """
    def extract_data(self):
        
        if not os.path.exists(self.file_path): # Verifica se o arquivo PDF existe
            raise FileNotFoundError(f"Arquivo '{self.file_path}' não encontrado.")
        
        self.data = tabula.read_pdf(self.file_path, pages='3-180', lattice=True) # Lê as tabelas do PDF (páginas 3 a 180)
        
        if not self.data: # Verifica se nenhum dado foi extraído
            raise ValueError("Nenhum dado foi extraído da tabela.")
        
        self.data = pd.concat(self.data, ignore_index=True) # Concatena os DataFrames extraídos em um único DataFrame
        self.data.columns = [col.replace("\r", " ") for col in self.data.columns] # Substitui quebras de linha nos nomes das colunas por espaços
        self.data = self.data.rename(columns={'OD': 'Seg. Odontologico', 'AMB': 'Seg. Ambulatorial'}) # Renomeia colunas 'OD' e 'AMB'
        
        return self.data  # Retorna o DataFrame com os dados extraídos