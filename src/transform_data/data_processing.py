# Classe responsável por orquestrar o processo de extração, salvamento e compressão de dados
class DataProcessing:
    
    """
        Construtor da classe DataProcessing.
        Inicializa a classe com os componentes necessários para o processamento de dados

        :param extractor: Instância de um extrator de dados (deve implementar IDataExtractor).
        :param saver: Instância de um salvador de dados (deve implementar IDataSaver).
        :param zip_creator: Instância de um criador de arquivos ZIP (ZipFileCreator).
    """
    def __init__(self, extractor, saver, zip_creator):
        self.extractor = extractor  # Objeto responsável por extrair dados
        self.saver = saver          # Objeto responsável por salvar dados
        self.zip_creator = zip_creator  # Objeto responsável por criar arquivos ZIP

    
    # --------------------------------------------------------------------------------------------------- #

    """
        Método que executa o processo de extração, salvamento e compressão de dados.

        :param csv_file_name: Nome do arquivo CSV onde os dados extraídos serão salvos.
        :param zip_file_name: Nome do arquivo ZIP que será criado para armazenar o arquivo CSV.
    """
    def process(self, csv_file_name, zip_file_name):
        try:
            data = self.extractor.extract_data() # Extrai os dados usando o extrator fornecido
            self.saver.save(data, csv_file_name) # Salva os dados extraídos em um arquivo CSV
            self.zip_creator.create_zip(zip_file_name, csv_file_name) # Cria um arquivo ZIP contendo o arquivo CSV
            print("Processo concluído com sucesso!") # Mensagem de sucesso após a conclusão do processo
        
        except Exception as e:
            print(f"Erro durante a execução: {e}") # Captura e imprime qualquer erro que ocorra durante o processo