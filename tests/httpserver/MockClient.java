package httpserver;

import java.net.Socket;
import java.io.*;

public class MockClient {
    private String request;
    private DataInputStream response;
    private Socket socket;

    public MockClient() {
    }

    public void stubRequest(String request) throws Exception {
        socket = new Socket("localhost", 5000);
        DataOutputStream writer = new DataOutputStream(socket.getOutputStream());
        writer.writeBytes(request);
        writer.flush();

        this.response = new DataInputStream(socket.getInputStream());
    }

    public void closeClient() throws IOException {
        response.close();
        socket.close();
    }

    public String getResponse() throws IOException {
        return new String(convertDataInputStreamToBytes(this.response), "UTF-8");
    }

    private byte[] convertDataInputStreamToBytes(DataInputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        while ((nRead = is.read()) != -1) {
            buffer.write(nRead);
        }

        buffer.flush();
        return buffer.toByteArray();
    }

}