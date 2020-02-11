package by.a1.popov.homework7910app.phonebook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.OnClick;
import by.a1.popov.homework7910app.R;
import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;
import by.a1.popov.homework7910app.utils.BaseFragment;

public class EditContactFragment extends BaseFragment implements EditContactsView {

    private static final String KEY_CONTACT_ID = "KEY_CONTACT_ID";
    public static EditContactFragment newInstance(long id) {
        Bundle bundle = new Bundle();
        bundle.putLong(KEY_CONTACT_ID, id);

        EditContactFragment fragment = new EditContactFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private AppCompatActivity activity;
    private ContactsPresenter presenter;
    private Contacts contactRec;

    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.viewEditName)
    public EditText editTextName;
    @BindView(R.id.viewEditContact)
    public EditText editTextContact;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.edit_contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showContacts();
                return true;
            case R.id.action_edit:
                contactRec.setName(editTextName.getText().toString());
                contactRec.setContact(editTextContact.getText().toString());
                presenter.updateContact(contactRec);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_edit_contact,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = (AppCompatActivity) getActivity();
        if (activity != null){
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        presenter = new ContactsPresenterImpl(this);
        fillContactInfo();
    }
    @OnClick(R.id.btn_remove)
    void onClickListener(){
        presenter.deleteContact(contactRec);
    }

    @Override
    public void showContacts() {
        activity.onBackPressed();
    }

    @Override
    public void showContactData(Contacts contact) {
        contactRec = contact;
        editTextName.setText(contactRec.getName());
        editTextContact.setText(contactRec.getContact());
    }

    private void fillContactInfo(){
        Bundle bundle = getArguments();
        if (bundle != null) {
            presenter.getContactById(bundle.getLong(KEY_CONTACT_ID));
        }
    }
}
