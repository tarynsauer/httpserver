package httpserver;

import org.junit.Test;

import java.io.*;

import static httpserver.HTTPStatusConstants.*;
import static httpserver.HTTPStatusConstants.NOT_FOUND;
import static httpserver.HTTPStatusConstants.PARTIAL_RESPONSE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Taryn on 3/27/14.
 */
public class ResponseDispatcherTest extends TestHelpers {
    private ResponseDispatcher dispatcher;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        dispatcher = new ResponseDispatcher(parser);
    }

    @Test
    public void testGetResponseMessageForGetForm() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = >There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForPostForm() throws Exception {
        setRequestString("POST /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 10\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=cosby");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = cosby>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForGetFormAgain() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = cosby>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForPutForm() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\n" +
                "Content-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = heathcliff>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForGetFormYetAgain() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = heathcliff>There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testGetResponseMessageForDelete() throws Exception {
        setRequestString("DELETE /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = >There may be a hidden name value here.</p>" +
                "</body></html>", responseToString(dispatcher.getResponse().getResponseMessage()));
    }

    @Test
    public void testRequestToBeLoggedLog() throws Exception {
        setRequestString("GET /log HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        Boolean actual = dispatcher.requestToBeLogged();
        assertTrue(actual);
    }

    @Test
    public void testRequestToBeLoggedThese() throws Exception {
        setRequestString("PUT /these HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        Boolean actual = dispatcher.requestToBeLogged();
        assertTrue(actual);
    }

    @Test
    public void testRequestToBeLoggedRequests() throws Exception {
        setRequestString("HEAD /requests HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        Boolean actual = dispatcher.requestToBeLogged();
        assertTrue(actual);
    }

    @Test
    public void testGetResponseForRootRequest() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expectedRootResponse()));
    }

    @Test
    public void testGetResponseForGifImageRequest() throws Exception {
        setRequestString("GET /image.gif HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        byte[] response = dispatcher.getResponse().getResponseMessage();
        assertTrue(response.length > 30000);
    }

    @Test
    public void testGetResponseForJpegImageRequest() throws Exception {
        setRequestString("GET /image.jpeg HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        byte[] response = dispatcher.getResponse().getResponseMessage();
        assertTrue(response.length > 38000);
    }

    @Test
    public void testGetResponseForPngImageRequest() throws Exception {
        setRequestString("GET /image.png HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        byte[] response = dispatcher.getResponse().getResponseMessage();
        assertTrue(response.length > 240000);
    }

    @Test
    public void testGetResponseForNotFound() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 404 Not FoundDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body><h1>404 Not Found</h1></body></html>");
    }

    @Test
    public void testGetResponseFile1Response() throws Exception {
        setRequestString("GET /file1 HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/htmlfile1 contents");
    }

    @Test
    public void testGetResponseFile2Response() throws Exception {
        setRequestString("GET /file2 HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/htmlfile2 contents");
    }

    @Test
    public void testGetResponseTextFileResponse() throws Exception {
        setRequestString("GET /text-file.txt HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/plainfile1 contents");
    }

    @Test
    public void testGetResponsePartialContentResponse() throws Exception {
        setRequestString("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 206 Partial ResponseDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/plainThis");
    }

    @Test
    public void testGetResponseNotAuthenticatedResponse() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 401 UnauthorizedDate: " + getDate() + "Server: Taryn's Java ServerWWW-Authenticate: Basic realm=\"Authentication required for Logs\"Content-Type: text/html<html><title>Taryn's Website</title><body><h1>Authentication required</h1></body></html>");
    }

    @Test
    public void testGetFormDeleteResponse() throws Exception {
        setRequestString("DELETE /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body><h1>There may be a hidden name value here</h1><p data = >There may be a hidden name value here.</p></body></html>");
    }

    @Test
    public void testGetFormGetResponse() throws Exception {
        setRequestString("OPTIONS /method_options HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerAllow: GET,HEAD,POST,OPTIONS,PUTContent-Type: text/html<html><title>Taryn's Website</title><body><h1>Here are you method options</h1></body></html>");
    }

    @Test
    public void testGetRedirectResponse() throws Exception {
        setRequestString("GET /redirect HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 302 Moved PermanentlyDate: " + getDate() + "Server: Taryn's Java ServerLocation: http://localhost:5000/Content-Type: text/html<html><title>Taryn's Website</title><body><h1>redirect</h1></body></html>");
    }

    @Test
    public void testGetParameterDecodeResponse() throws Exception {
        setRequestString("GET /parameters?variable_1=Operators%20%3C%2C%20%3E%2C%20%3D%2C%20!%3D%3B%20%2B%2C%20-%2C%20*%2C%20%26%2C%20%40%2C%20%23%2C%20%24%2C%20%5B%2C%20%5D%3A%20%22is%20that%20all%22%3F&variable_2=stuff HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n");
        String actual = new String(dispatcher.getResponse().getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), "HTTP/1.1 200 OKDate: " + getDate() + "Server: Taryn's Java ServerContent-Type: text/html<html><title>Taryn's Website</title><body><h1>Decoded parameters</h1><p>variable_1 = Operators <, >, =, !=; +, -, *, &, @, #, $, [, ]: \"is that all\"?</p><p>variable_2 = stuff</p></body></html>");
    }

    @Test
    public void testGetStatusReturnsRedirect() throws Exception {
        setRequestString("GET /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), OK);
    }

    @Test
    public void testGetStatusReturnsOK() throws Exception {
        setRequestString("GET /redirect HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), MOVED_PERMANENTLY);
    }

    @Test
    public void testGetStatusReturnsMETHODNOTALLOWED() throws Exception {
        setRequestString("PUT /file1 HTTP/1.1/\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), METHOD_NOT_ALLOWED);
    }

    @Test
    public void testGetStatusReturnsUNAUTHORIZED() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), UNAUTHORIZED);
    }

    @Test
    public void testGetStatusReturnsNOTFOUND() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), NOT_FOUND);
    }

    @Test
    public void testGetStatusReturnsPARTIALRESPONSE() throws Exception {
        setRequestString("GET /partial_content.txt HTTP/1.1\r\nRange: bytes=0-4\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(dispatcher.getStatus(), PARTIAL_RESPONSE);
    }

    @Test
    public void testUriFoundReturnsFalseWhenUriIsInvalid() throws Exception {
        setRequestString("GET /foobar HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertFalse(dispatcher.uriFound());
    }

    @Test
    public void testUriFoundReturnsTrueWhenUriIsValid() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertTrue(dispatcher.uriFound());
    }
}
