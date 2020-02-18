package by.a1.popov.weatherforecast.CitiesFiew;

import android.app.Application;
import com.google.android.gms.tasks.TaskExecutors;
import by.a1.popov.weatherforecast.repo.CitiesRepo;
import by.a1.popov.weatherforecast.repo.database.CityEntity;

public class CitiesPresenterImpl implements CitiesPresenter {

    private CitiesView view;
    private CitiesRepo citiesRepo;

    public CitiesPresenterImpl(CitiesView view, Application application) {
        this.view = view;
        this.citiesRepo = new CitiesRepo(application);
    }

    @Override
    public void getAllCities() {
        citiesRepo.getAllCities()
                .thenAcceptAsync(cityEntityList -> view.showAllCities(cityEntityList), TaskExecutors.MAIN_THREAD);
    }

    @Override
    public void addNewCity(String city) {
        citiesRepo.saveCity(city)
                .thenAccept(aVoid -> getAllCities());
    }

    @Override
    public void deleteCity(CityEntity cityEntity) {
        citiesRepo.deleteCity(cityEntity)
                .thenAccept(aVoid -> getAllCities());
    }

    @Override
    public void setLasCity(int cityId) {
        citiesRepo.setLasCityId(cityId);
    }

    @Override
    public int getLasCityID() {
        return citiesRepo.getLasCityId();
    }
}
