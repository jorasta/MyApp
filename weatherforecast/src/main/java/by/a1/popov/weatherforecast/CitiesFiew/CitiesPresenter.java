package by.a1.popov.weatherforecast.CitiesFiew;

import by.a1.popov.weatherforecast.repo.database.CityEntity;

public interface CitiesPresenter {
    void getAllCities();
    void addNewCity(String city);
    void deleteCity(CityEntity cityEntity);
    void setLasCity(int cityId);
    int getLasCityID();
}
