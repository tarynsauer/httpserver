package httpserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Taryn on 3/20/14.
 */
public class MockOutputStream extends DataOutputStream {
    public MockOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    @Override
    public void write(int i) throws IOException {
        System.out.println("'Creating' mock output stream");
    }
}
