package sk.upjs.kopr2014.ppatrik.rest;

import org.restlet.Component;
import org.restlet.data.Protocol;
import sk.upjs.kopr2014.ppatrik.rest.resources.DokumentDataResource;
import sk.upjs.kopr2014.ppatrik.rest.resources.DokumentMetaResource;
import sk.upjs.kopr2014.ppatrik.rest.resources.DokumentPodobnostResource;
import sk.upjs.kopr2014.ppatrik.rest.resources.DokumentsResource;

public class SimpleRestletRunner {
    public static void main(String[] args) throws Exception {
        Component component = new Component();
        component.getServers().add(Protocol.HTTP, 8182);
        component.getDefaultHost().attach("/dokument", DokumentsResource.class);
        component.getDefaultHost().attach("/dokument/{uuid}", DokumentMetaResource.class);
        component.getDefaultHost().attach("/dokument/{uuid}/data", DokumentDataResource.class);
        component.getDefaultHost().attach("/dokument/{uuid}/{limit}", DokumentPodobnostResource.class);

        component.start();
    }
}