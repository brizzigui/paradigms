import java.util.Scanner;

public class q02
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int acc = 0;
        int iter = 0;

        while(true)
        {
            int num = input.nextInt();
            if (num <= 0) 
            {
                break;
            }

            acc += num;
            iter++;
        }

        System.out.println((float)acc/iter);
    }
}