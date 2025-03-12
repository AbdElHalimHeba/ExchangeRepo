package com.xische.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.xische.dto.ItemDto;
import com.xische.enums.Category;
import com.xische.service.AmountDiscount;
import com.xische.service.UserDiscount;

@Component
public class HundredDollarDiscount implements AmountDiscount {

	private static final Set<Category> EXCLUDED_CATEGORIES = Collections.singleton(Category.GROCERY);
	
	private static final Double MULTIPLIER = 100.0;
	
	private static final Double WAIVE = 5.0;
	
	private UserDiscount userDiscount;
	
	@Override
	public void setUserDiscount(UserDiscount userDiscount) {
		this.userDiscount = userDiscount;
	}
	
	@Override
	public BigDecimal apply(List<ItemDto> itemDtos) {
		BigDecimal discountAmount = itemDtos.stream()
				.filter(i -> !EXCLUDED_CATEGORIES.contains(Category.valueOf(i.getCategory())))
				.map(ItemDto::getPrice)
				.reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO)
				.divide(BigDecimal.valueOf(MULTIPLIER), RoundingMode.FLOOR)
                .multiply(BigDecimal.valueOf(WAIVE));
		
		BigDecimal userDiscountAmount = userDiscount.apply(itemDtos);
		
        return discountAmount.add(userDiscountAmount);
	}

}
