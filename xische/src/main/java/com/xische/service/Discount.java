package com.xische.service;

import java.math.BigDecimal;
import java.util.List;

import com.xische.dto.ItemDto;

public interface Discount {

	public BigDecimal apply(List<ItemDto> itemDtos);
}
