package com.xische.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xische.api.request.BillCalculationRequest;
import com.xische.api.response.BillCalculationResponse;
import com.xische.service.BillService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class BillController {

	@Autowired
	private BillService service;
	
	@PostMapping(path = "/calculate")
	public BillCalculationResponse calculate(@Valid @RequestBody BillCalculationRequest request) {
		return service.calculate(request);
	}
}
