package exercicios;

import java.util.Scanner;

public class Exercicio2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira a quantia em dólares: ");
        double dolares = sc.nextDouble();
        double euros = dolares / 1.2;
        double total = euros - 2;
        System.out.printf("Valor convertido: %.2f euros (comissão de 2€ já descontada)%n", total);
        sc.close();
    }
}
