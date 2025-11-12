package pt.escnaval.exercicios;

import java.util.Scanner;

public class UtilsIO {
    private static final Scanner scanner = new Scanner(System.in);

    public static String readLine(String prompt) {
        if (prompt != null && !prompt.isEmpty()) System.out.print(prompt);
        String line = scanner.nextLine();
        return line == null ? "" : line.trim();
    }

    public static int readInt(String prompt) {
        while (true) {
            String s = readLine(prompt);
            if (s.isEmpty()) {
                System.out.println("Entrada vazia. Tenta novamente.");
                continue;
            }
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Por favor insere um número válido.");
            }
        }
    }

    // Portuguese aliases required by the lab description
    public static int lerInt(String prompt) {
        return readInt(prompt);
    }

    /**
     * Ler uma opção do menu (inteiro) com validação de intervalo.
     * Se min>max, não valida o intervalo.
     */
    public static int lerOpcao(String prompt, int min, int max) {
        while (true) {
            int v = readInt(prompt);
            if (min <= max) {
                if (v < min || v > max) {
                    System.out.printf("Opção inválida (deve ser entre %d e %d).\n", min, max);
                    continue;
                }
            }
            return v;
        }
    }
}
