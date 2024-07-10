import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class q04 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            Scanner sc = new Scanner(System.in);
            System.out.print("Digite o número #" + i + "/10: ");
            list.add(parseInt(sc.nextLine()));
        }

        System.out.println("Entre 0 e 25: " + list.stream().filter(a -> a >= 0 && a <= 25).count());
        System.out.println("Entre 26 e 50: " + list.stream().filter(a -> a >= 26 && a <= 50).count());
        System.out.println("Entre 51 e 75: " + list.stream().filter(a -> a >= 51 && a <= 75).count());
        System.out.println("Entre 76 e 100: " + list.stream().filter(a -> a >= 76 && a <= 100).count());

    }
}
