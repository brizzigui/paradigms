import java.util.Scanner;

public class q05
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int age = input.nextInt();
        
        if(age >= 5 && age <= 7)
        {
            System.out.println("Infantil A");

        }

        else if(age >= 8 && age <= 10)
        {
            System.out.println("Infantil B");
            
        }

        else if(age >= 11 && age <= 13)
        {
            System.out.println("Juvenil A");
            
        }

        else if(age >= 14 && age <= 17)
        {
            System.out.println("Juvenil B");
        
        }
        else if(age >= 18)
        {
            System.out.println("Senior");
            
        }

        else
        {
            System.out.println("Sem categoria.");
        }
    }

}
