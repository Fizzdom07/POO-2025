package main.java.exercicios;

public class Cliente {
    private String nome;
    private String nif;

    public Cliente(String nome, String nif) {
        this.nome = nome;
        this.nif = nif;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }

    public boolean isValido() {
        return nome != null && !nome.isEmpty() && nif != null && !nif.isEmpty();
    }

    public void mostrar() {
        System.out.println("Nome: " + nome);
        System.out.println("NIF: " + nif);
    }
}
