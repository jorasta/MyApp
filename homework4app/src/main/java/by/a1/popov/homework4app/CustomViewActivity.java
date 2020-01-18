package by.a1.popov.homework4app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CustomViewActivity extends AppCompatActivity implements View.OnTouchListener {

    private Switch aSwitch;
    private Toolbar toolbar;
    private CustomView customView;

    private float x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        toolbar = findViewById(R.id.toolbar);
        customView = findViewById(R.id.cust_view);
        aSwitch = findViewById(R.id.switchView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                int clr = customView.getPxlColor(x, y);
                int clr2 = getResources().getColor(R.color.colorCenter);
                String textMsg = "Coordinates [x:" + (int) x + "; y:" + (int) y + "]";
                // touched one of color sectors
                if ((clr != clr2) && (clr != 0)) {
                    if (aSwitch.isChecked()) {
                        Snackbar snackbar = Snackbar.make(findViewById(R.id.custom_view_root), textMsg, Snackbar.LENGTH_SHORT);
                        View snackbarView = snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(clr);
                        snackbar.show();
                        // get current time for new log record
                        Date currentTime = Calendar.getInstance().getTime();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss", Locale.getDefault());
                        String strDate = sdf.format(currentTime);
                        // add new log record in file snackbarLog.log
                        try {
                            FileWriter fw = new FileWriter(getFilesDir() + "/snackbarLog.log", true);
                            BufferedWriter bw = new BufferedWriter(fw);
                            PrintWriter printWriter = new PrintWriter(bw);
                            printWriter.println(strDate + " - " + textMsg);
                            printWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(findViewById(R.id.custom_view_root).getContext(), textMsg, Toast.LENGTH_SHORT).show();
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
