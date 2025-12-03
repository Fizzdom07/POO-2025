package pt.escnaval.exercicios.exceptions;

/**
 * Excepção unchecked lançada quando uma operação não é permitida pela política da conta.
 */
public class OperacaoNaoPermitidaException extends RuntimeException {
    public OperacaoNaoPermitidaException() { super(); }
    public OperacaoNaoPermitidaException(String message) { super(message); }
}
