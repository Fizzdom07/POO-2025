package pt.escnaval.exercicios.mediateca.utils;

import java.util.Scanner;

public final class UtilsIO {
    private UtilsIO() {}

    public static int lerInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String linha = sc.nextLine();
            if (linha == null) linha = "";
            linha = linha.trim();
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Entrada inválida. Introduza um inteiro: ");
            }
        }
    }

    public static String lerStringNaoVazia(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String linha = sc.nextLine();
            if (linha == null) linha = "";
            linha = linha.trim();
            if (!linha.isBlank()) return linha;
            System.out.print("Entrada vazia. Tente novamente: ");
        }
    }

    public static int lerOpcao(Scanner sc, int min, int max) {
        while (true) {
            int op = lerInt(sc, "Opção: ");
            if (op < min || op > max) {
                System.out.printf("Opção inválida. Escolha entre %d e %d.%n", min, max);
            } else {
                return op;
            }
        }
    }

    // Keep compatibility overloads used elsewhere
    public static int lerOpcao(Scanner sc, int min, int max, String prompt) {
        while (true) {
            int op = lerInt(sc, prompt);
            if (op < min || op > max) {
                System.out.printf("Opção inválida. Escolha entre %d e %d.%n", min, max);
            } else {
                return op;
            }
        }
    }
}
