package com.esure.cardpayment.steps;

import com.esure.api.cardpayment.model.HCCPaymentQueryResponse;
import com.esure.api.cardpayment.model.ThreeDSAuthoriseResponse;
import com.esure.api.cardpayment.model.ThreeDSEnrolResponse;
import com.esure.cardpayment.api.CardPaymentClient;
import com.esure.cardpayment.config.ConfigVariables;
import com.esure.cardpayment.utility.DriverHelper;
import com.esure.cardpayment.webpages.CardPaymentPage;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by bhawana on 22/04/2017.
 */
public class CardPaymentStepDefinitions implements En {

    @Autowired
    private CardPaymentPage cardPaymentPage;

    @Autowired
    private CardPaymentClient cardPaymentClient;

    @Autowired
    private ConfigVariables configVariables;

    @Autowired
    DriverHelper driverHelper;

    private String iFrameUrl;
    private String dtsReference;
    private String cardObfuscated;
    private String paresMessage;

    @Given("^iFrame url is retrieved by setup$")
    public void iFrameUrlIsRetrievedBySetup() throws Throwable {
        iFrameUrl = cardPaymentClient.setup().getIframeURL();
    }

    @When("^card payment page is loaded$")
    public void cardPaymentPageIsLoaded() throws Throwable {
        cardPaymentPage.open(iFrameUrl);
    }

    @When("^user enter the payment card details$")
    public void userEnterThePaymentCardDetails() throws Throwable {
        cardPaymentPage.setCardType("Maestro");
        cardPaymentPage.setNameOnCard("Raj Kumaran");
        cardPaymentPage.setCardNumber("6759000000000000");
        cardPaymentPage.setExpMonth("01");
        cardPaymentPage.setExpYear("2020");
        cardPaymentPage.setCv2Number("444");
        cardPaymentPage.clickSubmit();
    }

    @Then("^payment reference is retrieved$")
    public void paymentReferenceIsRetrieved() throws Throwable {
        driverHelper.waitForPageToBeRedirected("dts_reference");
        final String currentUrl = driverHelper.getCurrentUrl();
        dtsReference = currentUrl.substring(currentUrl.indexOf("=") + 1);
        driverHelper.quitBrowser();
    }

    @When("^query the payment details$")
    public void queryThePaymentDetails() throws Throwable {
        final HCCPaymentQueryResponse queryResponse = cardPaymentClient.query(dtsReference);
        assertEquals(new Integer(1), queryResponse.getStatus());
        cardObfuscated = queryResponse.getCreditCardObfuscated();
    }

    @When("^enrol for 3d secure payment$")
    public void enrolFor3dSecurePayment() throws Throwable {
        final ThreeDSEnrolResponse enroleResponse = cardPaymentClient.enrol(dtsReference, cardObfuscated);
        assertEquals(new Integer(150), enroleResponse.getStatus());
        paresMessage = enroleResponse.getParesMessage();
    }

    @When("^authorise 3d secure payment$")
    public void authorise3dSecurePayment() throws Throwable {
        final ThreeDSAuthoriseResponse authoriseResponse = cardPaymentClient.authorise(dtsReference, paresMessage);
        assertNotNull(authoriseResponse);
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            scenario.write("Card payment failed");
        } else {
            scenario.write("Card payment captured and authorised");
        }
    }
}
