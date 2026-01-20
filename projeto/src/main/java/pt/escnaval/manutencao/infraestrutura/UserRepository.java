package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.User;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de utilizadores.
 */
public interface UserRepository {
    void save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void update(User user);
    void delete(Long id);
}
