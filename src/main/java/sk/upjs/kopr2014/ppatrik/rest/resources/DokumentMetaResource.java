package sk.upjs.kopr2014.ppatrik.rest.resources;

import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataFactory;
import sk.upjs.kopr2014.ppatrik.rest.logic.Dokument;

import javax.xml.crypto.Data;
import java.util.UUID;

public class DokumentMetaResource extends ServerResource {
    @Get("json")
    public Response getDokumentMeta() {
        UUID uuid = UUID.fromString((String) getRequestAttributes().get("uuid"));
        try {
            return new Response(DataFactory.INSTANCE.getManager().read(uuid));
        } catch (Exception e) {
            return new Response(404, e.getMessage());
        }
    }
}