package com.farm.collector.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farm.collector.entity.Farm;

public interface FarmCollectorRepository extends JpaRepository<Farm, Long> {
	
	

}
