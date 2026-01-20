# GUIA DE INÍCIO RÁPIDO

## 🚀 Começar em 3 Passos

### 1. Compilar o Projeto

**Windows (PowerShell):**
```powershell
.\build.ps1 compile
```

**Windows (CMD):**
```cmd
build.bat compile
```

**Linux/Mac:**
```bash
javac -d target/classes -sourcepath src/main/java \
  src/main/java/pt/escnaval/manutencao/dominio/*.java \
  src/main/java/pt/escnaval/manutencao/infraestrutura/*.java \
  src/main/java/pt/escnaval/manutencao/aplicacao/*.java \
  src/main/java/pt/escnaval/manutencao/ui/*.java \
  src/main/java/pt/escnaval/manutencao/*.java
```

### 2. Executar o Sistema

**Windows (PowerShell):**
```powershell
.\build.ps1 run
```

**Windows (CMD):**
```cmd
build.bat run
```

**Linux/Mac:**
```bash
java -cp target/classes pt.escnaval.manutencao.MaintenanceSystemMain
```

### 3. Navegar no Menu

Ao iniciar, siga o menu interativo para:
- Gerir Ativos
- Criar/visualizar Ordens de Trabalho
- Gerir Inventário de Peças

## 📋 Primeiros Passos

1. **Criar um Ativo:**
   - Menu Principal → 1 (Gerir Ativos) → 2 (Criar Ativo)
   - Exemplo: Código=`BOMBA001`, Nome=`Bomba Centrifuga`, Criticidade=3, Estado=`ATIVO`

2. **Criar uma Ordem de Trabalho:**
   - Menu Principal → 2 (Gerir OTs) → 2 (Criar OT)
   - Tipo=`CORRETIVA`, Prioridade=3, Título=`Revisão da bomba`, Asset ID=1

3. **Registar uma Peça:**
   - Menu Principal → 3 (Gerir Inventário) → 2 (Registar Peça)
   - SKU=`PE-ROLAM-001`, Nome=`Rolamento`, Unidade=`unidade`, Reposição=5

## 🧹 Limpar Dados

Para eliminar todos os dados guardados:

**Windows (PowerShell):**
```powershell
.\build.ps1 clean
```

**Windows (CMD):**
```cmd
build.bat clean
```

**Linux/Mac:**
```bash
rm -rf target data/
```

## 📖 Documentação Completa

Veja [README.md](README.md) para documentação detalhada.

## ✅ Verificar Compilação

Se tudo estiver correto, deverá ver a mensagem de boas-vindas:

```
========================================
Bem-vindo ao Sistema de Gestão da Manutenção
Escola Naval - POO 2025/2026
========================================
```

## 🐛 Resolução de Problemas

**Erro: "javac not found"**
- Instale Java 17 ou superior
- Verifique se JAVA_HOME está configurado

**Erro: "Class not found"**
- Execute `compile` primeiro
- Verifique se target/classes existe

**Dados não persistem**
- Verifique permissões de escrita na pasta do projeto
- A pasta `data/` é criada automaticamente

---

Aproveite o sistema! 🎓
