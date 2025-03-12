package com.xische.cache.impl;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.xische.cache.ExchangeRateCache;
import com.xische.client.ExchangeRateClient;

@Component
public class ExchangeRateRedis implements ExchangeRateCache {

	private static final String CACHE_KEY = "rate"; 
	
	@Autowired
	private RedisTemplate<String, Map<String, Double>> redisTemplate;
	
	@Autowired
	private ExchangeRateClient client;

	@Override
	public Map<String, Double> getRates(String targetCurrency) {
		String key = CACHE_KEY + targetCurrency;
		
		if(redisTemplate.hasKey(key)) return redisTemplate.opsForValue().get(key);
		
		Map<String, Double> rates = client.getRates(targetCurrency);
		
		redisTemplate.opsForValue().set(key, rates);
		redisTemplate.expire(key, Duration.ofDays(1));
		
		return rates;
	}
	
}
