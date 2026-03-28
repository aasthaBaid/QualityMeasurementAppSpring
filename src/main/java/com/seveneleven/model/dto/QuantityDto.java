package com.seveneleven.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuantityDto {

    private double value;
    private String unit;
    private String measurementType;
}