package com.ujjwal.IntegratingStripe.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChargeRequest {

	private String token;
	private Long amount;
}
