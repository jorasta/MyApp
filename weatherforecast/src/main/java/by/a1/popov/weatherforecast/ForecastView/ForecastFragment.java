package by.a1.popov.weatherforecast.ForecastView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.prefs.Preferences;

import by.a1.popov.weatherforecast.R;
import by.a1.popov.weatherforecast.Repo.Consts;
import by.a1.popov.weatherforecast.Repo.CurrentWeather;
import by.a1.popov.weatherforecast.Repo.PrefConf;

public class ForecastFragment extends Fragment implements ForecastView {
    private  AppCompatActivity activity;



    public interface OnAddCityListener{
        void onAddCityListener();
    }
    private OnAddCityListener listener;

    private ForecastPresenter presenter;

    private Toolbar toolbar;
    private TextView textViewTemperature;
    private Switch switchTemperature;
    private TextView textViewDescription;
    private ImageView imageViewIcon;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public void showCurrentWeather(CurrentWeather currentWeather) {
        String tempMetric = switchTemperature.isChecked()?"°C":"°F";
        String currTemperature = currentWeather.getTemperature()+tempMetric;
        textViewTemperature.setText(currTemperature);
        textViewDescription.setText(currentWeather.getDescription());
        String imgUrl = String.format(Consts.GET_WEATHER_ICON,currentWeather.getIconId());
        Picasso.get().load(imgUrl).into(imageViewIcon);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddCityListener) {
            listener = (OnAddCityListener) context;
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_city, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                listener.onAddCityListener();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ForecastPresenterImpl(this);
        toolbar = view.findViewById(R.id.toolbar);
        activity = (AppCompatActivity)getActivity();
        activity.setSupportActionBar(toolbar);
        textViewTemperature = view.findViewById(R.id.textViewTemperature);
        switchTemperature = view.findViewById(R.id.switchTemperature);
        switchTemperature.setChecked(true);
        textViewDescription = view.findViewById(R.id.textViewDescription);
        imageViewIcon = view.findViewById(R.id.imageViewIcon);
        presenter.getCurrentWeather();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        PrefConf prefConf = App.getApp(this).getComponentsHolder().getAppComponent().getPreferences();
        View view = inflater.inflate(R.layout.fragment_forecast,container,false);
        setHasOptionsMenu(true);
        return view;
    }


}
