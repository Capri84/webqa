package test;

import app.Application;
import org.junit.After;
import org.junit.Before;

public class BaseRunner {

    Application app;

    @Before
    public void setUp() {
        app = new Application();
    }

    @After
    public void tearDown() {
        app.quit();
    }
}
