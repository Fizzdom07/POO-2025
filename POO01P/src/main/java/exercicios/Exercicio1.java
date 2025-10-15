package main.java.exercicios;

import java.util.Scanner;

public class Exercicio1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira a distância em milhas: ");
        double milhas = sc.nextDouble();
        double km = milhas * 1.609;
        System.out.printf("%.2f milhas equivalem a %.2f quilómetros.%n", milhas, km);
        sc.close();
    }
}
