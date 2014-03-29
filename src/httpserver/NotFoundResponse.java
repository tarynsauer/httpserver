package httpserver;

import java.io.IOException;
import static httpserver.HTTPStatusConstants.NOT_FOUND;

/**
 * Created by Taryn on 3/26/14.
 */
public class NotFoundResponse extends Response {
    private BodyGenerator bodyGenerator;

    public NotFoundResponse(RequestParser parser) {
        super(parser);
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(NOT_FOUND));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder, NOT_FOUND);
    }

}
