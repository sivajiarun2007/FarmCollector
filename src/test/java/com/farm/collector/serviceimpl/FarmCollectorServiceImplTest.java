package com.farm.collector.serviceimpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.farm.collector.entity.Crop;
import com.farm.collector.entity.Farm;
import com.farm.collector.entity.FarmDetail;
import com.farm.collector.model.CropDto;
import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.model.FarmDetailsResponse;
import com.farm.collector.repository.FarmRepository;

public class FarmCollectorServiceImplTest {

    @Mock
    private FarmRepository farmRepository;

    @InjectMocks
    private FarmCollectorServiceImpl farmCollectorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddFarmDetails() {
        FarmDetailsRequest request = new FarmDetailsRequest();
        request.setOwnerName("John Doe");
        request.setFarmName("Green Acres");
        request.setSeason("Spring");
        request.setPlantingArea(100L);

        List<CropDto> crops = new ArrayList<>();
        CropDto crop = new CropDto();
        crop.setCropName("Corn");
        crop.setActualHarvest(90L);
        crop.setExpectedOutcome(100L);
        crops.add(crop);

        request.setCrops(crops);

        Farm farm = new Farm();
        farm.setOwnerName(request.getOwnerName());

        FarmDetail farmDetail = new FarmDetail();
        farmDetail.setFarmName(request.getFarmName());
        farmDetail.setSeason(request.getSeason());
        farmDetail.setPlantingArea(request.getPlantingArea());

        List<Crop> cropList = new ArrayList<>();
        Crop cropEntity = new Crop();
        cropEntity.setCropName(crop.getCropName());
        cropEntity.setActualHarvest(crop.getActualHarvest());
        cropEntity.setExpectedOutcome(crop.getExpectedOutcome());
        cropList.add(cropEntity);

        farmDetail.setCrop(cropList);
        farm.setFarmDetail(farmDetail);

        when(farmRepository.save(any(Farm.class))).thenReturn(farm);

        farmCollectorService.addFarmDetails(request);

        verify(farmRepository, times(1)).save(any(Farm.class));
    }

    @Test
    public void testGetFarmDetailsByFarmID() {
        Long farmId = 1L;

        Farm farm = new Farm();
        farm.setOwnerName("John Doe");

        FarmDetail farmDetail = new FarmDetail();
        farmDetail.setFarmName("Green Acres");
        farmDetail.setSeason("Spring");
        farmDetail.setPlantingArea(100L);

        List<Crop> cropList = new ArrayList<>();
        Crop crop = new Crop();
        crop.setCropName("Corn");
        crop.setActualHarvest(90L);
        crop.setExpectedOutcome(100L);
        cropList.add(crop);

        farmDetail.setCrop(cropList);
        farm.setFarmDetail(farmDetail);

        when(farmRepository.findById(farmId)).thenReturn(Optional.of(farm));

        FarmDetailsResponse response = farmCollectorService.getFarmDetailsByFarmID(farmId);

        assertNotNull(response);
        assertEquals("John Doe", response.getOwnerName());
        assertEquals("Green Acres", response.getFarmName());
        assertEquals("Spring", response.getSeason());
        assertEquals(100, response.getPlantingArea());
        assertEquals(1, response.getCrops().size());
        assertEquals("Corn", response.getCrops().get(0).getCropName());
        assertEquals(90, response.getCrops().get(0).getActualHarvest());
        assertEquals(100, response.getCrops().get(0).getExpectedOutcome());
    }

    @Test
    public void testGetFarmDetailsBySeason() {
        String season = "Spring";

        List<Farm> farms = new ArrayList<>();

        Farm farm = new Farm();
        farm.setOwnerName("John Doe");

        FarmDetail farmDetail = new FarmDetail();
        farmDetail.setFarmName("Green Acres");
        farmDetail.setSeason(season);
        farmDetail.setPlantingArea(100L);

        List<Crop> cropList = new ArrayList<>();
        Crop crop = new Crop();
        crop.setCropName("Corn");
        crop.setActualHarvest(90L);
        crop.setExpectedOutcome(100L);
        cropList.add(crop);

        farmDetail.setCrop(cropList);
        farm.setFarmDetail(farmDetail);

        farms.add(farm);

        when(farmRepository.findFarmDetailsBySeason(season)).thenReturn(farms);

        List<FarmDetailsResponse> responses = farmCollectorService.getFarmDetailsBySeason(season);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        FarmDetailsResponse response = responses.get(0);
        assertEquals("John Doe", response.getOwnerName());
        assertEquals("Green Acres", response.getFarmName());
        assertEquals(season, response.getSeason());
        assertEquals(100, response.getPlantingArea());
        assertEquals(1, response.getCrops().size());
        assertEquals("Corn", response.getCrops().get(0).getCropName());
        assertEquals(90, response.getCrops().get(0).getActualHarvest());
        assertEquals(100, response.getCrops().get(0).getExpectedOutcome());
    }
}
