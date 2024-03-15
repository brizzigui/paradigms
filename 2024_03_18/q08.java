import java.util.Scanner;

public class q08
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double index = input.nextDouble();


        if (index >= 0.3 && index < 0.4) {
            System.out.println("PRIMEIRO GRUPO PARA DE BRINCADEIRA");
        }

        else if(index >= 0.4 && index < 0.5)
        {
            System.out.println("PARA TUDO UM E DOIS SEUS CORNO");
        }

        else if(index >= 0.5)
        {
            System.out.println("DEU TODO MUNDO NESSA CARALHA");
        }

    }

}
