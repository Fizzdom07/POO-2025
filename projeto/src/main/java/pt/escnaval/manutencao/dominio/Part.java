package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidade que representa uma peça/sobressalente no sistema.
 */
public class Part implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String sku;        // Unique identifier
    private String name;
    private String unit;       // ex: "unidade", "kg", etc
    private double reorderPoint;

    public Part(Long id, String sku, String name, String unit) {
        this.id = id;
        this.sku = Objects.requireNonNull(sku, "SKU não pode ser nulo");
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.unit = Objects.requireNonNull(unit, "Unidade não pode ser nula");
        this.reorderPoint = 0.0;
    }

    public Part(Long id, String sku, String name, String unit, double reorderPoint) {
        this.id = id;
        this.sku = Objects.requireNonNull(sku, "SKU não pode ser nulo");
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.unit = Objects.requireNonNull(unit, "Unidade não pode ser nula");
        if (reorderPoint < 0) {
            throw new IllegalArgumentException("Ponto de reposição não pode ser negativo");
        }
        this.reorderPoint = reorderPoint;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = Objects.requireNonNull(sku, "SKU não pode ser nulo");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = Objects.requireNonNull(unit, "Unidade não pode ser nula");
    }

    public double getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(double reorderPoint) {
        if (reorderPoint < 0) {
            throw new IllegalArgumentException("Ponto de reposição não pode ser negativo");
        }
        this.reorderPoint = reorderPoint;
    }

    @Override
    public String toString() {
        return "Part{" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Part part = (Part) o;
        return Objects.equals(id, part.id) && Objects.equals(sku, part.sku);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sku);
    }
}

