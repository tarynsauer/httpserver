package httpserver;

import java.util.HashMap;
/**
 * Created by Taryn on 3/13/14.
 */
public class Routes {

    public String[] getProtectedRoutes() {
        return new String[] {"/logs"};
    }

    public HashMap<String, String> getRedirectedRoutes() {
        HashMap<String, String> redirectedRoutes;
        redirectedRoutes = new HashMap<String, String>();
        redirectedRoutes.put("/redirect", "/");
        return redirectedRoutes;
    }

    public HashMap<String, String[]> getRestrictedMethods() {
        HashMap<String, String[]> restrictedMethods;
        restrictedMethods = new HashMap<String, String[]>();
        String[] restrictPutAndPost = {"PUT", "POST"};
        restrictedMethods.put("/file1", restrictPutAndPost);
        restrictedMethods.put("/text-file.txt", restrictPutAndPost);
        return restrictedMethods;
    }

    public HashMap<String, String[]> getMethodOptions() {
        HashMap<String, String[]> methodOptions;
        methodOptions = new HashMap<String, String[]>();
        String[] optionsAllowed = {"GET","HEAD","POST","OPTIONS","PUT"};
        methodOptions.put("/method_options", optionsAllowed);
        return methodOptions;
    }

    public HashMap<String, String> getRequestsToBeLogged() {
        HashMap<String, String> requestsToBeLogged;
        requestsToBeLogged = new HashMap<String, String>();
        requestsToBeLogged.put("/log", "GET");
        requestsToBeLogged.put("/these", "PUT");
        requestsToBeLogged.put("/requests", "HEAD");
        return requestsToBeLogged;
    }

    public HashMap<String, String> getUris() {
        HashMap<String, String> validUris = new HashMap<String, String>();
        validUris.put("/", "Hey there!");
        validUris.put("/file1", "file");
        validUris.put("/file2", "file");
        validUris.put("/text-file.txt", "file");
        validUris.put("/image.jpeg", "image");
        validUris.put("/image.png", "image");
        validUris.put("/image.gif", "image");
        validUris.put("/partial_content.txt", "file");
        validUris.put("/form", "There may be a hidden name value here.");
        validUris.put("/parameters", "Decoded parameters");
        validUris.put("/log", "Log");
        validUris.put("/these", "These");
        validUris.put("/requests", "Requests");
        validUris.put("/logs", "Logs");
        validUris.put("/method_options", "Here are you method options");
        validUris.put("/redirect", "redirect");
        return validUris;
    }

}