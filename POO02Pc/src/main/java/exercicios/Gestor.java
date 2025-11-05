package exercicios;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gestor extends Colaborador {
    private double bonus;
    private final List<Colaborador> equipa;

    public Gestor(String nome, int idade, double salario, LocalDate dataContratacao) {
        super(nome, idade, salario, dataContratacao);
        this.bonus = 0.0;
        this.equipa = new ArrayList<>();
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    @Override
    public double getSalario() {
        return super.getSalario() + bonus;
    }

    public void aumentarEquipa(Colaborador c) {
        if (c == null) return;
        equipa.add(c);
    }

    public void listarEquipa() {
        System.out.println("-> Equipa do gestor " + getNome() + ":");
        if (equipa.isEmpty()) {
            System.out.println("   (vazio)");
            return;
        }
        for (Colaborador c : equipa) {
            System.out.println("   " + c);
        }
    }

    @Override
    public String toString() {
        return String.format("Gestor[nome=%s, idade=%d, salarioBase=%.2f, bonus=%.2f, salarioTotal=%.2f]",
                getNome(), getIdade(), super.getSalario(), bonus, getSalario());
    }
}
