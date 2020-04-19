package com.novafutur.londonweather.model;

public class Forecast extends Weather {
    private String day;

    public Forecast(String weatherDescription, int weatherTempCurrent, int weatherTempMin, int weatherTempMax, String day, String weatherIcon) {
        super(weatherDescription, weatherTempCurrent, weatherTempMin, weatherTempMax, weatherIcon);
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
