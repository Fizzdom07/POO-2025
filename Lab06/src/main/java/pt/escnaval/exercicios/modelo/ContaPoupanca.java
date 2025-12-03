package pt.escnaval.exercicios.modelo;

import pt.escnaval.exercicios.exceptions.OperacaoNaoPermitidaException;
import pt.escnaval.exercicios.exceptions.SaldoInsuficienteException;

/**
 * Conta poupança sem descoberto e com limite diário por operação.
 */
public class ContaPoupanca extends ContaBancariaBase {
    private static final double LIMITE_DIARIO = 500.0;

    public ContaPoupanca(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }

    @Override
    public void depositar(double montante) {
        if (montante < 0) throw new IllegalArgumentException("Montante para deposito deve ser >= 0");
        this.saldo += montante;
    }

    @Override
    public void levantar(double montante) throws SaldoInsuficienteException {
        if (montante < 0) throw new IllegalArgumentException("Montante para levantamento deve ser >= 0");
        if (montante > LIMITE_DIARIO) {
            throw new OperacaoNaoPermitidaException("Levantamento acima do limite diário de " + LIMITE_DIARIO);
        }
        if (montante > this.saldo) {
            throw new SaldoInsuficienteException(this.saldo, montante);
        }
        this.saldo -= montante;
    }

    @Override
    public String getTipo() { return "Poupanca"; }
}
