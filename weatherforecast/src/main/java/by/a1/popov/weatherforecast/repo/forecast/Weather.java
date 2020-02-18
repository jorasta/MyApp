package by.a1.popov.weatherforecast.repo.forecast;

public class Weather {
    private double temperature;
    private String description;
    private String iconId;

    private String dateTimeForecast;

    public Weather(double temperature, String description, String iconId, String dateTimeForecast) {
        this.temperature = temperature;
        this.description = description;
        this.dateTimeForecast = dateTimeForecast;
        this.iconId = iconId;
    }

    public String getDateTimeForecast() {
        return dateTimeForecast;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getDescription() {
        return description;
    }

    public String getIconId() {
        return iconId;
    }
}
