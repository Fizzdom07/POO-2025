package exercicios;

import java.util.Scanner;

public class Exercicio4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Insira o valor em segundos: ");
        int totalSegundos = sc.nextInt();

        int horas = totalSegundos / 3600;
        int minutos = (totalSegundos % 3600) / 60;
        int segundos = totalSegundos % 60;

        System.out.printf("%02d:%02d:%02d%n", horas, minutos, segundos);
        sc.close();
    }
}
