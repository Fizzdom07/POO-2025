package exercicios;

import java.time.LocalDate;

public class Colaborador extends Pessoa {
    private double salario;
    private LocalDate dataContratacao;

    public Colaborador(String nome, int idade, double salario, LocalDate dataContratacao) {
        super(nome, idade);
        this.salario = salario;
        this.dataContratacao = dataContratacao;
    }

    public double getSalario() {
        return salario;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void aumentarSalario(double percent) {
        this.salario = this.salario * (1.0 + percent / 100.0);
    }

    @Override
    public String toString() {
        return String.format("Colaborador[nome=%s, idade=%d, salario=%.2f, contratado=%s]",
                getNome(), getIdade(), salario, dataContratacao);
    }
}
