package com.farm.collector.entity;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Farm {


	@Id
	private Long id;
	private String ownerName;
	
	@OneToMany
	private List<FarmDetail> farmDetails;
	
}
