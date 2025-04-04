import pandas as pd

# Define uma interface IDataSaver
class IDataSaver:
    # Método que deve ser implementado por qualquer classe que herde de IDataSaver
    def save(self, data, file_name):
        pass

# Classe que implementa o salvamento de dados no formato CSV
class CSVDataSaver(IDataSaver):
    
    """
        Método que salva os dados em um arquivo CSV.

        :param data: Os dados a serem salvos, que devem ser um DataFrame do pandas.
        :param file_name: O nome do arquivo CSV onde os dados serão salvos.
    """
    def save(self, data, file_name):
        
        # Usa o método to_csv do pandas para salvar o DataFrame em um arquivo CSV
        # index=False significa que o índice do DataFrame não será salvo no arquivo
        # encoding='utf-8-sig' garante que o arquivo seja salvo com a codificação UTF-8 e uma assinatura (BOM)
        data.to_csv(file_name, index=False, encoding='utf-8-sig')