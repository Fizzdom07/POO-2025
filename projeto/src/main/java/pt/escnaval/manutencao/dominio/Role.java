package pt.escnaval.manutencao.dominio;

/**
 * Enumeração dos papéis de utilizador no sistema.
 */
public enum Role {
    TECNICO("Técnico"),
    PLANEADOR("Planeador"),
    GESTOR("Gestor"),
    SOLICITANTE("Solicitante");

    private final String descricao;

    Role(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
