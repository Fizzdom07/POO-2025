package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.PreventivePlan;
import pt.escnaval.manutencao.infraestrutura.PreventivePlanRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço de domínio para gestão de planos de manutenção preventiva.
 * RF04: Planeamento de Manutenção Preventiva.
 */
public class PreventivePlanService {
    private final PreventivePlanRepository repository;

    public PreventivePlanService(PreventivePlanRepository repository) {
        this.repository = repository;
    }

    /**
     * Criar um novo plano preventivo.
     */
    public PreventivePlan criarPlano(Long assetId, String nome, PreventivePlan.PreventivePolicies politica,
                                     Integer periodicidade, String unidade, Integer janelaAceitacao) {
        PreventivePlan plano = new PreventivePlan(
            null,
            assetId,
            nome,
            politica,
            periodicidade,
            unidade,
            janelaAceitacao,
            null,  // lastExecution
            true   // active
        );
        repository.save(plano);
        return plano;
    }

    /**
     * Obter plano por ID.
     */
    public Optional<PreventivePlan> obterPlano(Long id) {
        return repository.findById(id);
    }

    /**
     * Listar planos ativos para um ativo.
     */
    public List<PreventivePlan> listarPlanosAtivosParaAsset(Long assetId) {
        return repository.findAll().stream()
            .filter(p -> assetId.equals(p.getAssetId()) && p.isActive())
            .collect(Collectors.toList());
    }

    /**
     * Listar todos os planos ativos.
     */
    public List<PreventivePlan> listarPlanosAtivos() {
        return repository.findAll().stream()
            .filter(PreventivePlan::isActive)
            .collect(Collectors.toList());
    }

    /**
     * Listar todos os planos.
     */
    public List<PreventivePlan> listarTodos() {
        return repository.findAll();
    }

    /**
     * Atualizar plano.
     */
    public PreventivePlan atualizarPlano(Long id, String nome, PreventivePlan.PreventivePolicies politica,
                                        Integer periodicidade, String unidade, Integer janelaAceitacao) {
        Optional<PreventivePlan> planoOpt = repository.findById(id);
        if (planoOpt.isPresent()) {
            PreventivePlan plano = planoOpt.get();
            plano.setName(nome);
            plano.setPolicy(politica);
            plano.setPeriodicity(periodicidade);
            plano.setWindowDays(janelaAceitacao);
            plano.setUpdatedAt(LocalDateTime.now());
            repository.update(plano);
            return plano;
        }
        throw new IllegalArgumentException("Plano preventivo não encontrado: " + id);
    }

    /**
     * Registar execução de um plano (atualiza lastExecution).
     */
    public PreventivePlan registarExecucao(Long planoId) {
        Optional<PreventivePlan> planoOpt = repository.findById(planoId);
        if (planoOpt.isPresent()) {
            PreventivePlan plano = planoOpt.get();
            plano.setLastExecution(LocalDateTime.now());
            plano.setUpdatedAt(LocalDateTime.now());
            repository.update(plano);
            return plano;
        }
        throw new IllegalArgumentException("Plano preventivo não encontrado: " + planoId);
    }

    /**
     * Desativar um plano.
     */
    public void desativarPlano(Long planoId) {
        Optional<PreventivePlan> planoOpt = repository.findById(planoId);
        if (planoOpt.isPresent()) {
            PreventivePlan plano = planoOpt.get();
            plano.setActive(false);
            plano.setUpdatedAt(LocalDateTime.now());
            repository.update(plano);
        } else {
            throw new IllegalArgumentException("Plano preventivo não encontrado: " + planoId);
        }
    }

    /**
     * Ativar um plano.
     */
    public void ativarPlano(Long planoId) {
        Optional<PreventivePlan> planoOpt = repository.findById(planoId);
        if (planoOpt.isPresent()) {
            PreventivePlan plano = planoOpt.get();
            plano.setActive(true);
            plano.setUpdatedAt(LocalDateTime.now());
            repository.update(plano);
        } else {
            throw new IllegalArgumentException("Plano preventivo não encontrado: " + planoId);
        }
    }

    /**
     * Verificar se um plano está vencido (baseado em TEMPO).
     */
    public boolean estaVencido(PreventivePlan plano) {
        if (!plano.isActive() || plano.getLastExecution() == null) {
            return false;
        }

        if (plano.getPolicy() != PreventivePlan.PreventivePolicies.TEMPO) {
            return false;  // Apenas para TEMPO policy
        }

        LocalDateTime ultimaExecucao = plano.getLastExecution();
        LocalDateTime agora = LocalDateTime.now();
        
        // Calcular dias decorridos
        long diasDecorridos = java.time.temporal.ChronoUnit.DAYS.between(ultimaExecucao, agora);
        long periodo = plano.getPeriodicity();

        // Incluir janela de aceitação se existir
        if (plano.getWindowDays() != null) {
            periodo += plano.getWindowDays();
        }

        return diasDecorridos > periodo;
    }

    /**
     * Obter planos vencidos.
     */
    public List<PreventivePlan> obterPlanosVencidos() {
        return listarPlanosAtivos().stream()
            .filter(this::estaVencido)
            .collect(Collectors.toList());
    }

    /**
     * Deletar plano.
     */
    public void deletarPlano(Long planoId) {
        repository.delete(planoId);
    }
}
