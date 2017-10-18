package com.esure.cardpayment.utility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bhawana on 08/01/2017.
 */
@Component
public class DriverHelper
{
    @Autowired private WebDriver webDriver;
    private static WebDriverWait wait;

    public void quitBrowser() {
        webDriver.close();
    }

    public void open(String aURL) {
        webDriver.navigate().to(aURL);
    }

    public void waitForElementToLoad(WebElement checkingElement) {
        wait = new WebDriverWait(webDriver, 30);
        wait.until(ExpectedConditions.visibilityOf(checkingElement));
    }

    public void waitForPageToBeRedirected(String value) {
        wait = new WebDriverWait(webDriver, 30);
        wait.until(ExpectedConditions.urlContains(value));
    }

    public String getCurrentUrl() {
        return webDriver.getCurrentUrl();
    }
}



