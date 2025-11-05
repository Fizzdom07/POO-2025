package exercicios;

public class Exercicio {
    public static void main(String[] args) {
        Cliente cliente = new Cliente("Daniel Moreira", "123456789");
        Endereco endereco = new Endereco("Rua Principal", 10, "1234-567");
        Conta conta = new Conta(1, cliente, 100.0, endereco);

        System.out.println("--- Conta criada ---");
        conta.mostrar();

        System.out.println("\n--- Depósito de 50€ ---");
        conta.deposito(50.0);
        conta.mostrar();

        System.out.println("\n--- Levantamento de 30€ ---");
        conta.levantar(30.0);
        conta.mostrar();

        System.out.println("\n--- Atualizar endereço ---");
        Endereco novoEndereco = new Endereco("Rua Secundária", 20, "9876-543");
        conta.atualizarEndereco(novoEndereco);
        conta.mostrar();
    }
}
