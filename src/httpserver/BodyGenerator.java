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

    public byte[] addBodyToResponse(StringBuilder builder, String bodyContent) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        if ((parser.containsHeader("Range"))) {
            byte[] partialResponse = getPartialResponse(builder);
            outputStream.write(partialResponse);
        } else if (getContentType().equals("text/plain") || isFile()) {
            builder = getFileContents(builder);
        } else if (getContentType().startsWith("image/")) {
            writeImageToResponse(outputStream, builder);
        } else {
            builder.append(displayBody(bodyContent));
        }
        outputStream.write(builder.toString().getBytes());
        outputStream.close();
        return outputStream.toByteArray();
    }

    public boolean isFile() {
        String name = parser.getFileName();
        return (name.equals("file1") || name.equals("file2"));
    }

    public String getContentType() {
        String ext = parser.getFileExtension();
        if (ext.equals(".txt")) {
            return "text/plain";
        } else if (ext.equals(".jpg") || ext.equals(".jpeg")) {
            return "image/jpeg";
        } else if (ext.equals(".gif")) {
            return "image/gif";
        } else if (ext.equals(".png")) {
            return "image/png";
        } else {
            return "text/html";
        }
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

    private byte[] getPartialResponse(StringBuilder builder) throws IOException {
        byte[] contents = getFileContents(builder).toString().getBytes();
        if (parser.getRange() == null) {
            return contents;
        } else {
            return copyOfRange(contents, parser.getBeginRange(), parser.getEndRange());
        }
    }

}