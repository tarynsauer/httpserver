package httpserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class RoutesTest extends TestHelpers {
    private Routes routes;

    @Before
    public void setUp() throws Exception {
        routes = new Routes();
    }

    @Test
    public void testGetValidUris() throws Exception {
        assertEquals(routes.getUris().size(), 16);
    }

    @Test
    public void testGetProtectedRoutes() throws Exception {
        assertEquals(routes.getProtectedRoutes().length, 1);
    }

    @Test
    public void testGetRedirectedRoutes() throws Exception {
        assertEquals(routes.getRedirectedRoutes().size(), 1);
    }

    @Test
    public void testGetRestrictedMethods() throws Exception {
        assertEquals(routes.getRestrictedMethods().size(), 2);
    }

    @Test
    public void testGetMethodOptions() throws Exception {
        assertEquals(routes.getMethodOptions().size(), 1);
    }

    @Test
    public void testGetRequestsToBeLogged() throws Exception {
        assertEquals(routes.getRequestsToBeLogged().size(), 3);
    }
}
