package com.ujjwal.IntegratingPayPal;

import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	// calling on api will direct me to paypal payment interface and after payment,
	// will be redirected to given redirect url.

	@PostMapping("/create-payment/{currency}/{pay}")
	public ResponseEntity createPayment(@PathVariable("currency") String currency, @PathVariable("pay") String pay) {
		Payment payment = new Payment();
		payment.setIntent("sale");

		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		payment.setPayer(payer);

		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setReturnUrl("http://localhost:8080/api/payments/execute-payment");
		redirectUrls.setCancelUrl("http://localhost:8080/api/payments/cancel-payment");
		payment.setRedirectUrls(redirectUrls);

		Transaction transaction = new Transaction();
		Amount amount = new Amount();
		amount.setCurrency(currency);
		amount.setTotal(pay);
		transaction.setAmount(amount);

		payment.setTransactions(Collections.singletonList(transaction));

		try { // Paypal client id , Paypal client secret key
			APIContext apiContext = new APIContext(
					"Your Paypal client Id",
					"Your Paypal Secret Key", "sandbox");
			Payment createdPayment = payment.create(apiContext);

			return ResponseEntity.ok(createdPayment.getLinks().get(1).getHref());
		} catch (PayPalRESTException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/execute-payment")
	public ResponseEntity executePayment(@RequestParam("paymentId") String paymentId,
			@RequestParam("PayerID") String payerId) {
		try {
			APIContext apiContext = new APIContext(
					"Your Paypal Client id",
					"Your Paypal Secret Key", "sandbox");
			Payment payment = Payment.get(apiContext, paymentId);
			PaymentExecution paymentExecution = new PaymentExecution();
			paymentExecution.setPayerId(payerId);
			Payment executedPayment = payment.execute(apiContext, paymentExecution);

			return ResponseEntity.ok(executedPayment.getState());
		} catch (PayPalRESTException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/cancel-payment")
	public ResponseEntity cancelPayment() {
		return ResponseEntity.ok("Payment canceled");
	}
}
