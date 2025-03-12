package com.xische.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xische.service.AmountDiscount;
import com.xische.service.Discount;
import com.xische.service.UserDiscount;
import com.xische.service.UserService;

@Component
public class DiscountFactory {

	@Autowired
	private UserService userService;
	
	@Autowired
	private Map<String, UserDiscount> userDiscounts;
	
	@Autowired
	private Map<String, AmountDiscount> amountDiscounts;

	public Discount getDefaultDiscount() {
		
		UserDiscount userDiscount = switch (userService.getAuthenticatedUser().getRole()) {
	        case "EMPLOYEE" -> userDiscounts.get("employeeDiscount");
	        case "AFFILIATE" -> userDiscounts.get("affiliateDiscount");
	        default -> userDiscounts.get("regularDiscount");
		};
		
		AmountDiscount amountDiscount = amountDiscounts.get("hundredDollarDiscount");
		amountDiscount.setUserDiscount(userDiscount);
		
		return amountDiscount;
		
	}
}
