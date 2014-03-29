package httpserver;
import java.io.IOException;
import java.util.Arrays;

import static httpserver.HTTPStatusConstants.*;

/**
 * Created by Taryn on 3/25/14.
 */
public class ResponseDispatcher {
    private String status;
    private RequestParser parser;
    private Routes routes;
    private Logger logger;
    private AttributeValues attributeValues;

    public ResponseDispatcher(RequestParser parser) {
        this.parser = parser;
        this.logger = new Logger(parser);
        this.attributeValues = new AttributeValues(parser);
        this.routes = new Routes();
        this.status = getStatus();
    }

    public Response getResponse() throws IOException {
        if (requestToBeLogged()) {
            logger.logRequest();
        }

        if (formRequest()) {
            attributeValues.updateAttributes();
        }

        if (status.equals(UNAUTHORIZED)) {
            return new AuthenticateResponse(parser);
        } else if (status.equals(NOT_FOUND)) {
            return new NotFoundResponse(parser);
        } else if (status.equals(MOVED_PERMANENTLY)) {
            return new MovedPermanentlyResponse(parser);
        } else if (status.equals(METHOD_NOT_ALLOWED)) {
            return new MethodNotAllowedResponse(parser);
        } else if (status.equals(PARTIAL_RESPONSE)) {
            return new PartialResponse(parser);
        } else {
            return new OKResponse(parser);
        }
    }

    private boolean formRequest() {
        return parser.getUri().equals("/form");
    }

    protected boolean requestToBeLogged() {
        return routes.getRequestsToBeLogged().containsKey(parser.getUri()) && routes.getRequestsToBeLogged().containsValue(parser.getMethod());
    }

    public String getStatus() {
        if (redirect()) {
            return MOVED_PERMANENTLY;
        } else if (methodNotAllowed()) {
            return METHOD_NOT_ALLOWED;
        } else if (authorizationRequired()) {
            return UNAUTHORIZED;
        } else if (partialContentRequest()) {
            return PARTIAL_RESPONSE;
        } else if (uriFound()) {
            return OK;
        } else {
            return NOT_FOUND;
        }
    }

    protected boolean uriFound() {
        return routes.getUris().containsKey(parser.getUri());
    }

    private boolean partialContentRequest() {
        return parser.containsHeader("Range");
    }

    private boolean redirect() {
        return routes.getRedirectedRoutes().containsKey(parser.getUri());
    }

    private boolean authorizationRequired() {
        return Arrays.asList(routes.getProtectedRoutes()).contains(parser.getUri());
    }

    private boolean methodNotAllowed() {
        if (routes.getRestrictedMethods().containsKey(parser.getUri())) {
            String[] restrictedMethods = routes.getRestrictedMethods().get(parser.getUri());
            if (Arrays.asList(restrictedMethods).contains(parser.getMethod())) {
                return true;
            }
        }
        return false;
    }

}
