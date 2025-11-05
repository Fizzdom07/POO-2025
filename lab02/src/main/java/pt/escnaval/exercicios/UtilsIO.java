package pt.escnaval.exercicios;

import java.util.Scanner;

public class UtilsIO {
    // Métodos utilitários para entrada robusta
    public static int lerInt(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = sc.nextLine();
            try {
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Inteiro inválido. Tente novamente: ");
            }
        }
    }

    public static double lerDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String s = sc.nextLine();
            try {
                return Double.parseDouble(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Double inválido. Tente novamente: ");
            }
        }
    }
}
