package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa uma notificação do sistema.
 * RF14: Notificações por estados, prazos, níveis de stock.
 */
public class Notification implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Enumeração para tipos de notificações.
     */
    public enum NotificationType {
        STATE_CHANGE("Alteração de estado"),
        PRIORITY_CHANGE("Alteração de prioridade"),
        LOW_STOCK("Stock baixo"),
        DEADLINE("Prazo próximo"),
        MAINTENANCE("Manutenção preventiva"),
        INFO("Informação geral");

        private final String descricao;

        NotificationType(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private Long id;
    private NotificationType type;
    private String title;
    private String message;
    private String entityType;       // ASSET, WORK_ORDER, PART, PREVENTIVE_PLAN
    private Long entityId;
    private boolean read;
    private LocalDateTime createdAt;

    /**
     * Construtor com todos os parâmetros.
     */
    public Notification(Long id, NotificationType type, String title, String message,
                       String entityType, Long entityId, boolean read, LocalDateTime createdAt) {
        this.id = id;
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
        this.title = Objects.requireNonNull(title, "Título não pode ser nulo");
        this.message = message;
        this.entityType = entityType;
        this.entityId = entityId;
        this.read = read;
        this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
    }

    /**
     * Construtor simplificado para criar novas notificações.
     */
    public Notification(NotificationType type, String title, String message, String entityType, Long entityId) {
        this(null, type, title, message, entityType, entityId, false, LocalDateTime.now());
    }

    // Getters
    public Long getId() { return id; }
    public NotificationType getType() { return type; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getEntityType() { return entityType; }
    public Long getEntityId() { return entityId; }
    public boolean isRead() { return read; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void markAsRead() { this.read = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", read=" + read +
                ", createdAt=" + createdAt +
                '}';
    }
}
