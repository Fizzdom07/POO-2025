package pt.escnaval.exercicios;

import java.util.Objects;

public class Aluno {
    private final int id;
    private String nome;

    public Aluno(int id, String nome) {
        if (id <= 0) throw new IllegalArgumentException("id must be > 0");
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("nome must not be empty");
        this.id = id;
        this.nome = nome.trim();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("nome must not be empty");
        this.nome = nome.trim();
    }

    @Override
    public String toString() {
        return String.format("Aluno[id=%d, nome='%s']", id, nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Aluno)) return false;
        Aluno aluno = (Aluno) o;
        return id == aluno.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
