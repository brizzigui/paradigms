package gym_system;

import static java.lang.Integer.parseInt;

public class Options
{
    private static int get_int_from_line(){
        return parseInt(Main.sc.nextLine());
    }

    public static int get_option()
    {
        System.out.print("Sua opção: ");
        return get_int_from_line();
    }

    public static int member_menu()
    {
        System.out.println("1. Incluir aluno");
        System.out.println("2. Excluir aluno");
        System.out.println("3. Alterar aluno");
        System.out.println("4. Listar alunos");
        System.out.println("5. Buscar aluno por CPF");
        System.out.println("6. Buscar aluno por Nome");
        System.out.println("7. Modificar treino de aluno");
        System.out.println("8. Iniciar treino de aluno");
        System.out.println("9. Atribuir/modificar assinatura de aluno");

        System.out.println("-------------------");
        System.out.println("10. Voltar");
        System.out.println("-------------------");

        return get_option();
    }

    public static int plan_menu()
    {
        System.out.println("1. Incluir plano");
        System.out.println("2. Excluir plano");
        System.out.println("3. Alterar plano");
        System.out.println("4. Listar planos");

        System.out.println("-------------------");
        System.out.println("5. Voltar");
        System.out.println("-------------------");

        return get_option();
    }

    public static int exercise_menu()
    {
        System.out.println("1. Incluir exercício");
        System.out.println("2. Excluir exercício");
        System.out.println("3. Alterar exercício");
        System.out.println("4. Ver exercícios cadastrados");

        System.out.println("-------------------");
        System.out.println("5. Voltar");
        System.out.println("-------------------");

        return get_option();
    }

    public static int report_menu()
    {
        System.out.println("1. Ver comparecimento do aluno por intervalo");
        System.out.println("2. Ver evolução do aluno em exercício");

        System.out.println("-------------------");
        System.out.println("3. Voltar");
        System.out.println("-------------------");

        return get_option();

    }

    public static int menu() {
        System.out.println(" ---------------------");
        System.out.println("| Academia Trembolona |");
        System.out.println(" ---------------------");

        System.out.println("1. Gerenciar Alunos");
        System.out.println("2. Gerenciar Planos");
        System.out.println("3. Gerenciar Exercícios");
        System.out.println("4. Relatórios");
        System.out.println("-------------------");
        System.out.println("9. Sair");
        System.out.println("-------------------");

        return get_option();
    }
}
