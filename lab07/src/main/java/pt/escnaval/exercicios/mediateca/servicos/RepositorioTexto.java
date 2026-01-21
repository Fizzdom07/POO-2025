package pt.escnaval.exercicios.mediateca.servicos;

import pt.escnaval.exercicios.mediateca.modelo.Album;
import pt.escnaval.exercicios.mediateca.modelo.Faixa;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação simples de persistência em CSV (texto UTF-8).
 */
public class RepositorioTexto {
    private final Path ficheiro;

    public RepositorioTexto(Path ficheiro) {
        this.ficheiro = ficheiro;
    }

    public List<Album> carregar() throws IOException {
        List<Album> out = new ArrayList<>();
        if (!Files.exists(ficheiro)) return out;
        try (BufferedReader br = Files.newBufferedReader(ficheiro, StandardCharsets.UTF_8)) {
            String linha;
            while ((linha = br.readLine()) != null) {
                // formato: id;titulo;autor;faixa1,faixa2,...
                // cada faixa: numero|titulo|duracaoSegundos|ficheiro
                String[] parts = linha.split(";", -1);
                if (parts.length >= 3) {
                    String id = parts[0];
                    String titulo = parts[1];
                    String autor = parts[2];
                    Album a = new Album(id, titulo, autor);
                    if (parts.length >= 4 && parts[3] != null && !parts[3].isEmpty()) {
                        String faixasStr = parts[3];
                        String[] faixasArr = faixasStr.split(",");
                        for (String fstr : faixasArr) {
                            String[] fparts = fstr.split("\\|", -1);
                            if (fparts.length >= 4) {
                                try {
                                    int numero = Integer.parseInt(fparts[0]);
                                    String ftitulo = fparts[1];
                                    int dur = Integer.parseInt(fparts[2]);
                                    String caminho = fparts[3].isEmpty() ? null : fparts[3];
                                    Faixa fa = new Faixa(numero, ftitulo, dur, caminho);
                                    a.adicionarFaixa(fa);
                                } catch (NumberFormatException ex) {
                                    // ignorar faixa inválida
                                }
                            }
                        }
                    }
                    out.add(a);
                }
            }
        }
        return out;
    }

    public void guardar(List<Album> albuns) throws IOException {
        try (BufferedWriter bw = Files.newBufferedWriter(ficheiro, StandardCharsets.UTF_8)) {
            for (Album a : albuns) {
                StringBuilder sb = new StringBuilder();
                sb.append(a.getId()).append(";")
                  .append(a.getTitulo()).append(";")
                  .append(a.getAutor()).append(";");
                List<Faixa> faixas = a.getFaixas();
                for (int i = 0; i < faixas.size(); i++) {
                    Faixa f = faixas.get(i);
                    String caminho = f.getFicheiroMp3Path() == null ? "" : f.getFicheiroMp3Path();
                    sb.append(String.format("%d|%s|%d|%s", f.getNumero(), f.getTitulo(), f.getDuracaoSegundos(), caminho));
                    if (i < faixas.size() - 1) sb.append(",");
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
    }

    // Static convenience methods matching the guided example API
    public static ArrayList<Album> carregar(Path ficheiro) throws IOException {
        RepositorioTexto r = new RepositorioTexto(ficheiro);
        List<Album> list = r.carregar();
        return new ArrayList<>(list);
    }

    public static void salvar(Path ficheiro, ArrayList<Album> albuns) throws IOException {
        RepositorioTexto r = new RepositorioTexto(ficheiro);
        // ensure parent dirs exist
        if (ficheiro.getParent() != null) Files.createDirectories(ficheiro.getParent());
        r.guardar(albuns);
    }
}
