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
import java.util.Calendar;
import java.util.Collections;
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
                    // get 'weather' object from response
                    // Note, it is possible to get more than one weather condition for a requested location.
                    // but OpenWeather mentions that the first weather condition in the API respond is primary
                    // so I've used the first index to get the primary object.
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

                    // get current date
                    Date currentDate = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
                    String currentDateFormatted = df.format(currentDate);

                    // min_temp numbers for a day
                    ArrayList<Integer> minTemps = new ArrayList<>();
                    // max_temp numbers for a day
                    ArrayList<Integer> maxTemps = new ArrayList<>();

                    // get weather list
                    JSONArray jsonArray = response.getJSONArray("list");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject currentObject = jsonArray.getJSONObject(i);
                        String responseDateTime = currentObject.getString("dt_txt");

                        // check if current object is not current day because forecast data CAN include current day.
                        if (!responseDateTime.contains(currentDateFormatted)) {

                            // get 'main' object from response
                            JSONObject main = currentObject.getJSONObject("main");
                            // get 'weather' object from response
                            // Note, it is possible to get more than one weather condition for a requested location.
                            // but OpenWeather mentions that the first weather condition in the API respond is primary
                            // so I've used the first index to get the primary object.
                            JSONObject weather = currentObject.getJSONArray("weather").getJSONObject(0);

                            long unixDate = currentObject.getLong("dt");
                            Date date = new java.util.Date(unixDate * 1000L);
                            // format the date to show day of week only (e.g. Monday)
                            SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE", Locale.UK);
                            String dayOfWeek = sdf.format(date);

                            // create forecast objects for the 3pm
                            if (responseDateTime.contains("15:00:00")) {
                                forecastArrayList.add(new Forecast(
                                        weather.getString("description"),
                                        (int) Math.round(main.getDouble("temp")),
                                        0,
                                        0,
                                        dayOfWeek
                                ));
                            }

                            minTemps.add((int) Math.round(main.getDouble("temp_min")));
                            maxTemps.add((int) Math.round(main.getDouble("temp_max")));

                            // check if it's the last object in the list
                            if (i != jsonArray.length()-1) {
                                // get next json object
                                JSONObject nextObject = jsonArray.getJSONObject(i+1);
                                // check if the next date in the list is not the same date as the current date
                                if (!nextObject.getString("dt_txt").substring(0, 10)
                                        .equals(currentObject.getString("dt_txt").substring(0, 10))) {
                                    forecastArrayList.get(forecastArrayList.size()-1).setWeatherTempMin(Collections.min(minTemps));
                                    forecastArrayList.get(forecastArrayList.size()-1).setWeatherTempMax(Collections.max(maxTemps));

                                    // clear these lists for the next day's calculations
                                    minTemps.clear();
                                    maxTemps.clear();
                                }
                            } else { // last item in list

                                // check if there are less than 5 days in list. If there are less than 5 at this point,
                                // then it means that the last day's forecast didn't reach 3pm (which is when it is added)
                                // to the list). So, I just take the latest time and use that as the forecast data instead.
                                if(forecastArrayList.size() < 5) {
                                    forecastArrayList.add(new Forecast(
                                            weather.getString("description"),
                                            (int) Math.round(main.getDouble("temp")),
                                            Collections.min(minTemps),
                                            Collections.max(maxTemps),
                                            dayOfWeek
                                    ));
                                }
                            }
                        }
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