package com.farm.collector.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.farm.collector.entity.FarmDetail;

public interface FarmDetailsRepository extends JpaRepository<FarmDetail, Long> {

	List<FarmDetail> findBySeason(String season);
}
