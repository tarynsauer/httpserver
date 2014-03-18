package httpserver;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by Taryn on 3/14/14.
 */
public class HttpServerTest {
    private HttpServer server;

    @org.junit.Before
    public void setUp() throws Exception {
        server = new HttpServer(5000);
    }

    @Test
    public void testSynchronizeCurrentThread() throws Exception {
        server.synchronizeCurrentThread();
        assertNotNull(server.runningThread);
    }

    @Test
    public void testServerOpensServerSocket() throws Exception {
        server.openServerSocket();
        assertNotNull(server.serverSocket);
        server.closeServerSocket();
    }

    @Test
    public void testServerIsStoppedReturnsFalse() throws Exception {
        assertFalse(server.isStopped);
    }

    @Test
    public void testServerStartNewThread() throws Exception {
        int beginCount = Thread.activeCount();
        server.startNewThread();
        int endCount = Thread.activeCount();
        assertEquals(beginCount + 1, endCount);
    }

}