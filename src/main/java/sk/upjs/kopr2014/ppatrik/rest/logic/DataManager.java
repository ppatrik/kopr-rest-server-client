package sk.upjs.kopr2014.ppatrik.rest.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {
    private List<Dokument> list = new ArrayList<Dokument>();

    public UUID create(String nazov, String autor, String jazyk, String text, String kluc, String data) throws Exception {
        Dokument novyDokument = new Dokument();
        novyDokument.setUuid(UUID.randomUUID());
        novyDokument.setNazov(nazov);
        novyDokument.setAutor(autor);
        novyDokument.setJazyk(jazyk);
        novyDokument.setText(text);
        novyDokument.setKluc(kluc);

        if (dokumentExistuje(novyDokument)) {
            throw new Exception("sk.upjs.kopr2014.ppatrik.rest.logic.Dokument uz existuje!");
        }

        novyDokument.saveData(data);

        if (!list.add(novyDokument)) {
            throw new Exception("sk.upjs.kopr2014.ppatrik.rest.logic.Dokument sa nepodarilo pridat!");
        }

        return novyDokument.getUuid();
    }

    private boolean dokumentExistuje(Dokument novyDokument) {
        for (Dokument dokument : list) {
            if(compareDokument(novyDokument, dokument)) {
                return true;
            }
        }
        return false;
    }

    private boolean compareDokument(Dokument a, Dokument b) {
        if(!a.getNazov().equals(b.getNazov()))
            return false;

        if(!a.getAutor().equals(b.getAutor()))
            return false;

        if(!a.getJazyk().equals(b.getJazyk()))
            return false;

        if(!a.getText().equals(b.getText()))
            return false;

        if(!a.getKluc().equals(b.getKluc()))
            return false;

        return true;
    }

    public Dokument read(UUID uuid) throws Exception {
        for (Dokument dokument: list) {
            if(dokument.getUuid().equals(uuid)) {
                return dokument;
            }
        }
        throw new Exception("sk.upjs.kopr2014.ppatrik.rest.logic.Dokument nebol nájdený");
    }

    // getList
    public List<Dokument> list() {
        return list;
    }

    public int length() {
        return list.size();
    }

    public class Podobnost {
        public UUID id;
        public double podobnost;

        public Podobnost(UUID id, double podobnost) {
            this.id = id;
            this.podobnost = podobnost;
        }
    }

    public List<Podobnost> getPodobnost(UUID uuid, int limit) {
        List<Podobnost> l = new ArrayList<Podobnost>();
        l.add(new Podobnost(UUID.randomUUID(), 10));
        l.add(new Podobnost(UUID.randomUUID(), 10));
        return l;
    }

    private int exists(Dokument dokument) {
        UUID uuid = dokument.getUuid();
        return existsUuid(uuid);
    }

    private int existsUuid(UUID uuid) {
        int i = 0;
        for (Dokument dokument : list) {
            if (dokument.getUuid().equals(uuid)) {
                return i;
            }
            i++;
        }
        return -1;
    }
}
