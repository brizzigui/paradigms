import math
import mysql.connector as sql
import io
import random

def create_connection(user, password, database_name):
    # creates db connection with args info
    return sql.connect(host="localhost", user=user, password=password, database=database_name)


def open_target_file(name: str) -> io.TextIOWrapper:
    # creates file in auxiliary folder
    return io.open(f"./target/{name}.py", "w", encoding="utf-8")


def write_entity_classes(run):
    run.execute("SHOW TABLES;")
    tables = list()
    for element in run:
        tables.append(element[0])


    for table in tables:
        target = open_target_file(table + "_entity")
        target.write(f"class {table}_entity:\n")
        target.write("\n")

        run.execute(f"DESCRIBE {table};")
        for element in run:
            target.write(f"    def set_{element[0]}(self, {element[0]}):\n")
            target.write(f"        self.{element[0]} = {element[0]}\n")
            target.write("\n")
            target.write(f"    def get_{element[0]}(self):\n")
            target.write(f"        return self.{element[0]}\n")
            target.write("\n")

        target.close()


def write_insert(variables, table, target):
    target.write("    @staticmethod\n")
    target.write(f"    def insert_{table}(run, {table}):\n")


    variables_string = ', '.join(variables)
    variables_print = ', '.join([f"{table}.get_{element}()" for element in variables])
    placeholders = ', '.join(['%s' for _ in variables])

    target.write(f"        add_{table} = (\"") 
    target.write(f"INSERT INTO {table} ({variables_string}) VALUES ({placeholders});\")\n\n")
    target.write(f"        variables = [{variables_print}]\n\n")

    target.write(f"        run.execute(add_{table}, variables)\n\n")


def write_delete(variables, table, target):
    target.write("    @staticmethod\n")
    target.write(f"    def delete_{table}(run, {table}):\n")
    
    variables_string = ', '.join(f"{variable} = %s" for variable in variables)
    variables_print = ', '.join([f"{table}.get_{element}()" for element in variables])
    variables_and = ' AND '.join([f"{variable} = %s" for variable in variables])

    target.write(f"        check = (\"SELECT 1 FROM {table} WHERE {variables_and}\")\n")
    target.write(f"        varcheck = [{variables_print}]\n\n")
    target.write("        run.execute(check, varcheck)\n\n")

    target.write("        if not run.fetchone():\n")
    target.write("            print(\"This value does not exist in the table\")\n")
    target.write("            return None\n\n")

    target.write("        for _ in run:\n")
    target.write("            pass # debugs the command\n")

    target.write(f"        delete_{table} = (\"")
    target.write(f"DELETE FROM {table} WHERE {variables_and};\")\n\n")
    target.write(f"        variables = [{variables_print}]\n\n")

    target.write(f"        run.execute(delete_{table}, variables)\n\n")

    
def write_update(variables, table, target):
    target.write("    @staticmethod\n")
    target.write(f"    def update_{table}(run, old_{table}, new_{table}):\n")
    
    variables_string = ', '.join(f"{variable} = %s" for variable in variables)
    variables_and = ' AND '.join([f"{variable} = %s" for variable in variables])

    new_variables_print = ', '.join([f"new_{table}.get_{element}()" for element in variables])
    old_variables_print = ', '.join([f"old_{table}.get_{element}()" for element in variables])
    variables_print = ', '.join([new_variables_print, old_variables_print])

    target.write(f"        check = (\"SELECT 1 FROM {table} WHERE {variables_and}\")\n")
    target.write(f"        varcheck = [{old_variables_print}]\n\n")
    target.write("        run.execute(check, varcheck)\n\n")

    target.write("        if not run.fetchone():\n")
    target.write("            print(\"This value does not exist in the table\")\n")
    target.write("            return None\n\n")

    target.write(f"        update_{table} = (\"")
    target.write(f"UPDATE {table} SET {variables_string} WHERE {variables_and};\")\n\n")
    target.write(f"        variables = [{variables_print}]\n\n")

    target.write(f"        run.execute(update_{table}, variables)\n\n")


def write_select(table, target, variables, comparable):
    target.write("    @staticmethod\n")
    target.write(f"    def select_{table}(run, {table}):\n")

    variables_and = ' AND '.join([f"{variable} = %s" for variable in comparable])
    variables_print = ', '.join([f"{table}.get_{element}()" for element in comparable])

    target.write(f"        select_{table} = (\"SELECT * FROM {table} WHERE {variables_and};\")\n\n")
    target.write(f"        variables = [{variables_print}]\n\n")

    target.write(f"        run.execute(select_{table}, variables)\n\n")

    target.write("        rows = run.fetchall()\n\n")

    target.write("        if not rows:\n")
    target.write("            return None\n\n")
    
    target.write("        for row in rows:\n")
    target.write(f"            new_{table} = {table}_entity()\n\n")
    for i, variable in enumerate(variables):
        target.write(f"            new_{table}.set_{variable}(row[{i}])\n")
    
    target.write(f"\n        return new_{table}\n\n")


def write_select_all(table, target, variables):
    target.write("    @staticmethod\n")
    target.write(f"    def select_all_{table}(run):\n")

    target.write(f"        select_all_{table} = (\"SELECT * FROM {table};\")\n\n")

    target.write(f"        run.execute(select_all_{table})\n\n")    

    target.write("        rows = run.fetchall()\n")
    target.write(f"        {table}_list = []\n\n")

    target.write("        if not rows:\n")
    target.write("            return None\n\n")

    target.write("        for row in rows:\n")
    target.write(f"            new_{table} = {table}_entity()\n\n")
    for i, variable in enumerate(variables):
        target.write(f"            new_{table}.set_{variable}(row[{i}])\n")
    target.write(f"            {table}_list.append(new_{table})\n\n")

    target.write(f"        return {table}_list\n\n")
    

def write_dao_classes(run):

    run.execute("SHOW TABLES")
    tables = list()
    for element in run:
        tables.append(element[0])

    for table in tables:
        target = open_target_file(table + "_DAO")
        target.write(f"from {table}_entity import *\n")
        target.write(f"class {table}_DAO:\n")
        target.write("\n")

        run.execute(f"DESCRIBE {table};")
        variables = [element[0] for element in run]

        run.execute(f"DESCRIBE {table};")
        comparable_variables = [element[0] for element in run if all(str(element[1]).find(sub) == -1 for sub in ["float", "double", "decimal"])]

        write_insert(variables, table, target)

        write_delete(comparable_variables, table, target)

        write_update(comparable_variables, table, target)

        write_select(table, target, variables, comparable_variables)
        
        write_select_all(table, target, variables)


        target.close()


def write_basic_python_functions(target: io.TextIOWrapper, table, user, password, database_name) -> None:
    target.write("import mysql.connector as sql\n")
    target.write(f"from {table}_entity import *\n")
    target.write(f"from {table}_DAO import *\n")
    target.write("\n")
    target.write("def main():\n")
    target.write(f"    db = sql.connect(host='localhost', user='{user}', password='{password}', database='{database_name}')\n")
    target.write(f"    db.autocommit = True\n") # avoids problems with database modification
    target.write(f"    run = db.cursor()\n")

    target.write("\n\n")


def write_instantiate_entity(target: io.TextIOWrapper, table, run, entity_name = "entity") -> None:
    target.write(f"    {entity_name} = {table}_entity()\n")
    run.execute(f"DESCRIBE {table}")
    for column in run:
        # generates different random data based on type.
        # only fully supported datatypes for the examplo are the ones here.
        # using other types may result in unexpected errors.

        if column[1] in ["tinyint", "smallint", "mediumint", "int", "bigint"]:
            arg = str(random.randint(-100, 100))

        elif column[1] in ["float", "double"]:
            arg = float(f"{random.randint(1, 9)}.{random.randint(1, 9)}")

        elif str(column[1]).find("decimal") != -1:
            arg = math.pi

        elif any(str(column[1]).find(element) != -1 for element in ["char", "varchar", "tinytext", "mediumtext", "longtext", "text"]):
            arg = "'x'"

        elif column[1] == "date":
            arg = f"'{random.randint(2000, 2030)}-{random.randint(1, 12)}-{random.randint(1, 28)}'"

        elif column[1] == "datetime" or column[1] == "timestamp":
            arg = f"'{random.randint(2000, 2030)}-{random.randint(1, 12)}-{random.randint(1, 28)} {random.randint(0, 23)}:{random.randint(0, 59)}:{random.randint(0, 59)}'"

        elif column[1] == "year":
            arg = str(random.randint(2000, 2030))

        else: # if unknown type is used
            arg = 0
            print("Um tipo não suportado foi usado. Isso poderá causar erros.")

        target.write(f"    {entity_name}.set_{column[0]}({arg})\n")

    
def write_instantiate_dao(target: io.TextIOWrapper, table) -> None:
    target.write(f"    dao = {table}_DAO()\n")

def write_insert_thru_dao(target: io.TextIOWrapper, table) -> None:
    target.write(f"    dao.insert_{table}(run, entity)\n")

def write_update_thru_dao(target: io.TextIOWrapper, table) -> None:
    target.write(f"    dao.update_{table}(run, entity, new_entity)\n")

def write_select_thru_dao(target: io.TextIOWrapper, table) -> None:
    target.write(f"    result = dao.select_{table}(run, entity)\n")

def write_delete_thru_dao(target: io.TextIOWrapper, table) -> None:
    target.write(f"    dao.delete_{table}(run, new_entity)\n")

def write_print_demo(target: io.TextIOWrapper, variables: list) -> None:  
    for var in variables:
        target.write(f"    print(f'| {var} = {{result.get_{var}()}} |')\n")

def write_select_all_thru_dao(target: io.TextIOWrapper, table, variables: list) -> None:
    target.write(f"    result = dao.select_all_{table}(run)\n")
    target.write("    if result == None:\n")
    target.write("        print('Vazia')\n")
    target.write("\n")
    target.write("    else:\n")

    target.write(f"        for element in result:\n")
    for var in variables:
        target.write(f"            print(f'| {var} = {{element.get_{var}()}} |')\n")

   

def write_closing_python_funtions(target: io.TextIOWrapper) -> None:
    target.write(f'if __name__ == "__main__":\n')
    target.write(f'    main()\n')


def write_example(run, user, password, database_name) -> None:
    run.execute("SHOW TABLES;")
    tables = list()
    for element in run:
        tables.append(element[0])

    for table in tables:
        run.execute(f"DESCRIBE {table}")
        variables = [element[0] for element in run]

        target = open_target_file(f"{table}_example")

        write_basic_python_functions(target, table, user, password, database_name)
        write_instantiate_entity(target, table, run)
        write_instantiate_dao(target, table)
        
        target.write("\n")
        
        target.write("    print('Inserção via DAO:')\n")
        write_insert_thru_dao(target, table)
        write_select_thru_dao(target, table)

        target.write("    print('Resultado da seleção via DAO do dado inserido:')\n")
        write_print_demo(target, variables)

        target.write("\n")

        target.write("    print('Tabela atualizada após inserção via DAO:')\n")
        write_select_all_thru_dao(target, table, variables)

        target.write("\n")

        write_instantiate_entity(target, table, run, entity_name="new_entity")
        write_update_thru_dao(target, table)
        target.write("    print('Tabela atualizada após update via DAO:')\n")
        write_select_all_thru_dao(target, table, variables)

        target.write("\n")

        write_delete_thru_dao(target, table)
        target.write("    print('Tabela atualizada após delete via DAO:')\n")

        write_select_all_thru_dao(target, table, variables)

        target.write("\n")

        write_closing_python_funtions(target)

        target.close()


def main():
    print("O caminho padrão é localhost. Se o caminho desejado for outro, modifique em código.")
    user = input("Digite o user do banco de dados: ")
    password = input("Digite a senha do banco de dados: ")
    database_name = input("Digite o nome do banco de dados desejado: ")

    db = create_connection(user, password, database_name)
    run = db.cursor()

    write_entity_classes(run)
    write_dao_classes(run)
    write_example(run, user, password, database_name)

    db.close()

if __name__ == "__main__":
    main()