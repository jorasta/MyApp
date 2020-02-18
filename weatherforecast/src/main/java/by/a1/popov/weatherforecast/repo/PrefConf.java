package by.a1.popov.weatherforecast.repo;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConf {

    private final static String FILE_NAME = "LastCityID";

    private final static String PREF_CITY = "LASTCITYID";

    private SharedPreferences preferences;

    PrefConf(Context context) {
        preferences = context.getSharedPreferences(FILE_NAME, 0);
    }

    private SharedPreferences.Editor getEditor() {
        return preferences.edit();
    }

    void setPrefCity(int id) {
        getEditor().putInt(PREF_CITY, id).commit();
    }

    int getPrefCity() {
        return preferences.getInt(PREF_CITY, 0);
    }
}
