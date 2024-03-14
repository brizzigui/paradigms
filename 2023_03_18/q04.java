import java.util.Scanner;

public class q04
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();
        int c = input.nextInt();

        if(a >= 0)
        {
            System.out.println(Math.sqrt((double)a));
        }

        else
        {
            System.out.println(a*a);
        }

        if(b > 10 && b < 100)
        {
            System.out.println("Numero esta entre 10 e 100 - intervalo permitido");
        }

        if(c < b)
        {
            System.out.println(b-c);
        }

        else
        {
            System.out.println(c+1);
        }
    }

}
