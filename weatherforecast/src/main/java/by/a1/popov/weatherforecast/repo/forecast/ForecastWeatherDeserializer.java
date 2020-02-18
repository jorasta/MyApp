package by.a1.popov.weatherforecast.repo.forecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ForecastWeatherDeserializer implements Deserializer<List<Weather>> {

    @Override
    public List<Weather> getData(String data) throws JSONException {
        JSONObject jRoot = new JSONObject(data);
        JSONArray jForecastList = jRoot.getJSONArray("list");
        ArrayList<Weather> forecastList = new ArrayList<>();

        for (int i =0; i < jForecastList.length(); i++){
            JSONObject jForecast = jForecastList.getJSONObject(i);
            JSONObject jMain = jForecast.getJSONObject("main");
            JSONObject jWeather = jForecast.getJSONArray("weather").getJSONObject(0);
            Weather weather = new Weather(
                    jMain.getDouble("temp"),
                    jWeather.getString("description"),
                    jWeather.getString("icon"),
                    unixSecToStringDate(jForecast.getLong("dt"))
            );
            forecastList.add(weather);
        }
        return forecastList;
    }

    private String unixSecToStringDate(long unixSec){
        Date date = new java.util.Date(unixSec*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy HH:mm");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }
}
