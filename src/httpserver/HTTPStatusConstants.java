package httpserver;

/**
 * Created by Taryn on 3/10/14.
 */
public interface HTTPStatusConstants {
    static final String OK = "200 OK";
    static final String METHOD_NOT_ALLOWED = "405 Method Not Allowed";
    static final String NOT_FOUND ="404 Not Found";
    static final String PARTIAL_RESPONSE = "206 Partial Response";
    static final String MOVED_PERMANENTLY = "302 Moved Permanently";
    static final String UNAUTHORIZED = "401 Unauthorized";
}
