package httpserver;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Taryn on 3/12/14.
 */
public class RequestParserTest extends TestHelpers {
    private RequestParser requestParser;

    private void mockPutFormRequest() throws Exception {
        String str = "PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff";
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        requestParser = new RequestParser(bufferedReader);
    }

    private void mockGetImageRequest() throws Exception {
        String str = "GET /image.jpeg HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n";
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        requestParser = new RequestParser(bufferedReader);
    }

    @Before
    public void setUp() throws Exception {
        mockPutFormRequest();
    }

    @Test
    public void testGetRequest() throws Exception {
        String actual = normalizeString(requestParser.getRequest());
        String expected = normalizeString("PUT /form HTTP/1.1--break--Connection: close--break--Host: localhost:5000--break--Content-Length: 15--break--Content-Type: application/x-www-form-urlencoded--break----break----break--data=heathcliff");
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMethodReturnsPUT() throws Exception {
        assertEquals("PUT", requestParser.getMethod());
    }

    @Test
    public void testGetMethodReturnsGET() throws Exception {
        mockGetImageRequest();
        assertEquals("GET", requestParser.getMethod());
    }

    @Test
    public void testGetUri() throws Exception {
        assertEquals("/form", requestParser.getUri());
    }

    @Test
    public void testGetFileNameReturnsPageName() throws Exception {
        assertEquals("form", requestParser.getFileName());
    }

    @Test
    public void testGetFileNameReturnsFileName() throws Exception {
        mockGetImageRequest();
        assertEquals("image.jpeg", requestParser.getFileName());
    }

    @Test
    public void testGetFileExtensionReturnsEmptyStringForNoFile() throws Exception {
        assertEquals("", requestParser.getFileExtension());
    }

    @Test
    public void testGetFileExtensionReturnsFileExtension() throws Exception {
        mockGetImageRequest();
        assertEquals(".jpeg", requestParser.getFileExtension());
    }

    @Test
    public void testGetHeaders() throws Exception {
        Map<String,String> expectedResult= new HashMap<String, String>();
        expectedResult.put("Host", "localhost:5000");
        expectedResult.put("Content-Length", "15");
        expectedResult.put("Content-Type", "application/x-www-form-urlencoded");
        expectedResult.put("Connection", "close");
        assertEquals(expectedResult, requestParser.getHeaders());
    }

    @Test
    public void testGetParamsGetsValue() throws Exception {
        assertTrue(requestParser.getParams().containsValue("heathcliff"));
    }

    @Test
    public void testGetParamsGetsKey() throws Exception {
        assertTrue(requestParser.getParams().containsKey("data"));
    }

    @Test
    public void testContainsHeaderReturnsTrueWhenHeaderPresent() throws Exception {
        assertTrue(requestParser.containsHeader("Content-Length"));
    }

    @Test
    public void testContainsHeaderReturnsFalseWhenHeaderNotPresent() throws Exception {
        assertFalse(requestParser.containsHeader("Range"));
    }

    @Test
    public void testGetHeaderReturnsHeaderValueWhenPresent() throws Exception {
        assertEquals(requestParser.getHeader("Content-Length"), "15");
    }

    @Test
    public void testGetHeaderReturnsNullValueWhenNotPresent() throws Exception {
        assertEquals(requestParser.getHeader("Range"), null);
    }

}