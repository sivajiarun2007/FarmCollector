package com.farm.collector.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.farm.collector.entity.Crop;
import com.farm.collector.entity.Farm;
import com.farm.collector.entity.FarmDetail;
import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.repository.FarmCollectorRepository;

@Service
public class FarmCollectorServiceImpl implements FarmCollectorService {
	
	private FarmCollectorRepository farmCollectorRepository;
	
	public FarmCollectorServiceImpl(FarmCollectorRepository farmCollectorRepository) {
		this.farmCollectorRepository = farmCollectorRepository;
	}

	@Override
	public void addFarmDetails(FarmDetailsRequest farmDetailsRequest) {
		Farm farmRo = constructFarmRo(farmDetailsRequest);
		farmCollectorRepository.save(farmRo);

	}

	private Farm constructFarmRo(FarmDetailsRequest farmDetailsRequest) {

		Farm farm = new Farm();
		FarmDetail farmDetail = new FarmDetail();
		List<Crop> cropsList = new ArrayList<>();

		farmDetailsRequest.getCrops().stream().forEach(crop -> {
			Crop cropItem = new Crop();
			cropItem.setCropName(crop.getCropName());
			cropItem.setActualHarvest(crop.getActualHarvest());
			cropItem.setExpectedOutcome(crop.getExpectedOutcome());
			cropsList.add(cropItem);

		});

		farmDetail.setCrop(cropsList);
		farmDetail.setPlantingArea(farmDetailsRequest.getPlantingArea());
		farmDetail.setFarmName(farmDetailsRequest.getFarmName());
		farmDetail.setSeason(farmDetailsRequest.getSeason());

		farm.setOwnerName(farmDetailsRequest.getOwnerName());
		farm.setFarmDetail(farmDetail);
		
		return farm;

	}

}
