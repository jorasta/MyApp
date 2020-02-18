package by.a1.popov.weatherforecast.repo.forecast;

public class Consts {

    public static final String GET_WEATHER_BY_CITY_NAME = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=%s&appid=%s&lang=ru";
    public static final String GET_FORECAST_WEATHER_BY_CITY_NAME = "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=%s&appid=%s&lang=ru";
    public static final String GET_WEATHER_ICON = "https://openweathermap.org/img/wn/%s.png";

    public Consts() {
    }
}
