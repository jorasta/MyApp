package by.a1.popov.homework2app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

public class EditContactActivity extends AddContactActivity{

    Button button;
    Toolbar toolbar;
    EditText editTextName, editTextContact;
    private int poz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        editTextName = findViewById(R.id.viewEditName);
        editTextContact = findViewById(R.id.viewEditContact);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.btn_remove);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(PhoneContacts.KEY_POSITION))
        {
            poz = intent.getIntExtra(PhoneContacts.KEY_POSITION,0);
            editTextContact.setText(intent.getStringExtra(PhoneContacts.KEY_CONTACT));
            editTextName.setText(intent.getStringExtra(PhoneContacts.KEY_NAME));
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SingletoneObserve.getInstance().notifyContactDel(poz);
            finish();
            }
        });


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_contact_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_edit:

                EditText edtTextName = findViewById(R.id.viewEditName);
                EditText edtTextContact = findViewById(R.id.viewEditContact);

                ContactRecord cRec = new ContactRecord(edtTextName.getText().toString(),
                                                       edtTextContact.getText().toString(),0);
                SingletoneObserve.getInstance().notifyContactsChange(cRec,poz);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
