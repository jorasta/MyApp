package by.a1.popov.homework4app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import by.a1.popov.homework4app.DBSrcs.Contacts;
import by.a1.popov.homework4app.DBSrcs.ContactsDAO;
import by.a1.popov.homework4app.DBSrcs.ContactsDB;

public class EditContactActivity extends AddContactActivity{

    private Button button;
    private Toolbar toolbar;
    private EditText editTextName, editTextContact;
    private int poz, contactId;
    private ContactsDAO contactsDAO;
    private Contacts contactRec;

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
            contactId = intent.getIntExtra(PhoneContacts.KEY_ID,0);
        }
        ContactsDB contactsDB = App.getInstance().getDatabase();
        contactsDAO = contactsDB.contactsDAO();
        contactRec = contactsDAO.getContactById(contactId);
        editTextName.setText(contactRec.getName());
        editTextContact.setText(contactRec.getContact());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            SingletoneObserve.getInstance().notifyContactDel(contactRec,poz);
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
                contactRec.setName(editTextName.getText().toString());
                contactRec.setContact(editTextContact.getText().toString());
                SingletoneObserve.getInstance().notifyContactsChange(contactRec,poz);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
