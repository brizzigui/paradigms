import java.util.Scanner;

public class q09
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double sallary = input.nextDouble();


        if (sallary <= 200) {
            System.out.println("No credit");
        }

        else if(sallary > 200 && sallary <= 400)
        {
            System.out.println("20% credit = " + (sallary*0.2));
        }

        else if(sallary > 400 && sallary <= 600)
        {
            System.out.println("30% credit = " + (sallary*0.3));
        }

        else if(sallary > 600)
        {
            System.out.println("40% credit = " + (sallary*0.4));
        }

    }

}
