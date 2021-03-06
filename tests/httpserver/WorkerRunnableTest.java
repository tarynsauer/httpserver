package httpserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Created by Taryn on 3/15/14.
 */
public class WorkerRunnableTest extends TestHelpers {
    private WorkerRunnable worker;
    private MockSocket mockSocket;
    private String exampleRequest;

    @Before
    public void setUp() throws Exception {
        mockSocket = new MockSocket();
        mockSocket.setInputStream("Test request");
        mockSocket.setOutputStream("Test response");
        worker = new WorkerRunnable(mockSocket);

        exampleRequest = "GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n";
        ByteArrayInputStream mockRequest = new ByteArrayInputStream(exampleRequest.getBytes());
        worker.setClientInputStream(mockRequest);
    }

    public String bufferedReaderToString(BufferedReader request) throws IOException {
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while((line = request.readLine()) != null){
            stringBuilder.append(line);
            if (line.equals("")) {
                return stringBuilder.toString();
            }
        }
        return stringBuilder.toString();
    }

    static String convertStreamToString(InputStream is) {
        Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    @Test
    public void testGetClientRequest() throws Exception {
        assertEquals(normalizeString(exampleRequest), normalizeString(bufferedReaderToString(worker.getClientRequest())));
    }

    @Test
    public void testGetResponse() throws Exception {
        byte[] data = "Example request".getBytes();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
        String actualResponse = new String(worker.getResponseMessage(reader), "UTF-8");
        assertTrue(actualResponse.startsWith("HTTP/1.1 200 OK"));
    }

    @Test
    public void testSetClientInputStream() throws Exception {
        worker.createClientInputStream();
        assertTrue(convertStreamToString(worker.getClientInputStream()).startsWith("GET / HTTP/1.1"));
    }

    @Test
    public void testProvideResponseForClient() throws Exception {
        byte[] testResponse = "Test response".getBytes();
        worker.createClientInputStream();
        worker.getClientRequest();
        worker.provideResponseForClient(testResponse);
        assertNotNull(worker.getClientOutputStream());
    }
}
