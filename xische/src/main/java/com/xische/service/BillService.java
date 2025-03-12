package com.xische.service;

import com.xische.api.request.BillCalculationRequest;
import com.xische.api.response.BillCalculationResponse;

public interface BillService {

	public BillCalculationResponse calculate(BillCalculationRequest request);

}
