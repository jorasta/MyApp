package by.a1.popov.weatherforecast.ForecastView;



import java.util.function.Consumer;

import by.a1.popov.weatherforecast.Repo.CurrentWeather;
import by.a1.popov.weatherforecast.Repo.ForecastRepo;

public class ForecastPresenterImpl implements ForecastPresenter {

    private ForecastView view;
    private ForecastRepo forecastRepo = new ForecastRepo();

    public ForecastPresenterImpl(ForecastView view) {
        this.view = view;
    }

    @Override
    public void getCurrentWeather() {
        forecastRepo.getCurrentWeather(new Consumer<CurrentWeather>() {
            @Override
            public void accept(CurrentWeather currentWeather) {
                view.showCurrentWeather(currentWeather);
            }
        });
    }
}
