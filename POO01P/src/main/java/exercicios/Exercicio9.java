package exercicios;

import java.util.Scanner;

public class Exercicio9 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int totalPecas = 0;
        int numDias = 0;
        int diasMenos250 = 0;

        System.out.println("Insira o nº de peças produzidas por dia (-1 para terminar):");
        while (true) {
            int valor = sc.nextInt();
            if (valor == -1) break;
            totalPecas += valor;
            numDias++;
            if (valor < 250) diasMenos250++;
        }

        if (numDias > 0) {
            double media = (double) totalPecas / numDias;
            System.out.printf("Total de peças produzidas: %d%n", totalPecas);
            System.out.printf("Média de peças por dia: %.2f%n", media);
            System.out.printf("Dias com produção inferior a 250 peças: %d%n", diasMenos250);
        } else {
            System.out.println("Nenhum dia registado.");
        }
        sc.close();
    }
}
