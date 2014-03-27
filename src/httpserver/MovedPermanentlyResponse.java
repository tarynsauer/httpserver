package httpserver;

import java.io.IOException;

import static httpserver.HTTPStatusConstants.MOVED_PERMANENTLY;
import static httpserver.JavaserverConstants.DEFAULT_PORT;

/**
 * Created by Taryn on 3/26/14.
 */
public class MovedPermanentlyResponse extends Response {
    private BodyGenerator bodyGenerator;
    private Routes routes;
    private RequestParser parser;

    public MovedPermanentlyResponse(RequestParser parser) {
        super(parser);
        this.routes = new Routes();
        this.parser = parser;
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(MOVED_PERMANENTLY));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder);
    }

    public String displayResponseHeaders() {
        return "Location: http://localhost:" + Integer.toString(DEFAULT_PORT) + getRedirect() + "\r\n";
    }

    protected String getRedirect() {
        return routes.getRedirectedRoutes().get(parser.getUri());
    }
}
