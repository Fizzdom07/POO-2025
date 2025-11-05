package exercicios;

import java.util.Scanner;

public class Exercicio6 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira a temperatura: ");
        double temp = sc.nextDouble();
        System.out.print("Tipo ('C' para Celsius, 'F' para Fahrenheit): ");
        char tipo = sc.next().toUpperCase().charAt(0);

        if (tipo == 'C') {
            double f = 1.8 * temp + 32;
            System.out.printf("%.2fº Celsius é equivalente a %.2fº Fahrenheit%n", temp, f);
        } else if (tipo == 'F') {
            double c = (temp - 32) / 1.8;
            System.out.printf("%.2fº Fahrenheit é equivalente a %.2fº Celsius%n", temp, c);
        } else {
            System.out.println("Tipo inválido. Use 'C' ou 'F'.");
        }
        sc.close();
    }
}
