package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.WorkOrder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação de repositório de ordens de trabalho com persistência em ficheiros.
 */
public class FileWorkOrderRepository implements WorkOrderRepository {
    private final String dataDir;
    private final String workOrdersFile;
    private final AtomicLong idCounter;
    private final Map<Long, WorkOrder> cache;

    public FileWorkOrderRepository(String dataDir) {
        this.dataDir = dataDir;
        this.workOrdersFile = Paths.get(dataDir, "workorders.dat").toString();
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
    public void save(WorkOrder workOrder) {
        if (workOrder.getId() == null) {
            workOrder.setId(idCounter.incrementAndGet());
        }
        idCounter.set(Math.max(idCounter.get(), workOrder.getId()));
        cache.put(workOrder.getId(), workOrder);
        saveToFile();
    }

    @Override
    public Optional<WorkOrder> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public List<WorkOrder> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public List<WorkOrder> findByAssetId(Long assetId) {
        return cache.values().stream()
                .filter(wo -> wo.getAssetId() != null && wo.getAssetId().equals(assetId))
                .toList();
    }

    @Override
    public void update(WorkOrder workOrder) {
        if (workOrder.getId() == null || !cache.containsKey(workOrder.getId())) {
            throw new PersistenceException("Ordem de trabalho não encontrada para atualizar: " + workOrder.getId());
        }
        cache.put(workOrder.getId(), workOrder);
        saveToFile();
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        saveToFile();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(workOrdersFile))) {
            oos.writeObject(new HashMap<>(cache));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao guardar ordens de trabalho em ficheiro", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        Path path = Paths.get(workOrdersFile);
        if (!Files.exists(path)) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(workOrdersFile))) {
            Map<Long, WorkOrder> loaded = (Map<Long, WorkOrder>) ois.readObject();
            cache.putAll(loaded);
            if (!cache.isEmpty()) {
                idCounter.set(cache.keySet().stream().mapToLong(Long::longValue).max().orElse(0));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao carregar ordens de trabalho do ficheiro", e);
        }
    }
}
