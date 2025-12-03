package pt.escnaval.exercicios.servicos;

import pt.escnaval.exercicios.modelo.ContaBancaria;

import java.util.ArrayList;
import java.util.List;

/**
 * Repositório simples de contas com array dinâmico.
 */
public class Banco {
    private ContaBancaria[] contas;
    private int tamanho;
    private static final int CAPACIDADE_INICIAL = 8;

    public Banco() {
        this.contas = new ContaBancaria[CAPACIDADE_INICIAL];
        this.tamanho = 0;
    }

    private void redimensionar() {
        ContaBancaria[] novo = new ContaBancaria[contas.length * 2];
        for (int i = 0; i < tamanho; i++) novo[i] = contas[i];
        contas = novo;
    }

    public boolean adicionar(ContaBancaria c) {
        if (c == null) return false;
        if (buscarPorNumero(c.getNumero()) != null) return false;
        if (tamanho == contas.length) redimensionar();
        contas[tamanho++] = c;
        return true;
    }

    public boolean removerPorNumero(String numero) {
        int idx = -1;
        for (int i = 0; i < tamanho; i++) {
            if (contas[i].getNumero().equals(numero)) { idx = i; break; }
        }
        if (idx == -1) return false;
        for (int i = idx; i < tamanho - 1; i++) contas[i] = contas[i+1];
        contas[--tamanho] = null;
        return true;
    }

    public ContaBancaria buscarPorNumero(String numero) {
        for (int i = 0; i < tamanho; i++) if (contas[i].getNumero().equals(numero)) return contas[i];
        return null;
    }

    public List<ContaBancaria> listarTodas() {
        List<ContaBancaria> out = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) out.add(contas[i]);
        return out;
    }

    public double saldoTotal() {
        double s = 0.0;
        for (int i = 0; i < tamanho; i++) s += contas[i].getSaldo();
        return s;
    }

    public List<ContaBancaria> listarComDescoberto() {
        List<ContaBancaria> res = new ArrayList<>();
        for (int i = 0; i < tamanho; i++) if (contas[i].getSaldo() < 0) res.add(contas[i]);
        return res;
    }
}
