package by.a1.popov.homework2app;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.snackbar.Snackbar;


public class CustomViewActivity extends AppCompatActivity implements View.OnTouchListener {

    float x;
    float y;

    Switch aSwitch;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);


        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aSwitch = findViewById(R.id.switchView);
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

        CustomView view = findViewById(R.id.cust_view);
        view.setOnTouchListener(this);
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
                CustomView customView = (CustomView) findViewById(R.id.cust_view);
                int clr = customView.getPxlColor(x,y);
                int clr2 = getResources().getColor(R.color.colorCenter);
                String textMsg = "Coordinates [x:" + (int)x + "; y:" + (int)y +"]";
                if ((clr != clr2) && (clr != 0))   {
                    if (aSwitch.isChecked()) {

                        Snackbar snackbar = Snackbar.make(findViewById(R.id.custom_view_root), textMsg, Snackbar.LENGTH_SHORT);
                        View snackbarView = snackbar.getView();
                        TextView textView = (TextView) snackbarView.findViewById(R.id.snackbar_text);
                        textView.setTextColor(clr);
                        snackbar.show();
                    } else {
                        Toast.makeText(findViewById(R.id.custom_view_root).getContext(), textMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (clr == clr2)
                    customView.shuffleColors();
                }
                break;
        }
        return true;
    }
}
