package by.a1.popov.homework7910app.webview;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import by.a1.popov.homework7910app.utils.BaseFragment;
import by.a1.popov.homework7910app.R;

public class WebViewFragment extends BaseFragment implements View.OnClickListener {

    public static WebViewFragment newInstance(){
        return new WebViewFragment();
    }

    private AppCompatActivity activity;
    @BindView(R.id.webView)
    public WebView mWebView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = (AppCompatActivity) getActivity();
        view.findViewById(R.id.btn_opn_brwsr).setOnClickListener(this);
        view.findViewById(R.id.btn_prev_page).setOnClickListener(this);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient());
//      deprecated :
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return false;
//            }
//        });
        mWebView.loadUrl(getResources().getString(R.string.home_page));

        toolbar.setTitle(R.string.btn_opn_brwsr);
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                activity.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_webview,container,false);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            // pressed button "Open in Browser"
            case R.id.btn_opn_brwsr:
                //.setTitle("Внешний браузер")
                Dialog dialog = new AlertDialog.Builder(activity.getApplicationContext())
                        //.setTitle("Внешний браузер")
                        .setMessage("Вы уверены что хотите открыть страницу в стороннем браузере?")
                        .setPositiveButton("Да", (dialog12, which) -> {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                            startActivity(browserIntent);
                        })
                        .setNegativeButton("Отмена", (dialog1, which) -> {
                        })
                        .create();
                dialog.show();
                break;
            // pressed button "Previous Page"
            case R.id.btn_prev_page:
                mWebView.goBack();
                break;
        }
    }
}
