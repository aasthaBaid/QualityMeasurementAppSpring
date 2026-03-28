package com.seveneleven.model.units;

public enum LengthUnit {

	METER(1.0),
	KILOMETER(1000.0),
	CENTIMETER(0.01),
	MILLIMETER(0.001),
	INCH(0.0254),
	FOOT(0.3048),
	YARD(0.9144),
	MILE(1609.34);

	private final double toMeter;

	LengthUnit(double toMeter) {
		this.toMeter = toMeter;
	}

	public double toBase(double value) {
		return value * toMeter;
	}

	public double fromBase(double baseValue) {
		return baseValue / toMeter;
	}

}
