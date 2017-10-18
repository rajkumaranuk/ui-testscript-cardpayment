package com.esure.cardpayment.webpages;

import com.esure.cardpayment.utility.DriverHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by bhawana on 23/04/2017.
 */
@Component
public class CardPaymentPage extends PageObject
{

    @FindBy(how = How.ID , id = "capf1")
    private WebElement cardType;

    @FindBy(how = How.ID , id = "capf2")
    private WebElement nameOnCard;

    @FindBy(how = How.ID , id = "card_number")
    private WebElement cardNumber;

    @FindBy(how = How.ID_OR_NAME , name = "exp_month")
    private WebElement expMonth;

    @FindBy(how = How.ID_OR_NAME , name = "exp_year")
    private WebElement expYear;

    @FindBy(how = How.ID , id = "issue_number")
    private WebElement issueNumber;

    @FindBy(how = How.ID , id = "cv2_number")
    private WebElement cv2Number;

    @FindBy(how = How.ID , id = "continue")
    private WebElement submit;

    @Autowired
    private DriverHelper driver;

    @Autowired
    public CardPaymentPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void open(String url) {
        driver.open(url);
    }

    public void setCardType(String card)
    {
        new Select(cardType).selectByVisibleText(card);
    }

    public void setNameOnCard(String name)
    {
        nameOnCard.sendKeys(name);
    }

    public void setCardNumber(String number)
    {
        cardNumber.sendKeys(number);
    }

    public void setExpMonth(String month)
    {
        new Select(expMonth).selectByVisibleText(month);
    }

    public void setExpYear(String year)
    {
        new Select(expYear).selectByVisibleText(year);
    }

    public void setIssueNumber(String number)
    {
        issueNumber.sendKeys(number);
    }

    public void setCv2Number(String cv2)
    {
        cv2Number.sendKeys(cv2);
    }

    public void clickSubmit()
    {
        submit.click();
    }

}
