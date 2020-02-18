package by.a1.popov.weatherforecast.repo.forecast;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeatherDeserializer implements Deserializer<Weather> {

    @Override
    public Weather getData(String data) throws JSONException {
        JSONObject jRoot = new JSONObject(data);
        JSONObject jMain = jRoot.getJSONObject("main");
        JSONObject jWeather = jRoot.getJSONArray("weather").getJSONObject(0);

        double temperature = jMain.getDouble("temp");
        String description = jWeather.getString("description");
        String iconId = jWeather.getString("icon");

        return new Weather(temperature,description,iconId,null);
    }
}
