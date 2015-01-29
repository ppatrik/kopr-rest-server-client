package sk.upjs.kopr2014.ppatrik.rest.resources;

public class Response {
    public int status;
    public String text;
    public Object data;

    public Response(int status, String text, Object data) {
        this.status = status;
        this.text = text;
        this.data = data;
    }

    public Response(Object data) {
        this(200, "success", data);
    }

    public Response(int status, String text) {
        this(status, text, null);
    }


}
