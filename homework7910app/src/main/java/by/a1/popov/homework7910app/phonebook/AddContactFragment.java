package by.a1.popov.homework7910app.phonebook;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import by.a1.popov.homework7910app.R;
import by.a1.popov.homework7910app.repo.DBSrcs.Contacts;
import by.a1.popov.homework7910app.utils.BaseFragment;

public class AddContactFragment extends BaseFragment implements AddContactsView {

    public static AddContactFragment newInstance(){
        return new AddContactFragment();
    }

    private AppCompatActivity activity;
    private ContactsPresenter presenter;

    @BindView(R.id.viewEditName)
    public EditText editNameText;
    @BindView(R.id.viewEditContact)
    public EditText editContactText;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.rgTypeContact)
    public RadioGroup radioGroup;
    @BindView(R.id.rbPhone)
    public RadioButton rbPhone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_add_contact,container,false);
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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            radioGroup.setOrientation(LinearLayout.VERTICAL);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            radioGroup.setOrientation(LinearLayout.HORIZONTAL);
        }
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rbPhone:
                    editContactText.setHint(R.string.hint_Phone);
                    break;
                case R.id.rbEmail:
                    editContactText.setHint(R.string.hint_Email);
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.add_contact_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                showContacts();
                return true;
            case R.id.action_add:
                Contacts cRec = new Contacts(editNameText.getText().toString(),
                        editContactText.getText().toString(),
                        rbPhone.isChecked() ? 1 : 0);
                presenter.addContact(cRec);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showContacts() {
        activity.onBackPressed();
    }
}
