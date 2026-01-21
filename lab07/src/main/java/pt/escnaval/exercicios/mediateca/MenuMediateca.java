package pt.escnaval.exercicios.mediateca;

import pt.escnaval.exercicios.mediateca.modelo.Album;
import pt.escnaval.exercicios.mediateca.modelo.Faixa;
import pt.escnaval.exercicios.mediateca.servicos.Mediateca;
import pt.escnaval.exercicios.mediateca.servicos.RepositorioTexto;
import pt.escnaval.exercicios.mediateca.servicos.Mp3Util;
import pt.escnaval.exercicios.mediateca.utils.UtilsIO;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

/**
 * Interface de linha de comandos para a mediateca.
 */
public class MenuMediateca {
    private static final Mediateca med = new Mediateca();
    private static final Path DATA = Path.of("data/mediateca.csv");
    private static final RepositorioTexto repo = new RepositorioTexto(DATA);

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            // carregar catálogo existente (se houver)
            try {
                List<Album> carreg = repo.carregar();
                for (Album a : carreg) med.adicionarAlbum(a);
                System.out.println("Carregados " + carreg.size() + " álbum(ens) de " + DATA);
            } catch (IOException e) {
                System.out.println("Aviso: não foi possível carregar dados: " + e.getMessage());
            }
            int op;
            do {
                mostrarMenu();
                op = UtilsIO.lerOpcao(sc, 1, 6);
                switch (op) {
                    case 1 -> criarAlbum(sc);
                    case 2 -> listarAlbuns();
                    case 3 -> adicionarFaixa(sc);
                    case 4 -> copiarMp3(sc);
                    case 5 -> salvar(sc);
                    case 6 -> System.out.println("A terminar...");
                    default -> System.out.println("Opção inválida.");
                }
                System.out.println();
            } while (op != 6);
        }
    }

    private static void mostrarMenu() {
        System.out.println("=== Menu Mediateca ===");
        System.out.println("1) Criar álbum");
        System.out.println("2) Listar álbuns");
        System.out.println("3) Adicionar faixa a álbum");
        System.out.println("4) Copiar ficheiro MP3 para media/");
        System.out.println("5) Guardar em CSV");
        System.out.println("6) Sair");
    }

    private static void criarAlbum(Scanner sc) {
        String id = UtilsIO.lerStringNaoVazia(sc, "ID do álbum: ");
        String titulo = UtilsIO.lerStringNaoVazia(sc, "Título: ");
        String autor = UtilsIO.lerStringNaoVazia(sc, "Autor: ");
        var a = new Album(id, titulo, autor);
        boolean ok = med.adicionarAlbum(a);
        System.out.println(ok ? "Álbum criado." : "Falha: álbum já existe.");
    }

    private static void listarAlbuns() {
        var l = med.listarAlbuns();
        if (l.isEmpty()) System.out.println("(nenhum álbum)");
        else l.forEach(System.out::println);
    }

    private static void adicionarFaixa(Scanner sc) {
        String idAlbum = UtilsIO.lerStringNaoVazia(sc, "ID álbum: ");
        var a = med.buscarPorId(idAlbum);
        if (a == null) { System.out.println("Álbum não encontrado."); return; }
        int numero = UtilsIO.lerInt(sc, "Número da faixa: ");
        String titulo = UtilsIO.lerStringNaoVazia(sc, "Título: ");
        int dur = UtilsIO.lerInt(sc, "Duração (segundos): ");
        String ficheiro = UtilsIO.lerStringNaoVazia(sc, "Caminho relativo (media/) (vazio se não existir): ");
        if (ficheiro.isBlank()) ficheiro = null;
        var f = new Faixa(numero, titulo, dur, ficheiro);
        a.adicionarFaixa(f);
        System.out.println("Faixa adicionada.");
    }

    private static void copiarMp3(Scanner sc) {
        String origem = UtilsIO.lerStringNaoVazia(sc, "Caminho ficheiro origem: ");
        String destino = UtilsIO.lerStringNaoVazia(sc, "Nome destino em media/: ");
        try {
            var src = java.nio.file.Path.of(origem);
            var dst = java.nio.file.Path.of("media").resolve(destino);
            pt.escnaval.exercicios.mediateca.servicos.Mp3Util.copiarMp3(src, dst);
            System.out.println("Ficheiro copiado para media/" + destino);
        } catch (Exception e) {
            System.out.println("Erro ao copiar ficheiro: " + e.getMessage());
        }
    }

    private static void salvar(Scanner sc) {
        try {
            repo.guardar(med.listarAlbuns());
            System.out.println("Guardado com sucesso em " + DATA);
        } catch (Exception e) {
            System.out.println("Erro ao guardar: " + e.getMessage());
        }
    }
}
