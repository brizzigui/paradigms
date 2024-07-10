import java.util.Scanner;

public class q09
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double radius = input.nextDouble();

        System.out.println("Perimetro: " + 2*3.14*radius + " Area: " + 3.14*Math.pow(radius, 2));
    }
}