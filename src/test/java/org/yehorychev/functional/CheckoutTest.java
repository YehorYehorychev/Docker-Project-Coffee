package org.yehorychev.functional;

import org.yehorychev.Utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    @Test
    public void testCheckout() {
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));
        WebElement loginButton = driver.findElement(By.id("login-button"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");
        loginButton.click();

        WebElement firstItemAddToCartButton = driver.findElement(By.id("add-to-cart-sauce-labs-backpack"));
        firstItemAddToCartButton.click();

        WebElement cartLink = driver.findElement(By.className("shopping_cart_link"));
        cartLink.click();

        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();

        WebElement checkoutHeader = driver.findElement(By.className("title"));
        Assert.assertTrue(checkoutHeader.getText().contains("Checkout"));
    }
}
