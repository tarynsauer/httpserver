package httpserver;

import java.io.*;
import static java.util.Arrays.copyOfRange;
import static httpserver.JavaserverConstants.DIRECTORY_PATH;

/**
 * Created by Taryn on 3/12/14.
 */
public class BodyGenerator {
    private RequestParser parser;

    public BodyGenerator(RequestParser parser) {
        this.parser = parser;
    }

    public byte[] addBodyToResponse(StringBuilder builder, String contents) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if (parser.containsHeader("Range")) {
            String partialContents = new String(getPartialResponse());
            builder.append(partialContents);
        }
        else if (contents.equals("file")) {
            builder = getFileContents(builder);
        } else if (contents.equals("image")) {
            writeImageToResponse(outputStream, builder);
        } else {
            builder.append(displayBody(contents));
        }
        outputStream.write(builder.toString().getBytes());
        outputStream.close();
        return outputStream.toByteArray();
    }

    public byte[] addNotFoundResponse(StringBuilder builder) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        builder.append(displayBody("<h1>404 Not Found</h1>"));
        outputStream.write(builder.toString().getBytes());
        outputStream.close();
        return outputStream.toByteArray();
    }

    private OutputStream writeImageToResponse(OutputStream outputStream, StringBuilder builder) {
        try {
            outputStream.write(builder.toString().getBytes());
            outputStream.write(getImageContents());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputStream;
    }

    private byte[] getImageContents() throws IOException {
        File filePath = new File(DIRECTORY_PATH + parser.getFileName());
        byte[] fileData = new byte[(int)filePath.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(filePath);
            fileInputStream.read(fileData);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileData;
    }

    private StringBuilder getFileContents(StringBuilder builder) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(DIRECTORY_PATH + parser.getFileName());
            int content;
            while ((content = fis.read()) != -1) {
                builder.append((char)content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return builder;
    }

    private String displayBody(String bodyContent) throws UnsupportedEncodingException {
        return "<html><title>Taryn's Website</title><body>" + bodyContent + "</body></html>";
    }

    private byte[] getPartialResponse() throws IOException {
        StringBuilder builder = new StringBuilder();
        byte[] contents = getFileContents(builder).toString().getBytes();
        if (parser.getRange() == null) {
            return contents;
        } else {
            return copyOfRange(contents, parser.getBeginRange(), parser.getEndRange());
        }
    }

}