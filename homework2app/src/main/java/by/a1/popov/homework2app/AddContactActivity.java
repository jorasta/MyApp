package by.a1.popov.homework2app;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddContactActivity extends AppCompatActivity {

    private DynamicLayout dynamicLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        /*findViewById(R.id.btnAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_add_contact);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_add:
            /*
                dynamicLayout = findViewById(R.id.viewItemContainer);
                EditText etName = findViewById(R.id.viewEditName);
                EditText etContact = findViewById(R.id.viewEditContact);
                dynamicLayout.addItem(etName.getText().toString(), etContact.getText().toString(), R.drawable.ic_contact_mail_black_24dp);
            */
                Toast.makeText(this, "Not done yet", Toast.LENGTH_SHORT).show();
                //super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
