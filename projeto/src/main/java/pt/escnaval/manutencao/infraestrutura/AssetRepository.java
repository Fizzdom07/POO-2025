package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Asset;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de ativos.
 */
public interface AssetRepository {
    void save(Asset asset);
    Optional<Asset> findById(Long id);
    Optional<Asset> findByCode(String code);
    List<Asset> findAll();
    void update(Asset asset);
    void delete(Long id);
}
