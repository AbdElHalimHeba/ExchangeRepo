package com.xische.api.response;

import lombok.Data;

@Data
public class BillCalculationResponse {

	private String targetCurrency;
	
	private Double amount;
	
	private Double discountAmount;
}
