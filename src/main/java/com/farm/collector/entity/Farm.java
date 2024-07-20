package com.farm.collector.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Farm {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String ownerName;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private FarmDetail farmDetail;
	
}
