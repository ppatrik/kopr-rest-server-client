package sk.upjs.kopr2014.ppatrik.rest.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Podobnost {
    UUID uuid;
    int podobnost;

    public Podobnost() {

    }

    public Podobnost(UUID uuid, int podobnost) {
        this.uuid = uuid;
        this.podobnost = podobnost;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public int getPodobnost() {
        return podobnost;
    }

    public void setPodobnost(int podobnost) {
        this.podobnost = podobnost;
    }

    public static List<Podobnost> get(Dokument input, int limit, List<Dokument> korpus) {
        List<Podobnost> l = new ArrayList<Podobnost>();
        l.add(new Podobnost(input.getUuid(), limit));
        return l;
    }
}
