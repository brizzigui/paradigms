import java.util.ArrayList;
import java.util.Scanner;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

class Product
{
    private int code;
    private double price;

    public void setCode(int code) {
        this.code = code;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }
}

public class q05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<Product>();

        while (true)
        {
            System.out.print("Digite o código: ");
            int code = parseInt(sc.nextLine());

            if (code < 0)
            {
                break;
            }

            System.out.print("Digite o preço: ");
            double price = parseDouble(sc.nextLine());
            price *= 1.2;

            Product p = new Product();
            p.setCode(code);
            p.setPrice(price);

            products.add(p);
        }

        System.out.println("Média de preços após o aumento: " + products.stream().mapToDouble((a -> a.getPrice())).average().getAsDouble());
    }
}
