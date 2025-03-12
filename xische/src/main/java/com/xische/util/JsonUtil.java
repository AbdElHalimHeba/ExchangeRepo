package com.xische.util;

import java.util.Map;

import org.springframework.boot.json.JsonParseException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Map<String, Double> parseApiConversionRates(String jsonString) {
        try {
			return objectMapper.convertValue(
					objectMapper.readTree(jsonString).path("conversion_rates"), 
					Map.class);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
    
    public static Map<String, Double> parseFallbackConversionRates(String jsonString, String targetCurrency) {
        try {
        	if(objectMapper.readTree(jsonString).path("base_code").path(targetCurrency).isMissingNode())
        		throw new IllegalArgumentException("Target currency is missing");
        	
        	return objectMapper.convertValue(objectMapper.readTree(jsonString)
					.path("base_code")
					.path(targetCurrency)
					.path("conversion_rates"), Map.class);
        } catch (Exception e) {
            throw new JsonParseException(e);
        }
    }
}
