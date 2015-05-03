package org.bathbranchringing.emailer;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

public class Login extends BaseTest {
    
    @Test
    public void testLogin() {

        driver.navigate().to(WEBSITE_BASE_URL + "login");
        
        driver.findElement(By.name("username")).sendKeys("tomlongridge@gmail.com");
        driver.findElement(By.name("password")).sendKeys("tom");
        driver.findElement(By.tagName("form")).submit();
        
        Assert.assertEquals("http://localhost:8080/emailer/", driver.getCurrentUrl());
        
    }
    
    @Test
    public void testLogout() {
        
        driver.navigate().to(WEBSITE_BASE_URL + "logout");

        Assert.assertNotNull(driver.findElement(By.cssSelector("div.alert.alert-success")));
    }

}
