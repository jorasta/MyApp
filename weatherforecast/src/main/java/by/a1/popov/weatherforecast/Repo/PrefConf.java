package by.a1.popov.weatherforecast.Repo;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConf {

    final static String FILE_NAME = "LastCity";

    final static String PREF_CITY = "LASTCITY";

    private SharedPreferences preferences;

    public PrefConf(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    public void setPrefCity(String data) {
        getEditor().putString(PREF_CITY, data).commit();
    }

    public String getPrefCity() {
        return preferences.getString(PREF_CITY, "Minsk");
    }
}
