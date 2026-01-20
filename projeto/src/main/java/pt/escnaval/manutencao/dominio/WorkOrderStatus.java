package pt.escnaval.manutencao.dominio;

/**
 * Enumeração dos estados possíveis para uma ordem de trabalho.
 */
public enum WorkOrderStatus {
    ABERTA("Aberta"),
    PLANEADA("Planeada"),
    EM_EXECUCAO("Em Execução"),
    CONCLUIDA("Concluída"),
    CANCELADA("Cancelada");

    private final String descricao;

    WorkOrderStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
