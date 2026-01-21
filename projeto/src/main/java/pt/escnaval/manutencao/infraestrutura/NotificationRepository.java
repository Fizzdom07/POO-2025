package pt.escnaval.manutencao.infraestrutura;

import pt.escnaval.manutencao.dominio.Notification;
import java.util.List;
import java.util.Optional;

/**
 * Interface para persistência de notificações.
 */
public interface NotificationRepository {
    void save(Notification notification);
    void update(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findAll();
    void delete(Long id);
}
