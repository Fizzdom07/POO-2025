package pt.escnaval.exercicios.exceptions;

/**
 * Excepção unchecked lançada quando a conta não cumpre validações de formato/estado.
 */
public class ContaInvalidaException extends RuntimeException {
    public ContaInvalidaException() { super(); }
    public ContaInvalidaException(String message) { super(message); }
}
