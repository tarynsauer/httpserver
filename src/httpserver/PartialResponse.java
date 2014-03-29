package httpserver;

import java.io.IOException;

import static httpserver.HTTPStatusConstants.PARTIAL_RESPONSE;

/**
 * Created by Taryn on 3/26/14.
 */
public class PartialResponse extends Response {
    BodyGenerator bodyGenerator;

    public PartialResponse(RequestParser parser) {
        super(parser);
        this.bodyGenerator = new BodyGenerator(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(PARTIAL_RESPONSE));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayContentType());

        return bodyGenerator.addPartialBodyToResponse(builder);
    }

}
