package com.novafutur.londonweather.model;

/**
 * This class represents the current weather for a single day
 */
public class Weather {
    // description of weather (e.g. 'overcast clouds')
    private String weatherDescription;
    private int weatherTempCurrent;
    private int weatherTempMin;
    private int weatherTempMax;

    Weather(String weatherDescription, int weatherTempCurrent, int weatherTempMin, int weatherTempMax) {
        this.weatherDescription = weatherDescription;
        this.weatherTempCurrent = weatherTempCurrent;
        this.weatherTempMin = weatherTempMin;
        this.weatherTempMax = weatherTempMax;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public int getWeatherTempCurrent() {
        return weatherTempCurrent;
    }

    public int getWeatherTempMin() {
        return weatherTempMin;
    }

    public int getWeatherTempMax() {
        return weatherTempMax;
    }

}
