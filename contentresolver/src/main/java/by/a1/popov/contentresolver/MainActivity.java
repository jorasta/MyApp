package by.a1.popov.contentresolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static final String URI_CONTACTS = "content://" + Contacts.AUTHORITY + "/" + Contacts.TABLE_CONTACTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.viewContactList);
        recyclerView.setAdapter(new ContactListAdapter(new ArrayList<Contacts>()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    @Override
    protected void onResume() {
        super.onResume();
        showContactList();
    }

    private void showContactList() {
        ContactListAdapter adapter = (ContactListAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.updateList(getContactsList());
        }
    }

    private List<Contacts> getContactsList() {
        ContentResolver contentResolver = getContentResolver();

        Cursor resultCursor = contentResolver.query(
                Uri.parse(URI_CONTACTS),
                null,
                null,
                null,
                "name");

        ArrayList<Contacts> contactsList = new ArrayList<>();
        if (resultCursor != null) {
            if (resultCursor.moveToFirst()) {
                int idInd = resultCursor.getColumnIndex(Contacts.CONTACT_ID);
                int displaNameInd = resultCursor.getColumnIndex(Contacts.CONTACT_NAME);
                int contactInd = resultCursor.getColumnIndex(Contacts.CONTACT_DATA);
                int contactTypeInd = resultCursor.getColumnIndex(Contacts.CONTACT_TYPE);
                do {
                    long contactId = resultCursor.getLong(idInd);
                    String contactName = resultCursor.getString(displaNameInd);
                    String contactData = resultCursor.getString(contactInd);
                    int contactType = resultCursor.getInt(contactTypeInd);
                    Contacts contact = new Contacts(contactId, contactName, contactData, contactType);
                    contactsList.add(contact);
                } while (resultCursor.moveToNext());
            }
            resultCursor.close();
        }
        return contactsList;
    }

    private void delContact(Contacts contact) {

        Uri uri = ContentUris.withAppendedId(Uri.parse(URI_CONTACTS), contact.getId());
        int cnt = getContentResolver().delete(uri, null, null);
        if (cnt == 1){
            Toast.makeText(getApplicationContext(),
                    "Successfully deleted record with name "+contact.getName(),
                    Toast.LENGTH_SHORT)
                    .show();
        }
        showContactList();
    }

    public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactItemViewHolder> {

        private List<Contacts> itemlist;

        private void updateList(List<Contacts> contacts) {
            if (contacts != null) {
                itemlist = contacts;
                notifyDataSetChanged();
            }
        }

        ContactListAdapter(List<Contacts> itemlist) {
            this.itemlist = itemlist;
        }

        @NonNull
        @Override
        public ContactItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view, parent, false);
            return new ContactItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactItemViewHolder holder, int position) {
            final Contacts contact = itemlist.get(position);
            holder.bindData(contact);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delContact(contact);
                }
            });
        }

        @Override
        public int getItemCount() {
            return itemlist != null ? itemlist.size() : 0;
        }

        public class ContactItemViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.imageView)
            public ImageView imageView;
            @BindView(R.id.textViewName)
            public TextView textViewName;
            @BindView(R.id.textViewContact)
            public TextView textViewContact;

            ContactItemViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindData(Contacts contactRec) {
                textViewContact.setText(contactRec.getContact());
                textViewName.setText(contactRec.getName());
                int resId = contactRec.getTypeOfContact() == 1 ?
                        R.drawable.ic_contact_phone_black_24dp :
                        R.drawable.ic_contact_mail_black_24dp;
                imageView.setImageResource(resId);
            }
        }
    }
}
