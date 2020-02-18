package by.a1.popov.weatherforecast.repo.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cities_entity")
public class CityEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String city;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
