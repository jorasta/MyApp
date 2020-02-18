package by.a1.popov.weatherforecast.CitiesFiew;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import by.a1.popov.weatherforecast.R;
import by.a1.popov.weatherforecast.repo.database.CityEntity;

public class CitiesFragment extends Fragment implements CitiesView {

    public static CitiesFragment newInstance() {
        return new CitiesFragment();
    }
    public interface OnSetCityListener {
        void onSetCityListener();
    }
    private OnSetCityListener listener;

    private AppCompatActivity activity;
    private Unbinder unbinder;
    @BindView(R.id.toolbarCities)
    public Toolbar toolbarCities;
    @BindView(R.id.recycleViewCities)
    public RecyclerView recycleViewCities;
    @BindView(R.id.textViewNoCities)
    public TextView textViewNoCities;

    private CitiesPresenter presenter;
    private int lastCityID;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (lastCityID == 0) {
                Toast.makeText(activity.getApplicationContext(),
                        getString(R.string.toast_add_city),
                        Toast.LENGTH_SHORT).show();
            } else {
                activity.onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSetCityListener) {
            listener = (OnSetCityListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            presenter = new CitiesPresenterImpl(this, activity.getApplication());
            activity.setSupportActionBar(toolbarCities);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        recycleViewCities.setAdapter(new CitiesListAdapter(new ArrayList<>()));
        recycleViewCities.setLayoutManager(new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false));
        lastCityID = presenter.getLasCityID();
        presenter.getAllCities();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cities, container, false);
        setHasOptionsMenu(true);
        return view;
    }

    @OnClick(R.id.fabAddCity)
    void onClickAddCity(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.dialog_title_enter_city);
        View viewInflated = LayoutInflater.from(view.getContext()).inflate(R.layout.dialog_add_city, null);
        final EditText input = viewInflated.findViewById(R.id.textViewCityNameAdd);
        Dialog dialog1 = builder.setView(viewInflated)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    String cityName = input.getText().toString();
                    if (!cityName.equals("")) {
                        addNewCity(cityName);
                    }
                })
                .setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();
        dialog1.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void showAllCities(List<CityEntity> cityEntities) {
        if (cityEntities.isEmpty()) {
            textViewNoCities.setVisibility(View.VISIBLE);
        } else {
            textViewNoCities.setVisibility(View.GONE);
        }
        CitiesListAdapter adapter = (CitiesListAdapter) recycleViewCities.getAdapter();
        if (adapter != null) {
            adapter.updateItemList(cityEntities);
        }
    }

    @Override
    public void addNewCity(String city) {
        presenter.addNewCity(city);
    }

    @Override
    public int getLastCityId() {
        return lastCityID;
    }

    private void setLastCityID(int CityId){
        presenter.setLasCity(CityId);
    }

    // Cities Adapter

    public class CitiesListAdapter extends RecyclerView.Adapter<CitiesFragment.CitiesListAdapter.CityItemViewHolder> {

        private List<CityEntity> itemList;

        CitiesListAdapter(List<CityEntity> itemList) {
            this.itemList = itemList;
        }

        @NonNull
        @Override
        public CitiesFragment.CitiesListAdapter.CityItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city, parent, false);
            return new CitiesFragment.CitiesListAdapter.CityItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CitiesFragment.CitiesListAdapter.CityItemViewHolder holder, int position) {
            int cityID = itemList.get(position).getId();
            if (itemList.size() == 1) {
                setLastCityID(cityID);
                lastCityID = cityID;
            }
            holder.itemView.setTag(cityID);
            holder.itemView.setLongClickable(true);
            holder.itemView.setOnClickListener(holder::setLastCityId);
            holder.itemView.setOnLongClickListener(v -> {
                holder.deleteCity(v);
                return true;
            });
            holder.showCity(itemList.get(position));
        }

        @Override
        public int getItemCount() {
            return itemList != null ? itemList.size() : 0;
        }

        void updateItemList(List<CityEntity> cityEntityList) {
            itemList = cityEntityList;
            notifyDataSetChanged();
        }

        public class CityItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.textViewCityName)
            public TextView textViewCityName;

            CityItemViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void showCity(CityEntity cityEntity) {
                if (lastCityID == cityEntity.getId() || itemList.size() == 1) {
                    textViewCityName.setTextColor(Color.RED);
                } else {
                    textViewCityName.setTextColor(Color.BLACK);
                }
                textViewCityName.setText(cityEntity.getCity());
            }

            private void changeDeletedLastCity() {
                if (itemList.size() == 1) {
                    lastCityID = 0;
                } else {
                    for (CityEntity entity : itemList) {
                        if (entity.getId() != lastCityID) {
                            lastCityID = entity.getId();
                            break;
                        }
                    }
                }
                setLastCityID(lastCityID);
            }

            private void setLastCityId(View v) {
                setLastCityID((int) v.getTag());
                listener.onSetCityListener();
            }

            private void deleteCity(View v) {
                CityEntity cityEntity = new CityEntity();
                cityEntity.setId((int) v.getTag());
                cityEntity.setCity(textViewCityName.getText().toString());
                Dialog dialogDel = new AlertDialog.Builder(activity)
                        .setMessage(getString(R.string.dialog_title_delete_city) + " " + cityEntity.getCity() + " ?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            if (lastCityID == cityEntity.getId()) {
                                changeDeletedLastCity();
                            }
                            presenter.deleteCity(cityEntity);
                        })
                        .setNegativeButton(android.R.string.cancel, (dialog, which) -> {
                        })
                        .create();
                dialogDel.show();
            }
        }
    }
}
