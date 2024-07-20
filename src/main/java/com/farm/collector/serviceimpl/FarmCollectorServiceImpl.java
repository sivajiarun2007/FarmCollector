package com.farm.collector.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.farm.collector.entity.Crop;
import com.farm.collector.entity.Farm;
import com.farm.collector.entity.FarmDetail;
import com.farm.collector.model.CropDto;
import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.model.FarmDetailsResponse;
import com.farm.collector.repository.FarmRepository;

@Service
public class FarmCollectorServiceImpl implements FarmCollectorService {

	private FarmRepository farmRepository;

	public FarmCollectorServiceImpl(FarmRepository farmRepository) {
		this.farmRepository = farmRepository;
	}

	@Override
	public void addFarmDetails(FarmDetailsRequest farmDetailsRequest) {
		Farm farmRo = constructFarmRo(farmDetailsRequest);
		farmRepository.save(farmRo);

	}

	@Override
	public FarmDetailsResponse getFarmDetailsByFarmID(Long id) {
		if(farmRepository.findById(id).isPresent()) {
			return constructFarmDetailsResp(farmRepository.findById(id).get());
		}
		return null;
		
	}

	@Override
	public List<FarmDetailsResponse> getFarmDetailsBySeason(String season) {
		List<Farm> farm = farmRepository.findFarmDetailsBySeason(season);
		return constructFarmDetailsResp(farm);
	}

	private List<FarmDetailsResponse> constructFarmDetailsResp(List<Farm> farms) {
		List<FarmDetailsResponse> response = new ArrayList<>();

		farms.stream().forEach(farm -> {
			FarmDetailsResponse resp = new FarmDetailsResponse();
			resp.setOwnerName(farm.getOwnerName());
			FarmDetail detail = farm.getFarmDetail();
			resp.setFarmName(detail.getFarmName());
			resp.setSeason(detail.getSeason());
			resp.setPlantingArea(detail.getPlantingArea());

			List<CropDto> crops = new ArrayList<>();
			detail.getCrop().stream().forEach(crop -> {
				CropDto dto = new CropDto();
				dto.setCropName(crop.getCropName());
				dto.setActualHarvest(crop.getActualHarvest());
				dto.setExpectedOutcome(crop.getExpectedOutcome());
				crops.add(dto);
			});

			resp.setCrops(crops);

			response.add(resp);

		});

		return response;
	}

	private FarmDetailsResponse constructFarmDetailsResp(Farm farm) {
		FarmDetailsResponse resp = new FarmDetailsResponse();
		resp.setOwnerName(farm.getOwnerName());
		FarmDetail detail = farm.getFarmDetail();
		resp.setFarmName(detail.getFarmName());
		resp.setSeason(detail.getSeason());

		List<CropDto> crops = new ArrayList<>();
		detail.getCrop().stream().forEach(crop -> {
			CropDto dto = new CropDto();
			dto.setCropName(crop.getCropName());
			dto.setActualHarvest(crop.getActualHarvest());
			dto.setExpectedOutcome(crop.getExpectedOutcome());
			crops.add(dto);
		});

		resp.setCrops(crops);

		return resp;
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
