package pt.escnaval.exercicios;

import java.util.List;

public class MenuAlunos {
    private final AlunoRepo repo = new AlunoRepo();

    public static void main(String[] args) {
        new MenuAlunos().run();
    }

    public void run() {
        int op;
        do {
            System.out.println();
            System.out.println("=== Menu Alunos ===");
            System.out.println("1) Listar por ID");
            System.out.println("2) Listar por Nome");
            System.out.println("3) Adicionar");
            System.out.println("4) Remover por ID");
            System.out.println("5) Buscar por nome");
            System.out.println("6) Renomear por ID");
            System.out.println("0) Sair");
            op = UtilsIO.lerOpcao("Escolhe uma opção: ", 0, 6);
            switch (op) {
                case 1:
                    listarPorIdFluxo();
                    break;
                case 2:
                    listarPorNomeFluxo();
                    break;
                case 3:
                    adicionarFluxo();
                    break;
                case 4:
                    removerFluxo();
                    break;
                case 5:
                    buscarFluxo();
                    break;
                case 6:
                    renomearFluxo();
                    break;
                case 0:
                    System.out.println("Adeus!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (op != 0);
    }

    private void adicionarFluxo() {
        System.out.println();
        System.out.println("-- Adicionar Aluno --");
        int id = UtilsIO.lerInt("Id do aluno: ");
        String nome = UtilsIO.readLine("Nome do aluno: ");
        try {
            Aluno a = new Aluno(id, nome);
            if (a.getMensagemErro() != null && !a.getMensagemErro().isEmpty()) {
                System.out.println(a.getMensagemErro());
            } else {
                boolean ok = repo.adicionar(a);
                System.out.println(ok ? "Adicionado." : "Falha: ID já existente.");
            }
        } catch (IllegalArgumentException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        System.out.println();
    }

    private void listarPorIdFluxo() {
        System.out.println();
        System.out.println("-- Lista (ordenada por ID) --");
        System.out.printf("%6s | %s\n", "ID", "Nome");
        System.out.println("------+--------------------------------------------------");
        repo.listarPorId();
        System.out.println();
    }

    private void listarPorNomeFluxo() {
        System.out.println();
        System.out.println("-- Lista (ordenada por Nome) --");
        System.out.printf("%6s | %s\n", "ID", "Nome");
        System.out.println("------+--------------------------------------------------");
        repo.listarPorNome();
        System.out.println();
    }

    private void removerFluxo() {
        System.out.println();
        System.out.println("-- Remover Aluno --");
        int id = UtilsIO.lerInt("Id do aluno a remover: ");
        boolean ok = repo.removerPorId(id);
        System.out.println(ok ? "Removido." : "Não encontrado.");
        System.out.println();
    }

    private void buscarFluxo() {
        System.out.println();
        System.out.println("-- Buscar por Nome --");
        String termo = UtilsIO.readLine("Termo de busca: ");
        repo.buscarPorNome(termo);
        System.out.println();
    }

    private void renomearFluxo() {
        System.out.println();
        System.out.println("-- Renomear Aluno --");
        int id = UtilsIO.lerInt("Id do aluno: ");
        Aluno a = repo.findById(id);
        if (a == null) {
            System.out.println("Aluno não encontrado.");
            System.out.println();
            return;
        }
        System.out.println("Aluno atual: " + a);
        String novo = UtilsIO.readLine("Novo nome: ");
        a.setNome(novo);
        if (a.getMensagemErro() != null && !a.getMensagemErro().isEmpty()) {
            System.out.println(a.getMensagemErro());
        } else {
            System.out.println("Atualizado.");
        }
        System.out.println();
    }
}
