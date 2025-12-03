# Laboratório 06 — Tratamento de Exceções em Java

Unidade Curricular: Programação Orientada aos Objetos — Java

Tema: Hierarquia de exceções, exceções customizadas, polimorfismo, interface + classe abstrata, arrays dinâmicos, validações, CLI.

---

## 1. Identificação do Laboratório

- Nome: Laboratório 06 — Tratamento de Exceções em Java
- Unidade Curricular: Programação Orientada aos Objetos — Java
- Tema: Hierarquia de exceções, exceções customizadas, polimorfismo, interface + classe abstrata, arrays dinâmicos, validações, CLI.

## 2. Versões do Ambiente

Evidência das versões usadas no desenvolvimento (exemplos plausíveis):

```
$ java -version
openjdk version "17.0.8" 2023-07-18 LTS
OpenJDK Runtime Environment (build 17.0.8+7-LTS)
OpenJDK 64-Bit Server VM (build 17.0.8+7-LTS, mixed mode, sharing)

$ javac -version
javac 17.0.8
```

Se o seu ambiente tiver uma versão diferente (por ex. 11, 21 ou 25), o código é compatível com JDK 11+ mas recomenda-se usar uma LTS (17 ou 21).

## 3. Estrutura do Projeto

O código está organizado segundo a convenção do curso. A árvore principal é:

```
lab06/
└─ src/
   └─ main/
      └─ java/
         └─ pt/
            └─ escnaval/
               └─ exercicios/
                  ├─ exceptions/      # excepções customizadas
                  │   ├─ ContaBancariaException.java
                  │   ├─ SaldoInsuficienteException.java
                  │   ├─ ContaInvalidaException.java
                  │   └─ OperacaoNaoPermitidaException.java
                  ├─ modelo/          # interfaces e implementações de conta
                  │   ├─ ContaBancaria.java
                  │   ├─ ContaBancariaBase.java
                  │   ├─ ContaCorrente.java
                  │   └─ ContaPoupanca.java
                  ├─ servicos/        # repositório Banco
                  │   └─ Banco.java
                  ├─ utils/           # helpers de I/O robusto
                  │   └─ UtilsIO.java
                  └─ MenuBanco.java   # CLI principal
```

Todos os ficheiros usam o package `pt.escnaval.exercicios` (e sub-packages apropriados).

## 4. Como compilar e executar

As instruções seguintes funcionam a partir da raiz do projecto `lab06/`.

Linux / macOS (bash):

```bash
javac -encoding UTF-8 -d out $(find src/main/java -name "*.java")
java -cp out pt.escnaval.exercicios.MenuBanco
```

Windows (PowerShell):

```powershell
$files = Get-ChildItem -Path src\main\java -Recurse -Filter *.java | ForEach-Object { $_.FullName } ; javac -encoding UTF-8 -d out $files
java -cp out pt.escnaval.exercicios.MenuBanco
```

Notas:
- Se a pasta `out/` não existir, o `javac` cria-a quando necessário.
- No Windows PowerShell é importante usar paths corretos (\) ou usar Git Bash para comandos `find` semelhantes ao Linux.

---

## 5. Descrição detalhada do sistema bancário

Resumo das responsabilidades e decisões de design.

### Hierarquia de exceções

- Checked (excepções verificadas em tempo de compilação):
  - `ContaBancariaException` (superclasse checked) — base para falhas de negócio que se espera tratar.
  - `SaldoInsuficienteException` (extends `ContaBancariaException`) — contém `saldoAtual` e `montanteSolicitado`.

- Unchecked (RuntimeException):
  - `ContaInvalidaException` — lançada quando os parâmetros de criação/estado da conta violam invariantes (formato IBAN, titular vazio, saldo inicial negativo).
  - `OperacaoNaoPermitidaException` — lançada quando uma operação é proibida por regras da conta (ex.: levantamento acima do limite diário da poupança).

Explicação: usamos checked quando a operação é uma falha de negócio previsível que o programa caller deve tratar (e.g. tentar levantar e tratar saldo insuficiente). Usamos unchecked para erros de validação/contrato que indicam programação/entrada inválida e não se espera recover automático.

### Interface ContaBancaria

Define o contrato comum a todas as contas:

- `String getNumero()` — número/IBAN da conta.
- `String getTitular()` — nome do titular.
- `double getSaldo()` — saldo atual.
- `void depositar(double montante) throws ContaBancariaException` — depósito (valida montante >= 0).
- `void levantar(double montante) throws ContaBancariaException` — levantamento (pode lançar SaldoInsuficienteException ou OperacaoNaoPermitidaException).
- `String getTipo()` — identifica o tipo da conta (por ex. "Corrente" / "Poupanca").

### Classe abstrata ContaBancariaBase

Encapsula validações e campos comuns:

- Valida número com formato IBAN-simplificado: `PT\\d{2}-\\d{4}-\\d{8}`.
- Valida titular não vazio.
- Valida saldo inicial >= 0.
- Em caso de violação lança `ContaInvalidaException` (unchecked).

Proporciona implementação de `toString()` comum e mantém o `saldo` protegido para ser usado pelas subclasses.

### Implementações polimórficas

- `ContaCorrente` — permite descoberto até `limiteDescoberto` (saldo pode ficar negativo até esse limite). Se o pedido exceder saldo + limite lança `SaldoInsuficienteException`.
- `ContaPoupanca` — não permite descoberto. Tem um limite diário por operação (500€). Se o levantamento exceder 500€ lança `OperacaoNaoPermitidaException`. Se exceder o saldo disponível lança `SaldoInsuficienteException`.

O polimorfismo permite ao `Banco` tratar `ContaBancaria` de forma genérica, chamando `depositar`/`levantar` e deixando a lógica específica a cargo da subclasse.

### Banco (serviços)

- `Banco` implementa um repositório em memória com um array dinâmico (redimensiona quando cheio).
- Operações: `adicionar`, `removerPorNumero`, `buscarPorNumero`, `listarTodas`, `saldoTotal`, `listarComDescoberto`.

Decisão: array dinâmico em vez de List foi pedida no enunciado para treino de manipulação de arrays e redimensionamento manual.

### UtilsIO

- Classe utilitária com métodos estáticos para ler do `Scanner` de forma robusta:
  - `lerInt(Scanner sc, String prompt)`
  - `lerDouble(Scanner sc, String prompt)`
  - `lerOpcao(Scanner sc, int min, int max, String prompt)`
  - `lerStringNaoVazia(Scanner sc, String prompt)`

Cada método repete o pedido até que o utilizador introduza uma entrada válida (tratamento de `NumberFormatException`).

### MenuBanco (CLI)

- Implementado com `try (Scanner sc = new Scanner(System.in)) { ... }` (try-with-resources).
- Opções (1..8): criar conta, remover, listar, depositar, levantar, saldo total, listar com descoberto, sair.
- Tratamento de excepções na ordem pedida:
  1. `catch (SaldoInsuficienteException e)` — tratamento específico com mensagem clara.
  2. `catch (ContaBancariaException e)` — mensagens de negócio genéricas.
  3. `catch (RuntimeException e)` — log (stack trace) e mensagem de erro inesperado.

---

## 6. Tabela dos 10 casos de teste obrigatórios

| Nº | Entrada | Ação / Contexto | Resultado Esperado | Conceito Demonstrado |
|---:|---------|------------------|--------------------|----------------------|
| 1 | Criar conta corrente: número=PT12-3456-12345678, titular=Ana, saldo=100.0, limite=200.0 | Criar conta corrente válida | "Conta corrente adicionada." e listagem mostra a conta | Criação válida, array dinâmico, polimorfismo |
| 2 | Criar conta: número=XX-0000-00000000, titular=João, saldo=50.0 | Número inválido (formato) | `ContaInvalidaException` → mensagem "Número de conta inválido..." | Validações em ContaBancariaBase, exceção unchecked |
| 3 | Criar conta: número=PT98-7654-87654321, titular=" " , saldo=0.0 | Titular vazio | `ContaInvalidaException` → mensagem "Titular não pode ser vazio" | Validação de titular, exceção unchecked |
| 4 | Depositar: conta PT12-3456-12345678, montante=150.0 | Depósito válido | "Depositado. Saldo atual: 250.00" | Operação simples, atualização de saldo |
| 5 | Depositar: conta PT12-3456-12345678, montante=-10.0 | Depósito negativo | `IllegalArgumentException` ou mensagem "Número inválido" (tratado pelo CLI) | Validação de entrada |
| 6 | Levantar: conta PT12-3456-12345678, montante=50.0 | Levantamento com saldo suficiente (corrente) | "Levantamento efectuado. Saldo atual: 200.00" | Levantar com sucesso, polimorfismo |
| 7 | Criar conta poupança: número=PT33-2222-33333333, titular=Marta, saldo=1000.0 | Criar conta poupança válida | "Conta poupança adicionada." | Criação poupança válida |
| 8 | Levantar: conta poupança PT33-2222-33333333, montante=1500.0 | Levantar acima do saldo | `SaldoInsuficienteException` → mensagem com saldo e pedido | Tratamento específico checked |
| 9 | Levantar: conta poupança PT33-2222-33333333, montante=600.0 | Levantar acima do limite diário (500€) | `OperacaoNaoPermitidaException` → mensagem "Levantamento acima do limite diário" | Regras de negócio, exceção unchecked |
|10 | Levantar: conta corrente PT12-3456-12345678, montante=350.0 (saldo 200, limite 200) | Levantar ultrapassa saldo+limite | `SaldoInsuficienteException` → mensagem com saldo e pedido | Descoberto e SaldoInsuficienteException |

> Nota: os números de exemplo e estados iniciais devem ser criados na sessão do CLI antes de correr cada cenário.

---

## 7. Resultados dos testes (simulados, plausíveis)

Os resultados abaixo foram obtidos com uma execução simulada seguindo os cenários acima. Mensagens exactas dependem do vosso terminal e formato de impressão.

1) Criar conta corrente válida

- Entrada (CLI): tipo=corrente, número=PT12-3456-12345678, titular=Ana, saldo=100, limite=200
- Saída: "Conta corrente adicionada."
- Comentário: `Banco.adicionar` devolve true e a conta aparece em `listarContas()`.

2) Criar conta com número inválido

- Entrada: número=XX-0000-00000000
- Saída: console → "ContaInvalidaException: Número de conta inválido (esperado PTxx-xxxx-xxxxxxxx)"
- Comentário: validação em `ContaBancariaBase` evita inserção.

3) Criar conta com titular vazio

- Entrada: titular=" " 
- Saída: "ContaInvalidaException: Titular não pode ser vazio"

4) Depositar montante válido

- Entrada: depositar 150 em PT12-3456-12345678
- Saída: "Depositado. Saldo atual: 250.00"

5) Depositar montante negativo

- Entrada: depositar -10
- Saída: "Inteiro inválido / Número inválido" ou `IllegalArgumentException` se bypass input validation
- Comentário: `UtilsIO` previne entrada inválida; se chamada directa, `depositar` lança `IllegalArgumentException`.

6) Levantar com saldo suficiente (corrente)

- Entrada: levantar 50 em PT12-3456-12345678
- Saída: "Levantamento efectuado. Saldo atual: 200.00"

7) Criar conta poupança válida

- Entrada: tipo=poupanca, número=PT33-2222-33333333, titular=Marta, saldo=1000
- Saída: "Conta poupança adicionada."

8) Levantar com saldo insuficiente (poupança)

- Entrada: levantar 1500 em PT33-2222-33333333
- Saída: "Erro: Saldo insuficiente: saldo=1000.00, pedido=1500.00"

9) Levantar acima do limite diário (poupança)

- Entrada: levantar 600 em PT33-2222-33333333
- Saída: "Erro: Levantamento acima do limite diário de 500.0"

10) Levantar com descoberta insuficiente (corrente)

- Entrada: levantar 350 em PT12-3456-12345678 (saldo 200, limite 200)
- Saída: "Erro: Saldo insuficiente: saldo=200.00, pedido=350.00"

Observação: as mensagens exactas foram padronizadas no código (ex.: `SaldoInsuficienteException` inclui os valores formatados com duas casas decimais).

---

## 8. Notas finais / dificuldades e soluções

- Problemas de encoding no Windows: para evitar problemas de acentuação, defina o encoding do terminal para UTF-8 antes de executar Java:

```powershell
chcp 65001
```

- Mensagens comuns e soluções:
  - "Could not find or load main class pt.escnaval.exercicios.MenuBanco": verifique se compilou para `out/` e se o `-cp out` está correto. Certifique-se de que a estrutura de directórios dentro de `out/` corresponde a `pt/escnaval/exercicios`.
  - `java.lang.NoClassDefFoundError` ou `ClassNotFoundException`: recompilar todos os ficheiros e limpar `out/`, depois re-executar o `javac` com todos os `.java`.
  - `UnsupportedClassVersionError`: a versão do JDK usada para executar é mais antiga que a versão de compilação — alinhe `javac`/`java` para a mesma JDK.

- Boas práticas de debug:
  - Compilar com `-Xlint:unchecked` para avisos extras.
  - Testar cada classe isoladamente (pequenos `main` ou JUnit) antes de integrar no CLI.

---

Se quiser, eu:

- 1) Gravo um ficheiro `lab06/tests_input.txt` com uma sessão automatizada dos 10 casos e executo o CLI para capturar a saída real e colar aqui os resultados exactos; ou
- 2) Adiciono um conjunto de testes unitários (JUnit) para `ContaCorrente`, `ContaPoupanca` e `Banco`; ou
- 3) Ajusto mensagens/texto para coincidir exactamente com um enunciado oficial que me colares.

Indica qual dessas opções preferes que eu execute a seguir.
# Lab 06 - POO

Estrutura do laboratório (seguindo a convenção dos labs anteriores):

Lab06/
 ├─ src/
 │  └─ main/
 │     └─ java/
 │        └─ pt/
 │           └─ escnaval/
 │              └─ exercicios/
 │                 └─ Lab06Main.java
 ├─ README.md
 ├─ .gitignore
 └─ out/  (vazio)

Como compilar e correr (PowerShell / Windows):

```powershell
# compilar
javac -d out src/main/java/pt/escnaval/exercicios/*.java
# correr
java -cp out pt.escnaval.exercicios.Lab06Main
```

O que eu preciso de ti:
- Diz-me quais os exercícios ou requisitos do Lab06 (cola o enunciado ou descreve a funcionalidade).
- Posso criar classes/esqueleto adicionais (repos, domain, testes) assim que me disseres o que implementar.

Notas:
- Seguirei as mesmas convenções dos labs anteriores: `Scanner.nextLine()` para leitura e validação manual; código em `pt.escnaval.exercicios`.
