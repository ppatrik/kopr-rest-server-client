package sk.upjs.kopr2014.ppatrik.rest.logic;

import java.io.IOException;
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

    public static List<Podobnost> get(Dokument input, int limit, List<Dokument> korpus) throws IOException {
        List<Podobnost> l = new ArrayList<Podobnost>();
        //for (int i = 0; i < korpus.size(); i++) {
        String a = input.readData();
        for (int j = 0; j < korpus.size(); j++) {
            Dokument output = korpus.get(j);
            if (!output.getUuid().equals(input.getUuid())) {
                String b = korpus.get(j).readData();
                int podobnost = (int) (StringSimilarity.similarity(a, b) * 100);
                if (podobnost >= limit) {
                    l.add(new Podobnost(korpus.get(j).getUuid(), podobnost));
                }
            }
        }
        //}
        return l;
    }
}
