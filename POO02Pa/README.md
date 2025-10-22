# POO02Pa — Exercício

Esta pasta contém a resolução do exercício POO02Pa.

## Estrutura
```
POO02Pa/
  src/
    main/
      java/
        exercicios/
          Exercicio.java
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
java -cp out exercicios.Exercicio
```

## Exportar para o GitHub
1. git add .
2. git commit -m "POO02Pa: exercício resolvido"
3. git push origin main
