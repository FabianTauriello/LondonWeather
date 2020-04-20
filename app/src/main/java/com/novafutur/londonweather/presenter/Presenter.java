package com.novafutur.londonweather.presenter;

import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.novafutur.londonweather.R;
import com.novafutur.londonweather.model.CurrentWeather;
import com.novafutur.londonweather.model.DataManager;
import com.novafutur.londonweather.model.Forecast;
import com.novafutur.londonweather.view.MainActivity;

import java.util.ArrayList;

/**
 * This class is used as the intermediate node in the MVP pattern, where data is passed between the
 * Model and the View.
 */
public class Presenter {
    private MainActivity activity;
    private ArrayList<Forecast> forecastItems;
    private DataManager dataManager;

    public Presenter(MainActivity activity) {
        this.activity = activity;
        dataManager = new DataManager(this, activity);
    }

    public void updateCurrentWeather() {
        dataManager.fetchCurrentWeather();
    }

    public void onFetchCurrentWeatherSuccess(CurrentWeather currentWeather) {
        activity.updateWeatherDescription(currentWeather.getWeatherDescription());
        activity.updateHumidityPercentage(currentWeather.getHumidity());
        activity.updateDateOfLastUpdate(currentWeather.getDateOfLastUpdate());
        activity.updateWeatherTemperature(
                currentWeather.getWeatherTempCurrent(),
                currentWeather.getWeatherTempMin(),
                currentWeather.getWeatherTempMax()
        );
        String iconUrl = "http://openweathermap.org/img/wn/" + currentWeather.getWeatherIcon() + "@2x.png";
        activity.updateCurrentWeatherIcon(iconUrl);

        activity.getVMinMaxDivider().setVisibility(android.view.View.VISIBLE);
        activity.getBtnRefresh().setImageResource(R.drawable.ic_refresh);
        activity.getPbRefreshCurrentWeather().setVisibility(android.view.View.INVISIBLE);
    }

    public void onFetchCurrentWeatherError(VolleyError error) {
        Toast.makeText(activity, "An error has occurred while attempting to retrieve current weather data.", Toast.LENGTH_LONG).show();

        activity.getVMinMaxDivider().setVisibility(android.view.View.INVISIBLE);
        activity.getBtnRefresh().setImageResource(R.drawable.ic_refresh);
        activity.getPbRefreshCurrentWeather().setVisibility(android.view.View.INVISIBLE);
    }

    public void updateForecastWeather() {
        dataManager.fetchForecastWeather();
    }

    public void onFetchForecastWeatherSuccess(ArrayList<Forecast> forecastItems) {
        this.forecastItems = forecastItems;
    }

    public ArrayList<Forecast> getForecastItems() {
        return forecastItems;
    }

    /**
     * Interface which will need to implemented by MainActivity to display weather data
     */
    public interface View {
        void updateWeatherDescription(String description);

        void updateWeatherTemperature(int tempCurrent, int tempMin, int tempMax);

        void updateCurrentWeatherIcon(String icon);

        void updateDateOfLastUpdate(String date);

        void updateHumidityPercentage(int humidity);
    }
}
