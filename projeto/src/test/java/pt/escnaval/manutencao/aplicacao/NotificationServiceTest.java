package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.Notification;
import pt.escnaval.manutencao.infraestrutura.FileNotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para NotificationService.
 */
public class NotificationServiceTest {
    private NotificationService service;

    @BeforeEach
    public void setUp() {
        service = new NotificationService(new FileNotificationRepository());
    }

    @Test
    public void testCriarNotificacao() {
        Notification notif = service.criarNotificacao(
            Notification.NotificationType.STATE_CHANGE,
            "Alteração de Estado",
            "A OT foi alterada para CONCLUÍDA",
            "WORK_ORDER",
            1L
        );

        assertNotNull(notif.getId());
        assertEquals("Alteração de Estado", notif.getTitle());
        assertFalse(notif.isRead());
    }

    @Test
    public void testObterNotificacoesNaoLidas() {
        service.criarNotificacao(
            Notification.NotificationType.STATE_CHANGE,
            "Notif 1",
            "Mensagem 1",
            "WORK_ORDER",
            1L
        );

        service.criarNotificacao(
            Notification.NotificationType.LOW_STOCK,
            "Stock Baixo",
            "Stock baixo em peça X",
            "PART",
            2L
        );

        List<Notification> unread = service.obterNotificacoesNaoLidas();
        assertEquals(2, unread.size());
    }

    @Test
    public void testMarcarComoLida() {
        Notification notif = service.criarNotificacao(
            Notification.NotificationType.DEADLINE,
            "Prazo",
            "Prazo próximo",
            "WORK_ORDER",
            1L
        );

        service.marcarComoLida(notif.getId());

        var found = service.obter(notif.getId());
        assertTrue(found.get().isRead());
    }

    @Test
    public void testContarNaoLidas() {
        service.criarNotificacao(Notification.NotificationType.INFO, "T1", "M1", "ASSET", 1L);
        service.criarNotificacao(Notification.NotificationType.INFO, "T2", "M2", "ASSET", 2L);

        int count = service.contarNaoLidas();
        assertEquals(2, count);
    }
}
