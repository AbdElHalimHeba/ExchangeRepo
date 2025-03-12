package com.xische.api.request;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
public class BillCalculationRequest {

	@NotNull(message = "Items can not be null")
	@NotEmpty(message = "Items can not be empty")
	private List<Long> items;
	
	@NotNull(message = "Target currency can not be null")
	private String targetCurrency;
}
