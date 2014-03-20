package httpserver;

import java.io.File;
import java.util.HashMap;

import static httpserver.JavaserverConstants.DIRECTORY_PATH;
/**
 * Created by Taryn on 3/13/14.
 */
public class SiteManager {
    private static HashMap<String, String> validUris = new HashMap<String, String>();
    private static String[] protectedRoutes = {"/logs"};
    private static HashMap<String, String> redirectedRoutes = new HashMap<String, String>();
    private static HashMap<String, String[]> restrictedMethods = new HashMap<String, String[]>();
    private static HashMap<String, String[]> methodOptions = new HashMap<String, String[]>();

    public SiteManager() {
    }

    public String[] getProtectedRoutes() {
        return protectedRoutes;
    }

    public HashMap<String, String> getRedirectedRoutes() {
        redirectedRoutes.put("/redirect", "/");
        return redirectedRoutes;
    }

    public HashMap<String, String[]> getRestrictedMethods() {
        String[] restrictPutAndPost = {"PUT", "POST"};
        restrictedMethods.put("/file1", restrictPutAndPost);
        restrictedMethods.put("/text-file.txt", restrictPutAndPost);
        return restrictedMethods;
    }

    public HashMap<String, String[]> getMethodOptions() {
        String[] optionsAllowed = {"GET","HEAD","POST","OPTIONS","PUT"};
        methodOptions.put("/method_options", optionsAllowed);
        return methodOptions;
    }

    public HashMap<String, String> getUris() {
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