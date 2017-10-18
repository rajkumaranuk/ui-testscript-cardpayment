package com.esure.cardpayment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigVariables
{

	@Value("${base.url}")
	private String baseUrl;

	@Value("${setup.url}")
	private String setupUrl;

	@Value("${query.url}")
	private String queryUrl;

	@Value("${enrol.url}")
	private String enrolUrl;

	@Value("${authorise.url}")
	private String authoriseUrl;

	@Value("${tester}")
	private String tester;

	@Value("${browser}")
	private String browser;

	public String getBaseUrl()
	{
		return baseUrl;
	}

	public String getSetupUrl()
	{
		return setupUrl;
	}

	public String getQueryUrl()
	{
		return queryUrl;
	}

	public String getEnrolUrl()
	{
		return enrolUrl;
	}

	public String getAuthoriseUrl()
	{
		return authoriseUrl;
	}

	public String getTester()
	{
		return tester;
	}

	public String getBrowser()
	{
		return browser;
	}
}