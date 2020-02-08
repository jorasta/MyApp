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
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import by.a1.popov.weatherforecast.R;
import by.a1.popov.weatherforecast.repo.forecast.Consts;
import by.a1.popov.weatherforecast.repo.forecast.Weather;

public class ForecastFragment extends Fragment implements ForecastView {

    public interface OnAddCityListener {
        void onAddCityListener();
    }

    private OnAddCityListener listener;

    private Unbinder unbinder;
    private ForecastPresenter presenter;
    private boolean tempMetric = true;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.textViewTemperature)
    public TextView textViewTemperature;
    @BindView(R.id.switchTemperature)
    public Switch switchTemperature;
    @BindView(R.id.textViewDescription)
    public TextView textViewDescription;
    @BindView(R.id.imageViewIcon)
    public ImageView imageViewIcon;
    @BindView(R.id.recycleViewForecast)
    public RecyclerView recycleViewForecast;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    public static ForecastFragment newInstance() {
        return new ForecastFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddCityListener) {
            listener = (OnAddCityListener) context;
        }
    }

    @Override
    public void showCurrentWeather(Weather currentWeather) {
        progressBar.setVisibility(View.GONE);
        String tempSymbol = (tempMetric) ? getString(R.string.temp_symbol_celsius)
                : getString(R.string.temp_symbol_fahrenheit);
        String currTemperature = currentWeather.getTemperature() + tempSymbol;
        textViewTemperature.setText(currTemperature);
        textViewDescription.setText(currentWeather.getDescription());
        String imgUrl = String.format(Consts.GET_WEATHER_ICON, currentWeather.getIconId());
        Glide.with(this).load(imgUrl).into(imageViewIcon);
    }

    @Override
    public void showForecastWeather(List<Weather> forecastWeather) {
        ForecastListAdapter adapter = (ForecastListAdapter) recycleViewForecast.getAdapter();
        if (adapter != null) {
            adapter.updateItemList(forecastWeather);
        }
    }

    @Override
    public void showLastCityForecast(String city) {
        toolbar.setTitle(city.toUpperCase());
        presenter.getCurrentWeather(tempMetric, city);
        presenter.getForecastWeather(tempMetric, city);
    }

    @Override
    public void showCities() {
        listener.onAddCityListener();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_city, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            showCities();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);
        recycleViewForecast.setAdapter(new ForecastListAdapter(new ArrayList<>()));
        recycleViewForecast.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        presenter = new ForecastPresenterImpl(this);
        switchTemperature.setChecked(true);
        switchTemperature.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                buttonView.setText(getString(R.string.temp_symbol_celsius));
                tempMetric = true;
            } else {
                buttonView.setText(getString(R.string.temp_symbol_fahrenheit));
                tempMetric = false;
            }
            progressBar.setVisibility(View.VISIBLE);
            showLastCityForecast(toolbar.getTitle().toString());
        });
        progressBar.setVisibility(View.VISIBLE);
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            presenter.getLastCity(activity.getApplication());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    // Forecast Adapter

    public class ForecastListAdapter extends RecyclerView.Adapter<ForecastListAdapter.ForecastItemViewHolder> {

        private List<Weather> itemList;

        ForecastListAdapter(List<Weather> itemList) {
            this.itemList = itemList;
        }

        @NonNull
        @Override
        public ForecastItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_forecast, parent, false);
            return new ForecastItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ForecastItemViewHolder holder, int position) {
            holder.showForecast(itemList.get(position), holder.itemView);
        }

        @Override
        public int getItemCount() {
            return itemList != null ? itemList.size() : 0;
        }

        void updateItemList(List<Weather> forecastItemList) {
            itemList = forecastItemList;
            notifyDataSetChanged();
        }

        public class ForecastItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.imageViewIconForecast)
            public ImageView imageViewIconForecast;
            @BindView(R.id.textViewDateTimeForecast)
            public TextView textViewDateTimeForecast;
            @BindView(R.id.textViewDescriptionForecast)
            public TextView textViewDescriptionForecast;
            @BindView(R.id.textViewTemperatureForecast)
            public TextView textViewTemperatureForecast;

            ForecastItemViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void showForecast(Weather weather, View view) {
                textViewDateTimeForecast.setText(weather.getDateTimeForecast());
                textViewDescriptionForecast.setText(weather.getDescription());
                String tempSymbol = (tempMetric) ? getString(R.string.temp_symbol_celsius)
                        : getString(R.string.temp_symbol_fahrenheit);
                String forecastTemperature = weather.getTemperature() + tempSymbol;
                textViewTemperatureForecast.setText(forecastTemperature);
                String imgUrl = String.format(Consts.GET_WEATHER_ICON, weather.getIconId());
                Glide.with(view).load(imgUrl).into(imageViewIconForecast);
            }
        }
    }
}
