package com.xische.mapping;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import com.xische.api.request.BillCalculationRequest;
import com.xische.api.response.BillCalculationResponse;
import com.xische.dto.ItemDto;
import com.xische.entity.Item;
import com.xische.enums.Category;

public class BillMapper {

	public static BillCalculationResponse mapToBillCalculationResponse(String targetCurrency, BigDecimal amount, BigDecimal discountAmount) {
		
		BillCalculationResponse response = new BillCalculationResponse();
		
		response.setAmount(amount.doubleValue());
		response.setDiscountAmount(discountAmount.doubleValue());
		response.setTargetCurrency(targetCurrency);
		
		return response;
	}
	
	public static ItemDto mapItemToItemDto(Item item) {
		
		ItemDto dto = new ItemDto();
		
		dto.setName(item.getName());
		dto.setPrice(item.getPrice());
		dto.setCurrency(Currency.getInstance(item.getCurrency()));
		dto.setCategory(item.getCategory().name());
		
		return dto;
	}
	
	public static ItemDto mapItemToItemDto(String name, BigDecimal price, String currency, String category) {
		
		ItemDto dto = new ItemDto();
		
		dto.setName(name);
		dto.setPrice(price);
		dto.setCurrency(Currency.getInstance(currency));
		dto.setCategory(category);
		
		return dto;
	}
	
	public static BillCalculationRequest mapToBillCalculationRequest(List<Long> items, String targetCurrency) {
		
		BillCalculationRequest request = new BillCalculationRequest();
		
		request.setItems(items);
		request.setTargetCurrency(targetCurrency);
		
		return request;
	}
	
	public static Item mapToItem(String name, BigDecimal price, String currency, String category) {
		
		Item item = new Item();
		
		item.setName(name);
		item.setPrice(price);
		item.setCurrency(currency);
		item.setCategory(Category.valueOf(category));
		
		return item;
	}
}
