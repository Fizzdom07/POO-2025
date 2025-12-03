package pt.escnaval.exercicios;

import pt.escnaval.exercicios.exceptions.ContaBancariaException;
import pt.escnaval.exercicios.exceptions.SaldoInsuficienteException;
import pt.escnaval.exercicios.exceptions.OperacaoNaoPermitidaException;
import pt.escnaval.exercicios.modelo.ContaBancaria;
import pt.escnaval.exercicios.modelo.ContaCorrente;
import pt.escnaval.exercicios.modelo.ContaPoupanca;
import pt.escnaval.exercicios.servicos.Banco;
import pt.escnaval.exercicios.utils.UtilsIO;

import java.util.List;
import java.util.Scanner;

public class MenuBanco {
    private static final Banco banco = new Banco();

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int op;
            do {
                mostrarMenu();
                op = UtilsIO.lerOpcao(sc, 1, 8, "Opção (1..8): ");
                try {
                    switch (op) {
                        case 1 -> criarConta(sc);
                        case 2 -> removerConta(sc);
                        case 3 -> listarContas();
                        case 4 -> depositar(sc);
                        case 5 -> levantar(sc);
                        case 6 -> mostrarSaldoTotal();
                        case 7 -> listarComDescoberto();
                        case 8 -> System.out.println("A terminar...");
                        default -> System.out.println("Opção inválida.");
                    }
                } catch (SaldoInsuficienteException sie) {
                    System.out.println("Erro: " + sie.getMessage());
                } catch (ContaBancariaException cbe) {
                    System.out.println("Operação falhou: " + cbe.getMessage());
                } catch (RuntimeException re) {
                    System.err.println("Erro inesperado (runtime): " + re.getMessage());
                    re.printStackTrace();
                }
                System.out.println();
            } while (op != 8);
        }
    }

    private static void mostrarMenu() {
        System.out.println("=== MENU BANCO ===");
        System.out.println("1) Criar conta");
        System.out.println("2) Remover conta (por número)");
        System.out.println("3) Listar contas");
        System.out.println("4) Depositar");
        System.out.println("5) Levantar");
        System.out.println("6) Mostrar saldo total");
        System.out.println("7) Listar contas com descoberto");
        System.out.println("8) Sair");
    }

    private static void criarConta(Scanner sc) {
        System.out.println("-- Criar Conta --");
        String tipo = UtilsIO.lerStringNaoVazia(sc, "Tipo (corrente/poupanca): ").toLowerCase();
        String numero = UtilsIO.lerStringNaoVazia(sc, "Número (formato PTxx-xxxx-xxxxxxxx): ");
        String titular = UtilsIO.lerStringNaoVazia(sc, "Titular: ");
        double saldo = UtilsIO.lerDouble(sc, "Saldo inicial: ");
        if (tipo.equals("corrente")) {
            double limite = UtilsIO.lerDouble(sc, "Limite descoberto: ");
            ContaCorrente cc = new ContaCorrente(numero, titular, saldo, limite);
            boolean ok = banco.adicionar(cc);
            System.out.println(ok ? "Conta corrente adicionada." : "Falha: já existe conta com esse número.");
        } else if (tipo.equals("poupanca")) {
            ContaPoupanca cp = new ContaPoupanca(numero, titular, saldo);
            boolean ok = banco.adicionar(cp);
            System.out.println(ok ? "Conta poupança adicionada." : "Falha: já existe conta com esse número.");
        } else {
            System.out.println("Tipo desconhecido. Use 'corrente' ou 'poupanca'.");
        }
    }

    private static void removerConta(Scanner sc) {
        System.out.println("-- Remover Conta --");
        String numero = UtilsIO.lerStringNaoVazia(sc, "Número da conta: ");
        boolean ok = banco.removerPorNumero(numero);
        System.out.println(ok ? "Removido." : "Conta não encontrada.");
    }

    private static void listarContas() {
        System.out.println("-- Listagem de Contas --");
        List<ContaBancaria> todos = banco.listarTodas();
        if (todos.isEmpty()) System.out.println("(nenhuma conta)");
        else todos.forEach(System.out::println);
    }

    private static void depositar(Scanner sc) throws ContaBancariaException {
        System.out.println("-- Depositar --");
        String numero = UtilsIO.lerStringNaoVazia(sc, "Número da conta: ");
        ContaBancaria c = banco.buscarPorNumero(numero);
        if (c == null) { System.out.println("Conta não encontrada."); return; }
        double mont = UtilsIO.lerDouble(sc, "Montante: ");
        c.depositar(mont);
        System.out.println("Depositado. Saldo atual: " + c.getSaldo());
    }

    private static void levantar(Scanner sc) throws ContaBancariaException {
        System.out.println("-- Levantar --");
        String numero = UtilsIO.lerStringNaoVazia(sc, "Número da conta: ");
        ContaBancaria c = banco.buscarPorNumero(numero);
        if (c == null) { System.out.println("Conta não encontrada."); return; }
        double mont = UtilsIO.lerDouble(sc, "Montante: ");
        c.levantar(mont);
        System.out.println("Levantamento efectuado. Saldo atual: " + c.getSaldo());
    }

    private static void mostrarSaldoTotal() {
        System.out.println("-- Saldo Total do Banco --");
        System.out.printf("Saldo total: %.2f%n", banco.saldoTotal());
    }

    private static void listarComDescoberto() {
        System.out.println("-- Contas com descoberto --");
        List<ContaBancaria> res = banco.listarComDescoberto();
        if (res.isEmpty()) System.out.println("(nenhuma conta com descoberto)");
        else res.forEach(System.out::println);
    }
}
