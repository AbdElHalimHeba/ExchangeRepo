package com.xische.cache;

import java.util.Map;

public interface ExchangeRateCache {

	public Map<String, Double> getRates(String targetCurrency);
	
}
