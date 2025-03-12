package com.xische.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.mapping.BillMapper;
import com.xische.mapping.UserMapper;

@ExtendWith(MockitoExtension.class)
public class RegularDiscountTest {

	@InjectMocks
	private RegularDiscount discount;
	
	@Mock
	private UserServiceImpl service;
	
	@ParameterizedTest
	@CsvSource({
        "'2021-03-11', '0.5'",
        "'2024-03-11', '0.0'"
    })
	public void apply_shouldApplyDiscountOnUnExcludedCategoriesAndGtTenures(LocalDate tenure, Double expected) {
		List<ItemDto> dtos = new ArrayList<>() {
			{add(BillMapper.mapItemToItemDto("Tea", BigDecimal.ONE, "USD", Category.GROCERY.name()));}
			{add(BillMapper.mapItemToItemDto("TV", BigDecimal.TEN, "USD", Category.ELECTRONICS.name()));}};
			
		when(service.getAuthenticatedUser()).thenReturn(UserMapper.mapUserToUserDetails("Ali", null, "Regular", tenure));	
			
		BigDecimal actual = discount.apply(dtos);
		
		assertEquals(expected, actual.doubleValue());
	}
}
