package httpserver;
import java.io.IOException;
import static httpserver.HTTPStatusConstants.NOT_FOUND;
/**
 * Created by Taryn on 3/17/14.
 */
public class Response {
    private BodyGenerator bodyGenerator;
    private String contentType;
    private String status;
    private String contents;
    private String headerContents;

    public Response(RequestParser parser, String contentType, String status, String contents, String headerContents) {
        this.bodyGenerator = new BodyGenerator(parser);
        this.contentType = contentType;
        this.status = status;
        this.contents = contents;
        this.headerContents = headerContents;
    }

    public String getStatus() {
        return this.status;
    }

    public String getContents() {
        return this.contents;
    }

    public String getContentType() {
        return this.contentType;
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus());
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        if (status.equals(NOT_FOUND)) {
            return bodyGenerator.addNotFoundResponse(builder);
        } else {
            return bodyGenerator.addBodyToResponse(builder, getContents());
        }
    }

    protected String displayStatus() {
        return "HTTP/1.1 " + getStatus() +  "\r\n";
    }

    public String displayResponseHeaders() {
        return this.headerContents;
    }

    private String displayDate() {
        java.util.Date date= new java.util.Date();
        return "Date: " + date.toString() + "\r\n";
    }

    private String displayServer() {
        return "Server: Taryn's Java Server\r\n";
    }

    private String displayContentType() throws IOException {
        return "Content-Type: " + getContentType() + "\r\n\r\n";
    }
}