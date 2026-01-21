package pt.escnaval.manutencao.dominio;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade que representa um plano de manutenção preventiva.
 * RF04: Planejamento de Manutenção Preventiva
 */
public class PreventivePlan implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Enumeração para políticas de manutenção preventiva.
     */
    public enum PreventivePolicies {
        TEMPO("Baseado em período de tempo"),
        USO("Baseado em horas/ciclos de uso"),
        HIBRIDA("Combinação de tempo e uso");

        private final String descricao;

        PreventivePolicies(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private Long id;
    private Long assetId;
    private String name;
    private PreventivePolicies policy;
    private Integer periodicity;
    private String unit;              // ex: DAYS, HOURS, CYCLES
    private Integer windowDays;        // Janela de tolerância em dias
    private LocalDateTime lastExecution;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Construtor com todos os parâmetros.
     */
    public PreventivePlan(Long id, Long assetId, String name, PreventivePolicies policy,
                         Integer periodicity, String unit, Integer windowDays,
                         LocalDateTime lastExecution, boolean active) {
        this.id = id;
        this.assetId = Objects.requireNonNull(assetId, "Asset ID não pode ser nulo");
        this.name = Objects.requireNonNull(name, "Nome não pode ser nulo");
        this.policy = Objects.requireNonNull(policy, "Política não pode ser nula");
        this.periodicity = Objects.requireNonNull(periodicity, "Periodicidade não pode ser nula");
        this.unit = Objects.requireNonNull(unit, "Unidade não pode ser nula");
        this.windowDays = windowDays;
        this.lastExecution = lastExecution;
        this.active = active;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters
    public Long getId() { return id; }
    public Long getAssetId() { return assetId; }
    public String getName() { return name; }
    public PreventivePolicies getPolicy() { return policy; }
    public Integer getPeriodicity() { return periodicity; }
    public String getUnit() { return unit; }
    public Integer getWindowDays() { return windowDays; }
    public LocalDateTime getLastExecution() { return lastExecution; }
    public boolean isActive() { return active; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setters para atualização
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = Objects.requireNonNull(name); }
    public void setPolicy(PreventivePolicies policy) { this.policy = Objects.requireNonNull(policy); }
    public void setPeriodicity(Integer periodicity) { this.periodicity = Objects.requireNonNull(periodicity); }
    public void setWindowDays(Integer windowDays) { this.windowDays = windowDays; }
    public void setLastExecution(LocalDateTime lastExecution) { this.lastExecution = lastExecution; }
    public void setActive(boolean active) { this.active = active; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PreventivePlan that = (PreventivePlan) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PreventivePlan{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", policy=" + policy +
                ", periodicity=" + periodicity +
                ", unit='" + unit + '\'' +
                ", active=" + active +
                '}';
    }
}
