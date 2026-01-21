package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.PreventivePlan;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação file-based do repositório de planos preventivos.
 * Versão Nível 60: Persistência de dados com serialização.
 */
public class FilePreventivePlanRepository implements PreventivePlanRepository {
    private static final String DATA_FILE = "data/preventive_plans.dat";
    private Map<Long, PreventivePlan> plans = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    public FilePreventivePlanRepository() {
        load();
    }

    @Override
    public void save(PreventivePlan plan) {
        if (plan.getId() == null) {
            plan.setId(idCounter.incrementAndGet());
        }
        plans.put(plan.getId(), plan);
        persist();
    }

    @Override
    public void update(PreventivePlan plan) {
        if (plan.getId() != null && plans.containsKey(plan.getId())) {
            plans.put(plan.getId(), plan);
            persist();
        }
    }

    @Override
    public Optional<PreventivePlan> findById(Long id) {
        return Optional.ofNullable(plans.get(id));
    }

    @Override
    public List<PreventivePlan> findAll() {
        return new ArrayList<>(plans.values());
    }

    @Override
    public void delete(Long id) {
        plans.remove(id);
        persist();
    }

    private void persist() {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                oos.writeObject(plans);
                oos.writeObject(idCounter.get());
            }
        } catch (IOException e) {
            System.err.println("Erro ao persistir planos preventivos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                plans = (Map<Long, PreventivePlan>) ois.readObject();
                idCounter = new AtomicLong(ois.readLong());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar planos preventivos: " + e.getMessage());
                plans = new HashMap<>();
            }
        }
    }
}
