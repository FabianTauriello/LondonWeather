package com.novafutur.londonweather.model;

import android.content.Context;
import android.widget.Toast;

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
import java.util.Locale;

/**
 * This class handles API calls and sends the results back to the Presenter class
 */
public class DataManager {
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static Presenter presenter;
    private RequestQueue requestQueue;

    public DataManager(Presenter presenter, Context context) {
        DataManager.presenter = presenter;
        requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    /**
     * Sets up a request to grab the current weather for London. The request is added to a request
     * queue and once there is a response, the JSON data is processed and sent back to the presenter.
     */
    public void fetchCurrentWeather() {
        String url = BASE_URL + "weather?id=2643743&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d";

        // set up request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
                    int weatherHumidity = main.getInt("humidity");

                    // get time of data calculation
                    long unixSeconds = response.getLong("dt");
                    Date date = new java.util.Date(unixSeconds * 1000L);
                    // format the date
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM hh:mma", Locale.UK);
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

                    // pass result back to presenter
                    DataManager.presenter.onFetchCurrentWeatherSuccess(currentWeather);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // communication problem with the server
            @Override
            public void onErrorResponse(VolleyError error) {
                DataManager.presenter.onFetchCurrentWeatherError(error);
            }
        });
        // add request to queue
        requestQueue.add(request);
    }

    /**
     * Sets up a request to grab the weather forecast for London. The request is added to a request
     * queue and once there is a response, the JSON data is processed and sent back to the presenter. The api
     * provides 3-hour intervals for weather forecasts but I'm only using the weather forecast for 3pm for each day.
     */
    public void fetchForecastWeather() {
        String url = BASE_URL + "forecast?id=2643743&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d";

        // set up request
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Forecast> forecastArrayList = new ArrayList<>();
                try {
                    // get weather list
                    JSONArray jsonArray = response.getJSONArray("list");

                    // filter array above to only retrieve the weather forecast for 3pm each day
                    JSONArray jsonFilteredArray = new JSONArray();
                    jsonFilteredArray.put(jsonArray.get(5));
                    jsonFilteredArray.put(jsonArray.get(13));
                    jsonFilteredArray.put(jsonArray.get(21));
                    jsonFilteredArray.put(jsonArray.get(29));
                    jsonFilteredArray.put(jsonArray.get(37));

                    for (int i = 0; i < jsonFilteredArray.length(); i++) {
                        JSONObject main = jsonFilteredArray.getJSONObject(i).getJSONObject("main");
                        JSONObject weather = jsonFilteredArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0);
                        long unixDate = jsonFilteredArray.getJSONObject(i).getLong("dt");
                        Date date = new java.util.Date(unixDate * 1000L);
                        // format the date to show day of week only (e.g. Monday)
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE", Locale.UK);
                        String dayOfWeek = sdf.format(date);
                        // build forecast list
                        forecastArrayList.add(new Forecast(
                                weather.getString("description"),
                                (int) Math.round(main.getDouble("temp")),
                                (int) Math.round(main.getDouble("temp_min")),
                                (int) Math.round(main.getDouble("temp_max")),
                                dayOfWeek
                        ));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // pass result back to presenter
                DataManager.presenter.onFetchForecastWeatherSuccess(forecastArrayList);
            }
        }, new Response.ErrorListener() { // communication problem with the server
            @Override
            public void onErrorResponse(VolleyError error) {
                // Do nothing here. Presenter.forecast items will be empty and this case is handled in
                // ForecastBottomSheetDialogFragment where an error message will be shown.
            }
        });
        // add request to queue
        requestQueue.add(request);
    }
}