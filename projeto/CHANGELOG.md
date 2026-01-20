# CHANGELOG - Projeto

## [1.0] - 20 de Janeiro de 2026

### 🎉 Versão Inicial - Nível 50 (MVP)

#### Adicionado
- ✅ Arquitetura em 4 camadas (UI, Domínio, Aplicação, Infraestrutura)
- ✅ 10 classes de domínio (5 enums + 5 entidades)
- ✅ 9 classes de infraestrutura (4 interfaces + 4 repositórios + 1 exceção)
- ✅ 3 serviços de negócio (AssetService, WorkOrderService, InventoryService)
- ✅ Interface de consola com menu interativo
- ✅ Persistência em ficheiros binários (serialização Java)
- ✅ 8 testes unitários para AssetService
- ✅ Documentação completa (README.md, QUICKSTART.md, DELIVERABLES.md)
- ✅ Scripts de compilação (build.bat, build.ps1)
- ✅ Configuração Maven (pom.xml)

#### Funcionalidades
- ✅ RF01 - Cadastro de Ativos
  - Criar, listar, atualizar e eliminar ativos
  - Validação de código único
  - Criticidade de 0 a 5
  
- ✅ RF03 - Ordens de Trabalho Corretivas
  - Criar OT com tipo e prioridade
  - Máquina de estados (ABERTA → PLANEADA → EM_EXECUCAO → CONCLUIDA)
  - Manutenção de histórico de alterações
  - Alteração de prioridade
  
- ✅ RF02/RF08 - Inventário de Peças
  - Registar peça com SKU único
  - Listar, atualizar e eliminar peças
  - Ponto de reposição configurável

#### Qualidade
- ✅ Validações robustas com exceções customizadas
- ✅ Persistência automática após cada operação
- ✅ Testes unitários para cobertura mínima
- ✅ Código bem documentado e estruturado
- ✅ Sem dependências externas (exceto JUnit 5 para testes)

#### Conhecido/Limitações
- Persistência apenas em ficheiros (sem SQLite)
- Interface por consola apenas (sem GUI/JavaFX)
- Campos Location e parentAsset não persistem (transient)
- Sem autenticação ou controle de acesso
- Sem planeamento preventivo automático
- Sem relatórios ou dashboards

---

## Próximas Versões Planejadas

### [1.1] - Nível 60 (Esperado)
- Migração para SQLite
- Melhorias na interface (tabelas com filtros)
- Autenticação de utilizadores
- Planeamento preventivo básico

### [2.0] - Nível 70+
- Relatórios operacionais (MTTR/MTBF)
- Auditoria de dados
- Dashboard com KPIs
- Workflow de pedidos de manutenção

---

## Notas de Implementação

### Decisões de Design
1. **Serializable vs JSON**: Decidimos usar serialização Java nativa para simplicidade no Nível 50
2. **Transient fields**: Alguns campos são marcados como `transient` para evitar complexidade na serialização
3. **Service Layer**: Implementamos uma camada de serviços para lógica de negócio centralizada
4. **Exception Handling**: Duas exceções customizadas (BusinessException, PersistenceException)

### Testes Executados
- ✅ Criação de ativos
- ✅ Listagem e atualização de ativos
- ✅ Criação de ordens de trabalho
- ✅ Persistência em ficheiros
- ✅ Carregamento de dados ao iniciar
- ✅ Menu navegável e feedback ao utilizador

### Artefatos Entregues
- 23 ficheiros Java
- 4 ficheiros de documentação
- 4 ficheiros de configuração/build
- ~2500 linhas de código
- ~300 linhas de testes

---

## Como Compilar e Usar

```bash
# Compilar
.\build.ps1 compile

# Executar
.\build.ps1 run

# Limpar
.\build.ps1 clean
```

---

## Contato e Suporte

Para dúvidas sobre o projeto, consulte:
- [README.md](README.md) - Documentação completa
- [QUICKSTART.md](QUICKSTART.md) - Guia rápido
- [DELIVERABLES.md](DELIVERABLES.md) - Sumário do projeto

---

**Desenvolvido para Escola Naval - POO 2025/2026**
