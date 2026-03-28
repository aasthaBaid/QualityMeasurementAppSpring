package com.seveneleven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seveneleven.controller.QuantityMeasurementController;
import com.seveneleven.model.dto.QuantityDto;
import com.seveneleven.model.dto.QuantityMeasurementDto;
import com.seveneleven.model.dto.QuantityMeasurementInputDto;
import com.seveneleven.service.IQuantityMeasurementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(QuantityMeasurementController.class)
class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IQuantityMeasurementService service;

    @Autowired
    private ObjectMapper objectMapper;

    // ---------------- CONVERT ----------------
    @Test
    void testConvert() throws Exception {

        QuantityDto thisQ = new QuantityDto(1, "KILOMETER", "LENGTH");
        QuantityDto thatQ = new QuantityDto(0, "METER", "LENGTH");

        QuantityMeasurementInputDto input =
                new QuantityMeasurementInputDto(thisQ, thatQ, "CONVERT");

        QuantityMeasurementDto mockResponse = new QuantityMeasurementDto(
                1, "KILOMETER", "LENGTH",
                0, "METER", "LENGTH",
                "CONVERT",
                1000, "METER", null,
                false, null
        );

        when(service.convert(thisQ, thatQ)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/quantities/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(1000))
                .andExpect(jsonPath("$.resultUnit").value("METER"))
                .andExpect(jsonPath("$.operation").value("CONVERT"))
                .andExpect(jsonPath("$.error").value(false));
    }

    // ---------------- COMPARE ----------------
    @Test
    void testCompare() throws Exception {

        QuantityDto thisQ = new QuantityDto(1000, "METER", "LENGTH");
        QuantityDto thatQ = new QuantityDto(1, "KILOMETER", "LENGTH");

        QuantityMeasurementInputDto input =
                new QuantityMeasurementInputDto(thisQ, thatQ, "COMPARE");

        QuantityMeasurementDto mockResponse = new QuantityMeasurementDto(
                1000, "METER", "LENGTH",
                1, "KILOMETER", "LENGTH",
                "COMPARE",
                0, null, "true",
                false, null
        );

        when(service.compare(thisQ, thatQ)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/quantities/compare")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultString").value("true"))
                .andExpect(jsonPath("$.operation").value("COMPARE"))
                .andExpect(jsonPath("$.error").value(false));
    }

    // ---------------- ADD ----------------
    @Test
    void testAdd() throws Exception {

        QuantityDto thisQ = new QuantityDto(2, "KILOGRAM", "WEIGHT");
        QuantityDto thatQ = new QuantityDto(500, "GRAM", "WEIGHT");

        QuantityMeasurementInputDto input =
                new QuantityMeasurementInputDto(thisQ, thatQ, "ADD");

        QuantityMeasurementDto mockResponse = new QuantityMeasurementDto(
                2, "KILOGRAM", "WEIGHT",
                500, "GRAM", "WEIGHT",
                "ADD",
                2.5, "KILOGRAM", null,
                false, null
        );

        when(service.add(thisQ, thatQ)).thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue").value(2.5))
                .andExpect(jsonPath("$.operation").value("ADD"))
                .andExpect(jsonPath("$.error").value(false));
    }

    // ---------------- ERROR CASE ----------------
    @Test
    void testAddDifferentMeasurementType_Error() throws Exception {

        QuantityDto thisQ = new QuantityDto(1, "METER", "LENGTH");
        QuantityDto thatQ = new QuantityDto(1, "KILOGRAM", "WEIGHT");

        QuantityMeasurementInputDto input =
                new QuantityMeasurementInputDto(thisQ, thatQ, "ADD");

        QuantityMeasurementDto errorResponse = new QuantityMeasurementDto();
        errorResponse.setError(true);
        errorResponse.setErrorMessage(
                "Cannot perform ADD on different measurement types");

        when(service.add(thisQ, thatQ)).thenReturn(errorResponse);

        mockMvc.perform(post("/api/v1/quantities/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.error").value(true))
                .andExpect(jsonPath("$.errorMessage")
                        .value("Cannot perform ADD on different measurement types"));
    }
}
