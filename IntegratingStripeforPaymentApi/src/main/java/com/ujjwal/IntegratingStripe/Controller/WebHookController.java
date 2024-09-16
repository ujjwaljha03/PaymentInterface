package com.ujjwal.IntegratingStripe.Controller;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.model.Event;
import com.stripe.net.Webhook;

import jakarta.servlet.http.HttpServletRequest;


//NOT WORKING DONT KNOW WHY

@RestController
public class WebHookController {

	@PostMapping("/webhook")
	public ResponseEntity<String> handleWebHook(@RequestBody String payload, HttpServletRequest request) throws IOException {
		String secret = "";

		try {

			Event event = Webhook.constructEvent(payload, request.getHeader("Stripe-Signature"), secret);

			switch (event.getType()) {
			case "payment_intent.succeeded": {
				return ResponseEntity.ok("Payment was done successfully at ID: " + event.getId());
			}
			case "payment_intent.payment_failed": {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("Payment failed at ID: " + event.getId());
			}
			default:
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unhandled event type: " + event.getType());
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("WebHook Error: " + e.getMessage());
		}
	}
}
