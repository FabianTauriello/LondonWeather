package com.novafutur.londonweather.model;

public class CurrentWeather extends Weather {
    private double humidity;
    private String dateOfLastUpdate;

    public CurrentWeather(String weatherDescription, int weatherTempCurrent, int weatherTempMin, int weatherTempMax, double humidity, String dateOfLastUpdate,  String weatherIcon) {
        super(weatherDescription, weatherTempCurrent, weatherTempMin, weatherTempMax, weatherIcon);
        this.humidity = humidity;
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public String getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public void setDateOfLastUpdate(String dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
