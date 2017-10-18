package com.esure.cardpayment.webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by bhawana on 15/06/2017.
 */
public class PageObject {

    protected WebDriver webDriver;

    public PageObject(WebDriver driver){
        this.webDriver = driver;
        webDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        PageFactory.initElements(driver, this); }
}
