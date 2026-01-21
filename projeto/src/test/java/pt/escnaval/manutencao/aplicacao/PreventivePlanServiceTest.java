package pt.escnaval.manutencao.aplicacao;

import pt.escnaval.manutencao.dominio.PreventivePlan;
import pt.escnaval.manutencao.infraestrutura.FilePreventivePlanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para PreventivePlanService.
 */
public class PreventivePlanServiceTest {
    private PreventivePlanService service;

    @BeforeEach
    public void setUp() {
        service = new PreventivePlanService(new FilePreventivePlanRepository());
    }

    @Test
    public void testCriarPlano() {
        PreventivePlan plan = service.criarPlano(
            1L, "Inspeção Periódica", PreventivePlan.PreventivePolicies.TEMPO, 30, "DAYS", 5
        );

        assertNotNull(plan.getId());
        assertEquals("Inspeção Periódica", plan.getName());
        assertTrue(plan.isActive());
    }

    @Test
    public void testObterPlano() {
        PreventivePlan created = service.criarPlano(
            1L, "Teste", PreventivePlan.PreventivePolicies.USO, 100, "HOURS", 10
        );

        Optional<PreventivePlan> found = service.obterPlano(created.getId());
        assertTrue(found.isPresent());
        assertEquals(created.getId(), found.get().getId());
    }

    @Test
    public void testListarPlanosAtivos() {
        service.criarPlano(1L, "Plano 1", PreventivePlan.PreventivePolicies.TEMPO, 30, "DAYS", 5);
        service.criarPlano(2L, "Plano 2", PreventivePlan.PreventivePolicies.USO, 100, "HOURS", 10);

        var ativos = service.listarPlanosAtivos();
        assertEquals(2, ativos.size());
    }

    @Test
    public void testDesativarPlano() {
        PreventivePlan plan = service.criarPlano(
            1L, "Plano", PreventivePlan.PreventivePolicies.TEMPO, 30, "DAYS", 5
        );

        service.desativarPlano(plan.getId());
        Optional<PreventivePlan> after = service.obterPlano(plan.getId());
        assertFalse(after.get().isActive());
    }
}
