package com.ujjwal.IntegratingStripe.Controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import com.ujjwal.IntegratingStripe.Model.ChargeRequest;

@Controller                                      // no Restcontroller beacuse i needed to have thymeleaf page first(view) not some data. 
public class PaymentController {
	
	@GetMapping("/")
	public String greet() {
		return "checkout";                                // checkout is thymeleaf build html page can see in template folder in resources.
	}

	@Value("${stripe.currency}")                      // manually added but could be removed with frontend
	private String currency;
	
	
	@PostMapping(value = "/charge")
	@ResponseBody                                                 // response body was given beacuse i needed to return data ony no view in this request,if there was a restController then no need to give it..
	public ResponseEntity<String> chargeCard(ChargeRequest chargeRequest){                         // VVVVIIP see no @RequestBody annotaion given because it gives this error application/x-www-form-urlencoded for this request beacuse spring foesnt understand reques body with this given.
		try {
			ChargeCreateParams createParams = new ChargeCreateParams.Builder()
					.setAmount(chargeRequest.getAmount())
					.setCurrency(currency)
					.setSource(chargeRequest.getToken())
					.build();
			Charge charge = Charge.create(createParams);
			return ResponseEntity.ok("Payment Succesfull at id: " + charge.getId());
			
		} catch (StripeException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment Failed beacuse " + e.getMessage());
		}
	}
}
