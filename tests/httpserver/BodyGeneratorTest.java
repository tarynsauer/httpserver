package httpserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/25/14.
 */
public class BodyGeneratorTest {
    private BodyGenerator generator;

    @Before
    public void setUp() throws Exception {
        setRequestString("PUT /form HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\nContent-Length: 15\r\nContent-Type: application/x-www-form-urlencoded\r\n\r\n\r\ndata=heathcliff");
    }

    private void setRequestString(String str) throws IOException {
        byte[] data = str.getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        RequestParser parser = new RequestParser(bufferedReader);
        generator = new BodyGenerator(parser);

    }

    @Test
    public void testGetContents() throws Exception {
        setRequestString("GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals(generator.getContents(), "<h1>Hey there!</h1><li><a href='file1'>file1</a></li><li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li><li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li><li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li>");
    }

    @Test
    public void testGetContentsReturnsLogsHeader() throws Exception {
        setRequestString("GET /logs HTTP/1.1\r\nAuthorization: Basic YWRtaW46aHVudGVyMg==\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n");
        assertEquals("<h1>Logs</h1>", generator.getContents());
    }
}
