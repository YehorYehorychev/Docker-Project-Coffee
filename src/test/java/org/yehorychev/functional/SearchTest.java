package org.yehorychev.functional;

import org.yehorychev.Utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    @Test
    public void testSearch() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement searchBox = driver.findElement(By.id("searchbox"));
        searchBox.sendKeys("backpack");
        searchBox.submit();

        WebElement firstItem = driver.findElement(By.xpath("//div[contains(text(),'Sauce Labs Backpack')]"));
        Assert.assertTrue(firstItem.isDisplayed(), "Item should be found in search results.");
    }
}
