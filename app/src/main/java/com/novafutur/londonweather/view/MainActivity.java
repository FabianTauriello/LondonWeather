package com.novafutur.londonweather.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.novafutur.londonweather.R;
import com.novafutur.londonweather.presenter.Presenter;
import com.squareup.picasso.Picasso;

/**
 * This class represents the single screen for the application. The current weather is displayed in the
 * middle and the weather forecast can be seen by clicking on the 'Open Forecast' button.
 */
public class MainActivity extends AppCompatActivity implements Presenter.View {
    private TextView tvDescription, tvTempCurrent, tvTempMin, tvTempMax, tvDateLastUpdated, tvHumidityPercentage;
    private Button btnForecast;
    private ImageButton btnRefresh;
    private ProgressBar pbRefreshCurrentWeather;
    private ImageView ivWeatherIcon;
    private ForecastBottomSheetDialogFragment forecastDialogFragment;
    private Presenter presenter;
    private View vMinMaxDivider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

        presenter = new Presenter(this);
        forecastDialogFragment = new ForecastBottomSheetDialogFragment(this, presenter);

        // fetch current weather and forecast weather data
        presenter.updateCurrentWeather();
        presenter.updateForecastWeather();

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateCurrentWeather();

                // remove the refresh icon and replace with an animating progress bar spinner
                btnRefresh.setImageResource(0);
                pbRefreshCurrentWeather.setVisibility(View.VISIBLE);
            }
        });
        btnForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forecastDialogFragment.show(getSupportFragmentManager(), "forecastSheet");
            }
        });
    }

    public ImageButton getBtnRefresh() {
        return btnRefresh;
    }

    public ProgressBar getPbRefreshCurrentWeather() {
        return pbRefreshCurrentWeather;
    }

    public View getVMinMaxDivider() {
        return vMinMaxDivider;
    }

    private void setUpViews() {
        tvDescription = findViewById(R.id.tv_description);
        tvTempCurrent = findViewById(R.id.tv_temp_current);
        tvTempMin = findViewById(R.id.tv_temp_min);
        tvTempMax = findViewById(R.id.tv_temp_max);
        tvDateLastUpdated = findViewById(R.id.tv_date_last_updated);
        btnForecast = findViewById(R.id.btn_forecast);
        btnRefresh = findViewById(R.id.btn_refresh);
        pbRefreshCurrentWeather = findViewById(R.id.progress_bar);
        ivWeatherIcon = findViewById(R.id.weather_icon);
        tvHumidityPercentage = findViewById(R.id.tv_humidity_percentage);
        vMinMaxDivider = findViewById(R.id.temp_min_max_divider);
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
    public void updateCurrentWeatherIcon(String icon) {
        Picasso.with(this).load(icon).into(ivWeatherIcon);
    }

    @Override
    public void updateDateOfLastUpdate(String date) {
        tvDateLastUpdated.setText((getString(R.string.time_of_last_update, date)));
    }

    @Override
    public void updateHumidityPercentage(int humidity) {
        tvHumidityPercentage.setText(getString(R.string.humidity_percentage, Integer.toString(humidity)));
    }

}
