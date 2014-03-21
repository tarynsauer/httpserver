package httpserver;

import org.junit.Test;

import java.io.*;

import static httpserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/20/14.
 */
public class AuthenticateResponseTest extends TestHelpers {
    private Response authenticateResponse;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        authenticateResponse = new AuthenticateResponse(parser, OK, "", "");
    }

    @Test
    public void testGetContentsReturnsAuthenticationRequiredHeader() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("<h1>Authentication required</h1>", authenticateResponse.getContents());
    }

    @Test
    public void testGetContentsReturnsLogsHeader() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("<h1>Logs</h1>", authenticateResponse.getContents());
    }

    @Test
    public void testGetStatusReturns401Unauthorized() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("401 Unauthorized", authenticateResponse.getStatus());
    }

    @Test
    public void testGetStatusReturns200OK() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("200 OK", authenticateResponse.getStatus());
    }

    @Test
    public void testDisplayResponseHeadersReturnsAuthenticationHeader() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(normalizeString("WWW-Authenticate: Basic realm=\"Authentication required for Logs\""), normalizeString(authenticateResponse.displayResponseHeaders()));
    }

    @Test
    public void testDisplayResponseHeadersReturnsNoHeaders() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("", authenticateResponse.displayResponseHeaders());
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
        "<html><title>Taryn's Website</title><body><h1>Logs</h1></body></html>", responseToString(authenticateResponse.getResponseMessage()));
    }
}
