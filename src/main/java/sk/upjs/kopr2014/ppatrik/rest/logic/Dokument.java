package sk.upjs.kopr2014.ppatrik.rest.logic;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class Dokument {
    private UUID uuid;
    private String nazov;
    private String autor;
    private String jazyk;
    private String text;
    private String kluc;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getNazov() {
        return nazov;
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getJazyk() {
        return jazyk;
    }

    public void setJazyk(String jazyk) {
        this.jazyk = jazyk;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getKluc() {
        return kluc;
    }

    public void setKluc(String kluc) {
        this.kluc = kluc;
    }

    private String getFilePath() {
        return "data/" + getUuid().toString();
    }

    public void saveData(String data) throws FileNotFoundException {
        FileOutputStream fos = new FileOutputStream(getFilePath());
        PrintStream out = new PrintStream(fos);
        out.print(data);
    }

    public String readData() throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(getFilePath()));
        return new String(encoded, Charset.defaultCharset());
    }
}
