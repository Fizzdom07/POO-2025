package pt.escnaval.manutencao.dominio;

/**
 * Enumeração do tipo de ordem de trabalho.
 */
public enum WorkOrderType {
    CORRETIVA("Corretiva"),
    PREVENTIVA("Preventiva");

    private final String descricao;

    WorkOrderType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
