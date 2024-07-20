package com.farm.collector.serviceimpl;

import java.util.List;

import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.model.FarmDetailsResponse;

public interface FarmCollectorService {
	
	void addFarmDetails(FarmDetailsRequest farmDetailsRequest);
	FarmDetailsResponse getFarmDetailsByFarmID(Long id);
	List<FarmDetailsResponse> getFarmDetailsBySeason(String season);

}