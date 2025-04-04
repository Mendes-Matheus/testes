from transform_data.data_extractor import PDFDataExtractor
from transform_data.data_saver import CSVDataSaver
from transform_data.zip_creator import ZipFileCreator
from transform_data.data_processing import DataProcessing
import os

# Define o diretório onde os dados serão armazenados
data_directory = 'data'

# Caminho para o arquivo PDF que será processado
pdf_file_path = os.path.join(data_directory, 'Anexo_I_Rol_2021RN_465.2021_RN627L.2024.pdf')

# Nome do arquivo CSV onde os dados extraídos serão salvos
csv_file_name = os.path.join(data_directory, 'Anexo.csv')

# Nome do arquivo ZIP que será criado para armazenar o arquivo CSV
zip_file_name = os.path.join(data_directory, 'Teste_Matheus_Mendes.zip')

# Instancia as classes responsáveis pela extração, salvamento e criação de ZIP
pdf_extractor = PDFDataExtractor(pdf_file_path)  # Extrator de dados do PDF
csv_saver = CSVDataSaver()  # Salvador de dados em formato CSV
zip_creator = ZipFileCreator()  # Criador de arquivos ZIP

# Cria uma instância da classe de processamento de dados, que orquestra as operações
data_processor = DataProcessing(pdf_extractor, csv_saver, zip_creator)

# Verifica se o script está sendo executado como o programa principal
if __name__ == "__main__":
    # Executa o processo de extração de dados, salvamento em CSV e compressão em ZIP
    data_processor.process(csv_file_name, zip_file_name)