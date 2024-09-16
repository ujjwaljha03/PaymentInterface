package com.ujjwal.IntegratingStripe_2.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChargeRequest {
	
	public enum Currency{
		EUR, USD;
	}
	
	private String description;
	private int amount;
	private Currency currency;
	private String stripeEmail;
	private String  stripeToken;

}
