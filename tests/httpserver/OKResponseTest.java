package httpserver;

import org.junit.Test;

import java.io.*;

import static httpserver.HTTPStatusConstants.NOT_FOUND;
import static httpserver.HTTPStatusConstants.OK;
import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class OKResponseTest extends TestHelpers {
    private OKResponse OKResponse;
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
        OKResponse = new OKResponse(parser);
        assertEquals(OKResponse.displayStatus(OK), "HTTP/1.1 200 OK\r\n");
    }

    @Test
    public void testDisplayStatusShowsDefault400NotFoundStatus() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        OKResponse = new OKResponse(parser);
        assertEquals(OKResponse.displayStatus(NOT_FOUND), "HTTP/1.1 404 Not Found\r\n");
    }

    @Test
    public void testGetContentTypeReturnsImageJpg() throws Exception {
        setRequestString("GET /image.jpeg HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        OKResponse = new OKResponse(parser);
        assertEquals(parser.getContentType(), "image/jpeg");
    }

    @Test
    public void testGetContentTypeReturnsTextHtml() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        OKResponse = new OKResponse(parser);
        assertEquals(parser.getContentType(), "text/html");
    }

    @Test
    public void testGetResponseMessage() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        OKResponse = new OKResponse(parser);
        assertEquals(responseToString(OKResponse.getResponseMessage()), "HTTP/1.1 200 OKDate: " + getDate() +
                "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body><h1>Hey there!</h1>" +
                "<li><a href='file1'>file1</a></li><li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li><li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li><li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li></body></html>");
    }
}