package by.a1.popov.weatherforecast.repo.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {CityEntity.class}, version = 1, exportSchema = false)
public abstract class CitiesDatabase extends RoomDatabase {

    private static volatile CitiesDatabase instance;

    public static CitiesDatabase getInstance(final Application application) {
        if (instance == null) {
            synchronized (CitiesDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application,
                            CitiesDatabase.class,
                            "db_cities.db")
                            .build();
                }
            }
        }
        return instance;
    }

    public CitiesDatabase() {
    }

    public abstract CitiesDao citiesDao();
}

