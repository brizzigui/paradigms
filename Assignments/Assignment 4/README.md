# Instalação

É necessário ter instalado Python. Para instalar a versão mais recente, dirija-se a https://www.python.org/downloads/

É necessário ter instalado o banco de dados MySQL. Para instalar a versão mais recente, dirija-se a https://www.mysql.com/downloads/

Após instalar o Python, é necessário instalar o conector MySQL
```bash
pip install mysql-connector-python
```

Para utilizar o programa, digite

```bash
python main.py
```

Siga as instruções no terminal. Em caso de erro de conexão, certifique o funcionamento de seu servidor.

# Informações adicionais

- Nem todos os tipos de dados MySQL são aceitos por esse programa. Se o seu dado não for aceito, poderá ocorrer um erro. Os tipos aceitos são:

    "tinyint", "smallint", "mediumint", "int", "bigint", "float", "double", "decimal", "char", "varchar", "tinytext", "mediumtext", "longtext", "text", "date", "datetime", "timestamp", "year"

- Devido à problemas de precisão na comparação de dados em ponto flutuante, este programa ignora esses dados para a busca na seleção, atualização e deleção.