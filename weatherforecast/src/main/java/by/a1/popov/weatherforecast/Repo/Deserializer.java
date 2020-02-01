package by.a1.popov.weatherforecast.Repo;

import org.json.JSONException;

public interface Deserializer<T> {
    T getData(String data) throws JSONException;
}
