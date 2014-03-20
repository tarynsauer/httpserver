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
    private String logContents;

    public AuthenticateResponse(RequestParser parser, String contentType, String status, String contents, String logContents) {
        super(parser, contentType, status, contents, logContents);
        this.authentication = parser.getAuthentication();
        isValid();
        this.contents = getContents();
        this.logContents = logContents;
    }

    public String getContents() {
        if (getAuthenticated()) {
            return "<h1>Logs</h1>" + getLogContents();
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

    private boolean getAuthenticated() {
        return this.authenticated;
    }

    private void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    private void isValid() {
        if (authentication == null) {
            setAuthenticated(false);
        } else if (authentication.equals(AUTH_TOKEN)) {
            setAuthenticated(true);
        } else {
            setAuthenticated(false);
        }
    }

    private String getLogContents() {
        return logContents;
    }
}
