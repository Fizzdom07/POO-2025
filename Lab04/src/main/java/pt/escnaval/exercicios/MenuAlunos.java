package pt.escnaval.exercicios;

import java.util.List;

public class MenuAlunos {
    private final AlunoRepo repo = new AlunoRepo();

    public static void main(String[] args) {
        new MenuAlunos().run();
    }

    public void run() {
        while (true) {
            System.out.println("\n=== Menu Alunos ===");
            System.out.println("1) Adicionar aluno");
            System.out.println("2) Listar alunos");
            System.out.println("3) Remover aluno por id");
            System.out.println("0) Sair");
            int op = UtilsIO.readInt("Escolhe uma opção: ");
            switch (op) {
                case 1:
                    adicionar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    remover();
                    break;
                case 0:
                    System.out.println("Adeus!");
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private void adicionar() {
        int id = UtilsIO.readInt("Id do aluno: ");
        String nome = UtilsIO.readLine("Nome do aluno: ");
    Aluno a = new Aluno(id, nome);
    repo.adicionar(a);
        System.out.println("Aluno adicionado: " + a);
    }

    private void listar() {
        List<Aluno> todos = repo.listarTodos();
        if (todos.isEmpty()) {
            System.out.println("Nenhum aluno registado.");
            return;
        }
        System.out.println("--- Lista de Alunos ---");
        for (Aluno a : todos) {
            System.out.println(a);
        }
    }

    private void remover() {
        int id = UtilsIO.readInt("Id do aluno a remover: ");
        boolean ok = repo.removerPorId(id);
        System.out.println(ok ? "Removido." : "Não encontrado.");
    }
}
