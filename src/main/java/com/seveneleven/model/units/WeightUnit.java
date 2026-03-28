package com.seveneleven.model.units;

public enum WeightUnit {

    KILOGRAM(1.0),
    GRAM(0.001),
    MILLIGRAM(0.000001),
    POUND(0.453592),
    OUNCE(0.0283495),
    TONNE(1000.0);

    private final double toKilogram;

    WeightUnit(double toKilogram) {
        this.toKilogram = toKilogram;
    }

    public double toBase(double value) {
        return value * toKilogram;
    }

    public double fromBase(double baseValue) {
        return baseValue / toKilogram;
    }
}
