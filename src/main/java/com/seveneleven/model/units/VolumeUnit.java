
package com.seveneleven.model.units;

public enum VolumeUnit {

    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541),
    CUP(0.236588),
    PINT(0.473176);

    private final double toLitre;

    VolumeUnit(double toLitre) {
        this.toLitre = toLitre;
    }

    public double toBase(double value) {
        return value * toLitre;
    }

    public double fromBase(double baseValue) {
        return baseValue / toLitre;
    }
}
