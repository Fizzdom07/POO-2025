package main.java.exercicios;

public class Endereco {
    private String rua;
    private int porta;
    private String codPostal;

    public Endereco(String rua, int porta, String codPostal) {
        this.rua = rua;
        this.porta = porta;
        this.codPostal = codPostal;
    }

    public void mostrar() {
        System.out.println("Rua: " + rua);
        System.out.println("Porta: " + porta);
        System.out.println("Código Postal: " + codPostal);
    }
}
