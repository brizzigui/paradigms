import java.util.HashMap;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class q02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, Integer> votes = new HashMap();


        for (int i = 1; i < 7; i++) {
            votes.put(i, 0);
        }

        int index = 0;
        System.out.print("Insira um voto: ");
        int current = parseInt(sc.nextLine());

        while(current != 0) {
            votes.put(current, votes.getOrDefault(current, 0) + 1);
            System.out.print("Insira um voto: ");
            current = parseInt(sc.nextLine());
        }

        votes.forEach(
                (k, v) ->
                {
                    if (k == 1 || k == 2 || k == 3 || k == 4) {
                        System.out.println("Candidato " + k + " - Votos: " + v);
                    }
                    else if (k == 5){
                        System.out.println("Nulos: " + v);
                    }
                    else if (k == 6){
                        System.out.println("Brancos: " + v);
                    }

                }
        );
    }
}
