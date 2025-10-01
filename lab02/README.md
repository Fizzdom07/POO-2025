# Lab 02 — I/O no terminal, Scanner e métodos

Este laboratório aborda entrada/saída robusta, métodos, controlo de fluxo e estrutura de projeto em Java.

## Estrutura do Projeto

```
lab02/
  src/
    main/
      java/
        pt/
          escnaval/
            exercicios/
              LeituraRobusta.java
              CalcCLI.java
              ConversorUnidades.java
              EstatisticasSimples.java
              UtilsIO.java
  out/
  .gitignore
  README.md
```

## Como compilar e executar

### Compilar tudo (PowerShell)
```
Get-ChildItem -Recurse src -Filter *.java | % { $_.FullName } | % { & javac -d out $_ }
```

### Executar exemplos
```
java -cp out pt.escnaval.exercicios.LeituraRobusta
java -cp out pt.escnaval.exercicios.CalcCLI
java -cp out pt.escnaval.exercicios.ConversorUnidades
java -cp out pt.escnaval.exercicios.EstatisticasSimples
```

## Testes mínimos
- LeituraRobusta: entradas inválidas, texto com espaços.
- CalcCLI: operador inválido, divisão/módulo por zero.
- ConversorUnidades: conversão km↔mi.
- EstatisticasSimples: valores, linha vazia, ignorar não numéricos.

## Exportar para o GitHub
1. git init
2. git add .
3. git commit -m "Lab02: estrutura inicial, README e .gitignore"
4. git remote add origin <URL-do-repo>
5. git push -u origin main
