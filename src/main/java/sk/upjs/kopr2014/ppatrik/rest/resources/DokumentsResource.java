package sk.upjs.kopr2014.ppatrik.rest.resources;

import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataFactory;
import sk.upjs.kopr2014.ppatrik.rest.logic.DataManager;

public class DokumentsResource extends ServerResource {
    @Get("json")
    public Response list() {
        return new Response(DataFactory.INSTANCE.getManager().list());
    }


    @Post("json")
    public Response create(DokumentCreate v) {
        DataManager dm = DataFactory.INSTANCE.getManager();
        try {
            return new Response(dm.create(v.nazov, v.autor, v.jazyk, v.text, v.kluc, v.data));
        } catch (Exception e) {
            return new Response(404, e.getMessage());
        }

    }

}
