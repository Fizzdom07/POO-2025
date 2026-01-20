package pt.escnaval.manutencao.aplicacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.escnaval.manutencao.dominio.Asset;
import pt.escnaval.manutencao.dominio.AssetStatus;
import pt.escnaval.manutencao.infraestrutura.AssetRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para o serviço de ativos.
 */
public class AssetServiceTest {

    private AssetService assetService;
    private InMemoryAssetRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new InMemoryAssetRepository();
        assetService = new AssetService(repository);
    }

    @Test
    public void testCreateAssetSuccess() {
        Asset asset = assetService.createAsset("COMP001", "Computador", 3, "ATIVO");

        assertNotNull(asset);
        assertNotNull(asset.getId());
        assertEquals("COMP001", asset.getCode());
        assertEquals("Computador", asset.getName());
        assertEquals(3, asset.getCriticality());
    }

    @Test
    public void testCreateAssetDuplicateCode() {
        assetService.createAsset("PUMP001", "Bomba", 2, "ATIVO");

        assertThrows(BusinessException.class, () -> {
            assetService.createAsset("PUMP001", "Outra Bomba", 2, "ATIVO");
        }, "Deve lançar exceção para código duplicado");
    }

    @Test
    public void testCreateAssetInvalidCriticality() {
        assertThrows(BusinessException.class, () -> {
            assetService.createAsset("TEST001", "Teste", 10, "ATIVO");
        }, "Criticidade inválida");

        assertThrows(BusinessException.class, () -> {
            assetService.createAsset("TEST001", "Teste", -1, "ATIVO");
        }, "Criticidade inválida");
    }

    @Test
    public void testGetAssetById() {
        Asset created = assetService.createAsset("PUMP002", "Bomba 2", 2, "ATIVO");
        Asset retrieved = assetService.getAssetById(created.getId());

        assertEquals(created.getId(), retrieved.getId());
        assertEquals("PUMP002", retrieved.getCode());
    }

    @Test
    public void testGetAssetByIdNotFound() {
        assertThrows(BusinessException.class, () -> {
            assetService.getAssetById(999L);
        }, "Deve lançar exceção para ID inexistente");
    }

    @Test
    public void testGetAllAssets() {
        assetService.createAsset("PUMP001", "Bomba 1", 2, "ATIVO");
        assetService.createAsset("MOTOR001", "Motor", 3, "ATIVO");

        List<Asset> assets = assetService.getAllAssets();
        assertEquals(2, assets.size());
    }

    @Test
    public void testUpdateAsset() {
        Asset created = assetService.createAsset("PUMP001", "Bomba", 2, "ATIVO");
        assetService.updateAsset(created.getId(), "", "Bomba Atualizada", 4, "");

        Asset updated = assetService.getAssetById(created.getId());
        assertEquals("Bomba Atualizada", updated.getName());
        assertEquals(4, updated.getCriticality());
    }

    @Test
    public void testDeleteAsset() {
        Asset created = assetService.createAsset("PUMP001", "Bomba", 2, "ATIVO");
        assetService.deleteAsset(created.getId());

        assertThrows(BusinessException.class, () -> {
            assetService.getAssetById(created.getId());
        }, "Ativo deve estar deletado");
    }

    // Repositório em memória para testes
    public static class InMemoryAssetRepository implements AssetRepository {
        private final Map<Long, Asset> storage = new HashMap<>();
        private final AtomicLong idCounter = new AtomicLong(0);

        @Override
        public void save(Asset asset) {
            if (asset.getId() == null) {
                asset.setId(idCounter.incrementAndGet());
            }
            storage.put(asset.getId(), asset);
        }

        @Override
        public Optional<Asset> findById(Long id) {
            return Optional.ofNullable(storage.get(id));
        }

        @Override
        public Optional<Asset> findByCode(String code) {
            return storage.values().stream()
                    .filter(a -> a.getCode().equals(code))
                    .findFirst();
        }

        @Override
        public List<Asset> findAll() {
            return new ArrayList<>(storage.values());
        }

        @Override
        public void update(Asset asset) {
            if (!storage.containsKey(asset.getId())) {
                throw new IllegalArgumentException("Ativo não existe");
            }
            storage.put(asset.getId(), asset);
        }

        @Override
        public void delete(Long id) {
            storage.remove(id);
        }
    }
}
