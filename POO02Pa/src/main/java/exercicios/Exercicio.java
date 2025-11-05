package exercicios;

import java.util.ArrayList;
import java.util.Scanner;

public class Exercicio {
    public static String infoFormatada(Livro livro) {
        return String.format(
            "Título: %s\nAutor: %s\nAno de Publicação: %d\nGênero: %s\nDisponibilidade: %s",
            livro.getTitulo(), livro.getAutor(), livro.getAnoPublicacao(), livro.getGenero(), livro.isDisponivel() ? "Disponível" : "Indisponível"
        );
    }

    public static void main(String[] args) {
        ArrayList<Livro> biblioteca = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Sistema de Biblioteca ===");
        while (true) {
            System.out.println("\n1 - Adicionar livro");
            System.out.println("2 - Listar todos os livros");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = sc.nextInt();
            sc.nextLine(); // consumir newline

            if (opcao == 1) {
                System.out.print("Título: ");
                String titulo = sc.nextLine();
                System.out.print("Autor: ");
                String autor = sc.nextLine();
                System.out.print("Ano de publicação: ");
                int ano = sc.nextInt();
                sc.nextLine();
                System.out.print("Gênero: ");
                String genero = sc.nextLine();
                System.out.print("Disponível (true/false): ");
                boolean disponibilidade = sc.nextBoolean();
                sc.nextLine();
                Livro livro = new Livro(titulo, autor, ano, genero, disponibilidade);
                biblioteca.add(livro);
                System.out.println("Livro adicionado com sucesso!");
            } else if (opcao == 2) {
                if (biblioteca.isEmpty()) {
                    System.out.println("Nenhum livro na biblioteca.");
                } else {
                    for (int i = 0; i < biblioteca.size(); i++) {
                        System.out.println("Livro " + (i+1) + ":\n" + infoFormatada(biblioteca.get(i)) + "\n");
                    }
                }
            } else if (opcao == 0) {
                System.out.println("A sair...");
                break;
            } else {
                System.out.println("Opção inválida!");
            }
        }
        sc.close();
    }
}
