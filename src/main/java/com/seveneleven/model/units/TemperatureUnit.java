
package com.seveneleven.model.units;

public enum TemperatureUnit {

    CELSIUS {
        public double toBase(double value) {
            return value;
        }

        public double fromBase(double value) {
            return value;
        }
    },
    FAHRENHEIT {
        public double toBase(double value) {
            return (value - 32) * 5 / 9;
        }

        public double fromBase(double value) {
            return (value * 9 / 5) + 32;
        }
    },
    KELVIN {
        public double toBase(double value) {
            return value - 273.15;
        }

        public double fromBase(double value) {
            return value + 273.15;
        }
    };

    public abstract double toBase(double value);
    public abstract double fromBase(double value);
}
