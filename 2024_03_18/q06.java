import java.util.Scanner;

public class q06
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        double grade_1 = input.nextDouble();
        double grade_2 = input.nextDouble();

        int all_classes = input.nextInt();
        int attended_classes = input.nextInt();

        double grade = (grade_1 + grade_2)/2;
        System.out.println("Média final: " + grade);

        if(grade >= 6 && attended_classes / (double)all_classes >= 0.75)
        {
            System.out.println("Aluno aprovado.");
        }

    }

}
