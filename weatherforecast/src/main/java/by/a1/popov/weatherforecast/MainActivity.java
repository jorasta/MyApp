package by.a1.popov.weatherforecast;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import by.a1.popov.weatherforecast.CitiesFiew.CitiesFragment;
import by.a1.popov.weatherforecast.ForecastView.ForecastFragment;

public class MainActivity extends AppCompatActivity implements ForecastFragment.OnAddCityListener, CitiesFragment.OnSetCityListener {

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showForecastFragment();
    }

    private void showForecastFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.viewFrameContainer, ForecastFragment.newInstance(), ForecastFragment.class.getName())
                .commit();
    }

    private void showCitiesFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.viewFrameContainer, CitiesFragment.newInstance(), CitiesFragment.class.getName())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onAddCityListener() {
        showCitiesFragment();
    }

    @Override
    public void onSetCityListener() {
        getSupportFragmentManager()
                .popBackStack();
    }

    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        CitiesFragment fragment = (CitiesFragment) getSupportFragmentManager()
                .findFragmentByTag(CitiesFragment.class.getName());
        if (fragment != null && fragment.getLastCityId() == 0){
            Toast.makeText(this,
                    getResources().getString(R.string.toast_add_city_or_exit),
                    Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce=false, 2000);
    }
}
