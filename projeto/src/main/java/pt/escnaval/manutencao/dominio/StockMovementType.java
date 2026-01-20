package pt.escnaval.manutencao.dominio;

/**
 * Enumeração do tipo de movimento de inventário.
 */
public enum StockMovementType {
    ENTRADA("Entrada"),
    SAIDA("Saída"),
    AJUSTE("Ajuste");

    private final String descricao;

    StockMovementType(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
