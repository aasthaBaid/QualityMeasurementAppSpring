package com.seveneleven.service;

import com.seveneleven.model.dto.QuantityDto;
import com.seveneleven.model.dto.QuantityMeasurementDto;

public interface IQuantityMeasurementService {

	QuantityMeasurementDto convert(QuantityDto thisQuantity, QuantityDto thatQuantity);
	QuantityMeasurementDto compare(QuantityDto thisQuantity, QuantityDto thatQuantity);
	QuantityMeasurementDto add(QuantityDto thisQuantity, QuantityDto thatQuantity);
	QuantityMeasurementDto subtract(QuantityDto thisQuantity, QuantityDto thatQuantity);
	QuantityMeasurementDto multiply(QuantityDto thisQuantity, QuantityDto thatQuantity);
	QuantityMeasurementDto divide(QuantityDto thisQuantity, QuantityDto thatQuantity);
}