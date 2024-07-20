package com.farm.collector.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FarmDetailsRequest {
	
	private String ownerName;
	private Long plantingArea;
	private String farmName;
	private String season;
	private List<CropDto> crops;

}
