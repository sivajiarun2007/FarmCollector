package com.farm.collector.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.farm.collector.model.CropDto;
import com.farm.collector.model.FarmDetailsRequest;
import com.farm.collector.model.FarmDetailsResponse;
import com.farm.collector.serviceimpl.FarmCollectorService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FarmCollectorController.class)
public class FarmCollectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FarmCollectorService farmCollectorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddFarmDetails() throws Exception {
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

        mockMvc.perform(post("/save-details")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testGetFarmDetailsBySeason() throws Exception {
        String season = "Spring";

        List<FarmDetailsResponse> responses = new ArrayList<>();
        FarmDetailsResponse response = new FarmDetailsResponse();
        response.setOwnerName("John Doe");
        response.setFarmName("Green Acres");
        response.setSeason(season);
        response.setPlantingArea(100L);

        List<CropDto> crops = new ArrayList<>();
        CropDto crop = new CropDto();
        crop.setCropName("Corn");
        crop.setActualHarvest(90L);
        crop.setExpectedOutcome(100L);
        crops.add(crop);

        response.setCrops(crops);
        responses.add(response);

        when(farmCollectorService.getFarmDetailsBySeason(anyString())).thenReturn(responses);

        mockMvc.perform(get("/get-farm-details-by-season/{season}", season))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ownerName").value("John Doe"))
                .andExpect(jsonPath("$[0].farmName").value("Green Acres"))
                .andExpect(jsonPath("$[0].season").value("Spring"))
                .andExpect(jsonPath("$[0].plantingArea").value(100))
                .andExpect(jsonPath("$[0].crops[0].cropName").value("Corn"))
                .andExpect(jsonPath("$[0].crops[0].actualHarvest").value(90))
                .andExpect(jsonPath("$[0].crops[0].expectedOutcome").value(100));
    }

    @Test
    public void testGetFarmDetailsById() throws Exception {
        Long id = 1L;

        FarmDetailsResponse response = new FarmDetailsResponse();
        response.setOwnerName("John Doe");
        response.setFarmName("Green Acres");
        response.setSeason("Spring");
        response.setPlantingArea(100L);

        List<CropDto> crops = new ArrayList<>();
        CropDto crop = new CropDto();
        crop.setCropName("Corn");
        crop.setActualHarvest(90L);
        crop.setExpectedOutcome(100L);
        crops.add(crop);

        response.setCrops(crops);

        when(farmCollectorService.getFarmDetailsByFarmID(anyLong())).thenReturn(response);

        mockMvc.perform(get("/get-farm-details-by-id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerName").value("John Doe"))
                .andExpect(jsonPath("$.farmName").value("Green Acres"))
                .andExpect(jsonPath("$.season").value("Spring"))
                .andExpect(jsonPath("$.plantingArea").value(100))
                .andExpect(jsonPath("$.crops[0].cropName").value("Corn"))
                .andExpect(jsonPath("$.crops[0].actualHarvest").value(90))
                .andExpect(jsonPath("$.crops[0].expectedOutcome").value(100));
    }
    
    @Test
    public void testGetFarmDetailsByIdThrowsException() throws Exception {

        when(farmCollectorService.getFarmDetailsByFarmID(anyLong())).thenThrow(NullPointerException.class);

        mockMvc.perform(get("/get-farm-details-by-id/{id}", 1L))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }
}
