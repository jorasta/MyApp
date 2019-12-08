package by.a1.popov.homework2app;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PhoneContacts extends AppCompatActivity implements SingletoneObserve.ContactsListener {

    public static ArrayList<ContactRecord> list = new ArrayList<ContactRecord>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /**
         * CYCLE JUST for TESTS *
         */
        /*for (int i = 0; i < 40; i++) {
            ContactRecord cRec = new ContactRecord("Name_"+i,
                                                   "+375441234"+i,
                    (i % 2 == 0 ? R.drawable.ic_contact_mail_black_24dp : R.drawable.ic_contact_phone_black_24dp));
            list.add(cRec);
        }*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_contacts);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(PhoneContacts.this, AddContactActivity.class));
            }
        });
        UpdateRecyclerView();

        SingletoneObserve.getInstance().subscribe(this);
    }

    /**
     * Update RecycleView and remove textView "No contacts"
     */
    private void UpdateRecyclerView(){
        if (list.size() != 0) {
            TextView textView = findViewById(R.id.textViewNoContacts);
            textView.setVisibility(View.GONE);

            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setAdapter(new ContactsAdapter(list));

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
                recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        }
    }

    @Override
    protected void onDestroy() {
        SingletoneObserve.getInstance().unsubscribe(this);
        super.onDestroy();
    }

    @Override
    public void onContactsChange(ArrayList<ContactRecord> contactRecs) {
        UpdateRecyclerView();
    }

    /**
     * Adapter for RecyclerView
     */
    private class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.NumbersViewHolder>{

        private List<ContactRecord> items;
        public ContactsAdapter(List<ContactRecord> items){
            super();
            this.items = items;
        }

        @NonNull
        @Override
        public NumbersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view,parent,false);
            return new NumbersViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NumbersViewHolder holder, int position) {
            ContactRecord contactRec = items.get(position);
            holder.textViewContact.setText(contactRec.getContact());
            holder.textViewName.setText(contactRec.getName());
            holder.imageView.setImageResource(contactRec.getIcon());
        }

        @Override
        public int getItemCount() {
            if (items != null) {
                return items.size();
            } else
                return 0;
        }

        private  class NumbersViewHolder extends RecyclerView.ViewHolder{

            private ImageView imageView;
            private TextView textViewName;
            private TextView textViewContact;

            public NumbersViewHolder(@NonNull View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textViewName = itemView.findViewById(R.id.textViewName);
                textViewContact = itemView.findViewById(R.id.textViewContact);
            }
        }
    }
}
