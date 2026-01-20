package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.util.Objects;

/**
 * Entidade que representa uma localização no sistema.
 */
public class Location implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Location parent; // optional

    public Location(Long id, String name) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
    }

    public Location(Long id, String name, Location parent) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
    }

    public Location getParent() {
        return parent;
    }

    public void setParent(Location parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
