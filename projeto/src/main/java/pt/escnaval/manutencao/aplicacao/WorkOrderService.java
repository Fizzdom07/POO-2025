package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.WorkOrder;
import pt.escnaval.manutencao.dominio.WorkOrderType;
import pt.escnaval.manutencao.dominio.WorkOrderStatus;
import pt.escnaval.manutencao.dominio.Asset;
import pt.escnaval.manutencao.infraestrutura.WorkOrderRepository;
import pt.escnaval.manutencao.infraestrutura.AssetRepository;

import java.util.List;
import java.util.Objects;

/**
 * Serviço de domínio para gestão de ordens de trabalho.
 */
public class WorkOrderService {
    private final WorkOrderRepository woRepository;
    private final AssetRepository assetRepository;

    public WorkOrderService(WorkOrderRepository woRepository, AssetRepository assetRepository) {
        this.woRepository = Objects.requireNonNull(woRepository, "Repositório de OT não pode ser nulo");
        this.assetRepository = Objects.requireNonNull(assetRepository, "Repositório de ativos não pode ser nulo");
    }

    /**
     * Criar uma nova ordem de trabalho.
     */
    public WorkOrder createWorkOrder(String type, int priority, String title, Long assetId) {
        if (title == null || title.isBlank()) {
            throw new BusinessException("Título da OT não pode ser vazio");
        }
        if (priority < 1 || priority > 5) {
            throw new BusinessException("Prioridade deve estar entre 1 e 5");
        }

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new BusinessException("Ativo não encontrado com ID: " + assetId));

        WorkOrderType woType = switch (type) {
            case "CORRETIVA" -> WorkOrderType.CORRETIVA;
            case "PREVENTIVA" -> WorkOrderType.PREVENTIVA;
            default -> throw new BusinessException("Tipo de OT inválido: " + type);
        };

        WorkOrder wo = new WorkOrder(null, woType, priority, title, asset);
        woRepository.save(wo);
        return wo;
    }

    /**
     * Obter uma OT pelo ID.
     */
    public WorkOrder getWorkOrderById(Long id) {
        return woRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Ordem de trabalho não encontrada com ID: " + id));
    }

    /**
     * Obter todas as OTs.
     */
    public List<WorkOrder> getAllWorkOrders() {
        return woRepository.findAll();
    }

    /**
     * Obter OTs de um ativo específico.
     */
    public List<WorkOrder> getWorkOrdersByAssetId(Long assetId) {
        assetRepository.findById(assetId)
                .orElseThrow(() -> new BusinessException("Ativo não encontrado com ID: " + assetId));
        return woRepository.findByAssetId(assetId);
    }

    /**
     * Alterar o estado de uma OT.
     */
    public void changeWorkOrderStatus(Long id, String newStatus) {
        WorkOrder wo = getWorkOrderById(id);

        WorkOrderStatus status = switch (newStatus) {
            case "ABERTA" -> WorkOrderStatus.ABERTA;
            case "PLANEADA" -> WorkOrderStatus.PLANEADA;
            case "EM_EXECUCAO" -> WorkOrderStatus.EM_EXECUCAO;
            case "CONCLUIDA" -> WorkOrderStatus.CONCLUIDA;
            case "CANCELADA" -> WorkOrderStatus.CANCELADA;
            default -> throw new BusinessException("Estado de OT inválido: " + newStatus);
        };

        wo.setStatus(status);
        woRepository.update(wo);
    }

    /**
     * Alternar a prioridade de uma OT.
     */
    public void changePriority(Long id, int newPriority) {
        WorkOrder wo = getWorkOrderById(id);
        if (newPriority < 1 || newPriority > 5) {
            throw new BusinessException("Prioridade deve estar entre 1 e 5");
        }
        wo.setPriority(newPriority);
        woRepository.update(wo);
    }

    /**
     * Eliminar uma OT.
     */
    public void deleteWorkOrder(Long id) {
        getWorkOrderById(id); // Validar que existe
        woRepository.delete(id);
    }
}
