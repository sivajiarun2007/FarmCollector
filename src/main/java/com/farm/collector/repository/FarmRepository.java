package com.farm.collector.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.farm.collector.entity.Farm;

public interface FarmRepository extends JpaRepository<Farm, Long> {

	@Query(value = "select f from Farm f join f.farmDetail as farmdetail where farmdetail.season = ?1")
	List<Farm> findFarmDetailsBySeason(String season);
	
	Optional<Farm> findById(Long id);

}
