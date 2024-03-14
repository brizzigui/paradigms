import java.util.Scanner;

public class q06
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();
        int d = input.nextInt();

        double average = (a+b*2+c*3+d*4)/10.0;
        System.out.println("Média ponderada é: " + average);

    }
}