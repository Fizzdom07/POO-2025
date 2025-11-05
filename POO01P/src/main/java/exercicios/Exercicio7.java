package exercicios;

import java.util.Scanner;

public class Exercicio7 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nota da avaliação periódica (0-20): ");
        double notaPeriodica = sc.nextDouble();
        if (notaPeriodica < 0 || notaPeriodica > 20) {
            System.out.println("Erro: Nota da avaliação periódica fora dos limites (0-20). Programa terminado.");
            sc.close();
            return;
        }
        System.out.print("Nota da avaliação final (0-20): ");
        double notaFinal = sc.nextDouble();
        if (notaFinal < 0 || notaFinal > 20) {
            System.out.println("Erro: Nota da avaliação final fora dos limites (0-20). Programa terminado.");
            sc.close();
            return;
        }

        double media = notaPeriodica * 0.3 + notaFinal * 0.7;
        System.out.printf("Nota final: %.2f", media);
        if (notaPeriodica < 6 || notaFinal < 6) {
            System.out.print(" — Reprovado por uma das notas ser nota inferior à mínima");
        }
        System.out.println();
        sc.close();
    }
}
