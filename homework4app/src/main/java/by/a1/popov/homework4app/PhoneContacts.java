package by.a1.popov.homework4app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.a1.popov.homework4app.DBSrcs.Contacts;
import by.a1.popov.homework4app.DBSrcs.ContactsDAO;
import by.a1.popov.homework4app.DBSrcs.ContactsDB;

public class PhoneContacts extends AppCompatActivity implements SingletoneObserve.ContactsListener {

    private List<Contacts> contacts;
    private ContactsAdapter adapter;
    public static String KEY_POSITION = "position";
    public static String KEY_ID = "contact_id";
    private RecyclerView recyclerView;
    private TextView textView;
    private ContactsDAO contactsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textViewNoContacts);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        ContactsDB contactsDB = App.getInstance().getDatabase();
        contactsDAO = contactsDB.contactsDAO();
        contacts = contactsDAO.getAllContacts();

        if (contacts.size() == 0) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PhoneContacts.this, AddContactActivity.class));
            }
        });

        recyclerView.setAdapter(new ContactsAdapter(contacts));
        adapter = (ContactsAdapter) recyclerView.getAdapter();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        SingletoneObserve.getInstance().subscribe(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_contact_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });
        return true;
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
    protected void onDestroy() {
        SingletoneObserve.getInstance().unsubscribe(this);
        super.onDestroy();
    }

    @Override
    public void onContactAdd(Contacts contactRec) {
        adapter.addItem(contactRec);
        contactsDAO.insert(contactRec);
        if (adapter.getItemCount() > 0) {
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onContactDel(Contacts contactRec, int position) {
        adapter.delItem(position);
        contactsDAO.delete(contactRec);
        if (adapter.getItemCount() == 0) {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onContactsChange(Contacts contactRec, int position) {
        adapter.changeItem(contactRec, position);
        contactsDAO.update(contactRec);
    }

    /**
     * Adapter for RecyclerView
     */
    private class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

        private List<Contacts> items;
        private List<Contacts> itemsCopy = new ArrayList<Contacts>();

        public ContactsAdapter(List<Contacts> items) {
            super();
            if (items != null) {
                this.items = items;
                if (!this.items.isEmpty()) {
                    this.itemsCopy.addAll(items);
                }
            }
        }

        protected void updateItemsCopy() {
            itemsCopy.clear();
            itemsCopy.addAll(items);
        }

        public void addItem(Contacts contacts) {
            items.add(contacts);
            updateItemsCopy();
            notifyDataSetChanged();
        }

        public void delItem(int position) {
            items.remove(position);
            updateItemsCopy();
            notifyDataSetChanged();
        }

        public void changeItem(Contacts contact, int position) {
            items.get(position).setName(contact.getName());
            items.get(position).setContact(contact.getContact());
            updateItemsCopy();
            notifyItemChanged(position);
        }

        public void filter(String text) {
            items.clear();
            if (text.isEmpty()) {
                items.addAll(itemsCopy);
            } else {
                text = text.toLowerCase();
                for (Contacts item : itemsCopy) {
                    if (item.getName().toLowerCase().contains(text) || item.getContact().toLowerCase().contains(text)) {
                        items.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view, parent, false);
            return new ContactsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
            final Contacts contactRec = items.get(position);
            holder.textViewContact.setText(contactRec.getContact());
            holder.textViewName.setText(contactRec.getName());
            holder.imageView.setImageResource(contactRec.getTypeOfContact() == 1 ? R.drawable.ic_contact_phone_black_24dp : R.drawable.ic_contact_mail_black_24dp);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PhoneContacts.this, EditContactActivity.class);
                    intent.putExtra(KEY_POSITION, position);
                    intent.putExtra(KEY_ID, (int) contactRec.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            if (items != null) {
                return items.size();
            } else
                return 0;
        }

        private class ContactsViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView textViewName;
            private TextView textViewContact;

            public ContactsViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewContact = itemView.findViewById(R.id.textViewContact);
            }
        }
    }
}
