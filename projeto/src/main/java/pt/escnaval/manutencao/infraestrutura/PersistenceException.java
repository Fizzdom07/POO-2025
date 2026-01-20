package pt.escnaval.manutencao.infraestrutura;

/**
 * Exceção base para erros de persistência.
 */
public class PersistenceException extends RuntimeException {
    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
