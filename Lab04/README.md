# Lab 04 - POO (esqueleto)

Este diretório contém o esqueleto do Lab 04. Usa a mesma convenção dos labs anteriores.

Estrutura criada:

lab04/
 ├─ src/
 │  └─ main/
 │     └─ java/
 │        └─ pt/
 │           └─ escnaval/
 │              └─ exercicios/
 │                 ├─ MenuAlunos.java
 │                 ├─ Aluno.java
 │                 ├─ AlunoRepo.java
 │                 └─ UtilsIO.java
 ├─ README.md
 ├─ .gitignore
 └─ out/  (vazio)

Biblioteca Java a usar (relevante para este lab):

- java.util.Scanner — leitura do terminal (usar sempre `nextLine()` e depois converter quando preciso)
- java.lang.String — `trim()`, `isEmpty()`, `toLowerCase()`, `contains(...)`

Regra: continuar a ler com `Scanner.nextLine()` e converter manualmente (por exemplo `Integer.parseInt`).

O que eu implementei aqui:
- Esqueletos das classes indicadas e um menu simples que podes estender.

Como compilar e correr (PowerShell / Windows):

```powershell
# compilar todo o package
javac -d . src/main/java/pt/escnaval/exercicios/*.java
# correr o menu
java pt.escnaval.exercicios.MenuAlunos
```

Próximo passo: diz qual exercício do PDF queres que eu implemente primeiro ou cola aqui o texto do enunciado.
# Lab 04 - POO

Este diretório é o esqueleto para o Lab_04_POO (ficheiro original: `Lab_04_POO.pdf`).

O que criei:
- `src/main/java/pt/escnaval/exercicios/Lab04Main.java` — esqueleto com `main` para executar os exercícios.

O que precisas fazer / como eu posso ajudar a seguir:
- Dizes quais os exercícios do PDF que queres que eu implemente (números/descrição), ou
- Podes colar aqui o enunciado do exercício 1 (ou do que quiseres) e eu começo a implementar.

Como compilar e correr (linha de comandos Windows / PowerShell):

```powershell
# compilar
javac -d . src/main/java/pt/escnaval/exercicios/Lab04Main.java
# correr
java pt.escnaval.exercicios.Lab04Main
```

Se preferires que eu extraia o texto automaticamente do PDF, diz-me e eu explico como instalas Python (ou eu executo se tiveres Python disponível).

## a) Ambiente / versões

Registo das versões do JDK nesta máquina (evidência):

- javac: javac 25
- java: java version "25" 2025-09-16 LTS

Estas linhas servem como evidência para a tarefa (a). Se quiseres, podes substituir pelos outputs exatos do teu terminal.

## h) README, tabela de testes e comandos

Tabela de testes (10 casos) — usar para validar o programa manualmente

| Caso | Ação | Entrada | Resultado esperado |
|------|------|---------|-------------------|
| 1 | Adicionar válido | id=1, nome=Maria | Sucesso, aparece na lista |
| 2 | Adicionar inválido (id<=0) | id=0, nome=João | Mensagem de erro, não adiciona |
| 3 | Adicionar inválido (nome vazio) | id=2, nome="" | Mensagem de erro, não adiciona |
| 4 | Adicionar duplicado | id=1, nome=Ana | Mensagem de id duplicado |
| 5 | Listar por id | (listar) | Lista ordenada por id com cabeçalho |
| 6 | Listar por nome | (listar) | Lista ordenada por nome com cabeçalho |
| 7 | Buscar por termo | termo="ar" | Imprime alunos cujo nome contém "ar" (case-insensitive) |
| 8 | Remover existente | id=1 | Removido, confirma remoção |
| 9 | Remover inexistente | id=999 | Mensagem "Não encontrado." |
|10 | Renomear existente | id=2, novo nome="Carlos" | Nome atualizado e mostrado |

Comandos para compilar e correr (PowerShell):

```powershell
# compilar
javac -d . Lab04/src/main/java/pt/escnaval/exercicios/*.java
# correr
java pt.escnaval.exercicios.MenuAlunos
```

Notas de design:

- Encapsulamento: campos privados em `Aluno`, getter e setter controlado com validações.
- equals/hashCode: implementados em `Aluno` apenas com `id` (identidade do domínio).
- Input robusto: `UtilsIO` usa `Scanner.nextLine()` com parsing manual e validações; `lerOpcao` garante intervalo.

## Resolução guiada — comentários e justificação (a–c)

Abaixo estão os comandos PowerShell sugeridos para realizar as tarefas a–c (Ambiente, Estrutura e Git). Cada comando inclui uma pequena justificação do porquê é necessário e que resultado esperar.

```powershell
# 1) Entrar na pasta do laboratório
cd lab04
# Justificação: garante que todos os comandos Git e de ficheiros correm no diretório do lab.

# 2) Inicializar um repositório Git local (somente se ainda não existir)
git init
# Justificação: cria a pasta .git com o controlo de versão local. Faz sentido quando o lab é entregue como repositório separado.

# 3) Configurar autor/local (opcional, útil se o Git não tiver user configurado globalmente)
git config user.name "meu.nome"
git config user.email "meu.email@example.com"
# Justificação: define o autor dos commits nesta máquina; podes omitir se já estiver configurado globalmente.

# 4) Criar/actualizar o README com um título simples (exemplo)
echo "# Lab 04 — Objetos de Domínio e Repositórios" > README.md
# Justificação: garante que existe um ficheiro README básico no repositório; o operador > substitui o ficheiro se existir.

# 5) Adicionar todos os ficheiros ao índice (staging)
git add .
# Justificação: inclui todos os ficheiros criados/alterados para o próximo commit. Usa .gitignore para evitar ficheiros indesejados.

# 6) Criar o commit inicial com mensagem explicativa
git commit -m "Lab04: estrutura inicial, README e .gitignore"
# Justificação: cria um ponto inicial no histórico com a estrutura do lab. Mensagens claras ajudam a revisão posterior.

```

Notas rápidas e boas práticas:
- Se já existe um repositório Git no nível superior do workspace (por exemplo um repositório para todo o ano/uc), não corras `git init` dentro de `lab04` — em vez disso adiciona e comita normalmente no repositório principal.
- Evita sobrescrever ficheiros importantes com `echo ... > README.md` se já tiveres conteúdo — usa um editor para mesclar manualmente ou `>>` para acrescentar.
- Depois do commit inicial, considera criar um ficheiro `.gitignore` com padrões para `*.class`, `out/`, IDEs (por exemplo `.vscode/`) e outros artefactos de build.

Exemplo de `.gitignore` útil para este lab (resumido):

```
out/
*.class
.vscode/
target/
build/
*.log
```

O texto acima corresponde exactamente aos passos a–c pedidos: preparar o ambiente local (entrar na pasta), inicializar/rever o controlo de versão e criar um commit inicial com README e `.gitignore`.
