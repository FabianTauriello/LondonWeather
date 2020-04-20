package com.novafutur.londonweather.model;

/**
 * This class is the parent class for all weather types ('current' and 'forecast' types)
 */
public class CurrentWeather extends Weather {
    private int humidity;
    private String dateOfLastUpdate;
    private String weatherIcon;

    public CurrentWeather(String weatherDescription, int weatherTempCurrent, int weatherTempMin,
                          int weatherTempMax, int humidity, String dateOfLastUpdate, String weatherIcon) {
        super(weatherDescription, weatherTempCurrent, weatherTempMin, weatherTempMax);
        this.humidity = humidity;
        this.dateOfLastUpdate = dateOfLastUpdate;
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public String getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public int getHumidity() {
        return humidity;
    }

}
