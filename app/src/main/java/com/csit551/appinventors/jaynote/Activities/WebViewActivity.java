package com.csit551.appinventors.jaynote.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.csit551.appinventors.jaynote.R;

public class WebViewActivity extends ActionBarActivity
{
    private Toolbar toolbar;
    private WebView webView;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.ic_launcher);
        }

        intent = this.getIntent();
        webView = (WebView) findViewById(R.id.webview);
        if (intent.hasExtra("web_path")) {
            String url = intent.getStringExtra("web_path");
            webView.loadUrl(url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case R.id.action_home:
                Intent intent1 = new Intent(WebViewActivity.this.getApplicationContext(), MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.action_notes:
                Intent intent2 = new Intent(WebViewActivity.this.getApplicationContext(), NoteListActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.action_tips:
                Intent intent3 = new Intent(WebViewActivity.this.getApplicationContext(), TipActivity.class);
                startActivity(intent3);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
