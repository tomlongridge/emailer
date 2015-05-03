package org.bathbranchringing.emailer;

import org.junit.Test;
import org.openqa.selenium.By;

public class TowerSearch extends BaseTest {
    
    @Test
    public void testSearch() {

        driver.navigate().to(WEBSITE_BASE_URL + "towers");

        driver.findElement(By.id("searchBox")).sendKeys("bathwick");
        driver.findElement(By.id("searchBtn")).click();
        
    }
    
}
