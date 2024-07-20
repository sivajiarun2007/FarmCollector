package com.farm.collector.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CropDto {
	
	private String cropName;
	private Long expectedOutcome;
	private Long actualHarvest;

}
