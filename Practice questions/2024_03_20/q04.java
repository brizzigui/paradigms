import java.util.Scanner;

// done just for a single employee to simplify io

public class q04
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        String code = input.nextLine();
        int hours_worked = input.nextInt();
        int dependents = input.nextInt();

        double inss = hours_worked*12*0.085;
        double lion = hours_worked*12*0.05;
        double net_salary = hours_worked*12 + dependents - lion - inss;

        System.out.println("Codigo: " + code);
        System.out.println("IR: " + lion);
        System.out.println("INSS: " + inss);
        System.out.println("Liquido: " + net_salary);
    }
}