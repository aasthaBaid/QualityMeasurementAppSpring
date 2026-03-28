package com.seveneleven.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityMeasurementDto {

    // Input info
    private double thisValue;
    private String thisUnit;
    private String thisMeasurementType;

    private double thatValue;
    private String thatUnit;
    private String thatMeasurementType;

    // Operation performed
    private String operation;

    // Result
    private double resultValue;
    private String resultUnit;
    private String resultString;        // for comparison: "true" or "false"

    // Error info
    private boolean isError;
    private String errorMessage;
}