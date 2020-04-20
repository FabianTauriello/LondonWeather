package com.novafutur.londonweather;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.novafutur.londonweather.view.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class Tests {
    /**
     * Simple test to see if MainActivity opens.
     */
    @Test
    public void testMainActivityCreate() {
        // launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // find id with id main_root and check if it's displayed
        Espresso.onView(ViewMatchers.withId(R.id.main_root)).check(matches(isDisplayed()));
    }

    /**
     * Test to see if pressing 'open forecast' button opens recycler view - will only work
     * if forecast weather api request was successful.
     */
    @Test
    public void testOpeningOfForecastFragment() {
        // launch MainActivity
        ActivityScenario.launch(MainActivity.class);

        // perform a click action on the open forecast button
        Espresso.onView(withId(R.id.btn_forecast)).perform(click());

        // find recycler view id and check if it's displayed
        Espresso.onView(withId(R.id.rv_forecasts)).check(matches(isDisplayed()));
    }

    /**
     * Test to see if current weather data is displayed - will only work
     * if current weather api request was successful.
     */
    @Test
    public void testDisplayingCurrentWeatherData() {
        // launch MainActivity
        ActivityScenario scenario = ActivityScenario.launch(MainActivity.class);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check weather description contains a value
        Espresso.onView(withId(R.id.tv_description)).check(matches(not(withText(""))));
        // Check weather temperature contains a value
        Espresso.onView(withId(R.id.tv_temp_current)).check(matches(not(withText(""))));
        // Check weather temperature min contains a value
        Espresso.onView(withId(R.id.tv_temp_min)).check(matches(not(withText(""))));
        // Check weather temperature max contains a value
        Espresso.onView(withId(R.id.tv_temp_max)).check(matches(not(withText(""))));
        // Check humidity of last update contains a value
        Espresso.onView(withId(R.id.tv_humidity_percentage)).check(matches(not(withText(""))));
        // Check date of last update contains a value
        Espresso.onView(withId(R.id.tv_date_last_updated)).check(matches(not(withText(""))));

    }
}
