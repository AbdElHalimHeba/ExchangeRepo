package com.xische.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xische.api.request.BillCalculationRequest;
import com.xische.api.response.BillCalculationResponse;
import com.xische.cache.ExchangeRateCache;
import com.xische.dto.ItemDto;
import com.xische.entity.Item;
import com.xische.mapping.BillMapper;
import com.xische.repository.ItemRepository;
import com.xische.service.BillService;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ExchangeRateCache cache;
	
	@Autowired
	private DiscountFactory discountFactory;
	
	@Override
	public BillCalculationResponse calculate(BillCalculationRequest request) {
		
		List<Item> items = itemRepository.findAllById(request.getItems());
				
		Map<String, Double> rates = cache.getRates(request.getTargetCurrency());

		List<ItemDto> itemDtos = items.stream()
		.map(i -> {
			Double rate = i.getCurrency().equals(request.getTargetCurrency()) ? 1.0 : rates.get(i.getCurrency());
			return BillMapper.mapItemToItemDto(
				i.getName(), 
				i.getPrice().divide(new BigDecimal(rate), RoundingMode.FLOOR), 
				request.getTargetCurrency(), 
				i.getCategory().name());
		}).collect(Collectors.toList());
		
		BigDecimal amount = itemDtos.stream()
		.map(ItemDto::getPrice)
		.reduce(BigDecimal::add)
		.orElse(BigDecimal.ZERO);
		
		BigDecimal discountAmount = discountFactory.getDefaultDiscount().apply(itemDtos);
		
		BillCalculationResponse response = BillMapper.mapToBillCalculationResponse(request.getTargetCurrency(), amount, discountAmount);
		
		return response;
	}

}
