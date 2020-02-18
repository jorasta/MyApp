package by.a1.popov.weatherforecast.repo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCity(CityEntity cityEntity);

    @Query("SELECT * FROM cities_entity")
    List<CityEntity> getAll();

    @Query("SELECT * FROM cities_entity WHERE ID=:id")
    CityEntity getEntity(int id);

    @Delete
    void delete(CityEntity cityEntity);

}
