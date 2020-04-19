package com.novafutur.londonweather.model;

public class Weather {
    private String weatherDescription;
    private int weatherTempCurrent;
    private int weatherTempMin;
    private int weatherTempMax;
    private String weatherIcon;

    public Weather(String weatherDescription, int weatherTempCurrent, int weatherTempMin, int weatherTempMax, String weatherIcon) {
        this.weatherDescription = weatherDescription;
        this.weatherTempCurrent = weatherTempCurrent;
        this.weatherTempMin = weatherTempMin;
        this.weatherTempMax = weatherTempMax;
        this.weatherIcon = weatherIcon;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public int getWeatherTempCurrent() {
        return weatherTempCurrent;
    }

    public void setWeatherTempCurrent(int weatherTempCurrent) {
        this.weatherTempCurrent = weatherTempCurrent;
    }

    public int getWeatherTempMin() {
        return weatherTempMin;
    }

    public void setWeatherTempMin(int weatherTempMin) {
        this.weatherTempMin = weatherTempMin;
    }

    public int getWeatherTempMax() {
        return weatherTempMax;
    }

    public void setWeatherTempMax(int weatherTempMax) {
        this.weatherTempMax = weatherTempMax;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }


}
