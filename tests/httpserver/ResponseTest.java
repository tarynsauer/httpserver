package httpserver;

import org.junit.Test;

import java.io.*;

import static httpserver.HTTPStatusConstants.NOT_FOUND;
import static httpserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class ResponseTest extends TestHelpers {
    private Response response;
    private RequestParser parser;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        parser = new RequestParser(bufferedReader);
    }

    @Test
    public void testDisplayStatusShowsDefault200OKStatus() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        response = new Response(parser, OK, "", "");
        assertEquals(response.displayStatus(), "HTTP/1.1 200 OK\r\n");
    }

    @Test
    public void testDisplayStatusShowsDefault400NotFoundStatus() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        response = new Response(parser, NOT_FOUND, "", "");
        assertEquals(response.displayStatus(), "HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    public void testGetContentTypeReturnsImageJpg() throws Exception {
        setRequestString("GET /image.jpeg HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        response = new Response(parser, OK, "", "");
        assertEquals(response.getContentType(), "image/jpeg");
    }

    @Test
    public void testGetContentTypeReturnsTextHtml() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        response = new Response(parser, OK, "", "");
        assertEquals(response.getContentType(), "text/html");
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        response = new Response(parser, OK, "", "");
        assertEquals(responseToString(response.getResponseMessage()), "HTTP/1.1 200 OKDate: " + getDate() +
                "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body></body></html>");
    }
}