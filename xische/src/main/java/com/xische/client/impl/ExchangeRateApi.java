package com.xische.client.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.xische.client.ExchangeRateClient;
import com.xische.util.JsonUtil;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Component
public class ExchangeRateApi implements ExchangeRateClient {

	private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/6870ea87144d95677e030254/latest/";
	
	private static final String FALLBACK_FILE = "fallback/rates.json";
	
	@Autowired
    private WebClient webClient; 
	
	@CircuitBreaker(name = "ExchangeRateApiCircuitBreaker", fallbackMethod = "exchangeRateApiFallback")
	@Override
	public Map<String, Double> getRates(String targetCurrency) {
		String url = String.format(BASE_URL + "%s", targetCurrency);
		String jsonString = webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
		
		return JsonUtil.parseApiConversionRates(jsonString);
	}
	
	public Map<String, Double> exchangeRateApiFallback(String targetCurrency, Throwable ex) {
        try {
           	String jsonString = new String(Files.readAllBytes(Paths.get(new ClassPathResource(FALLBACK_FILE).getURI())));
			return JsonUtil.parseFallbackConversionRates(jsonString, targetCurrency);
		} catch (IOException exception) {
            throw new IllegalStateException("An error occured while reading fallback rates file");
        }
    }

}
