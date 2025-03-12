package com.xische.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.mapping.BillMapper;

@SpringBootTest
public class EmployeeDiscountTest {

	@Autowired
	private EmployeeDiscount discount;
	
	@Test
	public void apply_shouldApplyDiscountOnUnExcludedCategories() {
		List<ItemDto> dtos = new ArrayList<>() {
			{add(BillMapper.mapItemToItemDto("Tea", BigDecimal.ONE, "USD", Category.GROCERY.name()));}
			{add(BillMapper.mapItemToItemDto("TV", BigDecimal.TEN, "USD", Category.ELECTRONICS.name()));}};
			
		BigDecimal actual = discount.apply(dtos);
		
		assertEquals(BigDecimal.TEN.multiply(new BigDecimal(30.0/100)), actual);
	}
}
