package com.xische.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.service.UserDiscount;
import com.xische.service.UserService;

@Component
public class RegularDiscount implements UserDiscount {

	private static final Set<Category> EXCLUDED_CATEGORIES = Collections.singleton(Category.GROCERY);
	
	private static final Double PERCENTAGE = 5.0;
	
	private static final Long TENURE_DISCOUNT_YRS = 2l;
	
	@Autowired
	private UserService service;
	
	@Override
	public Double getPercentage() {
		return PERCENTAGE;
	}

	@Override
	public BigDecimal apply(List<ItemDto> itemDtos) {
		LocalDate userTenure = service.getAuthenticatedUser().getTenure();
		if(userTenure.until(LocalDate.now(), ChronoUnit.YEARS) < TENURE_DISCOUNT_YRS) return BigDecimal.ZERO;
		
		return itemDtos.stream()
				.filter(i -> !EXCLUDED_CATEGORIES.contains(Category.valueOf(i.getCategory())))
				.map(ItemDto::getPrice)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO)
				.multiply(new BigDecimal(PERCENTAGE/100));
		
	}

}
