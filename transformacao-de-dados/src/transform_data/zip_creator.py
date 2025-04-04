import os
import zipfile

# Classe responsável pela criação de arquivos ZIP
class ZipFileCreator:
    
    """
        Método que cria um arquivo ZIP contendo um arquivo especificado.

        :param zip_name: Nome do arquivo ZIP que será criado.
        :param file_name: Nome do arquivo que será adicionado ao ZIP.
    """
    def create_zip(self, zip_name, file_name):
        if os.path.exists(file_name): # Verifica se o arquivo especificado existe
            with zipfile.ZipFile(zip_name, 'w') as zp: # Cria um novo arquivo ZIP com o nome especificado
                zp.write(file_name, os.path.basename(file_name)) # Adiciona o arquivo ao ZIP, usando apenas o nome base do arquivo (sem o caminho)
        else:    
            raise FileNotFoundError("O arquivo não existe.")  # Lança uma exceção FileNotFoundError se o arquivo não existir 