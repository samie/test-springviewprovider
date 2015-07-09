package org.vaadin.test.springviewprovider;

import com.vaadin.testbench.TestBench;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.elements.LabelElement;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringviewproviderTestApplication.class)
@WebAppConfiguration
public class SpringviewproviderTestApplicationTests extends TestBenchTestCase {

    private static final String MISSING_VIEW = "missing";
    private static String[] views = new String[]{"foo", "bar", "hello", "world", MISSING_VIEW};

    private static final String PAGE_URL = "http://localhost:8080/ui";
    private static WebDriver sharedDriver;

    @BeforeClass
    public static void beforeAllTests() {

        // Create a single webdriver
        sharedDriver = TestBench.createDriver(new PhantomJSDriver());
        sharedDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @AfterClass
    public static void afterAllTests() {
        // Stop the browser
        if (sharedDriver != null) {
            sharedDriver.quit();
        }
    }

    @Before
    public void beforeTest() {
        if (getDriver() == null) {
            setDriver(sharedDriver);
        }
    }

    @Test
    public void testSpringViewProvider() {
        //Iterate through views to see that they work
        for (String view : views) {
            getDriver().get(PAGE_URL+"#!"+view);
            LabelElement el = $(LabelElement.class).first();
            Assert.assertNotNull("Label should not be null", el);
            if (MISSING_VIEW.equals(view)) {
                Assert.assertTrue("Expected string 'error' but had '"+el.getText()+"'", el.getText().toLowerCase().contains("error"));
            } else {
                Assert.assertTrue("Expected string '"+view +"' but had '"+el.getText()+"'", el.getText().toLowerCase().contains(view));
            }
        }

    }



}
