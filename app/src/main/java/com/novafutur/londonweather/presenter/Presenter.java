package com.novafutur.londonweather.presenter;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.novafutur.londonweather.R;
import com.novafutur.londonweather.model.CurrentWeather;
import com.novafutur.londonweather.model.DataManager;
import com.novafutur.londonweather.model.Forecast;
import com.novafutur.londonweather.model.VolleySingleton;
import com.novafutur.londonweather.view.ForecastBottomSheetDialogFragment;
import com.novafutur.londonweather.view.ForecastItemAdapter;
import com.novafutur.londonweather.view.MainActivity;

import java.util.ArrayList;

public class Presenter {

    private static final String LOG_TAG = Presenter.class.getName();

    private MainActivity activity;

    public ArrayList<Forecast> getForecastItems() {
        return forecastItems;
    }

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
        activity.updateDateOfLastUpdate(currentWeather.getDateOfLastUpdate());
        activity.updateWeatherTemperature(
                currentWeather.getWeatherTempCurrent(),
                currentWeather.getWeatherTempMin(),
                currentWeather.getWeatherTempMax()
        );

        activity.getBtnRefresh().setImageResource(R.drawable.ic_refresh);
        activity.getPbRefreshCurrentWeather().setVisibility(android.view.View.INVISIBLE);
    }

    public void updateForecastWeather() {
        dataManager.fetchForecastWeather();
    }

    public void onFetchForecastWeatherSuccess(ArrayList<Forecast> forecastItems) {
        this.forecastItems = forecastItems;
//        RecyclerView recyclerView = activity.getForecastSheet().getRvForecasts();
//        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
//        recyclerView.setAdapter(new ForecastItemAdapter(activity, forecastItems));
    }

    public interface View {
        void updateWeatherDescription(String description);
        void updateWeatherTemperature(int tempCurrent, int tempMin, int tempMax);
        void updateWeatherIcon();
        void updateDateOfLastUpdate(String date);
    }
}
