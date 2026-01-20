package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Asset;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação de repositório de ativos com persistência em ficheiros.
 */
public class FileAssetRepository implements AssetRepository {
    private final String dataDir;
    private final String assetsFile;
    private final AtomicLong idCounter;
    private final Map<Long, Asset> cache;

    public FileAssetRepository(String dataDir) {
        this.dataDir = dataDir;
        this.assetsFile = Paths.get(dataDir, "assets.dat").toString();
        this.cache = new HashMap<>();
        this.idCounter = new AtomicLong(0);
        ensureDataDir();
        loadFromFile();
    }

    private void ensureDataDir() {
        try {
            Files.createDirectories(Paths.get(dataDir));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao criar diretório de dados: " + dataDir, e);
        }
    }

    @Override
    public void save(Asset asset) {
        if (asset.getId() == null) {
            asset.setId(idCounter.incrementAndGet());
        }
        idCounter.set(Math.max(idCounter.get(), asset.getId()));
        cache.put(asset.getId(), asset);
        saveToFile();
    }

    @Override
    public Optional<Asset> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Optional<Asset> findByCode(String code) {
        return cache.values().stream()
                .filter(a -> a.getCode().equals(code))
                .findFirst();
    }

    @Override
    public List<Asset> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void update(Asset asset) {
        if (asset.getId() == null || !cache.containsKey(asset.getId())) {
            throw new PersistenceException("Ativo não encontrado para atualizar: " + asset.getId());
        }
        cache.put(asset.getId(), asset);
        saveToFile();
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        saveToFile();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(assetsFile))) {
            oos.writeObject(new HashMap<>(cache));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao guardar ativos em ficheiro", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        Path path = Paths.get(assetsFile);
        if (!Files.exists(path)) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(assetsFile))) {
            Map<Long, Asset> loaded = (Map<Long, Asset>) ois.readObject();
            cache.putAll(loaded);
            if (!cache.isEmpty()) {
                idCounter.set(cache.keySet().stream().mapToLong(Long::longValue).max().orElse(0));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao carregar ativos do ficheiro", e);
        }
    }
}
