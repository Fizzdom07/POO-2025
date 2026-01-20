package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidade que representa uma ordem de trabalho.
 */
public class WorkOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private WorkOrderType type;
    private WorkOrderStatus status;
    private int priority; // 1..5
    private String title;
    private String description;
    private transient Asset asset;
    private transient User requester; // optional
    private transient User assignee;  // optional
    private LocalDateTime plannedStart; // optional
    private LocalDateTime plannedEnd;   // optional
    private LocalDateTime actualStart;  // optional
    private LocalDateTime actualEnd;    // optional
    private double totalCost;
    private LocalDateTime createdAt;
    private List<String> historyNotes;

    public WorkOrder(Long id, WorkOrderType type, int priority, String title, Asset asset) {
        this.id = id;
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Prioridade deve estar entre 1 e 5");
        }
        this.priority = priority;
        this.title = Objects.requireNonNull(title, "Título não pode ser nulo");
        this.asset = Objects.requireNonNull(asset, "Ativo não pode ser nulo");
        this.status = WorkOrderStatus.ABERTA;
        this.totalCost = 0.0;
        this.createdAt = LocalDateTime.now();
        this.historyNotes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WorkOrderType getType() {
        return type;
    }

    public void setType(WorkOrderType type) {
        this.type = Objects.requireNonNull(type, "Tipo não pode ser nulo");
    }

    public WorkOrderStatus getStatus() {
        return status;
    }

    public void setStatus(WorkOrderStatus status) {
        this.status = Objects.requireNonNull(status, "Estado não pode ser nulo");
        addHistoryNote("Status alterado para: " + status.getDescricao());
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 1 || priority > 5) {
            throw new IllegalArgumentException("Prioridade deve estar entre 1 e 5");
        }
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = Objects.requireNonNull(title, "Título não pode ser nulo");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = Objects.requireNonNull(asset, "Ativo não pode ser nulo");
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
        if (assignee != null) {
            addHistoryNote("Atribuído a: " + assignee.getName());
        }
    }

    public LocalDateTime getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(LocalDateTime plannedStart) {
        this.plannedStart = plannedStart;
    }

    public LocalDateTime getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(LocalDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public LocalDateTime getActualStart() {
        return actualStart;
    }

    public void setActualStart(LocalDateTime actualStart) {
        this.actualStart = actualStart;
    }

    public LocalDateTime getActualEnd() {
        return actualEnd;
    }

    public void setActualEnd(LocalDateTime actualEnd) {
        this.actualEnd = actualEnd;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        if (totalCost < 0) {
            throw new IllegalArgumentException("Custo não pode ser negativo");
        }
        this.totalCost = totalCost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<String> getHistoryNotes() {
        return new ArrayList<>(historyNotes);
    }

    public void addHistoryNote(String note) {
        if (note != null && !note.isBlank()) {
            this.historyNotes.add(LocalDateTime.now() + " - " + note);
        }
    }

    @Override
    public String toString() {
        return "WorkOrder{" +
                "id=" + id +
                ", type=" + type +
                ", status=" + status +
                ", priority=" + priority +
                ", title='" + title + '\'' +
                ", asset=" + asset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder that = (WorkOrder) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
