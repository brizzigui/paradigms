import java.util.Scanner;

public class q06
{
    static final int PEOPLE = 3;

    public static void main(String[] args)
    {
        int qnt_ten = 0;
        int acc_age = 0;
        int qnt_found_bad = 0;
        int oldest = 0;
        String oldest_id = "";

        for (int i = 0; i < PEOPLE; i++)
        {
            Scanner input = new Scanner(System.in);
            int age = input.nextInt();
            input.nextLine(); // to stop skipping
            String identifier = input.nextLine();
            int opinion = input.nextInt();
            input.nextLine();

            if(opinion == 10){
                qnt_ten++;
            }

            acc_age += age;

            if(opinion <= 5){
                qnt_found_bad++;
            }

            if (age > oldest)
            {
                oldest = age;
                oldest_id = identifier;
            }
        }

        System.out.println("Respostas 10: " + qnt_ten);
        System.out.println("Idade media: " + acc_age/(float)PEOPLE);
        System.out.println("Acharam podre: " + 100*qnt_found_bad/(float)PEOPLE + "%");
        System.out.println("Id da pessoa mais velha: " + oldest_id);

    }
}
