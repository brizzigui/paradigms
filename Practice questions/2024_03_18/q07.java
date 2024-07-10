import java.util.Scanner;

public class q07
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double side_a = input.nextDouble();
        double side_b = input.nextDouble();
        double side_c = input.nextDouble();

        if (!(side_c < (side_a + side_b) && side_a < (side_c + side_b) && side_b < (side_a + side_c))) {
            System.out.println("Triangulo invalido");
        }

        else if(side_a == side_b && side_b == side_c)
        {
            System.out.println("Triangulo equilatero");
        }

        else if(side_a == side_b || side_b == side_c || side_a == side_c)
        {
            System.out.println("Triangulo isosceles");
        }

        else
        {
            System.out.println("Triangulo escaleno");
        }

    }

}
