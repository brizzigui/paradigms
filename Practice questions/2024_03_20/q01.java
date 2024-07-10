import java.util.Scanner;

public class q01
{
    public static void main(String[] args)
    {
        double acc = 0;

        for (int i = 1; i < 51; i++) {
            int nominator = 2*i - 1;
            int denominator = i;

            acc += nominator/(float)denominator;
    
        }
        
        System.out.println(acc);
    }
}