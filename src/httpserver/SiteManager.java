package httpserver;

import java.util.HashMap;

/**
 * Created by Taryn on 3/13/14.
 */
public class SiteManager {
    private static String[] validUris = {"/", "/file1", "/file2", "/text-file.txt", "/image.jpeg", "/image.png",
            "/image.gif", "/partial_content.txt", "/form", "/parameters", "/log", "/logs", "/method_options", "/redirect"};
    private static String[] protectedRoutes = {"/log", "/logs"};
    private static HashMap<String, String> redirectedRoutes = new HashMap<String, String>();
    private static HashMap<String, String[]> restrictedMethods = new HashMap<String, String[]>();
    private static HashMap<String, String[]> methodOptions = new HashMap<String, String[]>();

    public String[] getValidUris() {
        return this.validUris;
    }

    public String[] getProtectedRoutes() {
        return this.protectedRoutes;
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

}