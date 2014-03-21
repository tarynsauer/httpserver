package httpserver;

import java.io.File;
import java.util.HashMap;

import static httpserver.JavaserverConstants.DIRECTORY_PATH;
/**
 * Created by Taryn on 3/13/14.
 */
public class SiteManager {

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
        validUris.put("/", "<h1>Hey there!</h1>" + listFiles());
        validUris.put("/file1", "file");
        validUris.put("/file2", "file");
        validUris.put("/text-file.txt", "file");
        validUris.put("/image.jpeg", "image");
        validUris.put("/image.png", "image");
        validUris.put("/image.gif", "image");
        validUris.put("/partial_content.txt", "file");
        validUris.put("/form", "<p>There may be a hidden name value here.</p>");
        validUris.put("/parameters", "<h1>Decoded parameters:</h1>");
        validUris.put("/log", "<h1>Log</h1>");
        validUris.put("/these", "<h1>These</h1>");
        validUris.put("/requests", "<h1>Requests</h1>");
        validUris.put("/logs", "<h1>Logs</h1>");
        validUris.put("/method_options", "<h1>Here are you method options</h1>");
        validUris.put("/redirect", "redirect");
        return validUris;
    }

    private String listFiles() {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles();
        String fileList = "";
        for (File file : files) {
            fileList += "<li><a href='" + file.getName() + "'>" + file.getName() + "</a></li>";
        }
        return fileList;
    }

}