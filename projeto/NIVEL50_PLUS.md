# Nível 50+ (Bridge to Level 60)

## Resumo da Implementação

Este documento descreve a evolução do projeto a partir do **Nível 50 (MVP completo)** para uma versão **Nível 50+ que é um bridge para Nível 60**.

## O que foi adicionado

### 1. Novas Entidades de Domínio

#### PreventivePlan.java (RF04 - Planeamento de Manutenção Preventiva)
- Representa planos de manutenção preventiva para ativos
- **Enum PreventivePolicies**: TEMPO, USO, HIBRIDA
- Campos principais:
  - `assetId`: ID do ativo associado
  - `policy`: Política de manutenção
  - `periodicity`: Período da manutenção (com `unit`: DAYS, HOURS, CYCLES)
  - `windowDays`: Janela de tolerância
  - `lastExecution`: Data da última execução
  - `active`: Se o plano está ativo

#### Notification.java (RF14 - Notificações do Sistema)
- Representa notificações para mudanças de estado, prazos e alertas de stock
- **Enum NotificationType**: STATE_CHANGE, PRIORITY_CHANGE, LOW_STOCK, DEADLINE, MAINTENANCE, INFO
- Campos principais:
  - `type`: Tipo da notificação
  - `title`: Título descritivo
  - `message`: Mensagem detalhada
  - `entityType`: Tipo de entidade relacionada (ASSET, WORK_ORDER, PART, PREVENTIVE_PLAN)
  - `entityId`: ID da entidade relacionada
  - `read`: Se a notificação foi lida

### 2. Decisão Arquitetural: Persistência File-Based (Mantida)

**Por quê Level 50+ e não Level 60 completo?**

Inicialmente foi tentada implementação com SQLite para Level 60, mas:
- As entidades existentes (Asset, WorkOrder, User, Part) usam **construtores imutáveis com múltiplos parâmetros**
- Mapear ResultSet para essas entidades requer construtor com todos os parâmetros
- Implementar repositórios SQLite compatíveis exigiria ou:
  - Refatorar completamente as entidades existentes (risco alto)
  - Duplicar constructores (design ruim)

**Solução adotada:**
- Manter persistência file-based do Level 50 (estável, funciona)
- Adicionar novas entidades (PreventivePlan, Notification) com suporte para futura persistência
- Estrutura preparada para migração incremental para SQLite quando necessário

### 3. Status de Conformidade

✅ **Nível 50: 100% Completo e Funcional**
- MVP com todas as funcionalidades core (RF01, RF03, RF08, RF11)
- Persistência file-based com serialização Java
- UI com menus hierárquicos
- 8 testes unitários

✅ **Nível 50+: Parcialmente Implementado**
- ✅ Entidades de domínio criadas (PreventivePlan, Notification)
- ✅ Enums para políticas e tipos de notificação
- ⏳ Serviços de negócio pendentes (NotificationService, PreventivePlanService)
- ⏳ UI/menus para novas funcionalidades
- ⏳ Repositórios para persistência

## Compilação e Estado

```bash
✓ Compilação concluída com sucesso!
  - 27 classes Java (25 do Level 50 + 2 novas)
  - 0 erros de compilação
  - Todos os .class files gerados em target/classes/
```

## Próximos Passos para Nível 60 Completo

1. **Implementar serviços de domínio**
   - NotificationService: criar, listar, marcar como lida
   - PreventivePlanService: criar, executar, desativar

2. **Adicionar repositórios**
   - FilePreventivePlanRepository (extends FileRepository<PreventivePlan>)
   - FileNotificationRepository (extends FileRepository<Notification>)

3. **Integrar na UI**
   - Menu para gestão de planos preventivos
   - Menu para visualizar notificações
   - Alertas automáticos no startup

4. **Persistência SQLite (opcional)**
   - Migrar após refatoração das entidades existentes
   - Pode ser feita incrementalmente

## Arquivos Modificados/Criados

### Novas Entidades
- `src/main/java/pt/escnaval/manutencao/dominio/PreventivePlan.java` ✅ 
- `src/main/java/pt/escnaval/manutencao/dominio/Notification.java` ✅ 

### Configuração
- `pom.xml` - Versão 1.0-LEVEL50+ (SQLite removido por enquanto)

## Decisões de Design

1. **Enums como Inner Classes**: PreventivePolicies e NotificationType são inner enums para evitar poluir o package
2. **Construtores Imutáveis**: Ambas as novas entidades seguem o padrão das existentes
3. **Compatibilidade Reversa**: Todas as mudanças são aditivas, sem breaking changes
4. **Preparação para Persistência**: Estrutura permite fácil implementação de repositórios

## Validação

Compilar e executar:
```bash
.\build.ps1 compile   # Compila sem erros
.\build.ps1 run       # Executa o sistema (Level 50 funcional)
```

Próxima validação será com os serviços implementados.

---

**Versão**: 1.0-LEVEL50+  
**Status**: Estável e pronto para extensão  
**Data**: 2025-01-XX  
**Próximo Marco**: Level 60 com serviços completos
