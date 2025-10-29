# Lab03 — Menu de Registos (CLI + Arrays)

Este laboratório implementa um CRUD simples em memória com arrays e menu de consola.

## Estrutura
```
lab03/
  src/
    main/
      java/
        pt/
          escnaval/
            exercicios/
              MenuRegistos.java
              Registos.java
              UtilsIO.java
  .gitignore
  README.md
  out/
```

## Como compilar e executar

### Compilar (PowerShell)
```
Get-ChildItem -Recurse src -Filter *.java | % { $_.FullName } | % { & javac -d out $_ }
```

### Executar
```
java -cp out pt.escnaval.exercicios.MenuRegistos
```

## Tabela de testes sugerida
| # | Passos/Entrada | Saída esperada |
|---|----------------|----------------|
| 1 | Listar vazio   | (vazio)        |
| 2 | Adicionar 1    | [OK]           |
| 3 | Duplicado      | [X]            |
| 4 | Remover ok     | [OK]           |
| 5 | Remover inexistente | [X]       |
| 6 | Adicionar vários | listar mostra 2 linhas |
| 7 | Nome vazio     | [X]            |
| 8 | Fluxo completo | estados coerentes |
| 9 | Sair           | Adeus!         |
