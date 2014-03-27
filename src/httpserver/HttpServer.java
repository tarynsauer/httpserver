package httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
/**
 * Created by Taryn on 3/14/14.
 */
public class HttpServer implements Runnable {
    private int port;
    protected ServerSocket serverSocket = null;
    protected Thread runningThread = null;
    protected boolean isStopped = false;
    protected Socket clientSocket = null;

    public HttpServer(int port) {
        this.port = port;
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    @Override
    public void run() {
        synchronizeCurrentThread();
        openServerSocket();
        acceptClientRequest();
    }

    private void acceptClientRequest() {
        while(!isStopped()) {
            try {
                clientSocket = serverSocket.accept();
            } catch (Exception e) {
                if (isStopped()) {
                    System.out.println("Server stopped");
                    return;
                }
                System.out.println("Error accepting client connection");
                e.printStackTrace();
            }
            startNewThread();
        }
        System.out.println("Server stopped");
    }

    protected void synchronizeCurrentThread() {
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
    }

    protected void startNewThread() {
        try {
            new Thread(new WorkerRunnable(clientSocket)).start();
        } catch (IOException e) {
            System.out.println("Error starting new Thread");
            e.printStackTrace();
        }
    }

    protected void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (Exception e) {
            System.out.println("Error opening server socket on port " + this.port);
            e.printStackTrace();
        }
    }

    protected void closeServerSocket() {
        try {
            this.serverSocket.close();
        } catch (Exception e) {
            System.out.println("Error closing server");
            e.printStackTrace();
        }
    }
}
