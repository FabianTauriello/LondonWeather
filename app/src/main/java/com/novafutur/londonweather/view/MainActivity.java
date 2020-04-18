package com.novafutur.londonweather.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.novafutur.londonweather.R;
import com.novafutur.londonweather.presenter.Presenter;

public class MainActivity extends AppCompatActivity implements Presenter.View {

    private static final String LOG_TAG = MainActivity.class.getName();

    private TextView tvToday, tvDescription, tvTemperature, tvDateLastUpdated;
    private Button btnForecast;
    private ImageButton btnRefresh;
    private ProgressBar pbRefreshCurrentWeather;

    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpViews();

        presenter = new Presenter(this);
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
        tvTemperature = findViewById(R.id.tv_temperature);
        tvDateLastUpdated = findViewById(R.id.tv_date_last_updated);
        btnForecast = findViewById(R.id.btn_forecast);
        btnRefresh = findViewById(R.id.btn_refresh);
        pbRefreshCurrentWeather = findViewById(R.id.progress_bar);

    }

    @Override
    public void updateWeatherDescription(String description) {
        tvDescription.setText(description);
    }

    @Override
    public void updateWeatherTemperature(int temperature) {
        tvTemperature.setText(getString(R.string.degree_celsius_symbol, Integer.toString(temperature)));
    }

    @Override
    public void updateWeatherIcon() {

    }

    @Override
    public void updateDateOfLastUpdate(String date) {
        tvDateLastUpdated.setText(date);
    }

    // forecast panel color = #384266
}
