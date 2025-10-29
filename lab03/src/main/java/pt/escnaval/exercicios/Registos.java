package pt.escnaval.exercicios;

public final class Registos {
    private static final int MAX_REGISTOS = 100;
    private static final int[] ids = new int[MAX_REGISTOS];
    private static final String[] nomes = new String[MAX_REGISTOS];
    private static int contador = 0;

    private Registos() {}

    public static boolean adicionar(int id, String nome) {
        if (nome == null || (nome = nome.trim()).isEmpty()) return false;
        if (idExiste(id)) return false;
        if (contador >= MAX_REGISTOS) return false;
        ids[contador] = id;
        nomes[contador] = nome;
        contador++;
        return true;
    }

    public static boolean removerPorId(int id) {
        int i = procurarIndice(id);
        if (i < 0) return false;
        for (int j = i; j < contador - 1; j++) {
            ids[j] = ids[j + 1];
            nomes[j] = nomes[j + 1];
        }
        contador--;
        return true;
    }


    public static void listar() {
        if (contador == 0) { System.out.println("(vazio)"); return; }
        for (int i = 0; i < contador; i++) {
            System.out.printf("%d\t%s%n", ids[i], nomes[i]);
        }
    }

    public static boolean editarNomePorId(int id, String novoNome) {
        int i = procurarIndice(id);
        if (i < 0) return false;
        if (novoNome == null || (novoNome = novoNome.trim()).isEmpty()) return false;
        nomes[i] = novoNome;
        return true;
    }

    public static boolean idExiste(int id) {
        return procurarIndice(id) >= 0;
    }

    private static int procurarIndice(int id) {
        for (int i = 0; i < contador; i++) {
            if (ids[i] == id) return i;
        }
        return -1;
    }
}
