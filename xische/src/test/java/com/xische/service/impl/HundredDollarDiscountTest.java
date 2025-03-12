package com.xische.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.mapping.BillMapper;
import com.xische.service.UserDiscount;

@ExtendWith(MockitoExtension.class)
public class HundredDollarDiscountTest {

	@InjectMocks
	private HundredDollarDiscount discount;
	
	@Mock
	private UserDiscount employeeDiscount;
	
	@Test
	public void apply_shouldApplyDiscountOnUnExcludedCategoriesAndGtTenures() {
		List<ItemDto> dtos = new ArrayList<>() {
			{add(BillMapper.mapItemToItemDto("Tea", BigDecimal.ONE, "USD", Category.GROCERY.name()));}
			{add(BillMapper.mapItemToItemDto("TV", BigDecimal.TEN, "USD", Category.ELECTRONICS.name()));}
			{add(BillMapper.mapItemToItemDto("PC", BigDecimal.valueOf(100), "USD", Category.ELECTRONICS.name()));}};
			
		when(employeeDiscount.apply(dtos)).thenReturn(BigDecimal.ONE);	
			
		BigDecimal actual = discount.apply(dtos);
		
		assertEquals(BigDecimal.valueOf(6.0), actual);
	}
}
