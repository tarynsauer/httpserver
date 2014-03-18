package httpserver;
import java.io.IOException;

import static httpserver.HTTPStatusConstants.NOT_FOUND;
/**
 * Created by Taryn on 3/17/14.
 */
public class Response {
    private RequestParser parser;
    private BodyGenerator bodyGenerator;

    public Response(RequestParser parser) {
        this.parser = parser;
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus());
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder, getBodyContents());
    }

    private String getBodyContents() {
        return "";
    }

    protected String displayStatus() {
        return "HTTP/1.1 " + NOT_FOUND +  "\r\n";
    }

    public String displayResponseHeaders() {
        return "";
    }

    private String displayDate() {
        java.util.Date date= new java.util.Date();
        return "Date: " + date.toString() + "\r\n";
    }

    private String displayServer() {
        return "Server: Taryn's Java Server\r\n";
    }

    private String displayContentType() throws IOException {
        return "Content-Type: text/html\r\n\r\n";
    }
}
