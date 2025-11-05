package exercicios;

public class Livro {
    private String titulo;
    private String autor;
    private int anoPublicacao;
    private String genero;
    private boolean disponibilidade;

    // Construtor completo
    public Livro(String titulo, String autor, int anoPublicacao, String genero, boolean disponibilidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = anoPublicacao;
        this.genero = genero;
        this.disponibilidade = disponibilidade;
    }

    // Construtor apenas com título e autor
    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
        this.anoPublicacao = 0;
        this.genero = "Desconhecido";
        this.disponibilidade = false;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public int getAnoPublicacao() { return anoPublicacao; }
    public String getGenero() { return genero; }
    public boolean isDisponivel() { return disponibilidade; }

    // Setters
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutor(String autor) { this.autor = autor; }
    public void setAnoPublicacao(int ano) { this.anoPublicacao = ano; }
    public void setGenero(String genero) { this.genero = genero; }
    public void setDisponibilidade(boolean disponibilidade) { this.disponibilidade = disponibilidade; }
}
