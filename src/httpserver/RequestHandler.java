package httpserver;

/**
 * Created by Taryn on 3/15/14.
 */
public class RequestHandler {
    private byte[] response;
    protected RequestParser parser;

    public RequestHandler() {
    }

    public byte[] getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response.getBytes();
    }

    public void setParser(RequestParser parser) {
        this.parser = parser;
    }
}