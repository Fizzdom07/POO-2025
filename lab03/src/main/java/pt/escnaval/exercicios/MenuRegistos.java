package pt.escnaval.exercicios;
import java.util.*;

public class MenuRegistos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            mostrarMenu();
            switch (sc.nextLine().trim()) {
                case "1" -> criar(sc);
                case "2" -> Registos.listar();
                case "3" -> remover(sc);
                case "4" -> editar(sc);
                case "0" -> { System.out.println("Adeus!"); return; }
                default -> System.out.println("Opção inválida");
            }
        }
    }

    static void mostrarMenu() {
        System.out.println("\n=== Menu Registos ===");
        System.out.println("1) Adicionar registo");
        System.out.println("2) Listar registos");
        System.out.println("3) Remover por ID");
        System.out.println("4) Editar nome por ID");
        System.out.println("0) Sair");
    }
    static void editar(Scanner sc) {
        int id = UtilsIO.lerInt(sc, "ID a editar? ");
        System.out.print("Novo nome? ");
        String novoNome = sc.nextLine();
        boolean ok = Registos.editarNomePorId(id, novoNome);
        System.out.println(ok ? "[OK] Nome editado." : "[X] Falhou (ID inexistente ou nome inválido).");
    }

    static void criar(Scanner sc) {
        int id = UtilsIO.lerInt(sc, "ID? ");
        System.out.print("Nome? ");
        String nome = sc.nextLine();
        boolean ok = Registos.adicionar(id, nome);
        System.out.println(ok ? "[OK] Adicionado." : "[X] Falhou (ID repetido ou nome vazio).");
    }

    static void remover(Scanner sc) {
        int id = UtilsIO.lerInt(sc, "ID a remover? ");
        System.out.println(Registos.removerPorId(id) ? "[OK] Removido." : "[X] ID inexistente.");
    }
}
