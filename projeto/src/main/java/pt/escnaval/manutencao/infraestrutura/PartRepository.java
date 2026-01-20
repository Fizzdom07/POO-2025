package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Part;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de peças.
 */
public interface PartRepository {
    void save(Part part);
    Optional<Part> findById(Long id);
    Optional<Part> findBySku(String sku);
    List<Part> findAll();
    void update(Part part);
    void delete(Long id);
}
