package pt.escnaval.exercicios.utils;

import java.util.Scanner;

public final class UtilsIO {
    private UtilsIO() {}

    public static int lerInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Inteiro inválido. Tente novamente.");
            }
        }
    }

    public static double lerDouble(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                System.out.println("Número inválido. Tente novamente.");
            }
        }
    }

    public static int lerOpcao(Scanner sc, int min, int max, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            try {
                int v = Integer.parseInt(s.trim());
                if (v < min || v > max) {
                    System.out.printf("Opção fora do intervalo (%d..%d). Tente novamente.%n", min, max);
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida. Introduza um número.");
            }
        }
    }

    public static String lerStringNaoVazia(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Entrada vazia. Tente novamente.");
        }
    }
}
