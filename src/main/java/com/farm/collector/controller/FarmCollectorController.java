package com.farm.collector.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.model.FarmDetailsResponse;
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

	@GetMapping("/get-farm-details-by-season/{season}")
	public ResponseEntity<List<FarmDetailsResponse>> addFarmDetails(@PathVariable String season) {

		List<FarmDetailsResponse> response = farmCollectorService.getFarmDetailsBySeason(season);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/get-farm-details-by-id/{id}")
	public ResponseEntity<FarmDetailsResponse> addFarmDetails(@PathVariable Long id) {

		FarmDetailsResponse response = farmCollectorService.getFarmDetailsByFarmID(id);

		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
