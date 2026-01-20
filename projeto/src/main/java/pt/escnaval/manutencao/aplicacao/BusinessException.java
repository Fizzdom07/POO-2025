package pt.escnaval.manutencao.aplicacao;

/**
 * Exceção para erros de negócio na aplicação.
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
