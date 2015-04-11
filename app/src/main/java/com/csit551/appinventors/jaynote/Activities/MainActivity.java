package com.csit551.appinventors.jaynote.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.csit551.appinventors.jaynote.Database.DatabaseManager;
import com.csit551.appinventors.jaynote.Database.SightingsModel;
import com.csit551.appinventors.jaynote.ListAdapters.SightingsListAdapter;
import com.csit551.appinventors.jaynote.R;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
{
    private DatabaseManager db;
    private Context context;
    private ListView sightingsList;
    private Button newSighting;
    private Button newNote;
    private ArrayList<SightingsModel> sightings;
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(MainActivity.this.getApplicationContext());
        context = this.getBaseContext();

        sightingsList = (ListView) findViewById(R.id.sighting_list);
        sightings = db.getAllSightings();
        sightingsList.setAdapter(new SightingsListAdapter(sightings, this)); /*nullpointer exception here-cina*/
        //Set listener to view existing sightings
        sightingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, SightingActivity.class);
                //view/edit an existing sighting
                intent.putExtra("create_view_edit", 1);
                SightingsModel sighting = (SightingsModel) sightingsList.getAdapter().getItem(position);
                intent.putExtra("sighting_id", sighting.getId());
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        newSighting = (Button) findViewById(R.id.sighting_button);
        newSighting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SightingActivity.class);
                //create a new sighting
                intent.putExtra("create_view_edit", 0);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        newNote = (Button) findViewById(R.id.note_button);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                //create a new note
                intent.putExtra("create_view_edit", 0);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == RESULT_OK)
        {
            sightings = db.getAllSightings();
            sightingsList.setAdapter(new SightingsListAdapter(sightings, this));
            //Set listener to view existing sightings
            sightingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, SightingActivity.class);
                    //view/edit an existing sighting
                    intent.putExtra("create_or_update", 1);
                    SightingsModel contact = (SightingsModel) sightingsList.getAdapter().getItem(position);
                    intent.putExtra("contact_id", contact.getId());
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });
        }
    }












    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
