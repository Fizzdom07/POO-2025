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
