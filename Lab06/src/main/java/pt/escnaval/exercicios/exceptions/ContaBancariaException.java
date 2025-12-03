package pt.escnaval.exercicios.exceptions;

/**
 * Excepção base checked para operações bancárias que falham por regras do domínio.
 */
public class ContaBancariaException extends Exception {
    public ContaBancariaException() { super(); }
    public ContaBancariaException(String message) { super(message); }
    public ContaBancariaException(String message, Throwable cause) { super(message, cause); }
}
