package by.a1.popov.weatherforecast.repo.forecast;

import org.json.JSONException;

public interface Deserializer<T> {
    T getData(String data) throws JSONException;
}
