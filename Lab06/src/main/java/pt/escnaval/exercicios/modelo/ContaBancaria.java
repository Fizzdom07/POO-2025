package pt.escnaval.exercicios.modelo;

import pt.escnaval.exercicios.exceptions.ContaBancariaException;

public interface ContaBancaria {
    String getNumero();
    String getTitular();
    double getSaldo();
    void depositar(double montante) throws ContaBancariaException;
    void levantar(double montante) throws ContaBancariaException;
    String getTipo();
}
