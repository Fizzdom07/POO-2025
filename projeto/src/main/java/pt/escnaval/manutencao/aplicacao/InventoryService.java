package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.Part;
import pt.escnaval.manutencao.infraestrutura.PartRepository;

import java.util.List;
import java.util.Objects;

/**
 * Serviço de domínio para gestão de peças e inventário.
 */
public class InventoryService {
    private final PartRepository repository;

    public InventoryService(PartRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repositório não pode ser nulo");
    }

    /**
     * Registar uma nova peça.
     */
    public Part registerPart(String sku, String name, String unit, double reorderPoint) {
        if (sku == null || sku.isBlank()) {
            throw new BusinessException("SKU não pode ser vazio");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessException("Nome da peça não pode ser vazio");
        }
        if (unit == null || unit.isBlank()) {
            throw new BusinessException("Unidade não pode ser vazia");
        }
        if (reorderPoint < 0) {
            throw new BusinessException("Ponto de reposição não pode ser negativo");
        }

        // Verificar unicidade do SKU
        if (repository.findBySku(sku).isPresent()) {
            throw new BusinessException("Peça com SKU '" + sku + "' já existe");
        }

        Part part = new Part(null, sku, name, unit, reorderPoint);
        repository.save(part);
        return part;
    }

    /**
     * Obter uma peça pelo ID.
     */
    public Part getPartById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Peça não encontrada com ID: " + id));
    }

    /**
     * Obter uma peça pelo SKU.
     */
    public Part getPartBySku(String sku) {
        return repository.findBySku(sku)
                .orElseThrow(() -> new BusinessException("Peça não encontrada com SKU: " + sku));
    }

    /**
     * Obter todas as peças.
     */
    public List<Part> getAllParts() {
        return repository.findAll();
    }

    /**
     * Atualizar uma peça existente.
     */
    public void updatePart(Long id, String name, String unit, double reorderPoint) {
        Part part = getPartById(id);

        if (!name.isBlank()) {
            part.setName(name);
        }
        if (!unit.isBlank()) {
            part.setUnit(unit);
        }
        if (reorderPoint >= 0) {
            part.setReorderPoint(reorderPoint);
        }

        repository.update(part);
    }

    /**
     * Eliminar uma peça.
     */
    public void deletePart(Long id) {
        getPartById(id); // Validar que existe
        repository.delete(id);
    }
}
