package by.a1.popov.weatherforecast.repo;

import android.app.Application;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import by.a1.popov.weatherforecast.repo.database.CitiesDao;
import by.a1.popov.weatherforecast.repo.database.CitiesDatabase;
import by.a1.popov.weatherforecast.repo.database.CityEntity;


public class CitiesRepo {

    private CitiesDao citiesDao;
    private PrefConf prefConf;

    public CitiesRepo(Application application) {
        citiesDao = CitiesDatabase.getInstance(application).citiesDao();
        prefConf = new PrefConf(application.getApplicationContext());
    }

    public int getLasCityId(){
        return prefConf.getPrefCity();
    }

    public void setLasCityId(int id){
        prefConf.setPrefCity(id);
    }

    public CompletableFuture<List<CityEntity>> getAllCities() {
        return CompletableFuture.supplyAsync(() -> citiesDao.getAll());
    }

    public CompletableFuture<CityEntity> getCity(final int id) {
        return CompletableFuture.supplyAsync(() -> citiesDao.getEntity(id));
    }

    public CompletableFuture<Void> saveCity(String city) {
        final CityEntity cityEntity = new CityEntity();
        cityEntity.setCity(city);

        return CompletableFuture.supplyAsync(() -> {
            citiesDao.insertCity(cityEntity);
            return null;
        });
    }

    public CompletableFuture<Void> deleteCity(CityEntity cityEntity) {
        return CompletableFuture.supplyAsync(() -> {
            citiesDao.delete(cityEntity);
            return null;
        });
    }

}
