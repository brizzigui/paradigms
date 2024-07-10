import java.util.Scanner;

public class q02
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();

        if(number % 2 == 0)
        {
            System.out.println("Número par. ");
        }

        else
        {
            System.out.println("Número ímpar. ");
        }

        if(number < 0)
        {
            System.out.println("Número negativo.");
        }

        else
        {
            System.out.println("Número positivo.");
        }
    }
}