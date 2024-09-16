package com.ujjwal.IntegratingStripe_2.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.ApiException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.ujjwal.IntegratingStripe_2.Model.ChargeRequest;
import com.ujjwal.IntegratingStripe_2.Model.ChargeRequest.Currency;
import com.ujjwal.IntegratingStripe_2.Service.StripeService;

@Controller
public class ChargeController {

	@Autowired
	private StripeService paymentService;
	
	@PostMapping("/charge")
	public String charge(ChargeRequest chargeRequest,Model model) throws AuthenticationException, InvalidRequestException, ApiConnectionException, CardException, ApiException, StripeException {
		chargeRequest.setDescription("Example Charge");
		chargeRequest.setCurrency(Currency.EUR);
		Charge charge = paymentService.charge(chargeRequest);
		model.addAttribute("id",charge.getId());
		model.addAttribute("status",charge.getStatus());
		model.addAttribute("chargeId",charge.getId());
		model.addAttribute("balance_transaction",charge.getBalanceTransaction());
		return "result";
	}
	
	@ExceptionHandler(StripeException.class)
	public String handleError(Model model,StripeException ex) {
		model.addAttribute("error",ex.getMessage());
		return "result";
	}
	
}
