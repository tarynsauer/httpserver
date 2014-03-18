package httpserver;

import java.io.IOException;

/**
 * Created by Taryn on 3/17/14.
 */
public class StartServer {
    public static void main(String[] args) throws IOException {
        ArgsParser options = new ArgsParser(args);
        int port = options.parsePort();
        String directory = options.parseDirectory();

        HttpServer server = new HttpServer(port);
        new Thread(server).start();
    }
}