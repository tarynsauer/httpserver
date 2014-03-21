package httpserver;

import org.junit.Test;

import java.io.*;

import static httpserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/20/14.
 */
public class FormResponseTest extends TestHelpers{
    private Response formResponse;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        formResponse = new FormResponse(parser, OK, "", "");
    }

    @Test
    public void testGetResponseMessageForGetForm() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = >There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForPostForm() throws Exception {
        setRequestString("POST /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=cosby");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = cosby>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForGetFormAgain() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = cosby>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForPutForm() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = heathcliff>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForGetFormYetAgain() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = heathcliff>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForDelete() throws Exception {
        setRequestString("DELETE /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><p data = >There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(formResponse.getResponseMessage()));
    }
}
