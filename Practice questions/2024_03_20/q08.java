import java.util.Scanner;

public class q08
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        double most_expensive = 0;
        int qnt_expensive_and_getting_worse = 0;
        double total_didnt_increase = 0;
        int qnt_didnt_increase = 0;

        while(true)
        {
            String id = input.nextLine();
            if (id.equals("0"))
            {
                break;
            }

            double cost = input.nextDouble();
            input.nextLine();
            double increase = input.nextDouble();
            input.nextLine();

            System.out.println("Novo valor do item #" + id + ": " + cost*(1+increase/100.0));

            if (cost*(1+increase/100.0) > 100 && increase > 5)
            {
                qnt_expensive_and_getting_worse++;
            }

            if (increase == 0)
            {
                qnt_didnt_increase++;
                total_didnt_increase += cost;
            }

            most_expensive = Math.max(most_expensive, cost*(1+increase/100.0));
        }

        System.out.println("Quantidade mais de 100 e 5% de aumento: " + qnt_expensive_and_getting_worse);
        System.out.println("Media sem aumento: " + (total_didnt_increase/qnt_didnt_increase));
        System.out.println("Mais caro: " + most_expensive);


    }
}
