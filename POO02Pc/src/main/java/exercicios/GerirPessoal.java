package exercicios;

import java.time.LocalDate;

public class GerirPessoal {
    public static void main(String[] args) {
        Colaborador[] pessoal = new Colaborador[10];
        int idx = 0;

        // Criar um gestor e adicioná-lo ao array
        Gestor gestor = new Gestor("Ana Silva", 45, 5000.00, LocalDate.of(2015, 5, 1));
        gestor.setBonus(500.0);
        pessoal[idx++] = gestor;

        // Criar mais dois colaboradores
        Colaborador c1 = new Colaborador("João Pereira", 30, 2000.00, LocalDate.of(2020, 1, 10));
        Colaborador c2 = new Colaborador("Maria Santos", 28, 2500.00, LocalDate.of(2019, 3, 20));

        // Acrescentar colaboradores à equipa do gestor
        gestor.aumentarEquipa(c1);
        gestor.aumentarEquipa(c2);

        // Acrescentar os três colaboradores ao array de pessoal
        pessoal[idx++] = c1;
        pessoal[idx++] = c2;

        // Mostrar informação completa antes do aumento
        System.out.println("=== Antes do aumento ===");
        for (int i = 0; i < idx; i++) {
            System.out.println(pessoal[i]);
            if (pessoal[i] instanceof Gestor) {
                ((Gestor) pessoal[i]).listarEquipa();
            }
        }

        // Aumentar gestor e colaboradores em 3%
        System.out.println("\nAplicando aumento de 3% a todos...");
        for (int i = 0; i < idx; i++) {
            pessoal[i].aumentarSalario(3.0);
        }

        // Mostrar informação após o aumento
        System.out.println("\n=== Depois do aumento ===");
        for (int i = 0; i < idx; i++) {
            System.out.println(pessoal[i]);
            if (pessoal[i] instanceof Gestor) {
                ((Gestor) pessoal[i]).listarEquipa();
            }
        }
    }
}
