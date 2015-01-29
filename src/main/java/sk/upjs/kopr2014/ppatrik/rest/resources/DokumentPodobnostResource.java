package sk.upjs.kopr2014.ppatrik.rest.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataFactory;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataManager;
import sk.upjs.kopr2014.ppatrik.rest.logic.Dokument;
import sk.upjs.kopr2014.ppatrik.rest.logic.Podobnost;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class DokumentPodobnostResource extends ServerResource {
    @Get("json")
    public Response getDokumentPodobnost() {
        Map<String, Object> req = getRequestAttributes();
        System.out.println(req);
        UUID uuid = UUID.fromString((String) req.get("uuid"));
        Integer limit = new Integer((String) req.get("limit"));
        try {
            DataManager dm = DataFactory.INSTANCE.getManager();
            Dokument dokument = dm.read(uuid);
            return new Response(Podobnost.get(dokument, limit, dm.list()));
        } catch (Exception e) {
            return new Response(404, "Neaka chyba" + e.getMessage());
        }
    }
}