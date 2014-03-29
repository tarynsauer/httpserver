package httpserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/15/14.
 */
public class RequestHandler {
    private byte[] response;
    protected RequestParser parser;

    public RequestHandler(RequestParser requestParser) throws IOException {
        this.parser = requestParser;
        this.response = generateResponse();
    }

    public byte[] generateResponse() throws IOException {
        ResponseDispatcher responseDispatcher = new ResponseDispatcher(parser);
        return responseDispatcher.getResponse().getResponseMessage();
    }

    public byte[] getResponse() {
        return this.response;
    }

}