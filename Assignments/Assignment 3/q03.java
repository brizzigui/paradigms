import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

class Student
{
    private String code;
    private double grade1;
    private double grade2;
    private double grade3;

    public void setCode(String code) {
        this.code = code;
    }

    public void setGrade1(double grade1) {
        this.grade1 = grade1;
    }

    public void setGrade2(double grade2) {
        this.grade2 = grade2;
    }

    public void setGrade3(double grade3) {
        this.grade3 = grade3;
    }

    public String getCode() {
        return code;
    }

    public double getGrade1() {
        return grade1;
    }

    public double getGrade2() {
        return grade2;
    }

    public double getGrade3() {
        return grade3;
    }

    public double getAverageGrade() {
        if (grade1 >= grade2 && grade1 >= grade3) {
            return 0.4*grade1 + 0.3*grade2 + 0.3*grade3;
        }

        else if (grade2 >= grade1 && grade2 >= grade3) {
            return 0.4*grade2 + 0.3*grade1 + 0.3*grade3;
        }

        else {
            return 0.4*grade3 + 0.3*grade1 + 0.3*grade2;
        }
    }
}

public class q03 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Student> all_students = new ArrayList<Student>();

        for (int i = 0; i < 5; i++) {
            System.out.println("Aluno #" + (i + 1) + ": ");

            Student current = new Student();
            System.out.print("Insira o código: ");
            current.setCode(sc.nextLine());

            System.out.print("Insira a primeira nota: ");
            current.setGrade1(parseDouble(sc.nextLine()));

            System.out.print("Insira a segunda nota: ");
            current.setGrade2(parseDouble(sc.nextLine()));

            System.out.print("Insira a terceira nota: ");
            current.setGrade3(parseDouble(sc.nextLine()));

            all_students.add(current);
        }

        all_students.forEach(student -> {
            double averageGrade = student.getAverageGrade();
            String status = averageGrade > 5 ? "aprovado" : "reprovado";
            System.out.println("Código: " + student.getCode());
            System.out.println("Nota 1: " + student.getGrade1());
            System.out.println("Nota 2: " + student.getGrade2());
            System.out.println("Nota 3: " + student.getGrade3());
            System.out.println("Nota média: " + averageGrade);
            System.out.println("Status: " + status);
        });
    }
}
