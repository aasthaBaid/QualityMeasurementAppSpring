package com.seveneleven.service;

import com.seveneleven.model.dto.QuantityDto;
import com.seveneleven.model.dto.QuantityMeasurementDto;
import com.seveneleven.model.entity.QuantityMeasurementEntity;
import com.seveneleven.repository.QuantityMeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    // CONVERT
    @Override
    public QuantityMeasurementDto convert(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        QuantityMeasurementDto result = new QuantityMeasurementDto();

        try {
            if (!thisQuantity.getMeasurementType().equals(thatQuantity.getMeasurementType())) {
                throw new Exception("Cannot convert between different measurement types");
            }

            double baseValue = toBaseUnit(thisQuantity.getValue(), thisQuantity.getUnit(), thisQuantity.getMeasurementType());
            double convertedValue = fromBaseUnit(baseValue, thatQuantity.getUnit(), thatQuantity.getMeasurementType());

            result.setThisValue(thisQuantity.getValue());
            result.setThisUnit(thisQuantity.getUnit());
            result.setThisMeasurementType(thisQuantity.getMeasurementType());
            result.setThatValue(thatQuantity.getValue());
            result.setThatUnit(thatQuantity.getUnit());
            result.setThatMeasurementType(thatQuantity.getMeasurementType());
            result.setOperation("CONVERT");
            result.setResultValue(convertedValue);
            result.setResultUnit(thatQuantity.getUnit());
            result.setError(false);

        } catch (Exception e) {
            result.setError(true);
            result.setErrorMessage(e.getMessage());
            result.setOperation("CONVERT");
        }

        saveToDatabase(thisQuantity, thatQuantity, result);
        return result;
    }

    // COMPARE
    @Override
    public QuantityMeasurementDto compare(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        QuantityMeasurementDto result = new QuantityMeasurementDto();

        try {
            if (!thisQuantity.getMeasurementType().equals(thatQuantity.getMeasurementType())) {
                throw new Exception("Cannot compare different measurement types");
            }

            double thisBase = toBaseUnit(thisQuantity.getValue(), thisQuantity.getUnit(), thisQuantity.getMeasurementType());
            double thatBase = toBaseUnit(thatQuantity.getValue(), thatQuantity.getUnit(), thatQuantity.getMeasurementType());

            boolean isEqual = Math.abs(thisBase - thatBase) < 0.0001;

            result.setThisValue(thisQuantity.getValue());
            result.setThisUnit(thisQuantity.getUnit());
            result.setThisMeasurementType(thisQuantity.getMeasurementType());
            result.setThatValue(thatQuantity.getValue());
            result.setThatUnit(thatQuantity.getUnit());
            result.setThatMeasurementType(thatQuantity.getMeasurementType());
            result.setOperation("COMPARE");
            result.setResultString(isEqual ? "true" : "false");
            result.setError(false);

        } catch (Exception e) {
            result.setError(true);
            result.setErrorMessage(e.getMessage());
            result.setOperation("COMPARE");
        }

        saveToDatabase(thisQuantity, thatQuantity, result);
        return result;
    }

    // ADD
    @Override
    public QuantityMeasurementDto add(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "ADD");
    }

    // SUBTRACT
    @Override
    public QuantityMeasurementDto subtract(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "SUBTRACT");
    }

    // MULTIPLY
    @Override
    public QuantityMeasurementDto multiply(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "MULTIPLY");
    }

    // DIVIDE
    @Override
    public QuantityMeasurementDto divide(QuantityDto thisQuantity, QuantityDto thatQuantity) {
        return performArithmetic(thisQuantity, thatQuantity, "DIVIDE");
    }

    // ARITHMETIC HELPER
    private QuantityMeasurementDto performArithmetic(QuantityDto thisQuantity, QuantityDto thatQuantity, String operation) {
        QuantityMeasurementDto result = new QuantityMeasurementDto();

        try {
            if (!thisQuantity.getMeasurementType().equals(thatQuantity.getMeasurementType())) {
                throw new Exception("Cannot perform " + operation + " on different measurement types");
            }

            double thisBase = toBaseUnit(thisQuantity.getValue(), thisQuantity.getUnit(), thisQuantity.getMeasurementType());
            double thatBase = toBaseUnit(thatQuantity.getValue(), thatQuantity.getUnit(), thatQuantity.getMeasurementType());

            double resultValue;

            switch (operation) {
                case "ADD":
                    resultValue = thisBase + thatBase;
                    break;
                case "SUBTRACT":
                    resultValue = thisBase - thatBase;
                    break;
                case "MULTIPLY":
                    resultValue = thisBase * thatBase;
                    break;
                case "DIVIDE":
                    if (thatBase == 0) throw new Exception("Cannot divide by zero");
                    resultValue = thisBase / thatBase;
                    break;
                default:
                    throw new Exception("Unknown operation: " + operation);
            }

            result.setThisValue(thisQuantity.getValue());
            result.setThisUnit(thisQuantity.getUnit());
            result.setThisMeasurementType(thisQuantity.getMeasurementType());
            result.setThatValue(thatQuantity.getValue());
            result.setThatUnit(thatQuantity.getUnit());
            result.setThatMeasurementType(thatQuantity.getMeasurementType());
            result.setOperation(operation);
            result.setResultValue(resultValue);
            result.setResultUnit(thisQuantity.getUnit());
            result.setError(false);

        } catch (Exception e) {
            result.setError(true);
            result.setErrorMessage(e.getMessage());
            result.setOperation(operation);
        }

        saveToDatabase(thisQuantity, thatQuantity, result);
        return result;
    }

    // TO BASE UNIT
    // LENGTH      → METER
    // WEIGHT      → KILOGRAM
    // VOLUME      → LITRE
    // TEMPERATURE → CELSIUS
    private double toBaseUnit(double value, String unit, String measurementType) throws Exception {
        switch (measurementType.toUpperCase()) {
            case "LENGTH":
                switch (unit.toUpperCase()) {
                    case "METER":       return value;
                    case "KILOMETER":   return value * 1000;
                    case "CENTIMETER":  return value / 100;
                    case "MILLIMETER":  return value / 1000;
                    case "INCH":        return value * 0.0254;
                    case "FOOT":        return value * 0.3048;
                    case "YARD":        return value * 0.9144;
                    case "MILE":        return value * 1609.34;
                    default: throw new Exception("Unknown length unit: " + unit);
                }
            case "WEIGHT":
                switch (unit.toUpperCase()) {
                    case "KILOGRAM":    return value;
                    case "GRAM":        return value / 1000;
                    case "MILLIGRAM":   return value / 1000000;
                    case "POUND":       return value * 0.453592;
                    case "OUNCE":       return value * 0.0283495;
                    case "TONNE":       return value * 1000;
                    default: throw new Exception("Unknown weight unit: " + unit);
                }
            case "VOLUME":
                switch (unit.toUpperCase()) {
                    case "LITRE":       return value;
                    case "MILLILITRE":  return value / 1000;
                    case "GALLON":      return value * 3.78541;
                    case "CUP":         return value * 0.236588;
                    case "PINT":        return value * 0.473176;
                    default: throw new Exception("Unknown volume unit: " + unit);
                }
            case "TEMPERATURE":
                switch (unit.toUpperCase()) {
                    case "CELSIUS":     return value;
                    case "FAHRENHEIT":  return (value - 32) * 5 / 9;
                    case "KELVIN":      return value - 273.15;
                    default: throw new Exception("Unknown temperature unit: " + unit);
                }
            default:
                throw new Exception("Unknown measurement type: " + measurementType);
        }
    }

    // FROM BASE UNIT TO TARGET UNIT
    private double fromBaseUnit(double value, String unit, String measurementType) throws Exception {
        switch (measurementType.toUpperCase()) {
            case "LENGTH":
                switch (unit.toUpperCase()) {
                    case "METER":       return value;
                    case "KILOMETER":   return value / 1000;
                    case "CENTIMETER":  return value * 100;
                    case "MILLIMETER":  return value * 1000;
                    case "INCH":        return value / 0.0254;
                    case "FOOT":        return value / 0.3048;
                    case "YARD":        return value / 0.9144;
                    case "MILE":        return value / 1609.34;
                    default: throw new Exception("Unknown length unit: " + unit);
                }
            case "WEIGHT":
                switch (unit.toUpperCase()) {
                    case "KILOGRAM":    return value;
                    case "GRAM":        return value * 1000;
                    case "MILLIGRAM":   return value * 1000000;
                    case "POUND":       return value / 0.453592;
                    case "OUNCE":       return value / 0.0283495;
                    case "TONNE":       return value / 1000;
                    default: throw new Exception("Unknown weight unit: " + unit);
                }
            case "VOLUME":
                switch (unit.toUpperCase()) {
                    case "LITRE":       return value;
                    case "MILLILITRE":  return value * 1000;
                    case "GALLON":      return value / 3.78541;
                    case "CUP":         return value / 0.236588;
                    case "PINT":        return value / 0.473176;
                    default: throw new Exception("Unknown volume unit: " + unit);
                }
            case "TEMPERATURE":
                switch (unit.toUpperCase()) {
                    case "CELSIUS":     return value;
                    case "FAHRENHEIT":  return (value * 9 / 5) + 32;
                    case "KELVIN":      return value + 273.15;
                    default: throw new Exception("Unknown temperature unit: " + unit);
                }
            default:
                throw new Exception("Unknown measurement type: " + measurementType);
        }
    }

    // SAVE TO DATABASE
    private void saveToDatabase(QuantityDto thisQuantity, QuantityDto thatQuantity, QuantityMeasurementDto result) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

        entity.setThisValue(thisQuantity.getValue());
        entity.setThisUnit(thisQuantity.getUnit());
        entity.setThisMeasurementType(thisQuantity.getMeasurementType());

        if (thatQuantity != null) {
            entity.setThatValue(thatQuantity.getValue());
            entity.setThatUnit(thatQuantity.getUnit());
            entity.setThatMeasurementType(thatQuantity.getMeasurementType());
        }

        entity.setOperation(result.getOperation());
        entity.setResultValue(result.getResultValue());
        entity.setResultUnit(result.getResultUnit());
        entity.setResultString(result.getResultString());
        entity.setError(result.isError());
        entity.setErrorMessage(result.getErrorMessage());

        repository.save(entity);
    }
}