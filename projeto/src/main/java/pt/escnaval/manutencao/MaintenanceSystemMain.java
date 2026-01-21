package pt.escnaval.manutencao;

import pt.escnaval.manutencao.aplicacao.*;
import pt.escnaval.manutencao.infraestrutura.*;
import pt.escnaval.manutencao.ui.MaintenanceConsole;

import java.nio.file.Paths;

/**
 * Classe principal do Sistema de Gestão da Manutenção.
 * Versão Nível 60: Consola com ficheiros, planos preventivos e notificações.
 */
public class MaintenanceSystemMain {

    public static void main(String[] args) {
        // Configurar diretório de dados
        String dataDir = Paths.get(System.getProperty("user.dir"), "data").toString();

        // Inicializar repositórios (persistência em ficheiros)
        AssetRepository assetRepository = new FileAssetRepository(dataDir);
        WorkOrderRepository workOrderRepository = new FileWorkOrderRepository(dataDir);
        PartRepository partRepository = new FilePartRepository(dataDir);
        UserRepository userRepository = new FileUserRepository(dataDir);
        PreventivePlanRepository preventivePlanRepository = new FilePreventivePlanRepository();
        NotificationRepository notificationRepository = new FileNotificationRepository();

        // Inicializar serviços
        AssetService assetService = new AssetService(assetRepository);
        WorkOrderService workOrderService = new WorkOrderService(workOrderRepository, assetRepository);
        InventoryService inventoryService = new InventoryService(partRepository);
        PreventivePlanService preventivePlanService = new PreventivePlanService(preventivePlanRepository);
        NotificationService notificationService = new NotificationService(notificationRepository);

        // Inicializar e executar consola
        MaintenanceConsole console = new MaintenanceConsole(assetService, workOrderService, inventoryService,
                                                            preventivePlanService, notificationService);
        console.run();
    }
}

