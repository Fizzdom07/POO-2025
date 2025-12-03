package pt.escnaval.exercicios.modelo;

import pt.escnaval.exercicios.exceptions.SaldoInsuficienteException;

/**
 * Conta corrente com limite de descoberto.
 */
public class ContaCorrente extends ContaBancariaBase {
    private final double limiteDescoberto;

    public ContaCorrente(String numero, String titular, double saldoInicial, double limiteDescoberto) {
        super(numero, titular, saldoInicial);
        this.limiteDescoberto = limiteDescoberto;
    }

    @Override
    public void depositar(double montante) {
        if (montante < 0) throw new IllegalArgumentException("Montante para deposito deve ser >= 0");
        this.saldo += montante;
    }

    @Override
    public void levantar(double montante) throws SaldoInsuficienteException {
        if (montante < 0) throw new IllegalArgumentException("Montante para levantamento deve ser >= 0");
        double permitido = this.saldo + limiteDescoberto;
        if (montante > permitido) {
            throw new SaldoInsuficienteException(this.saldo, montante);
        }
        this.saldo -= montante;
    }

    @Override
    public String getTipo() { return "Corrente"; }
}
