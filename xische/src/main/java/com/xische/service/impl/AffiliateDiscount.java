package com.xische.service.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.service.UserDiscount;

@Component
public class AffiliateDiscount implements UserDiscount {

	private static final Set<Category> EXCLUDED_CATEGORIES = Collections.singleton(Category.GROCERY);
	
	private static final Double PERCENTAGE = 10.0;
	
	@Override
	public Double getPercentage() {
		return PERCENTAGE;
	}

	@Override
	public BigDecimal apply(List<ItemDto> itemDtos) {
		return itemDtos.stream()
				.filter(i -> !EXCLUDED_CATEGORIES.contains(Category.valueOf(i.getCategory())))
				.map(ItemDto::getPrice)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO)
				.multiply(new BigDecimal(PERCENTAGE/100));
	}

}
