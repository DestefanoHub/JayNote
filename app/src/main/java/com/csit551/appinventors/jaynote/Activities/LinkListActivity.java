package com.csit551.appinventors.jaynote.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csit551.appinventors.jaynote.R;

public class LinkListActivity extends ActionBarActivity
{
    private ListView linkList;
    private Toolbar toolbar;
    private String[] links = getResources().getStringArray(R.array.links);
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setLogo(R.drawable.ic_launcher);
        }
        linkList = (ListView) findViewById(R.id.link_list);
        context = getBaseContext();

        ArrayAdapter<String> linkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, links);
        linkList.setAdapter(linkAdapter);
        linkList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("web_path", links[position]);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_links, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_home)
        {
            Intent intent = new Intent(LinkListActivity.this.getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_tips)
        {
            Intent intent = new Intent(LinkListActivity.this.getApplicationContext(), LinkListActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_notes)
        {
            Intent intent = new Intent(LinkListActivity.this.getApplicationContext(), TipActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
