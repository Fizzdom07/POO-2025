package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Notification;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação file-based do repositório de notificações.
 * Versão Nível 60: Persistência de dados com serialização.
 */
public class FileNotificationRepository implements NotificationRepository {
    private static final String DATA_FILE = "data/notifications.dat";
    private Map<Long, Notification> notifications = new HashMap<>();
    private AtomicLong idCounter = new AtomicLong(0);

    public FileNotificationRepository() {
        load();
    }

    @Override
    public void save(Notification notification) {
        if (notification.getId() == null) {
            notification.setId(idCounter.incrementAndGet());
        }
        notifications.put(notification.getId(), notification);
        persist();
    }

    @Override
    public void update(Notification notification) {
        if (notification.getId() != null && notifications.containsKey(notification.getId())) {
            notifications.put(notification.getId(), notification);
            persist();
        }
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return Optional.ofNullable(notifications.get(id));
    }

    @Override
    public List<Notification> findAll() {
        return new ArrayList<>(notifications.values());
    }

    @Override
    public void delete(Long id) {
        notifications.remove(id);
        persist();
    }

    private void persist() {
        try {
            File dir = new File("data");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
                oos.writeObject(notifications);
                oos.writeObject(idCounter.get());
            }
        } catch (IOException e) {
            System.err.println("Erro ao persistir notificações: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void load() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                notifications = (Map<Long, Notification>) ois.readObject();
                idCounter = new AtomicLong(ois.readLong());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar notificações: " + e.getMessage());
                notifications = new HashMap<>();
            }
        }
    }
}
