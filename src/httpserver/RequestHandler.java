package httpserver;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

import static httpserver.HTTPStatusConstants.*;
import static httpserver.JavaserverConstants.*;
/**
 * Created by Taryn on 3/15/14.
 */
public class RequestHandler {
    private byte[] response;
    protected RequestParser parser;
    private SiteManager site;

    public RequestHandler(RequestParser requestParser) throws IOException {
        this.site = new SiteManager();
        this.parser = requestParser;
        Response response = generateResponse();
        this.response = response.getResponseMessage();
    }

    public Response generateResponse() throws UnsupportedEncodingException {
        if (unauthorized()) {
            return new AuthenticateResponse(parser, getContentType(), getStatus(), getContents(), getHeaders());
        } else if (parser.getUri().equals("/form")) {
            return new FormResponse(parser, getContentType(), getStatus(), getContents(), getHeaders());
        } else {
            return new Response(parser, getContentType(), getStatus(), getContents(), getHeaders());
        }
    }

    private String getHeaders() {
        return getLocation() + methodOptionsHeader();
    }

    public byte[] getResponse() {
        return this.response;
    }

    public SiteManager getSite() {
        return this.site;
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
        } else if (uriFound()) {
            return OK;
        } else {
            return NOT_FOUND;
        }
    }

    protected boolean uriFound() {
        return site.getUris().containsKey(parser.getUri());
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

    protected String getContents() throws UnsupportedEncodingException {
        if (parser.getQueryString() == null) {
            return site.getUris().get(parser.getUri());
        } else {
            return site.getUris().get(parser.getUri()) + getQueries();
        }

    }

    protected String getRedirect() {
        return site.getRedirectedRoutes().get(parser.getUri());
    }

    protected String methodOptionsHeader() {
        if (getMethodOptions() == null) {
            return "";
        } else {
            return "Allow: " + getAllowedOptionsList() + "\r\n";
        }
    }

    protected String getLocation() {
        if (redirect()) {
            return "Location: http://localhost:" + Integer.toString(DEFAULT_PORT) + getRedirect() +"\r\n";
        } else {
            return "";
        }

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

    private String getQueries() throws UnsupportedEncodingException {
        String[] allVariables = parser.getAllVariables();
        String content = "";
        for (String match : allVariables) {
            content += "<p>" + URLDecoder.decode(match, "UTF-8") + "</p>";
        }
        return content;
    }

}