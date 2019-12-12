package by.a1.popov.homework2app;

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

public class AddContactActivity extends AppCompatActivity {

    EditText editText;
    Toolbar toolbar;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        toolbar = findViewById(R.id.toolbar);
        editText = findViewById(R.id.viewEditContact);
        radioGroup = (RadioGroup) findViewById(R.id.rgTypeContact);

        /**
         * different position RadioButtons depend on orientation
          */
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            radioGroup.setOrientation(LinearLayout.VERTICAL);
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rbPhone:
                    editText.setHint(R.string.hint_Phone);
                    break;
                case R.id.rbEmail:
                    editText.setHint(R.string.hint_Email);
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

                EditText edtTextName = findViewById(R.id.viewEditName);
                EditText edtTextContact = findViewById(R.id.viewEditContact);
                RadioButton rbPhone = findViewById(R.id.rbPhone);

                ContactRecord cRec = new ContactRecord(edtTextName.getText().toString(),
                                                        edtTextContact.getText().toString(),
                                                        rbPhone.isChecked() ? R.drawable.ic_contact_phone_black_24dp : R.drawable.ic_contact_mail_black_24dp);

                SingletoneObserve.getInstance().notifyContactAdd(cRec);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
