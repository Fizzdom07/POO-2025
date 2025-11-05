package exercicios;

import java.util.Scanner;

public class Exercicio3 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Valor do empréstimo (€): ");
        double valorEmprestimo = sc.nextDouble();
        System.out.print("Duração (anos): ");
        int anos = sc.nextInt();
        System.out.print("Taxa de juro anual (ex: 0.05 para 5%): ");
        double taxaJuro = sc.nextDouble();

        int numMeses = anos * 12;
        double juroMes = taxaJuro / 12.0;
        double baseMes = 1.0 + juroMes;

        double numerador = juroMes * Math.pow(baseMes, numMeses) * valorEmprestimo;
        double denominador = Math.pow(baseMes, numMeses) - 1.0;
        double mensalidade = numerador / denominador;
        double totalAPagar = mensalidade * numMeses;

        System.out.printf("Mensalidade: %.2f €%n", mensalidade);
        System.out.printf("Total a pagar: %.2f €%n", totalAPagar);
        sc.close();
    }
}
