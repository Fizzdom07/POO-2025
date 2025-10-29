package pt.escnaval.exercicios;
import java.util.Scanner;

public final class UtilsIO {
    private UtilsIO() {}

    public static boolean eInteiro(String s) {
        if (s == null || (s = s.trim()).isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i == 0 && c == '-') continue;
            if (c < '0' || c > '9') return false;
        }
        return true;
    }

    public static int stringParaInt(String s) {
        s = s.trim();
        int resultado = 0;
        int sinal = 1;
        int inicio = 0;
        if (s.charAt(0) == '-') {
            sinal = -1;
            inicio = 1;
        }
        for (int i = inicio; i < s.length(); i++) {
            resultado = resultado * 10 + (s.charAt(i) - '0');
        }
        return resultado * sinal;
    }

    public static int lerInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (eInteiro(s)) {
                return stringParaInt(s);
            }
            System.out.println("Inteiro inválido.");
        }
    }

    public static int lerOpcao(Scanner sc, int min, int max) {
        while (true) {
            int op = lerInt(sc, "Opção? ");
            if (op >= min && op <= max) return op;
            System.out.printf("Opção fora de %d..%d.%n", min, max);
        }
    }
}
