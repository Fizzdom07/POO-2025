package main.java.exercicios;

public class Conta {
    private int id;
    private Cliente cliente;
    private double saldo;
    private Endereco endereco;
    private Endereco enderecoAntigo;

    // Construtor completo
    public Conta(int aId, Cliente aCliente, double aSaldo, Endereco aEndereco) {
        this.id = aId;
        this.cliente = aCliente;
        this.saldo = aSaldo;
        this.endereco = aEndereco;
        this.enderecoAntigo = null;
    }

    // Construtor com valores padrão
    public Conta(int aId, Cliente aCliente) {
        this.id = aId;
        this.cliente = aCliente;
        this.saldo = 0.0;
        this.endereco = new Endereco("Desconhecida", 0, "0000-000");
        this.enderecoAntigo = null;
    }

    public void setId(int id) { this.id = id; }
    public int getId() { return id; }

    public void setSaldo(double saldo) {
        if (saldo >= 0) this.saldo = saldo;
    }
    public double getSaldo() { return saldo; }

    public void setCliente(Cliente cliente) {
        if (cliente != null && cliente.isValido()) this.cliente = cliente;
    }
    public Cliente getCliente() {
        if (cliente != null && cliente.isValido()) return cliente;
        return null;
    }

    public Conta deposito(double valor) {
        if (valor > 0) this.saldo += valor;
        return this;
    }

    public String getClienteName() {
        if (cliente != null && cliente.isValido()) return cliente.getNome();
        return "Cliente inválido";
    }

    public void mostrar() {
        System.out.println("ID: " + id);
        System.out.println("Saldo: " + saldo);
        System.out.println("Cliente:");
        if (cliente != null) cliente.mostrar();
        else System.out.println("(sem cliente)");
        System.out.println("Endereço atual:");
        if (endereco != null) endereco.mostrar();
        else System.out.println("(sem endereço)");
        if (enderecoAntigo != null) {
            System.out.println("Endereço antigo:");
            enderecoAntigo.mostrar();
        }
    }

    public void levantar(double valor) {
        if (valor > 0 && saldo >= valor) saldo -= valor;
    }

    public void atualizarEndereco(Endereco aEndereco) {
        if (endereco != null) {
            enderecoAntigo = endereco;
            endereco = aEndereco;
        }
    }

    public void mostrarEndereco() {
        if (endereco != null) endereco.mostrar();
        else System.out.println("(sem endereço)");
    }
}
