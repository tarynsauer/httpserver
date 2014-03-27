package httpserver;

import java.io.*;
import java.net.Socket;
/**
 * Created by Taryn on 3/14/14.
 */

public class WorkerRunnable implements Runnable {
    protected Socket clientSocket = null;
    protected BufferedReader input = null;
    protected InputStream clientInputStream = null;
    protected RequestHandler requestHandler;
    protected DataOutputStream clientOutputStream;

    public WorkerRunnable(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
    }

    protected void setClientInputStream(InputStream inputStream) {
        this.clientInputStream = inputStream;
    }

    public InputStream getClientInputStream() {
        return this.clientInputStream;
    }

    public DataOutputStream getClientOutputStream() {
        return this.clientOutputStream;
    }

    public void run() {
        try {
            createClientInputStream();
            BufferedReader reader = getClientRequest();
            byte[] response = getResponseMessage(reader);
            provideResponseForClient(response);
        } catch (IOException e) {
            System.out.println("Error writing response");
            e.printStackTrace();
        }
    }

    protected byte[] getResponseMessage(BufferedReader input) {
        try {
            this.requestHandler = new RequestHandler(new RequestParser(input));
        } catch (IOException e) {
            System.out.println("Error getting client request");
            e.printStackTrace();
        }
        return requestHandler.getResponse();
    }

    protected void provideResponseForClient(byte[] response) throws IOException {
        createClientOutputStream();
        clientOutputStream.write(response);
        clientOutputStream.close();
        input.close();
        clientSocket.close();
    }

    protected BufferedReader getClientRequest() {
        this.input = new BufferedReader(new InputStreamReader(clientInputStream));
        return input;
    }

    protected void createClientInputStream() {
        try {
            this.clientInputStream = clientSocket.getInputStream();
        } catch (IOException e) {
            System.out.println("Can't get client input stream");
            e.printStackTrace();
        }
    }

    protected void createClientOutputStream() throws IOException {
        try {
            this.clientOutputStream =  new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Can't open client output stream");
            e.printStackTrace();
        }
    }

}