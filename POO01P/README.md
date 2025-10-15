# POO01P — Ficha de Exercícios

Esta pasta contém a resolução dos exercícios da ficha POO01P.

## Estrutura
```
POO01P/
  src/
    main/
      java/
        exercicios/
          Exercicio1.java
          Exercicio2.java
          ...
  .gitignore
  README.md
  out/
```

## Como compilar e executar

### Compilar todos os exercícios (PowerShell)
```
Get-ChildItem -Recurse src -Filter *.java | % { $_.FullName } | % { & javac -d out $_ }
```

### Executar um exercício
```
java -cp out exercicios.Exercicio1
java -cp out exercicios.Exercicio2
```

## Exportar para o GitHub
1. git add .
2. git commit -m "POO01P: exercícios resolvidos"
3. git push origin main
