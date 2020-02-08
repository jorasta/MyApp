package by.a1.popov.weatherforecast.CitiesFiew;

import java.util.List;

import by.a1.popov.weatherforecast.repo.database.CityEntity;

public interface CitiesView {
    void showAllCities(List<CityEntity> cityEntities);
    void addNewCity(String city);
    int getLastCityId();
}
