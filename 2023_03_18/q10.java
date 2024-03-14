import java.util.Scanner;

public class q10
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int code = input.nextInt();

        switch(code)
        {
            case 1001:
                System.out.println("RS 5,32");
                break;

            case 1324:
                System.out.println("RS 6,42");
                break;

            case 6548:
                System.out.println("RS 2,37");
                break;  

            case 987:
                System.out.println("RS 5,32");
                break;

            case 7623:
                System.out.println("RS 6,45");
                break;

            default:
                System.out.println("Unknown code");
                break;
        }
    }

}
