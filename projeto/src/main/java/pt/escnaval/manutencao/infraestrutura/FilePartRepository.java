package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Part;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação de repositório de peças com persistência em ficheiros.
 */
public class FilePartRepository implements PartRepository {
    private final String dataDir;
    private final String partsFile;
    private final AtomicLong idCounter;
    private final Map<Long, Part> cache;

    public FilePartRepository(String dataDir) {
        this.dataDir = dataDir;
        this.partsFile = Paths.get(dataDir, "parts.dat").toString();
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
    public void save(Part part) {
        if (part.getId() == null) {
            part.setId(idCounter.incrementAndGet());
        }
        idCounter.set(Math.max(idCounter.get(), part.getId()));
        cache.put(part.getId(), part);
        saveToFile();
    }

    @Override
    public Optional<Part> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Optional<Part> findBySku(String sku) {
        return cache.values().stream()
                .filter(p -> p.getSku().equals(sku))
                .findFirst();
    }

    @Override
    public List<Part> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void update(Part part) {
        if (part.getId() == null || !cache.containsKey(part.getId())) {
            throw new PersistenceException("Peça não encontrada para atualizar: " + part.getId());
        }
        cache.put(part.getId(), part);
        saveToFile();
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        saveToFile();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(partsFile))) {
            oos.writeObject(new HashMap<>(cache));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao guardar peças em ficheiro", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        Path path = Paths.get(partsFile);
        if (!Files.exists(path)) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(partsFile))) {
            Map<Long, Part> loaded = (Map<Long, Part>) ois.readObject();
            cache.putAll(loaded);
            if (!cache.isEmpty()) {
                idCounter.set(cache.keySet().stream().mapToLong(Long::longValue).max().orElse(0));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao carregar peças do ficheiro", e);
        }
    }
}
