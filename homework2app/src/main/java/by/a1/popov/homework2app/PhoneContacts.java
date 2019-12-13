package by.a1.popov.homework2app;

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

public class PhoneContacts extends AppCompatActivity implements SingletoneObserve.ContactsListener {

    private ArrayList<ContactRecord> contacts = new ArrayList<ContactRecord>() {};

    private ContactsAdapter adapter;
    public static String KEY_POSITION = "position";
    public static String KEY_NAME = "name";
    public static String KEY_CONTACT = "contact";
    private static String KEY_VISIBLE = "visibility_no_contacts";
    private static String KEY_CONTACTS = "data_of_contacts";
    RecyclerView recyclerView;
    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts);
        toolbar = findViewById(R.id.toolbar);
        textView = findViewById(R.id.textViewNoContacts);
        recyclerView = findViewById(R.id.recyclerView);
        setSupportActionBar(toolbar);

        if (savedInstanceState != null){
            textView.setVisibility(savedInstanceState.getInt(KEY_VISIBLE));
            contacts = savedInstanceState.getParcelableArrayList(KEY_CONTACTS);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(PhoneContacts.this, AddContactActivity.class));
            }
        });


        recyclerView.setAdapter(new ContactsAdapter(contacts));
        adapter = (ContactsAdapter)recyclerView.getAdapter();

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));

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
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        TextView textView = findViewById(R.id.textViewNoContacts);
        contacts = adapter.items;
        outState.putInt(KEY_VISIBLE,textView.getVisibility());
        outState.putParcelableArrayList(KEY_CONTACTS, contacts);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        SingletoneObserve.getInstance().unsubscribe(this);
        super.onDestroy();
    }

    @Override
    public void onContactAdd(ContactRecord contactRec) {
        adapter.AddItem(contactRec);
        if (adapter.getItemCount()>0){
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onContactDel(int position) {
        adapter.DelItem(position);
        if (adapter.getItemCount() == 0){
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onContactsChange(ContactRecord contactRec, int position) {
        adapter.ChangeItem(contactRec,position);
    }


    /**
     * Adapter for RecyclerView
     */
    private class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{

        private ArrayList<ContactRecord> items;
        private ArrayList<ContactRecord> itemsCopy = new ArrayList<ContactRecord>();



        public ContactsAdapter(ArrayList<ContactRecord> items){
            super();
            this.items = items;
            if (!this.items.isEmpty()) {
                this.itemsCopy.addAll(items);
            }
        }

        protected void UpdateItemsCopy(){
            itemsCopy.clear();
            itemsCopy.addAll(items);
        }

        public void AddItem(ContactRecord contactRecord){
            items.add(contactRecord);
            UpdateItemsCopy();
            notifyDataSetChanged();
        }

        public void DelItem(int position){
            items.remove(position);
            UpdateItemsCopy();
            notifyDataSetChanged();
        }

        public void ChangeItem(ContactRecord contactRec, int position){
            items.get(position).setContact(contactRec.getContact());
            items.get(position).setName(contactRec.getName());
            UpdateItemsCopy();
            notifyItemChanged(position);
        }

        public void filter(String text) {
            items.clear();
            if(text.isEmpty()){
                items.addAll(itemsCopy);
            } else{
                text = text.toLowerCase();
                for(ContactRecord item: itemsCopy){
                    if(item.getName().toLowerCase().contains(text) || item.getContact().toLowerCase().contains(text)){
                        items.add(item);
                    }
                }
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view,parent,false);
            return new ContactsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsViewHolder holder, final int position) {
            final ContactRecord contactRec = items.get(position);
            holder.textViewContact.setText(contactRec.getContact());
            holder.textViewName.setText(contactRec.getName());
            holder.imageView.setImageResource(contactRec.getIcon());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PhoneContacts.this,EditContactActivity.class);
                    intent.putExtra(KEY_POSITION,position);
                    intent.putExtra(KEY_NAME,contactRec.getName());
                    intent.putExtra(KEY_CONTACT,contactRec.getContact());
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

        private  class ContactsViewHolder extends RecyclerView.ViewHolder{

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
