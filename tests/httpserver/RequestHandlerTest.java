package httpserver;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class RequestHandlerTest extends TestHelpers {
    private RequestHandler requestHandler;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        requestHandler = new RequestHandler(parser);
    }

    @Test
    public void testGenerateResponse() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(requestHandler.generateResponse(), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expectedRootResponse()));
    }

    @Test
    public void testGetResponseAuthenticatedResponse() throws Exception {
        setRequestString("GET /log HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        setRequestString("PUT /these HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        setRequestString("HEAD /requests HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(requestHandler.getResponse(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body><h1>Logs</h1><p>GET /log HTTP/1.1</p><p>PUT /these HTTP/1.1</p><p>HEAD /requests HTTP/1.1</p></body></html>");
    }

}
