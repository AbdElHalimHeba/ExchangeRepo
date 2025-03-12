package com.xische.dto;

import java.math.BigDecimal;
import java.util.Currency;

import lombok.Data;

@Data
public class ItemDto {

	private String name;
	
	private BigDecimal price;
	
	private Currency currency;
	
	private String category;
}
