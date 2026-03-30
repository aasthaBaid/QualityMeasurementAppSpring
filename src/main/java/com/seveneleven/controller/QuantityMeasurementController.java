package com.seveneleven.controller;

import com.seveneleven.model.dto.QuantityMeasurementDto;
import com.seveneleven.model.dto.QuantityMeasurementInputDto;
import com.seveneleven.service.IQuantityMeasurementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quantities")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    // CONVERT
    // POST http://localhost:8080/api/v1/quantities/convert
    @PostMapping("/convert")
    public ResponseEntity<QuantityMeasurementDto> convert(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.convert(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }

    // COMPARE
    // POST http://localhost:8080/api/v1/quantities/compare
    @PostMapping("/compare")
    public ResponseEntity<QuantityMeasurementDto> compare(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.compare(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }

    // ADD
    // POST http://localhost:8080/api/v1/quantities/add
    @PostMapping("/add")
    public ResponseEntity<QuantityMeasurementDto> add(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.add(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }

    // SUBTRACT
    // POST http://localhost:8080/api/v1/quantities/subtract
    @PostMapping("/subtract")
    public ResponseEntity<QuantityMeasurementDto> subtract(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.subtract(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }

    // MULTIPLY
    // POST http://localhost:8080/api/v1/quantities/multiply
    @PostMapping("/multiply")
    public ResponseEntity<QuantityMeasurementDto> multiply(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.multiply(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }

    // DIVIDE
    // POST http://localhost:8080/api/v1/quantities/divide
    @PostMapping("/divide")
    public ResponseEntity<QuantityMeasurementDto> divide(
            @RequestBody QuantityMeasurementInputDto input) {

        QuantityMeasurementDto result = service.divide(
                input.getThisQuantity(),
                input.getThatQuantity()
        );
        return ResponseEntity.ok(result);
    }
    
    @GetMapping("/history")
    public ResponseEntity<?> getHistory() {
		return ResponseEntity.ok(service.getHistory());
	}
    
}