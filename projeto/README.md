# Projeto - Sistema de Gestão da Manutenção

Escola Naval - Curso de Engenharia Naval/Armas e Electrónica
Programação em Java Orientada aos Objetos
2025/2026 - 1º Semestre

## 📋 Descrição do Projeto

Implementação de um **Sistema de Gestão da Manutenção** seguindo o enunciado fornecido. Este projeto segue uma arquitetura em camadas (UI, Domínio, Aplicação, Infraestrutura) com objetivo de gerir ativos, ordens de trabalho e inventário de peças.

**Nível Atual: 50 (MVP - Produto Viável Mínimo)**
- Consola simples para interface
- Persistência em ficheiros
- Funcionalidades básicas de CRUD para Ativos, Ordens de Trabalho e Peças

## 🏗️ Arquitetura

```
projeto/
├── src/
│   ├── main/java/pt/escnaval/manutencao/
│   │   ├── ui/                    # Interface de consola
│   │   │   └── MaintenanceConsole.java
│   │   ├── dominio/               # Entidades e regras de negócio
│   │   │   ├── Role.java
│   │   │   ├── AssetStatus.java
│   │   │   ├── WorkOrderType.java
│   │   │   ├── WorkOrderStatus.java
│   │   │   ├── StockMovementType.java
│   │   │   ├── User.java
│   │   │   ├── Location.java
│   │   │   ├── Asset.java
│   │   │   ├── WorkOrder.java
│   │   │   └── Part.java
│   │   ├── aplicacao/             # Serviços e lógica de negócio
│   │   │   ├── BusinessException.java
│   │   │   ├── AssetService.java
│   │   │   ├── WorkOrderService.java
│   │   │   └── InventoryService.java
│   │   ├── infraestrutura/        # Persistência e repositórios
│   │   │   ├── PersistenceException.java
│   │   │   ├── UserRepository.java
│   │   │   ├── AssetRepository.java
│   │   │   ├── WorkOrderRepository.java
│   │   │   ├── PartRepository.java
│   │   │   ├── FileUserRepository.java
│   │   │   ├── FileAssetRepository.java
│   │   │   ├── FileWorkOrderRepository.java
│   │   │   └── FilePartRepository.java
│   │   └── MaintenanceSystemMain.java   # Classe principal
│   └── test/java/pt/escnaval/manutencao/
│       └── AssetServiceTest.java        # Testes unitários
└── data/                           # Diretório de dados (criado em runtime)
    ├── users.dat
    ├── assets.dat
    ├── workorders.dat
    └── parts.dat
```

## 🎯 Funcionalidades Implementadas (Nível 50)

### RF01 - Cadastro de Ativos
- ✅ Criar novo ativo
- ✅ Listar todos os ativos
- ✅ Atualizar ativo existente
- ✅ Eliminar ativo
- ✅ Validação de código único
- ✅ Criticidade de 0 a 5

### RF03 - Ordens de Trabalho Corretivas
- ✅ Criar ordem de trabalho
- ✅ Listar ordens de trabalho
- ✅ Ver detalhes de OT
- ✅ Alterar estado (ABERTA → PLANEADA → EM_EXECUCAO → CONCLUIDA)
- ✅ Alterar prioridade (1-5)
- ✅ Manutenção de histórico

### RF02/RF08 - Catálogo de Peças e Inventário
- ✅ Registar nova peça com SKU único
- ✅ Listar peças
- ✅ Atualizar peça
- ✅ Eliminar peça
- ✅ Ponto de reposição configurável

### RNF05 - Validação e Regras
- ✅ Validação de entrada de dados
- ✅ Exceções customizadas (BusinessException, PersistenceException)
- ✅ Integridade referencial (um ativo não pode ser eliminado se tiver OTs)

### RNF03 - Persistência
- ✅ Ficheiros binários (serialização Java)
- ✅ Carregamento automático de dados ao iniciar
- ✅ Salvamento automático após cada operação

### RNF07 - Usabilidade
- ✅ Interface de consola clara e intuitiva
- ✅ Menus organizados por módulos
- ✅ Feedback ao utilizador
- ✅ Mensagens de erro descritivas

### RNF09 - Testes
- ✅ Testes unitários para AssetService
- ✅ Repositório em memória para testes
- ✅ Cobertura de casos de sucesso e erro

## 🚀 Como Compilar e Executar

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6+

### Compilação

```bash
cd projeto
mvn clean compile
```

### Execução

```bash
# Executar a aplicação principal
mvn exec:java -Dexec.mainClass="pt.escnaval.manutencao.MaintenanceSystemMain"

# Ou usando o comando direto:
java -cp target/classes pt.escnaval.manutencao.MaintenanceSystemMain
```

### Testes Unitários

```bash
mvn test
# ou para executar teste específico:
mvn test -Dtest=AssetServiceTest
```

## 📖 Guia de Utilização

### Menu Principal

Ao iniciar, o sistema apresenta:
- **1** - Gerir Ativos
- **2** - Gerir Ordens de Trabalho
- **3** - Gerir Inventário de Peças
- **0** - Sair

### Exemplo de Utilização

#### 1. Criar um Ativo
```
Escolha: 1
Escolha: 2 (Criar Ativo)
Código: BOMBA001
Nome: Bomba Centrifuga
Criticidade: 4
Estado: ATIVO
✓ Ativo criado com sucesso! ID: 1
```

#### 2. Criar uma Ordem de Trabalho
```
Escolha: 2
Escolha: 2 (Criar OT)
Tipo: CORRETIVA
Prioridade: 3
Título: Revisão da bomba
ID do Ativo: 1
✓ OT criada com sucesso! ID: 1
```

#### 3. Registar uma Peça
```
Escolha: 3
Escolha: 2 (Registar Peça)
SKU: PE-ROLAM-001
Nome: Rolamento esférico 6205
Unidade: unidade
Ponto de Reposição: 5
✓ Peça registada com sucesso! ID: 1
```

## 🔍 Estrutura de Camadas

### 1. **UI (Consola)**
- Responsável pela interação com o utilizador
- Menu-driven interface
- Validação básica de input

### 2. **Domínio**
- Entidades: User, Asset, Location, WorkOrder, Part
- Enums: Role, AssetStatus, WorkOrderType, WorkOrderStatus, StockMovementType
- Lógica de domínio simples (validações)
- Sem dependências de infraestrutura

### 3. **Aplicação**
- Serviços de negócio: AssetService, WorkOrderService, InventoryService
- Validações de regras de negócio
- Orquestração de operações

### 4. **Infraestrutura**
- Repositórios: UserRepository, AssetRepository, WorkOrderRepository, PartRepository
- Implementações em ficheiros: FileXxxRepository
- Persistência em serialização Java

## 📝 Próximas Etapas (Níveis 60+)

### Nível 60 - Camadas + SQLite + UI Melhorada
- [ ] Migração de ficheiros para SQLite
- [ ] Melhorias na interface (tabelas com filtros, paginação)
- [ ] Gestão de utilizadores e autenticação
- [ ] Planeamento preventivo básico

### Nível 70 - Manutenção Preventiva + Relatórios
- [ ] Planos preventivos com contadores
- [ ] Geração de OTs preventivas automáticas
- [ ] Relatórios operacionais (MTTR/MTBF)
- [ ] Auditoria de dados

### Nível 80 - Dashboard e Workflow
- [ ] Dashboard em tempo real
- [ ] KPIs e gráficos
- [ ] Workflow de pedidos de manutenção
- [ ] Gestor de anexos

### Nível 90+ - Funcionalidades Avançadas
- [ ] Gestão de inventário robusto
- [ ] APIs de integração
- [ ] Notificações do sistema
- [ ] Relatórios avançados

## 🔧 Configuração

Os dados são guardados na pasta `data/` do diretório de execução:
- `users.dat` - Utilizadores do sistema
- `assets.dat` - Ativos registados
- `workorders.dat` - Ordens de trabalho
- `parts.dat` - Peças em catálogo

Se a pasta não existir, é criada automaticamente na primeira execução.

## 📚 Referências

- **Enunciado**: [Ver documento completo do projeto](../Enunciado.md)
- **Modelo de Dados**: Normalizado em 3FN conforme especificado
- **Padrões Java**: Java 17+, Streams, Records (quando aplicável)

## ✅ Critérios de Aceitação (Nível 50)

- [x] Consola funcional com navegação por módulos
- [x] CRUD completo para Ativos, OTs e Peças
- [x] Persistência em ficheiros
- [x] Validação de dados e regras de negócio
- [x] Exceções customizadas
- [x] Testes unitários básicos
- [x] Documentação clara
- [x] Código comentado e bem estruturado

## 📞 Suporte

Para dúvidas sobre o projeto:
1. Consulte o enunciado original ([Enunciado.md](../Enunciado.md))
2. Revise a arquitectura e modelos propostos
3. Execute os testes para validar a lógica

---

**Versão 1.0 (Nível 50)** | Escola Naval 2025/2026
