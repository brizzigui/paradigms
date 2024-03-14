import java.util.Scanner;

public class q03 
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int a = input.nextInt();
        int b = input.nextInt();
        
        if(a == b)
        {
            System.out.println("Numbers are equal.");
        }

        else
        {
            int largest = Math.max(a, b);
            System.out.println("The largest number is: " + largest);
        }
    }

}
