package org.yehorychev.functional;

import org.yehorychev.Utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ElementVisibilityTest extends BaseTest {

    @Test
    public void testElementVisibility() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        Assert.assertTrue(usernameField.isDisplayed(), "Username field should be visible.");

        WebElement passwordField = driver.findElement(By.id("password"));
        Assert.assertTrue(passwordField.isDisplayed(), "Password field should be visible.");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed(), "Login button should be visible.");
    }
}
