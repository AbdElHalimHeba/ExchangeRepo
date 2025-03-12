package com.xische.client;

import java.util.Map;

public interface ExchangeRateClient {

	public Map<String, Double> getRates(String targetCurrency);
}
