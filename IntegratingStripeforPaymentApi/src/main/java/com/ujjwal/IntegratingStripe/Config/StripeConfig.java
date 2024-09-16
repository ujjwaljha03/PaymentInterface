package com.ujjwal.IntegratingStripe.Config;

import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import jakarta.annotation.PostConstruct;

@Configuration
public class StripeConfig {

	@Value("${stripe.secretKey}")
	private String secretKey;

	@PostConstruct
	public void init() {
		Stripe.apiKey = secretKey;
	}
}
