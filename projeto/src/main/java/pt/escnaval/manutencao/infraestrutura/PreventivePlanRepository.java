package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.PreventivePlan;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de planos preventivos.
 */
public interface PreventivePlanRepository {
    void save(PreventivePlan plan);
    void update(PreventivePlan plan);
    Optional<PreventivePlan> findById(Long id);
    List<PreventivePlan> findAll();
    void delete(Long id);
}
