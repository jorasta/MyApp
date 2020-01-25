package by.a1.popov.homework4app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private Dialog dialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        this.setTitle(R.string.btn_opn_brwsr);
        findViewById(R.id.btn_opn_brwsr).setOnClickListener(this);
        findViewById(R.id.btn_prev_page).setOnClickListener(this);

        mWebView = findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.loadUrl(getResources().getString(R.string.home_page));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        switch (viewId) {
            // pressed button "Open in Browser"
            case R.id.btn_opn_brwsr:
                dialog = new AlertDialog.Builder(this)
                        //.setTitle("Внешний браузер")
                        .setMessage("Вы уверены что хотите открыть страницу в стороннем браузере?")
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mWebView.getUrl()));
                                startActivity(browserIntent);
                            }
                        })
                        .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
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
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
