package com.xische.service.impl;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.xische.api.request.BillCalculationRequest;
import com.xische.api.response.BillCalculationResponse;
import com.xische.cache.ExchangeRateCache;
import com.xische.entity.Item;
import com.xische.enums.Category;
import com.xische.mapping.BillMapper;
import com.xische.mapping.UserMapper;
import com.xische.repository.ItemRepository;

@SpringBootTest
public class BillServiceImplTest {

	@Autowired
	private BillServiceImpl billService;
	
	@MockBean
	private ItemRepository itemRepository;
	
	@MockBean
	private ExchangeRateCache cache;
	
	@MockBean
	private UserServiceImpl userService;
	
	@ParameterizedTest
	@CsvSource({
        "'EMPLOYEE', '193.0', '62.9'",
        "'AFFILIATE', '193.0', '24.3",
        "'REGULAR', '193.0', '5.0'", 
    })
	public void calculate_shouldConvertToTargetCurrencyAndApplyCorrectDiscount(String role, Double expectedAmount, Double expectedDiscountAmount) {
		
		BillCalculationRequest request = BillMapper.mapToBillCalculationRequest(new ArrayList<Long>(), "EUR");
		
		List<Item> items = new ArrayList<>() {
			{add(BillMapper.mapToItem("Tea", BigDecimal.ONE, "USD", Category.GROCERY.name()));}
			{add(BillMapper.mapToItem("TV", BigDecimal.TEN, "USD", Category.ELECTRONICS.name()));}
			{add(BillMapper.mapToItem("PC", BigDecimal.valueOf(200), "USD", Category.ELECTRONICS.name()));}};
					
		when(itemRepository.findAllById(request.getItems())).thenReturn(items);
		when(cache.getRates(request.getTargetCurrency())).thenReturn(new HashMap<String, Double>() {{put("USD", 1.084);}});
		when(userService.getAuthenticatedUser()).thenReturn(UserMapper.mapUserToUserDetails("Ali", null, role, LocalDate.now()));
		
		BillCalculationResponse actual = billService.calculate(request);
		
		assertAll(
		        () -> assertEquals(expectedAmount, actual.getAmount()),
		        () -> assertEquals(expectedDiscountAmount, actual.getDiscountAmount()),
		        () -> assertEquals(request.getTargetCurrency(), actual.getTargetCurrency())
		    );
		
	}
}
