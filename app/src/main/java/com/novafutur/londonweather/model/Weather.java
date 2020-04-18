package com.novafutur.londonweather.model;

public class Weather {
    private String weatherDescription;
    private int weatherTemperature;
    private String weatherIcon;

    public Weather(String weatherDescription, int weatherTemperature, String weatherIcon) {
        this.weatherDescription = weatherDescription;
        this.weatherTemperature = weatherTemperature;
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getWeatherTemperature() {
        return weatherTemperature;
    }

    public void setWeatherTemperature(int weatherTemperature) {
        this.weatherTemperature = weatherTemperature;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }


}
