package httpserver;

/**
 * Created by Taryn on 3/26/14.
 */
public class Logger {
    private RequestParser parser;
    private static String logs = "";

    public Logger(RequestParser parser) {
        this.parser = parser;
    }

    public void logRequest() {
        logs += "<p>" + parser.getMethod() + " " + parser.getUri() + " " + "HTTP/1.1</p>";
    }

    public String getLogs() {
        return logs;
    }
}
