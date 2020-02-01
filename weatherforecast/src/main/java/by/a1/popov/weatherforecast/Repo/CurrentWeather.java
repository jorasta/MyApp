package by.a1.popov.weatherforecast.Repo;

public class CurrentWeather {
    private double temperature;
    private String description, iconId;

    public CurrentWeather(double temperature, String description, String iconId) {
        this.temperature = temperature;
        this.description = description;
        this.iconId = iconId;
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
