import java.util.Scanner;

public class q10
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int hour = input.nextInt();

        System.out.println("Minutes passed: " + (60*hour));
    }
}