package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.Notification;
import pt.escnaval.manutencao.infraestrutura.NotificationRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço de domínio para gestão de notificações.
 * RF14: Notificações por mudanças de estado, prazos, níveis de stock.
 */
public class NotificationService {
    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    /**
     * Criar uma nova notificação.
     */
    public Notification criarNotificacao(Notification.NotificationType tipo, String titulo, 
                                        String mensagem, String entidade, Long entidadeId) {
        Notification notif = new Notification(tipo, titulo, mensagem, entidade, entidadeId);
        repository.save(notif);
        return notif;
    }

    /**
     * Obter notificações não lidas.
     */
    public List<Notification> obterNotificacoesNaoLidas() {
        return repository.findAll().stream()
            .filter(n -> !n.isRead())
            .collect(Collectors.toList());
    }

    /**
     * Marcar notificação como lida.
     */
    public void marcarComoLida(Long notificacaoId) {
        Optional<Notification> notif = repository.findById(notificacaoId);
        if (notif.isPresent()) {
            notif.get().markAsRead();
            repository.update(notif.get());
        }
    }

    /**
     * Criar alerta de stock baixo.
     */
    public Notification criarAlertaStockBaixo(String nomePeca, Long partId) {
        return criarNotificacao(
            Notification.NotificationType.LOW_STOCK,
            "Stock Baixo: " + nomePeca,
            "O nível de stock da peça " + nomePeca + " atingiu o ponto de reposição.",
            "PART",
            partId
        );
    }

    /**
     * Criar alerta de mudança de estado.
     */
    public Notification criarAlertaMudancaEstado(String descricaoOT, String estadoNovo, Long otId) {
        return criarNotificacao(
            Notification.NotificationType.STATE_CHANGE,
            "Mudança de Estado: " + descricaoOT,
            "A OT foi alterada para estado: " + estadoNovo,
            "WORK_ORDER",
            otId
        );
    }

    /**
     * Criar alerta de prazo próximo.
     */
    public Notification criarAlertaPrazo(String descricaoOT, Long otId) {
        return criarNotificacao(
            Notification.NotificationType.DEADLINE,
            "Prazo Próximo: " + descricaoOT,
            "O prazo para esta OT está a aproximar-se.",
            "WORK_ORDER",
            otId
        );
    }

    /**
     * Criar alerta de manutenção preventiva.
     */
    public Notification criarAlertaManutencaoPreventiva(String nomePlano, Long planId) {
        return criarNotificacao(
            Notification.NotificationType.MAINTENANCE,
            "Manutenção Preventiva: " + nomePlano,
            "O plano de manutenção preventiva '" + nomePlano + "' está vencido.",
            "PREVENTIVE_PLAN",
            planId
        );
    }

    /**
     * Obter todas as notificações.
     */
    public List<Notification> obterTodas() {
        return repository.findAll();
    }

    /**
     * Obter notificação por ID.
     */
    public Optional<Notification> obter(Long id) {
        return repository.findById(id);
    }

    /**
     * Obter notificações de uma entidade específica.
     */
    public List<Notification> obterPorEntidade(String tipoEntidade, Long entidadeId) {
        return repository.findAll().stream()
            .filter(n -> tipoEntidade.equals(n.getEntityType()) && entidadeId.equals(n.getEntityId()))
            .collect(Collectors.toList());
    }

    /**
     * Marcar todas as notificações como lidas.
     */
    public void marcarTodasComoLidas() {
        repository.findAll().stream()
            .filter(n -> !n.isRead())
            .forEach(n -> {
                n.markAsRead();
                repository.update(n);
            });
    }

    /**
     * Contar notificações não lidas.
     */
    public int contarNaoLidas() {
        return (int) repository.findAll().stream()
            .filter(n -> !n.isRead())
            .count();
    }
}
