package by.a1.popov.weatherforecast.Repo;


import com.google.android.gms.tasks.TaskExecutors;

import java.util.function.Consumer;

import by.a1.popov.weatherforecast.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class ForecastRepo {
    public void getCurrentWeather(Consumer<CurrentWeather> callback){
        GetWeatherApiCallback<CurrentWeather> getWeatherApiCallback =
                new GetWeatherApiCallback<>(new CurrentWeatherDeserializer());

        String apiKey = BuildConfig.API_KEY;
        String url = String.format(Consts.GET_WEATHER_BY_CITY_NAME,"Brest",apiKey);
                //"https://newsapi.org/v2/top-headlines?country=ru&apiKey=" + apiKey;//String.format(Consts.GET_WEATHER_BY_CITY_NAME,cityName,apiKey);

        Request request = new Request.Builder().url(url).build();
        new OkHttpClient().newCall(request).enqueue(getWeatherApiCallback);

        getWeatherApiCallback
                .thenAcceptAsync(callback, TaskExecutors.MAIN_THREAD);
    }
}
