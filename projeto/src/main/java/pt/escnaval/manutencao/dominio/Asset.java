package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidade que representa um ativo (equipamento) no sistema.
 */
public class Asset implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String code;
    private String name;
    private int criticality; // 0..5
    private AssetStatus status;
    private transient Location location; // optional - transient para não serializar
    private transient Asset parentAsset; // optional - transient para não serializar

    public Asset(Long id, String code, String name, int criticality, AssetStatus status) {
        this.id = id;
        this.code = Objects.requireNonNull(code, "Código não pode ser nulo");
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        if (criticality < 0 || criticality > 5) {
            throw new IllegalArgumentException("Criticidade deve estar entre 0 e 5");
        }
        this.criticality = criticality;
        this.status = Objects.requireNonNull(status, "Estado não pode ser nulo");
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = Objects.requireNonNull(code, "Código não pode ser nulo");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
    }

    public int getCriticality() {
        return criticality;
    }

    public void setCriticality(int criticality) {
        if (criticality < 0 || criticality > 5) {
            throw new IllegalArgumentException("Criticidade deve estar entre 0 e 5");
        }
        this.criticality = criticality;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = Objects.requireNonNull(status, "Estado não pode ser nulo");
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Asset getParentAsset() {
        return parentAsset;
    }

    public void setParentAsset(Asset parentAsset) {
        this.parentAsset = parentAsset;
    }

    @Override
    public String toString() {
        return "Asset{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", criticality=" + criticality +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(id, asset.id) && Objects.equals(code, asset.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code);
    }
}
