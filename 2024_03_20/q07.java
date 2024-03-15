import java.util.Scanner;

public class q07
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);

        int third_grader = 0;
        int max_fourth_grader = 0;
        int dislikes_writing_third_grade = 0;
        int total = 0;

        while(true)
        {
            int year = input.nextInt();
            if (year == 0)
            {
                break;
            }

            int books = input.nextInt();
            int writes = input.nextInt();

            total++;

            if(year == 3)
            {
                third_grader++;
            }

            if (year == 4 && books > max_fourth_grader)
            {
                max_fourth_grader = books;
            }

            if (year == 3 && writes == 0)
            {
                dislikes_writing_third_grade++;
            }

        }


        System.out.println("Esta na terceira: " + third_grader);
        System.out.println("Maior quantidade de livros quarta serie: " + max_fourth_grader);
        System.out.println("Percentual que esta na terceira e nao gosta de ler: " + dislikes_writing_third_grade/(float)total);
    }
}
