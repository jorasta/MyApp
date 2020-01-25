package by.a1.popov.homework4app;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import by.a1.popov.homework4app.DBSrcs.Contacts;

public class AddContactActivity extends AppCompatActivity {

    private EditText editNameText, editContactText;
    private Toolbar toolbar;
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        toolbar = findViewById(R.id.toolbar);
        editNameText = findViewById(R.id.viewEditName);
        editContactText = findViewById(R.id.viewEditContact);
        radioGroup = (RadioGroup) findViewById(R.id.rgTypeContact);

        /**
         * different position RadioButtons depend on orientation
         */
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            radioGroup.setOrientation(LinearLayout.VERTICAL);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        }
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbPhone:
                        editContactText.setHint(R.string.hint_Phone);
                        break;
                    case R.id.rbEmail:
                        editContactText.setHint(R.string.hint_Email);
                        break;
                    default:
                        break;
                }
            }
        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Menu used for arrow back
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
                RadioButton rbPhone = findViewById(R.id.rbPhone);
                // create new Contact
                Contacts cRec = new Contacts(editNameText.getText().toString(),
                        editContactText.getText().toString(),
                        rbPhone.isChecked() ? 1 : 0);

                SingletoneObserve.getInstance().notifyContactAdd(cRec);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
