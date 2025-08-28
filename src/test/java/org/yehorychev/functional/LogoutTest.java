package org.yehorychev.functional;

import org.yehorychev.Utils.BaseTest;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LogoutTest extends BaseTest {

    private final Duration TIMEOUT = Duration.ofSeconds(10);

    @Test
    public void testLogout() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inventory_container")));

        driver.findElement(By.id("react-burger-menu-btn")).click();

        By menuWrap = By.cssSelector("div.bm-menu-wrap");
        wait.until(ExpectedConditions.attributeToBe(menuWrap, "aria-hidden", "false"));

        By logoutLink = By.id("logout_sidebar_link");
        WebElement logout = wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        try {
            logout.click();
        } catch (ElementNotInteractableException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", driver.findElement(logoutLink));
        }

        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("login_logo"))).isDisplayed(),
                "User should be redirected to login page.");
    }
}