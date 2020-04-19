package com.novafutur.londonweather.model;

import android.app.Activity;
import android.content.Context;
import android.media.audiofx.PresetReverb;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.novafutur.londonweather.presenter.Presenter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataManager {

    private static final String LOG_TAG = DataManager.class.getName();

    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";

    private static Presenter presenter;

    private RequestQueue requestQueue;

    public DataManager(Presenter presenter, Context context) {
        this.presenter = presenter;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public void fetchCurrentWeather() {
        String url = BASE_URL + "weather?id=2643743&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, "onResponse - current");
                try {

                    // get 'weather' object
                    JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                    String weatherDescription = weather.getString("description");
                    String weatherIcon = weather.getString("icon");

                    // get 'main' object from response
                    JSONObject main = response.getJSONObject("main");
                    // round the retrieved double up/down to int
                    int weatherTempCurrent = (int) Math.round(main.getDouble("temp"));
                    int weatherTempMin = (int) Math.round(main.getDouble("temp_min"));
                    int weatherTempMax = (int) Math.round(main.getDouble("temp_max"));
                    double weatherHumidity = main.getDouble("humidity");

                    // get time of data calculation
                    long unixSeconds = response.getLong("dt");
                    // convert seconds to milliseconds
                    Date date = new java.util.Date(unixSeconds*1000L);
                    // format the date
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                    String formattedDate = sdf.format(date);

                    // build current weather object
                    CurrentWeather currentWeather = new CurrentWeather(
                            weatherDescription,
                            weatherTempCurrent,
                            weatherTempMin,
                            weatherTempMax,
                            weatherHumidity,
                            formattedDate,
                            weatherIcon
                    );

                    DataManager.presenter.onFetchCurrentWeatherSuccess(currentWeather);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse");
            }
        });

        requestQueue.add(request);
    }

    public void fetchForecastWeather() {
        Log.d(LOG_TAG, "fetchForecastWeather called");
        String url = BASE_URL + "forecast?id=2643743&cnt=5&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(LOG_TAG, "onResponse - forecast");
                ArrayList<Forecast> forecastArrayList = new ArrayList<>();
                try {
                    // get weather list
                    JSONArray jsonArray = response.getJSONArray("list");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject main = jsonArray.getJSONObject(i).getJSONObject("main");
                        JSONObject weather = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                        forecastArrayList.add(new Forecast(
                                weather.getString("description"),
                                (int) Math.round(main.getDouble("temp")),
                                (int) Math.round(main.getDouble("temp_min")),
                                (int) Math.round(main.getDouble("temp_max")),
                                "",
                                weather.getString("icon")
                        ));
                    }
                } catch (JSONException e) {
                    Log.d(LOG_TAG, "onResponse - forecast - JSONException");
                    e.printStackTrace();
                }

                DataManager.presenter.onFetchForecastWeatherSuccess(forecastArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse - forecast");
            }
        });
        requestQueue.add(request);
    }


}