package httpserver;
import java.io.IOException;
import static httpserver.HTTPStatusConstants.NOT_FOUND;
/**
 * Created by Taryn on 3/17/14.
 */
public class Response {
    private BodyGenerator bodyGenerator;
    private String status;
    private String contents;
    private String headerContents;
    private RequestParser parser;

    public Response(RequestParser parser, String status, String contents, String headerContents) {
        this.bodyGenerator = new BodyGenerator(parser);
        this.status = status;
        this.contents = contents;
        this.headerContents = headerContents;
        this.parser = parser;
    }

    public String getStatus() {
        return this.status;
    }

    public String getContents() {
        return this.contents;
    }

    public String getContentType() {
        String ext = parser.getFileExtension();
        if (ext.equals(".txt")) {
            return "text/plain";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else {
            return "text/html";
        }
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