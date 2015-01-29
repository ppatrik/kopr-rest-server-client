package sk.upjs.kopr2014.ppatrik.rest.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataFactory;
import sk.upjs.kopr2014.ppatrik.rest.logic.Dokument;

import java.util.UUID;

public class DokumentDataResource extends ServerResource {
    @Get("json")
    public Response getDokumentData() {
        UUID uuid = UUID.fromString((String) getRequestAttributes().get("uuid"));
        try {
            Dokument dokument = DataFactory.INSTANCE.getManager().read(uuid);
            return new Response(dokument.readData());
        } catch (Exception e) {
            return new Response(404, e.getMessage());
        }
    }
}