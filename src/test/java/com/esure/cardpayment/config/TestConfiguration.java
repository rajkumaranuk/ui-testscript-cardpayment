package com.esure.cardpayment.config;

import com.esure.cardpayment.utility.DriverHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

/**
 * Created by bhawana on 15/06/2017.
 */
@Configuration
@ImportResource("cucumber.xml")
public class TestConfiguration {

    @Autowired
    private ConfigVariables configVariable;

    @Bean
    public WebDriver webDriver(){
        WebDriver webDriver = null;
        switch (this.configVariable.getBrowser()) {
            case "firefox":
                String geckoDriverLocation = DriverHelper.class.getResource("/tools/geckodriver").getPath();
                System.setProperty("webdriver.firefox.marionette", geckoDriverLocation);
                ProfilesIni profile = new ProfilesIni();
                FirefoxProfile firebugProfile = profile.getProfile("selenium");
                webDriver = new FirefoxDriver();
                break;
            case "chrome":
                String chromeDriverLocation = DriverHelper.class.getResource("/tools/chromedriver").getPath();
                System.setProperty("webdriver.chrome.driver", chromeDriverLocation);
                webDriver =  new ChromeDriver();
                break;
        }
        return webDriver;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
