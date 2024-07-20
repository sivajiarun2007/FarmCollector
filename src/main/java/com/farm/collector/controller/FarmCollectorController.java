package com.farm.collector.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.serviceimpl.FarmCollectorService;

@RestController("farmService")
public class FarmCollectorController {

	FarmCollectorService farmCollectorService;

	public FarmCollectorController(FarmCollectorService farmCollectorService) {
		this.farmCollectorService = farmCollectorService;
	}

	@PostMapping("/save-details")
	public ResponseEntity<String> addFarmDetails(@RequestBody FarmDetailsRequest farmDetailsRequest) {
		farmCollectorService.addFarmDetails(farmDetailsRequest);

		return new ResponseEntity<>(HttpStatus.ACCEPTED);

	}

}
