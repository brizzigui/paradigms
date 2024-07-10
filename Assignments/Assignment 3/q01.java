import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

class Person {
    private int salary;
    private int n_children;

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getSalary() {
        return salary;
    }

    public void setN_children(int n_children) {
        this.n_children = n_children;
    }

    public int getN_children() {
        return n_children;
    }
}

public class q01 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Insira o número de habitantes entrevistados: ");
        int n = parseInt(sc.nextLine());

        ArrayList<Person> people = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            Person current = new Person();

            System.out.print("Insira o salário: ");
            current.setSalary(parseInt(sc.nextLine()));

            System.out.print("Insira o numero de filhos: ");
            current.setN_children(parseInt(sc.nextLine()));

            people.add(current);
        }

        System.out.println("Média de salário da população: " +
                people.stream().mapToDouble(a -> a.getSalary()).average().getAsDouble()
        );

        System.out.println("Média de filhos da população: " +
                people.stream().mapToInt(a -> a.getN_children()).average().getAsDouble()
        );

        System.out.println("Maior salário da população: " +
                people.stream().mapToInt(a -> a.getSalary()).max().getAsInt()
        );

        System.out.println("Percentual até 1000 reais: " +
                (people.stream().mapToInt(a -> a.getSalary()).filter((a) -> a <= 1000).toArray().length/(double)people.size())*100
                + "%"
        );
    }
}
