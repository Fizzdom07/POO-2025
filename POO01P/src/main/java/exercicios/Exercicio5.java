package main.java.exercicios;

import java.util.Scanner;

public class Exercicio5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira as horas e minutos de cada registo (hh mm):");
        System.out.print("Chegada de manhã: ");
        int h1 = sc.nextInt(), m1 = sc.nextInt();
        System.out.print("Saída para almoço: ");
        int h2 = sc.nextInt(), m2 = sc.nextInt();
        System.out.print("Entrada depois do almoço: ");
        int h3 = sc.nextInt(), m3 = sc.nextInt();
        System.out.print("Saída final do dia: ");
        int h4 = sc.nextInt(), m4 = sc.nextInt();

        int minutosManha = (h2 * 60 + m2) - (h1 * 60 + m1);
        int minutosTarde = (h4 * 60 + m4) - (h3 * 60 + m3);
        int totalMinutos = minutosManha + minutosTarde;
        int horas = totalMinutos / 60;
        int minutos = totalMinutos % 60;

        System.out.printf("Tempo total trabalhado: %d horas e %d minutos.%n", horas, minutos);
        sc.close();
    }
}
