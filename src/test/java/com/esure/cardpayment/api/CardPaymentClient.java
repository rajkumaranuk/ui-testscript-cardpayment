package com.esure.cardpayment.api;

import com.esure.api.cardpayment.model.*;
import com.esure.cardpayment.config.ConfigVariables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Component
public class CardPaymentClient
{

	public static final String FAKE_PRODUCT_CODE = "EM";
	public static final String FAKE_ESURE_REF = String.valueOf("UT-" + System.currentTimeMillis());
	public static final int FAKE_AMOUNT = 1234;
	public static final String FAKE_RETURN_URL = "https://www.esure.com/return_url";
	public static final String FAKE_EXPIRY_URL = "https://www.esure.com/expiry_url";
	public static final String FAKE_CSS_DATA = "<link rel='stylesheet' type='text/css' href='http://www.esure.com/wcm/groups/visual/documents/webasset/qb_master.css' media='all' /><link rel='stylesheet' type='text/css' href='http://www.esure.com/wcm/groups/visual/documents/webasset/qb_motor.css' media='all' /><link rel='stylesheet' type='text/css' href='http://www.esure.com/wcm/groups/visual/documents/webasset/qb_esure.css' />";
	public static final String FAKE_HTML_DATA = "<p id='cpa_text'>This is where the text goes that tells you about CPA</p>";
	public static final String FAKE_ADDRESS = "Keats ,11,6 Saffron Square,Croydon";
	public static final String FAKE_POSTCODE = "CR0 2FT";
	public static final String FAKE_MERCHANT_URL = "https://egibis.esres.local";
	public static final String FAKE_ACCEPT_HEADER = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
	public static final String FAKE_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-GB; rv:1.9.0.4) Gecko/2008102920 Firefox/3.0.4 (.NET CLR 3.5.30729)";
	public static final boolean CPA_AGREEMENT = true;
	public static final int INIT_NUM_TRIES = 0;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ConfigVariables configVariables;

	public HCCPaymentSetupResponse setup() {
		HCCPaymentSetupRequest setupRequest = new HCCPaymentSetupRequest()
				.productCode(FAKE_PRODUCT_CODE)
				.esureReference(FAKE_ESURE_REF)
				.amount(FAKE_AMOUNT)
				.callbackSuccessURL(FAKE_RETURN_URL)
				.callbackExpiryURL(FAKE_EXPIRY_URL)
				.dynamicData(Arrays.asList(FAKE_CSS_DATA, FAKE_HTML_DATA));

		final String url = configVariables.getBaseUrl() + configVariables.getSetupUrl();
		ResponseEntity<HCCPaymentSetupResponse> setupResponse = restTemplate.postForEntity(url, setupRequest, HCCPaymentSetupResponse.class);
		return setupResponse.getBody();
	}

	public HCCPaymentQueryResponse query(String dtsReference) {
		HCCPaymentQueryRequest queryRequest = new HCCPaymentQueryRequest()
				.productCode(FAKE_PRODUCT_CODE)
				.pspReference(dtsReference);

		final String url = configVariables.getBaseUrl() + configVariables.getQueryUrl();
		ResponseEntity<HCCPaymentQueryResponse> queryResponse = restTemplate.postForEntity(url, queryRequest, HCCPaymentQueryResponse.class);
		return queryResponse.getBody();
	}

	public ThreeDSEnrolResponse enrol(String dtsReference, String obfuscateCardNumber) throws Exception {
		ThreeDSEnrolRequest enrolRequest = new ThreeDSEnrolRequest()
				.cardNumber(obfuscateCardNumber)
				.pspReference(dtsReference)
				.esureReference(FAKE_ESURE_REF)
				.postCode(FAKE_POSTCODE)
				.address(FAKE_ADDRESS)
				.merchantURL(FAKE_MERCHANT_URL)
				.browserAcceptHeader(FAKE_ACCEPT_HEADER)
				.browserAgent(FAKE_USER_AGENT)
				.amount(FAKE_AMOUNT)
				.cpaAgreement(CPA_AGREEMENT)
				.numberOfTries(INIT_NUM_TRIES)
				.productCode(FAKE_PRODUCT_CODE);

		final String url = configVariables.getBaseUrl() + configVariables.getEnrolUrl();
		ResponseEntity<ThreeDSEnrolResponse> response = restTemplate.postForEntity(url, enrolRequest, ThreeDSEnrolResponse.class);
		return response.getBody();
	}

	public ThreeDSAuthoriseResponse authorise(String dtsReference, String paresMessage) throws Exception {
		ThreeDSAuthoriseRequest authoriseRequest = new ThreeDSAuthoriseRequest()
				.paresMessage(paresMessage)
				.pspReference(dtsReference)
				.productCode(FAKE_PRODUCT_CODE);

		final String url = configVariables.getBaseUrl() + configVariables.getAuthoriseUrl();
		ResponseEntity<ThreeDSAuthoriseResponse> response = restTemplate.postForEntity(url, authoriseRequest, ThreeDSAuthoriseResponse.class);
		return response.getBody();
	}

}
