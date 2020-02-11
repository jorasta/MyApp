package by.a1.popov.homework7910app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import by.a1.popov.homework7910app.customview.CustomViewFragment;
import by.a1.popov.homework7910app.phonebook.AddContactFragment;
import by.a1.popov.homework7910app.phonebook.ContactsFragment;
import by.a1.popov.homework7910app.phonebook.EditContactFragment;
import by.a1.popov.homework7910app.utils.OnButtonClickMainListener;
import by.a1.popov.homework7910app.webview.WebViewFragment;

public class MainActivity extends AppCompatActivity implements OnButtonClickMainListener {

    public static final String KEY_PHONEBOOK = "KEY_PHONEBOOK";
    public static final String KEY_CUSTOM_VIEW = "KEY_CUSTOM_VIEW";
    public static final String KEY_WEBVIEW = "KEY_WEBVIEW";
    public static final String KEY_ADD_CONTACT = "KEY_ADD_CONTACT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMainFragment();
    }

    private void showMainFragment(){
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frame_container, MainFragment.newInstance(), MainFragment.class.getName())
                .commit();
    }

    private void showViewFragment(Fragment fragment,String tag){
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_container, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onButtonClickMainListener(String key) {
        switch (key) {
            case KEY_PHONEBOOK:
                showViewFragment(ContactsFragment.newInstance(),ContactsFragment.class.getName());
                break;
            case KEY_CUSTOM_VIEW:
                showViewFragment(CustomViewFragment.newInstance(),CustomViewFragment.class.getName());
                break;
            case KEY_WEBVIEW:
                showViewFragment(WebViewFragment.newInstance(),WebViewFragment.class.getName());
                break;
            case KEY_ADD_CONTACT:
                showViewFragment(AddContactFragment.newInstance(),AddContactFragment.class.getName());
                break;

        }
    }

    @Override
    public void onEditContactListener(long contactId) {
        showViewFragment(EditContactFragment.newInstance(contactId),EditContactFragment.class.getName());
    }
}
