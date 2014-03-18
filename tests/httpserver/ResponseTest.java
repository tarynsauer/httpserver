package httpserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

/**
 * Created by Taryn on 3/13/14.
 */
public class ResponseTest {
    private Response response;

    @Before
    public void setUp() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
    }

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        response = new Response(parser);
    }

    @Test
    public void testDisplayResponseHeadersReturnsAuthenticateHeader() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertNotSame(response.getResponseMessage().length, 0);
    }

    @Test
    public void testDisplayStatusShowsDefault404Status() throws Exception {
        assertEquals(response.displayStatus(), "HTTP/1.1 404 Not Found\r\n");
    }

}