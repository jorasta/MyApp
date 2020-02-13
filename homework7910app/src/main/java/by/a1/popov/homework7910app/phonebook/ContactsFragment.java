package by.a1.popov.homework7910app.phonebook;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import by.a1.popov.homework7910app.utils.BaseFragment;
import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;
import by.a1.popov.homework7910app.R;
import by.a1.popov.homework7910app.utils.OnButtonClickMainListener;

import static by.a1.popov.homework7910app.MainActivity.KEY_ADD_CONTACT;

public class ContactsFragment extends BaseFragment implements ContactsView {

    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }

    private OnButtonClickMainListener listener;
    private ContactsPresenter presenter;

    private AppCompatActivity activity;
    private ContactsAdapter adapter;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.textViewNoContacts)
    public TextView textViewNoContacts;
    @BindView(R.id.recyclerView)
    public RecyclerView recyclerView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickMainListener) {
            listener = (OnButtonClickMainListener) context;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getContacts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_phone_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        presenter = new ContactsPresenterImpl(this);
        recyclerView.setAdapter(new ContactsAdapter(new ArrayList<>()));
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = (ContactsAdapter) recyclerView.getAdapter();
        getContacts();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_contact_menu, menu);
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            activity.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        listener.onButtonClickMainListener(KEY_ADD_CONTACT);
    }

    @Override
    public void showContacts(List<Contacts> contactsList) {
        if (contactsList.isEmpty()) {
            textViewNoContacts.setVisibility(View.VISIBLE);
        } else {
            textViewNoContacts.setVisibility(View.GONE);
        }
        if (adapter != null) {
            adapter.updateContactsList(contactsList);
        }
    }

    @Override
    public void getContacts() {
        presenter.getAllContacts();
    }

    /**
     * Adapter for RecyclerView
     */
    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

        private List<Contacts> items;
        private List<Contacts> itemsCopy = new ArrayList<>();

        ContactsAdapter(List<Contacts> items) {
            super();
            if (items != null) {
                this.items = items;
                if (!this.items.isEmpty()) {
                    this.itemsCopy.addAll(items);
                }
            }
        }

        void updateContactsList(List<Contacts> contactsList) {
            items = contactsList;
            itemsCopy.clear();
            itemsCopy.addAll(items);
            notifyDataSetChanged();
        }

        void filter(String text) {
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
        public ContactsAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact_view, parent, false);
            return new ContactsAdapter.ContactsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, final int position) {
            final Contacts contactRec = items.get(position);
            holder.showContact(contactRec);
            holder.itemView.setOnClickListener(v -> listener.onEditContactListener(contactRec.getId()));
        }

        @Override
        public int getItemCount() {
            return items != null ? items.size() : 0;
        }

        public class ContactsViewHolder extends RecyclerView.ViewHolder {

            @BindView(R.id.imageView)
            public ImageView imageView;
            @BindView(R.id.textViewName)
            public TextView textViewName;
            @BindView(R.id.textViewContact)
            public TextView textViewContact;

            ContactsViewHolder(@NonNull View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void showContact(Contacts contactRec) {
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
