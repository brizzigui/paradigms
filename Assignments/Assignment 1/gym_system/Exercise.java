package gym_system;

import java.sql.SQLException;
import static java.lang.Integer.parseInt;

public class Exercise
{
    private static String get_muscles_activated(int num){
        String formatted_string = "";

        for (int i = 0; i < num; i++)
        {
            System.out.print("Insira o músculo #" + (i+1) + ": ");
            formatted_string += Main.sc.nextLine();

            if(i != num-1)
            {
                formatted_string += ", ";
            }
        }

        return formatted_string;
    }

    public static void add_exercise(){
        System.out.println("Incluir exercício");
        System.out.println("-------------------");

        System.out.println("Digite o código do exercício:");
        String code = Main.sc.nextLine();

        System.out.println("Digite o nome do exercício:");
        String name = Main.sc.nextLine();

        System.out.println("Quantos músculos são ativados?:");
        int n_activated = parseInt(Main.sc.nextLine());

        // to save variable number of items in a varchar in sql, im just throwing them in a string
        String formatted_string = get_muscles_activated(n_activated);

        try
        {
            var statement = Main.con.prepareStatement("INSERT INTO exercises (code, name, activated_muscles) VALUES (?,?,?)");
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setString(3, formatted_string);

            statement.execute();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove_exercise(){
        System.out.println("Remover exercício");
        System.out.println("-------------------");

        System.out.println("Digite o código do exercício:");
        String code = Main.sc.nextLine();

        try
        {
            var statement = Main.con.prepareStatement("SELECT exercise_code from splits WHERE exercise_code = ?");
            statement.setString(1, code);
            var result_set = statement.executeQuery();

            if (!result_set.next()) {
                statement = Main.con.prepareStatement("DELETE FROM exercises WHERE code = ?");
                statement.setString(1, code);
                statement.execute();
            }

            else {
                System.out.println("Não é possível deletar o exercício, pois há treinos com esse exercício cadastrado.");
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void edit_exercise(){

    }

    public static void print_all(){
        System.out.println("Listagem de todos exercícios:");
        System.out.println("-------------------");

        try
        {
            var statement = Main.con.createStatement();
            var result_set = statement.executeQuery("SELECT * FROM exercises");

            while(result_set.next()){
                System.out.println("Nome: " + result_set.getString("name"));
                System.out.println("Código: " + result_set.getString("code"));
                System.out.println("Músculos ativados: " + result_set.getString("activated_muscles"));

            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static boolean exercise_exists(String code){
        try {
            var statement = Main.con.prepareStatement("SELECT * FROM exercises WHERE code = ?");
            statement.setString(1, code);

            var result_set = statement.executeQuery();

            return result_set.next();
        }

        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
