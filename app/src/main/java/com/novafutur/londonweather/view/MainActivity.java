package com.novafutur.londonweather.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.novafutur.londonweather.R;
import com.novafutur.londonweather.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements Presenter.View {

    private static final String LOG_TAG = MainActivity.class.getName();

    private TextView tvToday, tvDescription, tvTempCurrent, tvTempMin, tvTempMax, tvDateLastUpdated;
    private Button btnForecast;
    private ImageButton btnRefresh;
    private ProgressBar pbRefreshCurrentWeather;

    private ForecastBottomSheetDialogFragment forecastSheet;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

        presenter = new Presenter(this);
        forecastSheet = new ForecastBottomSheetDialogFragment(this, presenter);
        presenter.updateCurrentWeather();
        presenter.updateForecastWeather();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOG_TAG, "refresh clicked");
                presenter.updateCurrentWeather();

                btnRefresh.setImageResource(0);
                pbRefreshCurrentWeather.setVisibility(View.VISIBLE);
            }
        });
        btnForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forecastSheet.show(getSupportFragmentManager(), "forecastSheet");
            }
        });
    }

    public ImageButton getBtnRefresh() {
        return btnRefresh;
    }

    public ProgressBar getPbRefreshCurrentWeather() {
        return pbRefreshCurrentWeather;
    }

    private void setUpViews() {
        tvToday = findViewById(R.id.tv_today);
        tvDescription = findViewById(R.id.tv_description);
        tvTempCurrent = findViewById(R.id.tv_temp_current);
        tvTempMin = findViewById(R.id.tv_temp_min);
        tvTempMax = findViewById(R.id.tv_temp_max);
        tvDateLastUpdated = findViewById(R.id.tv_date_last_updated);
        btnForecast = findViewById(R.id.btn_forecast);
        btnRefresh = findViewById(R.id.btn_refresh);
        pbRefreshCurrentWeather = findViewById(R.id.progress_bar);


//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(forecastSheet, "sheet");
//        transaction.hide(forecastSheet);
//        transaction.commit();
    }

    @Override
    public void updateWeatherDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void updateWeatherTemperature(int tempCurrent, int tempMin, int tempMax) {
        tvTempCurrent.setText(getString(R.string.degree_celsius_symbol, Integer.toString(tempCurrent)));
        tvTempMin.setText(getString(R.string.degree_celsius_symbol, Integer.toString(tempMin)));
        tvTempMax.setText(getString(R.string.degree_celsius_symbol, Integer.toString(tempMax)));
    }

    @Override
    public void updateWeatherIcon() {

    }

    @Override
    public void updateDateOfLastUpdate(String date) {
        tvDateLastUpdated.setText(date);
    }

    public ForecastBottomSheetDialogFragment getForecastSheet() {
        return forecastSheet;
    }
}
