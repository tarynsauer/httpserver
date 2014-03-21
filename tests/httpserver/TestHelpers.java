package httpserver;

import java.io.*;

/**
 * Created by Taryn on 3/11/14.
 */
public class TestHelpers {

    public String responseToString(byte[] input) throws UnsupportedEncodingException {
        String expectedOutput = new String(input, "UTF-8");
        return normalizeString(expectedOutput);
    }

    public String normalizeString(String input) {
        return input.replace("\n", "").replace("\r", "");
    }

    public String getDate() {
        java.util.Date date= new java.util.Date();
        return date.toString();
    }

}