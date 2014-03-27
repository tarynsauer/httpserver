package httpserver;
import java.io.IOException;

import static httpserver.HTTPStatusConstants.OK;
/**
 * Created by Taryn on 3/17/14.
 */
public class OKResponse extends Response {
    private BodyGenerator bodyGenerator;
    private RequestParser parser;
    private Routes routes;

    public OKResponse(RequestParser parser) {
        super(parser);
        this.bodyGenerator = new BodyGenerator(parser);
        this.parser = parser;
        this.routes = new Routes();
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(OK));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder);
    }

    private String displayResponseHeaders() {
        if (parser.getMethod().equals("OPTIONS") && (routes.getMethodOptions().containsKey(parser.getUri()))) {
            return "Allow: " + getAllowedOptionsList() + "\r\n";
        } else {
            return "";
        }
    }

    protected String getAllowedOptionsList() {
        String listString = "";
        for (String method : getMethodOptions()) {
            listString += method + ",";
        }
        return listString.substring(0, listString.length() - 1);
    }

    protected String[] getMethodOptions() {
        return routes.getMethodOptions().get(parser.getUri());
    }
}