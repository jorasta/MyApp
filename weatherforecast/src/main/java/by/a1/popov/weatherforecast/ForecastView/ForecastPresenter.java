package by.a1.popov.weatherforecast.ForecastView;

import android.app.Application;

public interface ForecastPresenter {
    void getCurrentWeather(boolean tempMetric, String city);
    void getForecastWeather(boolean tempMetric, String city);
    void getLastCity(Application application);

}
