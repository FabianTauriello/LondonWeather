package com.novafutur.londonweather.model;

/**
 * This class represents the weather forecast for a single day
 */
public class Forecast extends Weather {
    // day of week (e.g. Monday)
    private String day;

    Forecast(String weatherDescription, int weatherTempCurrent, int weatherTempMin, int weatherTempMax, String day) {
        super(weatherDescription, weatherTempCurrent, weatherTempMin, weatherTempMax);
        this.day = day;
    }

    public String getDay() {
        return day;
    }

}
