package httpserver;

import java.io.IOException;

import static httpserver.HTTPStatusConstants.OK;

/**
 * Created by Taryn on 3/26/14.
 */
public class Response {
    private BodyGenerator bodyGenerator;
    private RequestParser parser;
    private Routes routes;

    public Response(RequestParser parser) {
        this.bodyGenerator = new BodyGenerator(parser);
        this.parser = parser;
        this.routes = new Routes();
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(OK));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder);
    }

    protected String displayStatus(String status) {
        return "HTTP/1.1 " + status +  "\r\n";
    }

    protected String displayDate() {
        java.util.Date date= new java.util.Date();
        return "Date: " + date.toString() + "\r\n";
    }

    protected String displayServer() {
        return "Server: Taryn's Java Server\r\n";
    }

    protected String displayContentType() throws IOException {
        return "Content-Type: " + parser.getContentType() + "\r\n\r\n";
    }

}
