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
}
