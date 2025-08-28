package org.yehorychev.functional;

import org.yehorychev.Utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.yehorychev.Utils.BaseTest;

public class NavigationTest extends BaseTest {

    @Test
    public void testNavigation() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement inventoryLink = driver.findElement(By.id("inventory_sidebar_link"));
        inventoryLink.click();

        WebElement inventoryHeader = driver.findElement(By.className("title"));
        Assert.assertTrue(inventoryHeader.getText().contains("Inventory"));
    }
}
