package com.novafutur.londonweather.presenter;

import android.view.View;

import com.android.volley.RequestQueue;
import com.novafutur.londonweather.R;
import com.novafutur.londonweather.model.CurrentWeather;
import com.novafutur.londonweather.model.DataManager;
import com.novafutur.londonweather.model.Forecast;
import com.novafutur.londonweather.model.VolleySingleton;
import com.novafutur.londonweather.view.MainActivity;

import java.util.ArrayList;

public class Presenter {

    private static final String LOG_TAG = Presenter.class.getName();

    private RequestQueue requestQueue;
    private static MainActivity activity;

    public Presenter(MainActivity activity) {
        this.activity = activity;
        requestQueue = VolleySingleton.getInstance(activity).getRequestQueue();
    }

    public void updateCurrentWeather() {
        DataManager.fetchCurrentWeather(requestQueue);
    }

    public static void onFetchCurrentWeatherSuccess(CurrentWeather currentWeather) {
        activity.updateWeatherDescription(currentWeather.getWeatherDescription());
        activity.updateWeatherTemperature(currentWeather.getWeatherTemperature());
        activity.updateDateOfLastUpdate(currentWeather.getDateOfLastUpdate());

        activity.getBtnRefresh().setImageResource(R.drawable.ic_refresh);
        activity.getPbRefreshCurrentWeather().setVisibility(android.view.View.INVISIBLE);
    }

    public void updateForecastWeather() {
        DataManager.fetchForecastWeather(requestQueue);
    }

    public static void onFetchForecastWeatherSuccess(ArrayList<Forecast> currentWeather) {

    }

    public interface View {
        void updateWeatherDescription(String description);
        void updateWeatherTemperature(int temperature);
        void updateWeatherIcon();
        void updateDateOfLastUpdate(String date);
    }
}
