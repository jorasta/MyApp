package by.a1.popov.weatherforecast.ForecastView;

import android.app.Application;
import com.google.android.gms.tasks.TaskExecutors;
import by.a1.popov.weatherforecast.repo.CitiesRepo;
import by.a1.popov.weatherforecast.repo.forecast.ForecastRepo;

public class ForecastPresenterImpl implements ForecastPresenter {

    private ForecastView view;
    private ForecastRepo forecastRepo = new ForecastRepo();

    ForecastPresenterImpl(ForecastView view) {
        this.view = view;
    }

    @Override
    public void getCurrentWeather(boolean tempMetric, String city) {
        forecastRepo.getCurrentWeather(currentWeather -> view.showCurrentWeather(currentWeather), tempMetric, city);
    }

    @Override
    public void getForecastWeather(boolean tempMetric, String city) {
        forecastRepo.getForecastWeather(weathers -> view.showForecastWeather(weathers), tempMetric, city);
    }

    @Override
    public void getLastCity(Application application) {
        CitiesRepo citiesRepo = new CitiesRepo(application);
        int lastCityId = citiesRepo.getLasCityId();
        if (lastCityId == 0) {
            view.showCities();
        } else {
            citiesRepo.getCity(citiesRepo.getLasCityId())
                    .thenAcceptAsync(cityEntity -> view.showLastCityForecast(cityEntity.getCity()), TaskExecutors.MAIN_THREAD);
        }
    }


}
