package httpserver;
import static httpserver.JavaserverConstants.AUTH_TOKEN;
import static httpserver.HTTPStatusConstants.*;
/**
 * Created by Taryn on 3/18/14.
 */
public class AuthenticateResponse extends Response {
    private boolean authenticated = false;
    private String contents;
    private String authentication;
    private String status;

    public AuthenticateResponse(RequestParser parser, String contentType, String status, String contents, String headerContents) {
        super(parser, contentType, status, contents, headerContents);
        this.authentication = parser.getAuthentication();
        isValid();
        this.contents = getContents();
    }

    public String getContents() {
        if (getAuthenticated()) {    // + logger.getLogs());
            return "<h1>Logs</h1><p>GET /log HTTP/1.1</p><p>PUT /these HTTP/1.1</p><p>HEAD /requests HTTP/1.1</p>";
        } else {
            return "<h1>Authentication required</h1>";
        }
    }

    public String getStatus() {
        if (getAuthenticated()) {
            return OK;
        } else {
            return UNAUTHORIZED;
        }
    }

    public String displayResponseHeaders() {
        if (getAuthenticated()) {
            return "";
        } else {
            return "WWW-Authenticate: Basic realm=\"Authentication required for Logs\"\r\n";
        }
    }

    public boolean getAuthenticated() {
        return this.authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    private void isValid() {
        if (authentication == null) {
            this.authenticated = false;
        } else if (authentication.equals(AUTH_TOKEN)) {
            this.authenticated = true;
        } else {
            this.authenticated = false;
        }
    }

}
