package pt.escnaval.exercicios.mediateca.servicos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Utilitários para copiar ficheiros MP3 para a pasta media/.
 */
public final class Mp3Util {
    private Mp3Util() {}

    public static Path copiarMp3(Path origem, Path destino) throws IOException {
        if (!Files.exists(origem)) throw new IOException("Ficheiro de origem não existe: " + origem);
        if (destino.getParent() != null) Files.createDirectories(destino.getParent());
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(origem));
             BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(destino))) {
            byte[] buf = new byte[8192];
            int r;
            while ((r = in.read(buf)) != -1) {
                out.write(buf, 0, r);
            }
        }
        return destino;
    }

    /**
     * Alias with the name used in the guided example: copy(origem, destino)
     */
    public static void copy(Path origem, Path destino) throws IOException {
        copiarMp3(origem, destino);
    }
}
