package by.a1.popov.weatherforecast.Repo;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeatherDeserializer implements Deserializer<CurrentWeather> {

    @Override
    public CurrentWeather getData(String data) throws JSONException {
        JSONObject jRoot = new JSONObject(data);
        JSONObject jMain = jRoot.getJSONObject("main");
        JSONObject jWeather = jRoot.getJSONArray("weather").getJSONObject(0);

        double temperature = jMain.getDouble("temp");
        String description = jWeather.getString("description");
        String iconId = jWeather.getString("icon");

        return new CurrentWeather(temperature,description,iconId);
    }
}
