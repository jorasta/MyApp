package by.a1.popov.weatherforecast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import by.a1.popov.weatherforecast.ForecastView.ForecastFragment;

public class MainActivity extends AppCompatActivity implements ForecastFragment.OnAddCityListener {

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.viewFrameContainer,ForecastFragment.newInstance(),ForecastFragment.class.getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onAddCityListener() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewFrameContainer, CitiesFragment.newInstance(), CitiesFragment.class.getName());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
