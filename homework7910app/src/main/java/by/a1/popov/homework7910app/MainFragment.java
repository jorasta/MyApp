package by.a1.popov.homework7910app;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import butterknife.OnClick;
import by.a1.popov.homework7910app.utils.BaseFragment;
import by.a1.popov.homework7910app.utils.OnButtonClickMainListener;

import static by.a1.popov.homework7910app.MainActivity.KEY_CUSTOM_VIEW;
import static by.a1.popov.homework7910app.MainActivity.KEY_PHONEBOOK;
import static by.a1.popov.homework7910app.MainActivity.KEY_WEBVIEW;

public class MainFragment extends BaseFragment {

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    private OnButtonClickMainListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnButtonClickMainListener) {
            listener = (OnButtonClickMainListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main,container,false);
    }

    @OnClick(R.id.opn_phonebook)
    public void onPhoneBookBtnClickListener(){
        if (listener != null) {
            listener.onButtonClickMainListener(KEY_PHONEBOOK);
        }
    }

    @OnClick(R.id.opn_customview)
    public void onCustomViewBtnClickListener(){
        if (listener != null) {
            listener.onButtonClickMainListener(KEY_CUSTOM_VIEW);
        }
    }

    @OnClick(R.id.opn_webpage)
    public void onWebViewBtnClickListener(){
        if (listener != null) {
            listener.onButtonClickMainListener(KEY_WEBVIEW);
        }
    }
}
