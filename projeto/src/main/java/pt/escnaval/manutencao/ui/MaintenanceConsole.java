package pt.escnaval.manutencao.ui;

import pt.escnaval.manutencao.aplicacao.*;
import pt.escnaval.manutencao.dominio.*;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Interface de consola para o sistema de gestão de manutenção.
 * Versão Nível 60: Menus adicionais para planos preventivos e notificações.
 */
public class MaintenanceConsole {
    private final AssetService assetService;
    private final WorkOrderService workOrderService;
    private final InventoryService inventoryService;
    private final PreventivePlanService preventivePlanService;
    private final NotificationService notificationService;
    private final Scanner scanner;

    public MaintenanceConsole(AssetService assetService, WorkOrderService workOrderService,
                              InventoryService inventoryService, PreventivePlanService preventivePlanService,
                              NotificationService notificationService) {
        this.assetService = assetService;
        this.workOrderService = workOrderService;
        this.inventoryService = inventoryService;
        this.preventivePlanService = preventivePlanService;
        this.notificationService = notificationService;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Executar o menu principal do sistema.
     */
    public void run() {
        System.out.println("\n========================================");
        System.out.println("Bem-vindo ao Sistema de Gestão da Manutenção");
        System.out.println("Escola Naval - POO 2025/2026 - Nível 60");
        System.out.println("========================================\n");

        // Mostrar notificações não lidas no início
        mostrarNotificacoesNaoLidas();

        boolean running = true;
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> manageAssets();
                    case "2" -> manageWorkOrders();
                    case "3" -> manageInventory();
                    case "4" -> managePreventivePlans();
                    case "5" -> manageNotifications();
                    case "0" -> {
                        System.out.println("\nObrigado por usar o sistema. Até logo!");
                        running = false;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }
    }

    private void displayMainMenu() {
        int naoLidas = notificationService.contarNaoLidas();
        String badge = naoLidas > 0 ? " (" + naoLidas + " novas)" : "";

        System.out.println("\n--- Menu Principal ---");
        System.out.println("1. Gerir Ativos");
        System.out.println("2. Gerir Ordens de Trabalho");
        System.out.println("3. Gerir Inventário de Peças");
        System.out.println("4. Planos de Manutenção Preventiva");
        System.out.println("5. Notificações" + badge);
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // ==================== ATIVOS ====================
    private void manageAssets() {
        boolean inAssetMenu = true;
        while (inAssetMenu) {
            System.out.println("\n--- Gestão de Ativos ---");
            System.out.println("1. Listar Ativos");
            System.out.println("2. Criar Ativo");
            System.out.println("3. Atualizar Ativo");
            System.out.println("4. Eliminar Ativo");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listAssets();
                case "2" -> createAsset();
                case "3" -> updateAsset();
                case "4" -> deleteAsset();
                case "0" -> inAssetMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listAssets() {
        List<Asset> assets = assetService.getAllAssets();
        if (assets.isEmpty()) {
            System.out.println("\nNenhum ativo registado.");
            return;
        }

        System.out.println("\n--- Lista de Ativos ---");
        System.out.println(String.format("%-5s %-10s %-20s %-12s %-10s", "ID", "Código", "Nome", "Criticidade", "Estado"));
        System.out.println("-".repeat(60));
        for (Asset asset : assets) {
            System.out.println(String.format("%-5d %-10s %-20s %-12d %-10s",
                    asset.getId(), asset.getCode(), asset.getName(),
                    asset.getCriticality(), asset.getStatus().getDescricao()));
        }
    }

    private void createAsset() {
        System.out.println("\n--- Criar Novo Ativo ---");
        System.out.print("Código: ");
        String code = scanner.nextLine().trim();
        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();
        System.out.print("Criticidade (0-5): ");
        int criticality = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Estado (ATIVO/INATIVO/OBSOLETO): ");
        String status = scanner.nextLine().trim();

        Asset asset = assetService.createAsset(code, name, criticality, status);
        System.out.println("✓ Ativo criado com sucesso! ID: " + asset.getId());
    }

    private void updateAsset() {
        System.out.print("\nID do Ativo a atualizar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Novo Código (deixar vazio para não alterar): ");
        String code = scanner.nextLine().trim();
        System.out.print("Novo Nome (deixar vazio para não alterar): ");
        String name = scanner.nextLine().trim();
        System.out.print("Nova Criticidade (-1 para não alterar): ");
        int criticality = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Novo Estado (deixar vazio para não alterar): ");
        String status = scanner.nextLine().trim();

        assetService.updateAsset(id, code, name, criticality, status);
        System.out.println("✓ Ativo atualizado com sucesso!");
    }

    private void deleteAsset() {
        System.out.print("\nID do Ativo a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Tem certeza? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            assetService.deleteAsset(id);
            System.out.println("✓ Ativo eliminado com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== ORDENS DE TRABALHO ====================
    private void manageWorkOrders() {
        boolean inWOMenu = true;
        while (inWOMenu) {
            System.out.println("\n--- Gestão de Ordens de Trabalho ---");
            System.out.println("1. Listar OTs");
            System.out.println("2. Criar OT");
            System.out.println("3. Ver Detalhes da OT");
            System.out.println("4. Alterar Estado");
            System.out.println("5. Alterar Prioridade");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listWorkOrders();
                case "2" -> createWorkOrder();
                case "3" -> viewWorkOrderDetails();
                case "4" -> changeWorkOrderStatus();
                case "5" -> changePriority();
                case "0" -> inWOMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listWorkOrders() {
        List<WorkOrder> workOrders = workOrderService.getAllWorkOrders();
        if (workOrders.isEmpty()) {
            System.out.println("\nNenhuma ordem de trabalho registada.");
            return;
        }

        System.out.println("\n--- Lista de Ordens de Trabalho ---");
        System.out.println(String.format("%-5s %-12s %-12s %-5s %-20s %-10s", "ID", "Tipo", "Estado", "Prio", "Título", "Ativo ID"));
        System.out.println("-".repeat(75));
        for (WorkOrder wo : workOrders) {
            System.out.println(String.format("%-5d %-12s %-12s %-5d %-20s %-10d",
                    wo.getId(), wo.getType().getDescricao(), wo.getStatus().getDescricao(),
                    wo.getPriority(), wo.getTitle(), wo.getAsset().getId()));
        }
    }

    private void createWorkOrder() {
        System.out.println("\n--- Criar Nova Ordem de Trabalho ---");
        System.out.print("Tipo (CORRETIVA/PREVENTIVA): ");
        String type = scanner.nextLine().trim();
        System.out.print("Prioridade (1-5): ");
        int priority = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("Título: ");
        String title = scanner.nextLine().trim();
        System.out.print("ID do Ativo: ");
        Long assetId = Long.parseLong(scanner.nextLine().trim());

        WorkOrder wo = workOrderService.createWorkOrder(type, priority, title, assetId);
        System.out.println("✓ OT criada com sucesso! ID: " + wo.getId());
    }

    private void viewWorkOrderDetails() {
        System.out.print("\nID da OT: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        WorkOrder wo = workOrderService.getWorkOrderById(id);
        System.out.println("\n--- Detalhes da OT ---");
        System.out.println("ID: " + wo.getId());
        System.out.println("Tipo: " + wo.getType().getDescricao());
        System.out.println("Estado: " + wo.getStatus().getDescricao());
        System.out.println("Prioridade: " + wo.getPriority());
        System.out.println("Título: " + wo.getTitle());
        System.out.println("Descrição: " + (wo.getDescription() != null ? wo.getDescription() : "N/A"));
        System.out.println("Ativo: " + wo.getAsset().getName() + " (ID: " + wo.getAsset().getId() + ")");
        System.out.println("Custo Total: " + wo.getTotalCost() + "€");
    }

    private void changeWorkOrderStatus() {
        System.out.print("\nID da OT: ");
        Long id = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Novo Estado (ABERTA/PLANEADA/EM_EXECUCAO/CONCLUIDA/CANCELADA): ");
        String status = scanner.nextLine().trim();

        workOrderService.changeWorkOrderStatus(id, status);
        System.out.println("✓ Estado alterado com sucesso!");
    }

    private void changePriority() {
        System.out.print("\nID da OT: ");
        Long id = Long.parseLong(scanner.nextLine().trim());
        System.out.print("Nova Prioridade (1-5): ");
        int priority = Integer.parseInt(scanner.nextLine().trim());

        workOrderService.changePriority(id, priority);
        System.out.println("✓ Prioridade alterada com sucesso!");
    }

    // ==================== INVENTÁRIO ====================
    private void manageInventory() {
        boolean inInventoryMenu = true;
        while (inInventoryMenu) {
            System.out.println("\n--- Gestão de Inventário ---");
            System.out.println("1. Listar Peças");
            System.out.println("2. Registar Peça");
            System.out.println("3. Atualizar Peça");
            System.out.println("4. Eliminar Peça");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listParts();
                case "2" -> registerPart();
                case "3" -> updatePart();
                case "4" -> deletePart();
                case "0" -> inInventoryMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listParts() {
        List<Part> parts = inventoryService.getAllParts();
        if (parts.isEmpty()) {
            System.out.println("\nNenhuma peça registada.");
            return;
        }

        System.out.println("\n--- Lista de Peças ---");
        System.out.println(String.format("%-5s %-15s %-25s %-10s %-12s", "ID", "SKU", "Nome", "Unidade", "Reposição"));
        System.out.println("-".repeat(70));
        for (Part part : parts) {
            System.out.println(String.format("%-5d %-15s %-25s %-10s %-12.2f",
                    part.getId(), part.getSku(), part.getName(),
                    part.getUnit(), part.getReorderPoint()));
        }
    }

    private void registerPart() {
        System.out.println("\n--- Registar Nova Peça ---");
        System.out.print("SKU: ");
        String sku = scanner.nextLine().trim();
        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();
        System.out.print("Unidade: ");
        String unit = scanner.nextLine().trim();
        System.out.print("Ponto de Reposição: ");
        double reorderPoint = Double.parseDouble(scanner.nextLine().trim());

        Part part = inventoryService.registerPart(sku, name, unit, reorderPoint);
        System.out.println("✓ Peça registada com sucesso! ID: " + part.getId());
    }

    private void updatePart() {
        System.out.print("\nID da Peça a atualizar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Novo Nome (deixar vazio para não alterar): ");
        String name = scanner.nextLine().trim();
        System.out.print("Nova Unidade (deixar vazio para não alterar): ");
        String unit = scanner.nextLine().trim();
        System.out.print("Novo Ponto de Reposição (-1 para não alterar): ");
        double reorderPoint = Double.parseDouble(scanner.nextLine().trim());

        inventoryService.updatePart(id, name, unit, reorderPoint);
        System.out.println("✓ Peça atualizada com sucesso!");
    }

    private void deletePart() {
        System.out.print("\nID da Peça a eliminar: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Tem certeza? (S/N): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("S")) {
            inventoryService.deletePart(id);
            System.out.println("✓ Peça eliminada com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    // ==================== PLANOS PREVENTIVOS ====================
    private void managePreventivePlans() {
        boolean inPlanMenu = true;
        while (inPlanMenu) {
            System.out.println("\n--- Gestão de Planos Preventivos ---");
            System.out.println("1. Listar Planos Ativos");
            System.out.println("2. Criar Novo Plano");
            System.out.println("3. Registar Execução de Plano");
            System.out.println("4. Ver Planos Vencidos");
            System.out.println("5. Desativar Plano");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listActivePlans();
                case "2" -> createPreventivePlan();
                case "3" -> registerPlanExecution();
                case "4" -> listExpiredPlans();
                case "5" -> deactivatePlan();
                case "0" -> inPlanMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void listActivePlans() {
        List<PreventivePlan> plans = preventivePlanService.listarPlanosAtivos();
        if (plans.isEmpty()) {
            System.out.println("\nNenhum plano preventivo ativo.");
            return;
        }

        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│ Planos de Manutenção Preventiva Ativos                  │");
        System.out.println("├──────┬──────────────────┬────────────┬──────────────────┤");
        System.out.println("│ ID   │ Nome             │ Política   │ Periodicidade    │");
        System.out.println("├──────┼──────────────────┼────────────┼──────────────────┤");

        for (PreventivePlan plan : plans) {
            System.out.printf("│ %-4d │ %-16s │ %-10s │ %3d %s         │%n",
                plan.getId(),
                truncate(plan.getName(), 16),
                plan.getPolicy().name(),
                plan.getPeriodicity(),
                plan.getUnit()
            );
        }
        System.out.println("└──────┴──────────────────┴────────────┴──────────────────┘");
    }

    private void createPreventivePlan() {
        System.out.print("\nID do Ativo: ");
        Long assetId = Long.parseLong(scanner.nextLine().trim());

        System.out.print("Nome do Plano: ");
        String name = scanner.nextLine().trim();

        System.out.println("Política: 1=TEMPO, 2=USO, 3=HIBRIDA");
        System.out.print("Escolha: ");
        int policyChoice = Integer.parseInt(scanner.nextLine().trim());
        PreventivePlan.PreventivePolicies policy = policyChoice == 1 ? PreventivePlan.PreventivePolicies.TEMPO :
                                                   policyChoice == 2 ? PreventivePlan.PreventivePolicies.USO :
                                                   PreventivePlan.PreventivePolicies.HIBRIDA;

        System.out.print("Periodicidade (dias/horas): ");
        Integer periodicity = Integer.parseInt(scanner.nextLine().trim());

        System.out.print("Unidade (DAYS/HOURS/CYCLES): ");
        String unit = scanner.nextLine().trim();

        System.out.print("Janela de Aceitação (dias): ");
        Integer window = Integer.parseInt(scanner.nextLine().trim());

        PreventivePlan plan = preventivePlanService.criarPlano(assetId, name, policy, periodicity, unit, window);
        System.out.println("✓ Plano criado com sucesso! ID: " + plan.getId());
    }

    private void registerPlanExecution() {
        System.out.print("\nID do Plano: ");
        Long planId = Long.parseLong(scanner.nextLine().trim());

        PreventivePlan plan = preventivePlanService.registarExecucao(planId);
        notificationService.criarAlertaManutencaoPreventiva(plan.getName(), planId);
        System.out.println("✓ Execução registada com sucesso!");
    }

    private void listExpiredPlans() {
        List<PreventivePlan> expired = preventivePlanService.obterPlanosVencidos();
        if (expired.isEmpty()) {
            System.out.println("\nNenhum plano preventivo vencido.");
            return;
        }

        System.out.println("\n⚠️  Planos Preventivos Vencidos:");
        for (PreventivePlan plan : expired) {
            System.out.printf("  - [ID %d] %s (Política: %s, Período: %d %s)%n",
                plan.getId(), plan.getName(), plan.getPolicy().name(),
                plan.getPeriodicity(), plan.getUnit());
        }
    }

    private void deactivatePlan() {
        System.out.print("\nID do Plano a desativar: ");
        Long planId = Long.parseLong(scanner.nextLine().trim());

        preventivePlanService.desativarPlano(planId);
        System.out.println("✓ Plano desativado com sucesso!");
    }

    // ==================== NOTIFICAÇÕES ====================
    private void manageNotifications() {
        boolean inNotifMenu = true;
        while (inNotifMenu) {
            int unread = notificationService.contarNaoLidas();
            System.out.println("\n--- Notificações (" + unread + " não lidas) ---");
            System.out.println("1. Ver Notificações Não Lidas");
            System.out.println("2. Ver Todas as Notificações");
            System.out.println("3. Marcar como Lida");
            System.out.println("4. Marcar Todas como Lidas");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");

            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listUnreadNotifications();
                case "2" -> listAllNotifications();
                case "3" -> markNotificationAsRead();
                case "4" -> markAllAsRead();
                case "0" -> inNotifMenu = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void mostrarNotificacoesNaoLidas() {
        List<Notification> unread = notificationService.obterNotificacoesNaoLidas();
        if (!unread.isEmpty()) {
            System.out.println("\n🔔 Tem " + unread.size() + " notificação(ões) não lida(s):");
            for (Notification n : unread.stream().limit(3).toList()) {
                System.out.println("  [" + n.getType().getDescricao() + "] " + n.getTitle());
            }
            if (unread.size() > 3) {
                System.out.println("  ... e mais " + (unread.size() - 3));
            }
        }
    }

    private void listUnreadNotifications() {
        List<Notification> notifications = notificationService.obterNotificacoesNaoLidas();
        if (notifications.isEmpty()) {
            System.out.println("\nNenhuma notificação não lida.");
            return;
        }

        System.out.println("\n┌───────────────────────────────────────────────────────┐");
        System.out.println("│ Notificações Não Lidas                                │");
        System.out.println("├───────┬─────────────────┬────────────────────────────┤");
        System.out.println("│ ID    │ Tipo            │ Título                     │");
        System.out.println("├───────┼─────────────────┼────────────────────────────┤");

        for (Notification notif : notifications) {
            System.out.printf("│ %-5d │ %-15s │ %-26s │%n",
                notif.getId(),
                truncate(notif.getType().getDescricao(), 15),
                truncate(notif.getTitle(), 26)
            );
        }
        System.out.println("└───────┴─────────────────┴────────────────────────────┘");
    }

    private void listAllNotifications() {
        List<Notification> notifications = notificationService.obterTodas();
        if (notifications.isEmpty()) {
            System.out.println("\nNenhuma notificação registada.");
            return;
        }

        System.out.println("\n┌───────────────────────────────────────────────────────┐");
        System.out.println("│ Todas as Notificações                                 │");
        System.out.println("├───────┬──────┬──────────────┬──────────────────────┤");
        System.out.println("│ ID    │ Lida │ Tipo         │ Título               │");
        System.out.println("├───────┼──────┼──────────────┼──────────────────────┤");

        for (Notification notif : notifications) {
            String lida = notif.isRead() ? "✓" : "○";
            System.out.printf("│ %-5d │ %s    │ %-12s │ %-20s │%n",
                notif.getId(),
                lida,
                truncate(notif.getType().getDescricao(), 12),
                truncate(notif.getTitle(), 20)
            );
        }
        System.out.println("└───────┴──────┴──────────────┴──────────────────────┘");
    }

    private void markNotificationAsRead() {
        System.out.print("\nID da Notificação: ");
        Long id = Long.parseLong(scanner.nextLine().trim());

        notificationService.marcarComoLida(id);
        System.out.println("✓ Notificação marcada como lida!");
    }

    private void markAllAsRead() {
        notificationService.marcarTodasComoLidas();
        System.out.println("✓ Todas as notificações marcadas como lidas!");
    }

    // ==================== UTILITÁRIOS ====================
    private String truncate(String str, int length) {
        if (str == null) return "";
        return str.length() > length ? str.substring(0, length - 1) + "…" : String.format("%-" + length + "s", str);
    }
}

