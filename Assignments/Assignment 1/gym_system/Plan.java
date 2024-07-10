package gym_system;

import java.sql.SQLException;

import static java.lang.Double.parseDouble;

public class Plan
{
    public static void add_plan(){
        System.out.println("Incluir plano");
        System.out.println("-------------------");

        System.out.println("Digite o código do plano:");
        String code = Main.sc.nextLine();

        System.out.println("Digite o nome do plano:");
        String name = Main.sc.nextLine();

        System.out.println("Digite o preço do plano:");
        Double price = parseDouble(Main.sc.nextLine());

        try
        {
            var statement = Main.con.prepareStatement("INSERT INTO plans (code, name, monthly_cost) VALUES (?,?,?)");
            statement.setString(1, code);
            statement.setString(2, name);
            statement.setDouble(3, price);

            statement.execute();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void remove_plan(){
        System.out.println("Excluir plano");
        System.out.println("-------------------");
        System.out.println("Digite o código do plano: ");
        String code = Main.sc.nextLine();

        try
        {
            var statement = Main.con.prepareStatement("SELECT plan_code from members WHERE plan_code = ?");
            statement.setString(1, code);
            var result_set = statement.executeQuery();

            if (!result_set.next()) {
                statement = Main.con.prepareStatement("DELETE FROM plans WHERE code = ?");
                statement.setString(1, code);
                statement.execute();
            }

            else {
                System.out.println("Não é possível deletar o plano, pois há clientes com esse plano ativo.");
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void change_plan(){
        System.out.println("EU NAO FUI IMPLEMENTADOOOOOO");
    }

    public static void list_all(){
        System.out.println("Listagem de todos planos:");
        System.out.println("-------------------");

        try
        {
            var statement = Main.con.createStatement();
            var result_set = statement.executeQuery("SELECT * FROM plans");

            while(result_set.next()){
                System.out.println("Código: " + result_set.getString("code"));
                System.out.println("Nome: " + result_set.getString("name"));
                System.out.println("Preço mensal: " + result_set.getDouble("monthly_cost"));

            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
