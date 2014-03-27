package httpserver;
import java.io.IOException;

import static httpserver.JavaserverConstants.AUTH_TOKEN;
import static httpserver.HTTPStatusConstants.*;
/**
 * Created by Taryn on 3/18/14.
 */
public class AuthenticateResponse extends Response {
    private boolean authenticated = false;
    private String authentication;
    private Logger logger;
    private BodyGenerator bodyGenerator;

    public AuthenticateResponse(RequestParser parser) {
        super(parser);
        this.authentication = parser.getAuthentication();
        isValid();
        this.bodyGenerator = new BodyGenerator(parser);
        this.logger = new Logger(parser);
    }

    public byte[] getResponseMessage() throws IOException {
        StringBuilder builder = new StringBuilder();
        builder.append(displayStatus(getStatus()));
        builder.append(displayDate());
        builder.append(displayServer());
        builder.append(displayResponseHeaders());
        builder.append(displayContentType());

        return bodyGenerator.addBodyToResponse(builder, getContents());
    }

    public String getContents() {
        if (getAuthenticated()) {
            return "<h1>Logs</h1>" + logger.getLogs();
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

}
