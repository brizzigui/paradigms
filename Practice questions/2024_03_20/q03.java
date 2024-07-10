import java.util.Scanner;

public class q03
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int acc_even = 0;
        int acc_odd = 0;

        int qnt_even = 0;
        int qnt_odd = 0;

        for(int i = 0; i < 10; i++)
        {
            int number;
            number = input.nextInt();
            
            if(number % 2 == 0)
            {
                acc_even += number;
                qnt_even++;
            }
 
            else
            {
                acc_odd += number;
                qnt_odd++;
            }
        }

        System.out.println("Soma dos pares: " + acc_even + ". Media dos impares: " + acc_odd/qnt_odd);
    }
}