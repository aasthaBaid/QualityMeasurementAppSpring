package com.seveneleven.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementInputDto {

    private QuantityDto thisQuantity;      // first quantity
    private QuantityDto thatQuantity;      // second quantity (null for conversion)
    private String operation;             // "CONVERT", "COMPARE", "ADD", "SUBTRACT", "MULTIPLY", "DIVIDE"
}