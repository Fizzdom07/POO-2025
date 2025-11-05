package exercicios;

import java.util.Scanner;

public class Exercicio8 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Número de peças encomendadas: ");
        int numPecas = sc.nextInt();
        System.out.print("Preço unitário (€): ");
        double precoUnitario = sc.nextDouble();

        double valorEncomenda = numPecas * precoUnitario;
        double desconto;
        if (valorEncomenda < 2000) {
            desconto = 0.02;
        } else if (valorEncomenda < 5000) {
            desconto = 0.04;
        } else {
            desconto = 0.075;
        }
        double valorFinal = valorEncomenda * (1 - desconto);

        System.out.printf("Valor da encomenda: %.2f €%n", valorEncomenda);
        System.out.printf("Desconto aplicado: %.1f%%%n", desconto * 100);
        System.out.printf("Valor a pagar: %.2f €%n", valorFinal);
        sc.close();
    }
}
