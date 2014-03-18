package httpserver;

import java.io.*;

/**
 * Created by Taryn on 3/11/14.
 */
public class TestHelpers {
    public RequestParser requestParser;

    public String responseToString(byte[] input) throws UnsupportedEncodingException {
        String expectedOutput = new String(input, "UTF-8");
        return normalizeString(expectedOutput);
    }

    public void setUpParserForResponseTesting() throws IOException {
        byte[] data = "".getBytes();
        InputStream input = new ByteArrayInputStream(data);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        requestParser = new RequestParser(bufferedReader);
    }

    public String normalizeString(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

    public String expectedRequestResponse(String request) throws IOException {
        setUpParserForResponseTesting();
        requestParser.setRequest(request);
        RequestHandler rh = new RequestHandler();
        rh.setParser(requestParser);
        return new String(rh.getResponse(), "UTF-8");
    }

}