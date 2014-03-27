package httpserver;

import java.io.IOException;

import static httpserver.HTTPStatusConstants.METHOD_NOT_ALLOWED;

/**
 * Created by Taryn on 3/26/14.
 */
public class MethodNotAllowedResponse extends Response {
    private BodyGenerator bodyGenerator;
    private Routes routes;
    private RequestParser parser;

    public MethodNotAllowedResponse(RequestParser parser) {
        super(parser);
        this.routes = new Routes();
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(METHOD_NOT_ALLOWED));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder);
    }

}
