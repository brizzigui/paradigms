import java.util.Objects;
import java.util.Scanner;

public class q05
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        int read_by_under_10s = 0;
        int women_readers = 0;
        int dumb_male_age = 0;
        int dumb_males = 0;
        int illiterates = 0;
        int people_read = 0;

        while(true) {
            String sex = input.nextLine();
            int age = input.nextInt();

            if (age < 0) {
                break;
            }

            int books_read = input.nextInt();
            input.nextLine(); // to stop \n from fucking shit

            people_read++;

            if(age < 10){
                read_by_under_10s += books_read;
            }

            if(Objects.equals(sex, "f") && books_read >= 5){
                women_readers++;
            }

            if(Objects.equals(sex, "m") && books_read < 5){
                dumb_male_age += age;
                dumb_males++;
            }

            if (books_read == 0) {
                illiterates++;
            }
        }

        System.out.println("Menor que 10 leram " + read_by_under_10s);
        System.out.println("Mulheres 5 ou mais: " + women_readers);
        System.out.println("Idade homens menos que 5 livros: " + dumb_male_age/(float)dumb_males);
        System.out.println("Percentual que nao leu: " + 100*illiterates/(float)people_read);
    }
}