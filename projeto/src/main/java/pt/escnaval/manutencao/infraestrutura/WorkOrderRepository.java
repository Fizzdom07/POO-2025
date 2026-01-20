package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.WorkOrder;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de ordens de trabalho.
 */
public interface WorkOrderRepository {
    void save(WorkOrder workOrder);
    Optional<WorkOrder> findById(Long id);
    List<WorkOrder> findAll();
    List<WorkOrder> findByAssetId(Long assetId);
    void update(WorkOrder workOrder);
    void delete(Long id);
}
