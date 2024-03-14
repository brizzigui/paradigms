import java.util.Scanner;

public class q08
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double base = input.nextDouble();
        double height = input.nextDouble();

        System.out.println("Perimetro: " + (base*2 + height*2) + " Area: " + (base*height));

    }
}