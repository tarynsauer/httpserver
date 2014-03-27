package httpserver;

/**
 * Created by Taryn on 3/26/14.
 */
public class AttributeValues {
    private static String data = "";
    private RequestParser parser;

    public AttributeValues(RequestParser parser) {
        this.parser = parser;
    }

    public void setDataValue(String value) {
        data = value;
    }

    public String getData() {
        return data;
    }

    public void updateAttributes() {
        String method = parser.getMethod();

        if ((method.equals("POST") || (method.equals("PUT")))) {
            setDataValue(parser.getParamVal("data"));
        } else if (method.equals("DELETE")) {
            setDataValue("");
        }
    }
}
