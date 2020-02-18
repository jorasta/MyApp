package by.a1.popov.weatherforecast.repo.forecast;


import com.google.android.gms.tasks.TaskExecutors;

import java.util.List;
import java.util.function.Consumer;

import by.a1.popov.weatherforecast.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ForecastRepo {
    public void getCurrentWeather(Consumer<Weather> callback, boolean tempMetric, String city) {
        GetWeatherApiCallback<Weather> getWeatherApiCallback =
                new GetWeatherApiCallback<>(new CurrentWeatherDeserializer());

        String apiKey = BuildConfig.API_KEY;
        String units = (tempMetric) ? "metric" : "imperial";
        String url = String.format(Consts.GET_WEATHER_BY_CITY_NAME, city, units, apiKey);

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(getWeatherApiCallback);

        getWeatherApiCallback
                .thenAcceptAsync(callback, TaskExecutors.MAIN_THREAD);
    }

    public void getForecastWeather(Consumer<List<Weather>> callback, boolean tempMetric, String city) {

        GetWeatherApiCallback<List<Weather>> getForecastApiCallback =
                new GetWeatherApiCallback<>(new ForecastWeatherDeserializer());

        String apiKey = BuildConfig.API_KEY;
        String units = (tempMetric) ? "metric" : "imperial";
        String url = String.format(Consts.GET_FORECAST_WEATHER_BY_CITY_NAME, city, units, apiKey);

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(getForecastApiCallback);

        getForecastApiCallback
                .thenAcceptAsync(callback, TaskExecutors.MAIN_THREAD);
    }
}
