package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.Asset;
import pt.escnaval.manutencao.infraestrutura.AssetRepository;

import java.util.List;
import java.util.Objects;

/**
 * Serviço de domínio para gestão de ativos.
 */
public class AssetService {
    private final AssetRepository repository;

    public AssetService(AssetRepository repository) {
        this.repository = Objects.requireNonNull(repository, "Repositório não pode ser nulo");
    }

    /**
     * Registar um novo ativo.
     */
    public Asset createAsset(String code, String name, int criticality, String status) {
        if (code == null || code.isBlank()) {
            throw new BusinessException("Código do ativo não pode ser vazio");
        }
        if (name == null || name.isBlank()) {
            throw new BusinessException("Nome do ativo não pode ser vazio");
        }
        if (criticality < 0 || criticality > 5) {
            throw new BusinessException("Criticidade deve estar entre 0 e 5");
        }

        // Verificar unicidade do código
        if (repository.findByCode(code).isPresent()) {
            throw new BusinessException("Ativo com código '" + code + "' já existe");
        }

        var assetStatus = switch (status) {
            case "ATIVO" -> pt.escnaval.manutencao.dominio.AssetStatus.ATIVO;
            case "INATIVO" -> pt.escnaval.manutencao.dominio.AssetStatus.INATIVO;
            case "OBSOLETO" -> pt.escnaval.manutencao.dominio.AssetStatus.OBSOLETO;
            default -> throw new BusinessException("Estado inválido: " + status);
        };

        Asset asset = new Asset(null, code, name, criticality, assetStatus);
        repository.save(asset);
        return asset;
    }

    /**
     * Obter um ativo pelo ID.
     */
    public Asset getAssetById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new BusinessException("Ativo não encontrado com ID: " + id));
    }

    /**
     * Obter todos os ativos.
     */
    public List<Asset> getAllAssets() {
        return repository.findAll();
    }

    /**
     * Atualizar um ativo existente.
     */
    public void updateAsset(Long id, String code, String name, int criticality, String status) {
        Asset asset = getAssetById(id);

        if (!code.isBlank()) {
            asset.setCode(code);
        }
        if (!name.isBlank()) {
            asset.setName(name);
        }
        if (criticality >= 0 && criticality <= 5) {
            asset.setCriticality(criticality);
        }
        if (!status.isBlank()) {
            var assetStatus = switch (status) {
                case "ATIVO" -> pt.escnaval.manutencao.dominio.AssetStatus.ATIVO;
                case "INATIVO" -> pt.escnaval.manutencao.dominio.AssetStatus.INATIVO;
                case "OBSOLETO" -> pt.escnaval.manutencao.dominio.AssetStatus.OBSOLETO;
                default -> throw new BusinessException("Estado inválido: " + status);
            };
            asset.setStatus(assetStatus);
        }

        repository.update(asset);
    }

    /**
     * Eliminar um ativo.
     */
    public void deleteAsset(Long id) {
        getAssetById(id); // Validar que existe
        repository.delete(id);
    }
}
