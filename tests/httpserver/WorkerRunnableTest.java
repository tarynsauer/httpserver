package httpserver;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Taryn on 3/15/14.
 */
public class WorkerRunnableTest {
    private WorkerRunnable worker;
    private MockSocket mockSocket;
    private String exampleRequest;
    private InputStream mockRequest;

    @Before
    public void setUp() throws Exception {
        MockSocket mockSocket = new MockSocket();
        mockSocket.setInputStream("Test request");
        mockSocket.setOutputStream("Test response");
        worker = new WorkerRunnable(mockSocket);

        exampleRequest = "Test request";
        mockRequest = new ByteArrayInputStream(exampleRequest.getBytes());
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
        assertEquals(exampleRequest, bufferedReaderToString(worker.getClientRequest()));
    }

//    @Test
//    public void testGetResponse() throws Exception {
//        String exampleResponse = "Example response";
//        byte[] data = exampleResponse.getBytes();
//        BufferedReader reader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(data)));
//
//        String actualResponse = new String(worker.getResponseMessage(reader), "UTF-8");
//        assertEquals(exampleResponse, actualResponse);
//    }

    @Test
    public void testSetClientInputStream() throws Exception {
        worker.setClientInputStream();
        assertEquals(convertStreamToString(worker.getClientInputStream()), "Test request");
    }

    @Test
    public void testProvideResponseForClient() throws Exception {
        byte[] testResponse = "Test response".getBytes();
        worker.setClientInputStream();
        worker.getClientRequest();
        worker.provideResponseForClient(testResponse);
        assertNotNull(worker.getClientOutputStream());
    }
}
