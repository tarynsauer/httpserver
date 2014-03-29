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

    public String expectedRootResponse() {
        return "HTTP/1.1 200 OK" +
                "Date: " + getDate() +
                "Server: Taryn's Java Server" +
                "Content-Type: text/html" +
                "<html><title>Taryn's Website</title><body><h1>Hey there!</h1>" +
                "<li><a href='file1'>file1</a></li><li><a href='file2'>file2</a></li>" +
                "<li><a href='image.gif'>image.gif</a></li>" +
                "<li><a href='image.jpeg'>image.jpeg</a></li>" +
                "<li><a href='image.png'>image.png</a></li>" +
                "<li><a href='partial_content.txt'>partial_content.txt</a></li>" +
                "<li><a href='text-file.txt'>text-file.txt</a></li></body></html>";
    }

}