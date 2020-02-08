package by.a1.popov.weatherforecast.ForecastView;

import java.util.List;

import by.a1.popov.weatherforecast.repo.forecast.Weather;

public interface ForecastView {
    void showCurrentWeather(Weather currentWeather);
    void showForecastWeather(List<Weather> forecastWeather);
    void showLastCityForecast(String city);
    void showCities();
}
