package org.bathbranchringing.emailer;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    
    public static final String WEBDRIVER_LOCATION = "C:\\Development\\selenium\\webdrivers\\chromedriver.exe";
    public static final String WEBSITE_BASE_URL = "http://localhost:8080/emailer/";
    
    protected static WebDriver driver;
    
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", WEBDRIVER_LOCATION);
        
        driver = new ChromeDriver();
        driver.get(WEBSITE_BASE_URL);
    }
    
    @AfterClass
    public static void tearDown() {
//        driver.quit();
    }

}
