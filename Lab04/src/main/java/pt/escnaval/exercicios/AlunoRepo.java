package pt.escnaval.exercicios;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AlunoRepo {
    private final List<Aluno> alunos = new ArrayList<>();

    /**
     * Adiciona um aluno; falha se já existir um aluno com o mesmo id.
     */
    public void adicionar(Aluno a) {
        if (a == null) throw new IllegalArgumentException("Aluno não pode ser nulo");
        if (findById(a.getId()) != null) throw new IllegalArgumentException("Já existe um aluno com id=" + a.getId());
        alunos.add(a);
    }

    public boolean removerPorId(int id) {
        return alunos.removeIf(a -> a.getId() == id);
    }

    /** Retorna o Aluno ou null se não existir */
    public Aluno findById(int id) {
        return alunos.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    /** Busca por nome (case-insensitive) e imprime os resultados ordenados por nome */
    public void buscarPorNome(String termo) {
        if (termo == null) termo = "";
        String t = termo.trim().toLowerCase();
        List<Aluno> res = alunos.stream()
                .filter(a -> a.getNome().toLowerCase().contains(t))
                .sorted(Comparator.comparing(Aluno::getNome))
                .collect(Collectors.toList());
        if (res.isEmpty()) {
            System.out.println("Nenhum aluno encontrado para: '" + termo + "'");
            return;
        }
        res.forEach(System.out::println);
    }

    /** Imprime todos os alunos ordenados por id */
    public void listarPorId() {
        alunos.stream()
                .sorted(Comparator.comparingInt(Aluno::getId))
                .forEach(System.out::println);
    }

    /** Imprime todos os alunos ordenados por nome */
    public void listarPorNome() {
        alunos.stream()
                .sorted(Comparator.comparing(Aluno::getNome))
                .forEach(System.out::println);
    }

    /** Retorna uma cópia da lista */
    public List<Aluno> listarTodos() {
        return new ArrayList<>(alunos);
    }
}
