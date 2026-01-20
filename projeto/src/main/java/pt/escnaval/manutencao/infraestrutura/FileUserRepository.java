package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Implementação de repositório de utilizadores com persistência em ficheiros.
 */
public class FileUserRepository implements UserRepository {
    private final String dataDir;
    private final String usersFile;
    private final AtomicLong idCounter;
    private final Map<Long, User> cache;

    public FileUserRepository(String dataDir) {
        this.dataDir = dataDir;
        this.usersFile = Paths.get(dataDir, "users.dat").toString();
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
    public void save(User user) {
        if (user.getId() == null) {
            user.setId(idCounter.incrementAndGet());
        }
        idCounter.set(Math.max(idCounter.get(), user.getId()));
        cache.put(user.getId(), user);
        saveToFile();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(cache.get(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return cache.values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(cache.values());
    }

    @Override
    public void update(User user) {
        if (user.getId() == null || !cache.containsKey(user.getId())) {
            throw new PersistenceException("Utilizador não encontrado para atualizar: " + user.getId());
        }
        cache.put(user.getId(), user);
        saveToFile();
    }

    @Override
    public void delete(Long id) {
        cache.remove(id);
        saveToFile();
    }

    private void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(usersFile))) {
            oos.writeObject(new HashMap<>(cache));
        } catch (IOException e) {
            throw new PersistenceException("Erro ao guardar utilizadores em ficheiro", e);
        }
    }

    @SuppressWarnings("unchecked")
    private void loadFromFile() {
        Path path = Paths.get(usersFile);
        if (!Files.exists(path)) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(usersFile))) {
            Map<Long, User> loaded = (Map<Long, User>) ois.readObject();
            cache.putAll(loaded);
            if (!cache.isEmpty()) {
                idCounter.set(cache.keySet().stream().mapToLong(Long::longValue).max().orElse(0));
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new PersistenceException("Erro ao carregar utilizadores do ficheiro", e);
        }
    }
}
