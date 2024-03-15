import java.util.Scanner;

public class q03
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int number = input.nextInt();

        System.out.println("O predecessor é: " + (number-1) + " e o sucessor é: " + (number+1));
    }
}