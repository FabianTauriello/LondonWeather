# London Weather Android Application

An Android application built using the MVP architectural pattern and a single activity to show the current weather and a 5-day forecast for London.

## UI

Upon opening the app, the user will see the following screen (if the current weather API request was successful):

![](/images/MainActivity.png)

And here is an image showing what each UI view is for:

![](/images/MainActivityLabels.png)

Note: the "Time of last update" label does NOT change every time a user presses the refresh button. Instead, this value shows the last time OpenWeatherMap updated their weather data (which is no more than one time every 10 minutes).

Clicking on the 'OPEN FORECAST' button will open a fragment showing 5-day forecast if the API request was successful:

![](/images/ForecastFragment.png)

If the API request was unsuccessful, this error will be shown:

![](/images/ForecastFragmentError.png)

If the current weather API request was unsuccessful, a Toast message will be shown:

![](/images/CurrentWeatherToastError.png)


## Architecture

As mentioned, I followed the model view presenter (**MVP**) architectural pattern to build the application. 

The **Model** acts as the provider of the data and contains most of the business logic. It consists of 5 classes. *Forecast*, *CurrentWeather*, and *Weather* are the basic building blocks for holding the data, *DataManager* is responsible for calling the API and parsing the JSON for sending to the presenter, and *VolleySingleton* is responsible for retrieving a request queue. 

The **View**, principally consisting of *MainActivity*, contains a reference to the presenter to display the data and take user actions. It also includes the *ForecastBottomSheetDialogFragment* class which is where forecast weather data is shown via a Recycler View.

The **Presenter** contains a single class and acts as the middle-man between the View and Model layers. It retrieves data from the Model and returns it formatted to the View.

## API Usage

I used data supplied by OpenWeatherMap and used the following endpoints:

https://api.openweathermap.org/data/2.5/weather?id=2643743&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d

and...

https://api.openweathermap.org/data/2.5/forecast?id=2643743&units=metric&appid=e6c5283a6cd03ca3bd888fce21b6830d

I used the query parameter "units=metric" to retrieve temperature data in degrees celsius. The other parameters are "id=2643743" which is one of the city id's for London and "appid=e6c5283a6cd03ca3bd888fce21b6830d" which is my API key and is required for all requests.

## Testing

There are three basic tests in the application. I've used the Espresso
library to check if MainActivity opens, check if current weather is displayed, and check if the recycler view displays forecast weather data.

## Areas for improvement

- The forecast data will retrieve 5 days of data from the OpenWeather API but the 5th day may not be very valuable. This particular API gets weather data every 3 hours over 5 days but some of that data can be taken up from the current day, which means the API may only provide a single data point for the 5th day at 12 am. Obviously, this doesn't accurately reflect what the 5th day's weather will be.
- It could be prettier - different themes/colours and pictures.
- The RecyclerView items could have onClick listeners to display more detailed data for a particular day.
- In the case of an error fetching weather info (after the very first fetch), the latest successfully fetched information could be shown instead.

---

Thanks for checking out the application!

