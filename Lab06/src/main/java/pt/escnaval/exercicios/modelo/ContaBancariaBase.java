package pt.escnaval.exercicios.modelo;

import pt.escnaval.exercicios.exceptions.ContaInvalidaException;

/**
 * Classe base abstrata com validações comuns.
 */
public abstract class ContaBancariaBase implements ContaBancaria {
    private final String numero; // IBAN-like
    private final String titular;
    protected double saldo;

    private static final String IBAN_REGEX = "PT\\d{2}-\\d{4}-\\d{8}";

    protected ContaBancariaBase(String numero, String titular, double saldoInicial) {
        if (numero == null || !numero.matches(IBAN_REGEX)) {
            throw new ContaInvalidaException("Número de conta inválido (esperado PTxx-xxxx-xxxxxxxx)");
        }
        if (titular == null || titular.trim().isEmpty()) {
            throw new ContaInvalidaException("Titular não pode ser vazio");
        }
        if (saldoInicial < 0) {
            throw new ContaInvalidaException("Saldo inicial não pode ser negativo");
        }
        this.numero = numero;
        this.titular = titular.trim();
        this.saldo = saldoInicial;
    }

    @Override public String getNumero() { return numero; }
    @Override public String getTitular() { return titular; }
    @Override public double getSaldo() { return saldo; }

    @Override public String toString() {
        return String.format("%s | %s | %.2f | %s", numero, titular, saldo, getTipo());
    }
}
