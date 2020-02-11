package by.a1.popov.homework7910app.customview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import by.a1.popov.homework7910app.utils.BaseFragment;
import by.a1.popov.homework7910app.R;

public class CustomViewFragment extends BaseFragment implements View.OnTouchListener {

    public static CustomViewFragment newInstance(){
        return new CustomViewFragment();
    }

    private AppCompatActivity activity;
    @BindView(R.id.cust_view)
    public CustomView customView;
    @BindView(R.id.switchView)
    public Switch aSwitch;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_custom_view,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        if (activity != null){
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        customView.setOnTouchListener(this);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText(R.string.switch_snackbar);
                } else {
                    buttonView.setText(R.string.switch_toast);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activity.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                int clr = customView.getPxlColor(x, y);
                int clr2 = ContextCompat.getColor(activity.getApplicationContext(),R.color.colorCenter);
                String textMsg = "Coordinates [x:" + (int) x + "; y:" + (int) y + "]";
                // touched one of color sectors
                if ((clr != clr2) && (clr != 0)) {
                    if (aSwitch.isChecked()) {
                        Snackbar snackbar = Snackbar.make(v, textMsg, Snackbar.LENGTH_SHORT);
                        View snackbarView = snackbar.getView();
                        TextView textView = snackbarView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(clr);
                        snackbar.show();
                        // get current time for new log record
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.getDefault());
                        String strDate = sdf.format(currentTime);
                        // add new log record in file snackbarLog.log
                        try {
                            FileWriter fw = new FileWriter(activity.getFilesDir() + "/snackbarLog.log", true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter printWriter = new PrintWriter(bw);
                            printWriter.println(strDate + " - " + textMsg);
                            printWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //Toast.makeText(v.findViewById(R.id.custom_view_root).getContext(), textMsg, Toast.LENGTH_SHORT).show();
                        Toast.makeText(activity.getApplicationContext(), textMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // touched central sector
                    if (clr == clr2)
                        customView.shuffleColors();
                }
                break;
        }
        return true;
    }


}
