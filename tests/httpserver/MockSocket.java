package httpserver;

import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 * Created by Taryn on 3/17/14.
 */
public class MockSocket extends Socket {
    protected InputStream inputStream;
    protected OutputStream outputStream;

    public InputStream getInputStream() {
        String exampleRequest = "GET / HTTP/1.1\r\nConnection: close\r\nHost: localhost:5000\r\n\r\n\r\n";
        this.inputStream = new ByteArrayInputStream(exampleRequest.getBytes());
        return this.inputStream;
    }

    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setInputStream(String mockRequest) throws IOException {
        this.inputStream = new ByteArrayInputStream(mockRequest.getBytes());
    }

    public void setOutputStream(final String mockResponse) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new OutputStream() {
            @Override
            public void write(int i) throws IOException {
                mockResponse.getBytes(Charset.forName("UTF-8"));
            }
        });
        this.outputStream = dataOutputStream;
    }

    public void close() {

    }
}
