package httpserver;

/**
 * Created by Taryn on 3/18/14.
 */
public class FormResponse extends Response {
    private static String dataValue = "";
    private RequestParser parser;
    private String contents;

    public FormResponse(RequestParser parser, String contentType, String status, String contents, String headerContents) {
        super(parser, contentType, status, contents, headerContents);
        this.parser = parser;
        getAttribute();
        this.contents = getContents();
    }

    public void setDataValue(String value) {
        dataValue = value;
    }

    public String getDataValue() {
        return dataValue;
    }

    private void getAttribute() {
        String method = parser.getMethod();

        if ((method.equals("POST") || (method.equals("PUT")))) {
            setDataValue(parser.getParamVal("data"));
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
    }

    public String getContents() {
        return "<p data = " + getDataValue() + ">There may be a hidden name value here.</p>";
    }
}
