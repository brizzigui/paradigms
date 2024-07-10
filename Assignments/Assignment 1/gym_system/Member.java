package gym_system;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

public class Member {

    private static boolean is_cpf_already_in_use(String cpf){
        try
        {
            return get_by_cpf(cpf).next();
        }

        catch (SQLException e)
        {
            return false;
        }
    }

    public static void add_member(){
        System.out.println("Incluir aluno");
        System.out.println("-------------------");

        System.out.println("Digite o cpf: ");
        String cpf = Main.sc.nextLine();

        while (true){
            if(is_cpf_already_in_use(cpf)){
                System.out.println("CPF inserido já pertence a um aluno. Tente novamente.");
                System.out.println("Digite o cpf: ");
                cpf = Main.sc.nextLine();
            }
            else{
                break;
            }
        }

        System.out.println("Digite o nome: ");
        String name = Main.sc.nextLine();

        System.out.println("Digite o dia de nascimento: ");
        int day = parseInt(Main.sc.nextLine());

        System.out.println("Digite o mês de nascimento: ");
        int month = parseInt(Main.sc.nextLine());

        System.out.println("Digite o ano de nascimento: ");
        int year = parseInt(Main.sc.nextLine());

        try
        {
            var statement = Main.con.prepareStatement("INSERT INTO members (cpf, name, birth_year, birth_month, birth_day) VALUES (?,?,?,?,?)");
            statement.setString(1, cpf);
            statement.setString(2, name);
            statement.setInt(3, year);
            statement.setInt(4, month);
            statement.setInt(5, day);

            statement.execute();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }

    public static void remove_member(){
        System.out.println("Excluir aluno");
        System.out.println("-------------------");
        String cpf = get_valid_member();

        try
        {
            var statement = Main.con.prepareStatement("DELETE FROM members WHERE cpf = ?");
            statement.setString(1, cpf);
            statement.execute();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void change_member()
    {
        System.out.println("Modificar aluno:");
        System.out.println("-------------------");

        String cpf = get_valid_member();
        var result_set = get_by_cpf(cpf);

        try {
            while(result_set.next()) {
                System.out.println("-------------------");
                System.out.println("Nome do aluno: " + result_set.getString("name"));
                System.out.println("Opções:");
                System.out.println("(m) Manter; (a) alterar; (default = m)");
                System.out.print("Sua opção: ");

                String choice = Main.sc.nextLine();

                if (choice.equals("a")){
                    System.out.print("Insira o novo nome do aluno: ");
                    String new_name = Main.sc.nextLine();

                    var statement = Main.con.prepareStatement("UPDATE members SET name = ? WHERE cpf = ?");
                    statement.setString(1, new_name);
                    statement.setString(2, cpf);
                    statement.execute();
                }

                else {
                    System.out.println("Nome mantido.");
                }

                System.out.println("-------------------");
                print_formatted_birthday(result_set);
                System.out.println("Opções:");
                System.out.println("(m) Manter; (a) alterar; (default = m)");
                System.out.print("Sua opção: ");

                choice = Main.sc.nextLine();

                if (choice.equals("a")){
                    System.out.print("Insira o dia: ");
                    int new_day = parseInt(Main.sc.nextLine());
                    System.out.print("Insira o mês: ");
                    int new_month = parseInt(Main.sc.nextLine());
                    System.out.print("Insira o ano: ");
                    int new_year = parseInt(Main.sc.nextLine());

                    var statement = Main.con.prepareStatement("UPDATE members " +
                            "SET birth_day = ?, birth_month = ?, birth_year = ? " +
                            "WHERE cpf = ?");
                    statement.setInt(1, new_day);
                    statement.setInt(2, new_month);
                    statement.setInt(3, new_year);
                    statement.setString(4, cpf);
                    statement.execute();
                }

                else {
                    System.out.println("Data mantida.");
                }

                System.out.println("-------------------");
                System.out.println("CPF: " + result_set.getString("cpf"));
                System.out.println("Opções:");
                System.out.println("(m) Manter; (a) alterar; (default = m)");
                System.out.print("Sua opção: ");

                choice = Main.sc.nextLine();

                if (choice.equals("a")){
                    System.out.print("Insira o novo cpf do aluno: ");
                    String new_cpf = Main.sc.nextLine();

                    while (true){
                        if(is_cpf_already_in_use(new_cpf) && !Objects.equals(new_cpf, cpf)){
                            System.out.println("CPF inserido já pertence a um aluno. Tente novamente.");
                            System.out.println("Insira o novo cpf do aluno: ");
                            new_cpf = Main.sc.nextLine();
                        }

                        else{
                            break;
                        }
                    }

                    var statement = Main.con.prepareStatement("UPDATE members SET cpf = ? WHERE cpf = ?");
                    statement.setString(1, new_cpf);
                    statement.setString(2, cpf);
                    statement.execute();
                }

                else {
                    System.out.println("CPF mantido.");
                }

                System.out.println("Demais informações relativas a plano e treino são modificadas por outros menus.");
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void list_all(){
        System.out.println("Listagem de todos alunos:");
        System.out.println("-------------------");

        try
        {
            var statement = Main.con.createStatement();
            var result_set = statement.executeQuery("SELECT * FROM members");

            while(result_set.next()){
                System.out.println("Nome: " + result_set.getString("name"));
                System.out.println("CPF: " + result_set.getString("cpf"));
                print_formatted_birthday(result_set);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private static ResultSet get_by_cpf(String cpf)
    {
        try
        {
            var statement = Main.con.prepareStatement("SELECT * FROM members WHERE cpf = ?");
            statement.setString(1, cpf);

            return statement.executeQuery();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static void print_formatted_birthday(ResultSet result_set)
    {
        String birthday_text = null;
        try {
            birthday_text = String.format("Data de nascimento: %02d/%02d/%02d", result_set.getInt("birth_day"), result_set.getInt("birth_month"), result_set.getInt("birth_year"));
            System.out.println(birthday_text);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void search_by_cpf(){
        System.out.println("Digite o cpf: ");
        String cpf = Main.sc.nextLine();

        boolean is_empty = true;

        try
        {
            var result_set = get_by_cpf(cpf);

            while(result_set.next()){
                System.out.println("Nome: " + result_set.getString("name"));
                System.out.println("CPF: " + result_set.getString("cpf"));
                print_formatted_birthday(result_set);

                is_empty = false;
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (is_empty){
            System.out.println("Não foram encontrados resultados correspondentes à sua pesquisa.");
        }
    }

    public static void search_by_name(){
        System.out.println("Digite o nome: ");
        String name = Main.sc.nextLine().toLowerCase();

        boolean is_empty = true;

        try
        {
            var statement = Main.con.prepareStatement("SELECT * FROM members WHERE name = ?");
            statement.setString(1, name);
            var result_set = statement.executeQuery();

            while(result_set.next()){
                System.out.println("Nome: " + result_set.getString("name"));
                System.out.println("CPF: " + result_set.getString("cpf"));
                print_formatted_birthday(result_set);

                is_empty = false;
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        if (is_empty){
            System.out.println("Não foram encontrados resultados correspondentes à sua pesquisa.");
        }

    }

    public static void set_subscription(){
        String cpf = get_valid_member();

        try
        {
            while(true) {
                var statement = Main.con.prepareStatement("SELECT cpf FROM members WHERE cpf = ?");
                statement.setString(1, cpf);
                var result_set = statement.executeQuery();

                result_set.next();

                if (result_set.getString("cpf").equals(cpf)) {
                    break;
                }

                else{
                    System.out.println("CPF não encontrado. Tente novamente.");
                }

            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }


        System.out.println("Digite o código do plano: ");
        String plan_code = Main.sc.nextLine();

        System.out.println("Digite o número do cartão de crédito: ");
        String credit_card_number = Main.sc.nextLine();

        System.out.println("Digite o CVV do cartão de crédito: ");
        String credit_card_cvv = Main.sc.nextLine();

        System.out.println("Digite o mês de vencimento do cartão de crédito: ");
        int credit_card_exp_year = parseInt(Main.sc.nextLine());

        System.out.println("Digite o dia de vencimento do cartão de crédito: ");
        int credit_card_exp_month = parseInt(Main.sc.nextLine());

        System.out.println("Digite o dia de começo da matrícula: ");
        int subscription_start_day = parseInt(Main.sc.nextLine());

        System.out.println("Digite o mês de começo da matrícula: ");
        int subscription_start_month = parseInt(Main.sc.nextLine());

        System.out.println("Digite o ano de começo da matrícula: ");
        int subscription_start_year = parseInt(Main.sc.nextLine());

        try
        {
            var statement = Main.con.prepareStatement("UPDATE members " +
                    "SET plan_code = ?, " +
                    "credit_card_num = ?," +
                    "credit_card_cvv = ?," +
                    "credit_card_expiry_year = ?," +
                    "credit_card_expiry_month = ?," +
                    "subscription_start_day = ?," +
                    "subscription_start_month = ?," +
                    "subscription_start_year = ? " +
                    "WHERE cpf = ?;");

            statement.setString(1, plan_code);
            statement.setString(2, credit_card_number);
            statement.setString(3, credit_card_cvv);
            statement.setInt(4, credit_card_exp_year);
            statement.setInt(5, credit_card_exp_month);
            statement.setInt(6, subscription_start_day);
            statement.setInt(7, subscription_start_month);
            statement.setInt(8, subscription_start_year);
            statement.setString(9, cpf);
            statement.execute();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    private static ArrayList<String> get_split_options(String cpf){

        ArrayList<String> all_codes_by_cpf = new ArrayList<String>();

        try {
            var statement = Main.con.prepareStatement("SELECT DISTINCT split_code FROM splits WHERE cpf = ?");
            statement.setString(1, cpf);
            var result_set = statement.executeQuery();

            while (result_set.next()) {
                var split_code = result_set.getString("split_code");
                all_codes_by_cpf.add(split_code);
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return all_codes_by_cpf;
    }

    private static void print_single_exercise(ResultSet result_set){
        try {
            System.out.println("\t" + result_set.getString("name"));
            System.out.print("\t\t Músculos: " + result_set.getString("activated_muscles"));
            System.out.print(" - Min. reps: " + result_set.getString("min_reps"));
            System.out.print(" - Max. reps: " + result_set.getString("max_reps"));
            System.out.print(" - Peso indicado: " + result_set.getString("exercise_load"));
            System.out.print(" - Tempo descanso: " + result_set.getString("rest_time"));
            System.out.println("\n");
            
        }
        
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static ArrayList<String> print_and_get_split_options(String cpf){

        ResultSet result_set = null;
        ArrayList<String> all_codes_by_cpf = new ArrayList<String>();

        try {
            var statement = Main.con.prepareStatement("SELECT DISTINCT split_code FROM splits WHERE cpf = ?");
            statement.setString(1, cpf);
            result_set = statement.executeQuery();

            while (result_set.next()) {
                var split_code = result_set.getString("split_code");
                all_codes_by_cpf.add(split_code);
                System.out.println("Opção de Treino #" + split_code);

                // get all exercises from that split
                statement = Main.con.prepareStatement("SELECT exercises.name, exercises.activated_muscles, splits.min_reps, splits.max_reps, splits.exercise_load, splits.rest_time " +
                                                            "FROM exercises " +
                                                            "JOIN splits ON splits.exercise_code = exercises.code " +
                                                            "WHERE splits.split_code = ? AND cpf = ?;");
                statement.setString(1, split_code);
                statement.setString(2, cpf);
                var helper_result_set = statement.executeQuery();

                while (helper_result_set.next())
                {
                    print_single_exercise(helper_result_set);
                }

                System.out.println("\n");
                System.out.println("----------------------------------");
                System.out.println("\n");
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        if (all_codes_by_cpf.isEmpty()) {
            System.out.println("Não foram encontrados resultados correspondentes à sua pesquisa.");
        }

        return all_codes_by_cpf;

    }

    private static String get_valid_member(){
        String cpf;
        while(true){
            System.out.println("Insira um cpf:");
            cpf = Main.sc.nextLine();
            var result_set = get_by_cpf(cpf);

            try {
                if (result_set.next()) {
                    System.out.println("Aluno encontrado: " + result_set.getString("name") + "\n");
                    break;
                }

                else{
                    System.out.println("Aluno não encontrado. Tente novamente.");
                }
            }

            catch (SQLException e){
                e.printStackTrace();
            }
        }

        return cpf;
    }

    private static String select_split(String cpf) {

        ArrayList<String> all_codes_by_cpf = print_and_get_split_options(cpf);

        while(true) {
            if(all_codes_by_cpf.isEmpty()) {
                return null;
            }

            System.out.print("Digite o código da sua escolha: ");

            String user_choice = Main.sc.nextLine();

            if (!all_codes_by_cpf.contains(user_choice)) {
                System.out.println("Opção inválida.");
            }

            else{
                return user_choice;
            }
        }
    }

    private static ResultSet get_exercises_from_split(String code, String cpf){
        ResultSet result_set = null;

        try {
            // get all exercises from that split
            var statement = Main.con.prepareStatement("SELECT exercises.name, exercises.activated_muscles, splits.min_reps, splits.max_reps, splits.exercise_load, splits.rest_time, splits.exercise_code " +
                    "FROM exercises " +
                    "JOIN splits ON splits.exercise_code = exercises.code " +
                    "WHERE splits.split_code = ? AND cpf = ?;");
            statement.setString(1, code);
            statement.setString(2, cpf);
            result_set = statement.executeQuery();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

        return result_set;
    }
    
    private static void record_each_exercise(String code, String cpf, int day, int month, int year){

        try {
            System.out.println("Selecionada: Opção de Treino #" + code);
            System.out.println("Cada exercício será apresentado e as informações relativas à realização devem ser inseridas");

            var result_set = get_exercises_from_split(code, cpf);

            while (result_set.next())
            {
                print_single_exercise(result_set);

                System.out.println("----------------------------------");
                System.out.println("Esse exercício foi realizado (sim (s)/ não (n) / (default = n)? ");
                String was_done = Main.sc.nextLine();

                if (was_done.equals("s")) {
                    System.out.println("Qual a carga utilizada?");
                    double actual_load = parseDouble(Main.sc.nextLine());

                    var statement = Main.con.prepareStatement("INSERT INTO attendances VALUES (?, ?, ?, ?, ?, ?)");
                    statement.setString(1, cpf);
                    statement.setInt(2, day);
                    statement.setInt(3, month);
                    statement.setInt(4, year);
                    statement.setString(5, result_set.getString("exercise_code"));
                    statement.setDouble(6, actual_load);

                    statement.execute();
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void register_attendance(){
        System.out.println("Registro de Treino");
        System.out.println("-------------------");
        System.out.println("O registro de treino é feito a partir da seleção de um aluno e de um treino" +
                " e da inserção de uma data e dos dados de realização de cada exercício");
        System.out.println("-------------------");

        System.out.println("Digite o dia: ");
        int day = parseInt(Main.sc.nextLine());
        System.out.println("Digite o mês: ");
        int month = parseInt(Main.sc.nextLine());
        System.out.println("Digite o ano: ");
        int year = parseInt(Main.sc.nextLine());

        String cpf = get_valid_member();

        String selected_split_code = select_split(cpf);
        if (selected_split_code == null){
            System.out.println("Não é possível realizar registro para aluno sem cadastro de treino.");
            return;
        }
        System.out.println("O treino selecionado tem código " + selected_split_code); // remove for shipping

        record_each_exercise(selected_split_code, cpf, day, month, year);
    }

    private static void insert_exercise_into_split(String cpf, String split_code){
        String exercise_code = null;
        while(true) {
            System.out.print("Insira o código de um exercício cadastrado: ");
            exercise_code = Main.sc.nextLine();

            if (Exercise.exercise_exists(exercise_code)) {
                break;
            }

            else{
                System.out.println("Não encontrado. Tente novamente.");
            }
        }

        System.out.print("Insira o mínimo de repetições: ");
        int min_reps = parseInt(Main.sc.nextLine());
        System.out.print("Insira o máximo de repetições: ");
        int max_reps = parseInt(Main.sc.nextLine());
        System.out.print("Insira a carga (em kg): ");
        double exercise_load = parseDouble(Main.sc.nextLine());
        System.out.print("Insira o tempo de descanso: ");
        double rest_time = parseDouble(Main.sc.nextLine());

        try {
            var statement = Main.con.prepareStatement("INSERT INTO splits VALUES (?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, cpf);
            statement.setString(2, split_code);
            statement.setString(3, exercise_code);
            statement.setInt(4, min_reps);
            statement.setInt(5, max_reps);
            statement.setDouble(6, exercise_load);
            statement.setDouble(7, rest_time);
            statement.execute();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void add_split(){
        System.out.print("Insira o cpf do aluno: ");
        String cpf = Main.sc.nextLine();

        String code = null;

        ArrayList<String> used_codes = get_split_options(cpf);
        while(true){
            System.out.print("Insira um código para a ficha de treino: ");
            code = Main.sc.nextLine();
            if (!used_codes.contains(code)) {
                break;
            }

            else{
                System.out.println("Não é possível usar um código já usado em outra ficha de treino.");
            }
        }

        System.out.print("Quantos exercícios serão adicionados? ");
        int amount_of_exercises = parseInt(Main.sc.nextLine());

        for (int i = 0; i < amount_of_exercises; i++) {
            System.out.println("Adicionando exercício #" + (i + 1) + "/" + amount_of_exercises);
            insert_exercise_into_split(cpf, code);
        }
    }

    private static void remove_split(){
        System.out.print("Insira o cpf do aluno: ");
        String cpf = Main.sc.nextLine();

        System.out.println("Qual treino você deseja remover?");
        String code = select_split(cpf);

        try{
            var statement = Main.con.prepareStatement("DELETE FROM splits WHERE cpf = ? AND split_code = ?");
            statement.setString(1, cpf);
            statement.setString(2, code);

            statement.execute();
        }

        catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static void modify_split(){
        String cpf = get_valid_member();
        String split_code = select_split(cpf);

        System.out.println("Primeiro, revise os exercícios existentes nesse treino: ");
        System.out.println("----------------------------------");
        var result_set = get_exercises_from_split(split_code, cpf);
        try {
            while(result_set.next()) {
                print_single_exercise(result_set);
                System.out.println("----------------------------------");
                System.out.println("Opções:");
                System.out.println("(m) Manter; (r) remover; (a) alterar; (default = m)");
                System.out.print("Sua opção: ");

                String choice = Main.sc.nextLine();

                if (choice.equals("r"))
                {
                    var statement = Main.con.prepareStatement("DELETE FROM splits " +
                            "WHERE cpf = ? AND split_code = ? AND exercise_code = ?");
                    statement.setString(1, cpf);
                    statement.setString(2, split_code);
                    statement.setString(3, result_set.getString("exercise_code"));
                    statement.execute();

                    System.out.println("Removido.");
                }

                else if(choice.equals("a"))
                {
                    System.out.print("Insira o novo mínimo de repetições: ");
                    int min_reps = parseInt(Main.sc.nextLine());
                    System.out.print("Insira o novo máximo de repetições: ");
                    int max_reps = parseInt(Main.sc.nextLine());
                    System.out.print("Insira a nova carga (em kg): ");
                    double exercise_load = parseDouble(Main.sc.nextLine());
                    System.out.print("Insira o novo tempo de descanso: ");
                    double rest_time = parseDouble(Main.sc.nextLine());

                    var statement = Main.con.prepareStatement("UPDATE splits SET min_reps = ?, max_reps = ?, exercise_load = ?, rest_time = ? " +
                                                                "WHERE cpf = ? AND split_code = ? AND exercise_code = ?");
                    statement.setInt(1, min_reps);
                    statement.setInt(2, max_reps);
                    statement.setDouble(3, exercise_load);
                    statement.setDouble(4, rest_time);
                    statement.setString(5, cpf);
                    statement.setString(6, split_code);
                    statement.setString(7, result_set.getString("exercise_code"));
                    statement.execute();

                    System.out.println("Alterado.");

                }

                else
                {
                    System.out.println("Mantido.");
                }
            }

            System.out.println("Treino existente revisado.");

            while(true)
            {
                System.out.println("Opções:");
                System.out.println("(i) inserir novo exercício; (f) finalizar edição; default = f");
                String choice = Main.sc.nextLine();

                if (choice.equals("i")){
                    insert_exercise_into_split(cpf, split_code);
                }

                else {
                    System.out.println("Edição finalizada");
                    break;
                }
            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void show_all_user_splits()
    {
        String cpf = get_valid_member();
        print_and_get_split_options(cpf);
    }

    public static void split_menu(){
        System.out.println("Cadastro de ficha de treino para aluno");
        System.out.println("------------------");
        System.out.println("1. Adicionar treino");
        System.out.println("2. Remover treino");
        System.out.println("3. Alterar treino");
        System.out.println("4. Ver todos treinos");
        System.out.println("5. Voltar");
        System.out.println("------------------");
        System.out.print("Sua opção: ");

        int choice = parseInt(Main.sc.nextLine());

        switch (choice){
            case 1:
                add_split();
                break;

            case 2:
                remove_split();
                break;

            case 3:
                modify_split();
                break;

            case 4:
                show_all_user_splits();
                break;

            case 5:
                break;
        }

    }

    private static ResultSet get_attendance(String cpf){
        try
        {
            var statement = Main.con.prepareStatement("SELECT * FROM attendances WHERE cpf = ?");
            statement.setString(1, cpf);

            return statement.executeQuery();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean is_within_interval(int start_day, int start_month, int start_year, int end_day, int end_month, int end_year, int actual_day, int actual_month, int actual_year)
    {
        if(actual_year >= start_year && actual_year <= end_year){
            if (actual_year == start_year && actual_year == end_year) {
                if (actual_month == start_month && actual_month == end_month) {
                    return actual_day >= start_day && actual_day <= end_day;
                }

                else if (actual_month == start_month) {
                    return actual_day >= start_day;
                }

                else if (actual_month == end_month) {
                    return actual_day <= end_day;
                }

                else {
                    return actual_month > start_month && actual_month < end_month;
                }
            }

            else if (actual_year == start_year) {
                if (actual_month == start_month) {
                    return actual_day >= start_day;
                }
                else {
                    return actual_month > start_month;
                }
            }

            else if (actual_year == end_year) {
                if (actual_month == end_month) {
                    return actual_day <= end_day;
                }
                else {
                    return actual_month < end_month;
                }
            }

            else {
                return true;
            }
        }

        return false;
    }


    public static void see_member_attendance(){
        String cpf = get_valid_member();

        boolean is_empty = true;
        int start_day, end_day, start_month, end_month, start_year, end_year;
        long distance;

        System.out.println("Digite o dia inicial: ");
        start_day = parseInt(Main.sc.nextLine());
        System.out.println("Digite o mês incial: ");
        start_month = parseInt(Main.sc.nextLine());
        System.out.println("Digite o ano inicial: ");
        start_year = parseInt(Main.sc.nextLine());

        System.out.println("Digite o dia final: ");
        end_day = parseInt(Main.sc.nextLine());
        System.out.println("Digite o mês final: ");
        end_month = parseInt(Main.sc.nextLine());
        System.out.println("Digite o ano final: ");
        end_year = parseInt(Main.sc.nextLine());

        try{
            var result_set = get_attendance(cpf);
            while(result_set.next()){

                int actual_day = result_set.getInt("day");
                int actual_month = result_set.getInt("month");
                int actual_year = result_set.getInt("year");

                if(is_within_interval(start_day, start_month, start_year, end_day, end_month, end_year, actual_day, actual_month, actual_year)){
                        System.out.println("Registro em: " + actual_day + "/" + actual_month + "/" + actual_year);
                        is_empty = false;
                }
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        if(is_empty){
            System.out.println("Não foram encontrados treinos neste intervalo no histórico deste aluno.");
        }

    }

    private static ResultSet get_attendance_exercise(String cpf, String code){
        try
        {
            var statement = Main.con.prepareStatement("SELECT * FROM attendances WHERE cpf = ? AND exercise_code = ?");
            statement.setString(1, cpf);
            statement.setString(2, code);

            return statement.executeQuery();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void see_exercise_evolution(){
        String cpf = get_valid_member();
        System.out.println("Digite o código do exercício: ");
        String code = Main.sc.nextLine();

        boolean is_empty = true;

        try{
            var result_set = get_attendance_exercise(cpf, code);

            while(result_set.next()){
                System.out.println(result_set.getInt("day") + "/" + result_set.getInt("month") + "/" + result_set.getInt("year"));
                System.out.println("Carga no dia: " + result_set.getDouble("exercise_load"));
            }

            is_empty = false;
        }

        catch(SQLException e){
            e.printStackTrace();
        }

        if(is_empty){
            System.out.println("Não foi encontrado resultados desse exercício no histórico deste aluno.");
        }

    }
}
