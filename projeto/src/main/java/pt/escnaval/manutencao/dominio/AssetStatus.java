package pt.escnaval.manutencao.dominio;

/**
 * Enumeração dos estados possíveis para um ativo.
 */
public enum AssetStatus {
    ATIVO("Ativo"),
    INATIVO("Inativo"),
    OBSOLETO("Obsoleto");

    private final String descricao;

    AssetStatus(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
