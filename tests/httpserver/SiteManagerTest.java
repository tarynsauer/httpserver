package httpserver;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Taryn on 3/13/14.
 */
public class SiteManagerTest extends TestHelpers {
    private SiteManager siteManager;

    @Before
    public void setUp() throws Exception {
        siteManager = new SiteManager();
    }

    @Test
    public void testGetValidUris() throws Exception {
        assertEquals(siteManager.getUris().size(), 16);
    }

    @Test
    public void testGetProtectedRoutes() throws Exception {
        assertEquals(siteManager.getProtectedRoutes().length, 1);
    }

    @Test
    public void testGetRedirectedRoutes() throws Exception {
        assertEquals(siteManager.getRedirectedRoutes().size(), 1);
    }

    @Test
    public void testGetRestrictedMethods() throws Exception {
        assertEquals(siteManager.getRestrictedMethods().size(), 2);
    }

    @Test
    public void testGetMethodOptions() throws Exception {
        assertEquals(siteManager.getMethodOptions().size(), 1);
    }

    @Test
    public void testGetRequestsToBeLogged() throws Exception {
        assertEquals(siteManager.getRequestsToBeLogged().size(), 3);
    }
}
