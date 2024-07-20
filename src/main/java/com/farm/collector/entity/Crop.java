package com.farm.collector.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Crop {
	
	@Id
	private Long id;
	private String cropName;
	private Long expectedOutcome;
	private Long actualHarvest;
}
