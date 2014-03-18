package httpserver;

import java.io.IOException;
import java.util.Arrays;
import static httpserver.HTTPStatusConstants.*;
/**
 * Created by Taryn on 3/15/14.
 */
public class RequestHandler {
    private byte[] response;
    protected RequestParser parser;
    private SiteManager site;

    public RequestHandler(RequestParser requestParser) throws IOException {
        this.parser = requestParser;
        Response response = new Response(parser);
        this.response = response.getResponseMessage();
        this.site = new SiteManager();
    }

    public byte[] getResponse() {
        return this.response;
    }

    public void setResponse(String response) {
        this.response = response.getBytes();
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

    public String getStatus() {
        if (redirect()) {
            return MOVED_PERMANENTLY;
        } else if (methodNotAllowed()) {
            return METHOD_NOT_ALLOWED;
        } else if (unauthorized()) {
            return UNAUTHORIZED;
        } else if (partialContentRequest()) {
            return PARTIAL_RESPONSE;
        } else if (!uriFound()) {
            return NOT_FOUND;
        } else {
            return OK;
        }
    }

    protected boolean uriFound() {
        String[] uris = site.getValidUris();
        for (String uri : uris) {
            if (uri.matches(parser.getUri() + "?(.*?)")) {
                return true;
            }
        }
        return Arrays.asList(site.getValidUris()).contains(parser.getUri());
    }

    private boolean partialContentRequest() {
        return parser.containsHeader("Range");
    }

    private boolean redirect() {
        return site.getRedirectedRoutes().containsKey(parser.getUri());
    }

    private boolean unauthorized() {
        return Arrays.asList(site.getProtectedRoutes()).contains(parser.getUri());
    }

    private boolean methodNotAllowed() {
        if (site.getRestrictedMethods().containsKey(parser.getUri())) {
            String[] restrictedMethods = site.getRestrictedMethods().get(parser.getUri());
            if (Arrays.asList(restrictedMethods).contains(parser.getMethod())) {
                return true;
            }
        }
        return false;
    }

    protected String getContents() {
        // Get bodymiddle based on uri from SiteManager
        //
        return null;
    }

    protected boolean methodOptionsRequired() {
        if (getMethodOptions() == null) {
            return false;
        } else {
            return true;
        }
    }

    protected String getRedirect() {
        return site.getRedirectedRoutes().get(parser.getUri());
    }

    protected String[] getMethodOptions() {
        return site.getMethodOptions().get(parser.getUri());
    }

    protected String getAllowedOptionsList() {
        String listString = "";
        for (String method : getMethodOptions()) {
            listString += method + ",";
        }
        return listString.substring(0, listString.length() - 1);
    }

}