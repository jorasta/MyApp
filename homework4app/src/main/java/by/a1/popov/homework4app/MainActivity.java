package by.a1.popov.homework4app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.opn_phonebook).setOnClickListener(this);
        findViewById(R.id.opn_customview).setOnClickListener(this);
        findViewById(R.id.opn_webpage).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            case R.id.opn_phonebook:
                startActivity(new Intent(this, PhoneContacts.class));
                break;
            case R.id.opn_customview:
                startActivity(new Intent(this, CustomViewActivity.class));
                //Toast.makeText(view.getContext(), "Not done yet :(", Toast.LENGTH_SHORT).show();
                break;
            case R.id.opn_webpage:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
        }
    }
}
