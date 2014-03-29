package httpserver;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/20/14.
 */
public class OKResponseTest extends TestHelpers{
    private OKResponse response;

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        response = new OKResponse(parser);
    }

    @Test
    public void testGenerateResponse() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        String actual = new String(response.getResponseMessage(), "UTF-8");
        assertEquals(normalizeString(actual), normalizeString(expectedRootResponse()));
    }

}
