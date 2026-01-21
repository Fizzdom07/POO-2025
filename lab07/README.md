# Lab 07 — Mediateca

Unidade Curricular: Programação Orientada aos Objetos — Java

Este laboratório implementa uma pequena mediateca que guarda álbuns e faixas, com persistência em CSV e suporte para copiar ficheiros MP3 para um directório `media/`.

Estrutura principal (a criar automaticamente):

```
lab07/
 ├─ src/
 │  └─ main/
 │     └─ java/
 │        └─ pt/
 │           └─ escnaval/
 │              └─ exercicios/
 │                 └─ mediateca/
 │                    ├─ modelo/
 │                    │  ├─ Album.java
 │                    │  └─ Faixa.java
 │                    ├─ servicos/
 │                    │  ├─ Mediateca.java
 │                    │  ├─ RepositorioTexto.java
 │                    │  └─ Mp3Util.java
 │                    ├─ utils/
 │                    │  └─ UtilsIO.java
 │                    ├─ AppMediateca.java
 │                    └─ MenuMediateca.java
 ├─ data/          # ficheiro CSV persistente (ex: mediateca.csv)
 ├─ media/         # directório para armazenar mp3 copiados
 ├─ README.md
 └─ .gitignore
```

Bibliotecas / APIs usadas (relevantes):
- `java.util.ArrayList`, `java.util.List`
- `java.nio.file.Files`, `java.nio.file.Path`, `java.nio.file.StandardOpenOption`
- `java.io.InputStream`, `java.io.OutputStream`, `java.io.BufferedInputStream`, `java.io.BufferedOutputStream`
- `java.nio.charset.StandardCharsets`
- `java.util.Scanner` (usar `nextLine()` + parse manual)

O repositório inclui ficheiros esqueleto para permitir a implementação das Tarefas (a→k) do enunciado.

## a) Preparar ambiente e validar ferramentas

Confirme que tem o JDK instalado e disponível no PATH. Exemplo de saída registada no desenvolvimento:

```
$ javac -version
javac 17.0.8

$ java -version
openjdk version "17.0.8" 2023-07-18 LTS
OpenJDK Runtime Environment (build 17.0.8+7-LTS)
OpenJDK 64-Bit Server VM (build 17.0.8+7-LTS, mixed mode, sharing)
```

Regista acima as saídas do teu ambiente (estas linhas servem como evidência do requisito a).
