package com.csit551.appinventors.jaynote.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csit551.appinventors.jaynote.Database.DatabaseManager;
import com.csit551.appinventors.jaynote.Database.SightingsModel;
import com.csit551.appinventors.jaynote.R;


public class SightingActivity extends Activity
{
    private DatabaseManager db;
    private Context context;
    private SightingsModel sighting;
    private Intent intent;
    private Button save;
    private Button edit;
    private Button delete;
    private EditText sightingName;
    private EditText sightingSize;
    private EditText sightingType;
    private EditText sightingColor;
    private EditText sightingLocation;
    private EditText sightingMisc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sighting);

        db = new DatabaseManager(SightingActivity.this.getApplicationContext());
        context = this.getBaseContext();

        sightingName = (EditText) findViewById(R.id.sighting_title_input);
        sightingSize = (EditText) findViewById(R.id.size_animal_input);
        sightingType = (EditText) findViewById(R.id.type_organism_input);
        sightingColor = (EditText) findViewById(R.id.color_input);
        sightingLocation = (EditText) findViewById(R.id.location_input);
        sightingMisc = (EditText) findViewById(R.id.misc_input);
        save = (Button) findViewById(R.id.save_button);
        delete = (Button) findViewById(R.id.delete_button);
        edit = (Button) findViewById(R.id.edit_button);

        intent = this.getIntent();
        if(intent.hasExtra("create_view_edit"))
        {
            int val = intent.getIntExtra("create_view_edit", -1);
            if(val == 0)
            {
                newSighting();
            }
            else if(val == 1)
            {
                int id = intent.getIntExtra("sighting_id", 0);
                if(id > 0)
                {
                    sighting = db.getSightingById(id);
                    viewSighting();
                }
            }
            else if(val == 2)
            {
                int id = intent.getIntExtra("sighting_id", 0);
                if(id > 0)
                {
                    sighting = db.getSightingById(id);
                    editSighting();
                }
            }
        }
    }

    public void newSighting()
    {
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = sightingName.getText().toString();
                String size = sightingSize.getText().toString();
                String type = sightingType.getText().toString();
                String color = sightingColor.getText().toString();
                String location = sightingLocation.getText().toString();
                String misc = sightingMisc.getText().toString();
                db.insertSighting(name, size, type, color, null, null, location, misc);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void viewSighting()
    {
        save.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        sightingName.setText(sighting.getName());
        sightingSize.setText(sighting.getSize());
        sightingType.setText(sighting.getType());
        sightingColor.setText(sighting.getColor());
        sightingLocation.setText(sighting.getLocation());
        sightingMisc.setText(sighting.getMisc());
        //make the edittexts uneditable
        sightingName.setEnabled(false);
        sightingName.setFocusable(false);
        sightingSize.setEnabled(false);
        sightingSize.setFocusable(false);
        sightingType.setEnabled(false);
        sightingType.setFocusable(false);
        sightingColor.setEnabled(false);
        sightingColor.setFocusable(false);
        sightingLocation.setEnabled(false);
        sightingLocation.setFocusable(false);
        sightingMisc.setEnabled(false);
        sightingMisc.setFocusable(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent newIntent = new Intent(context, SightingActivity.class);
                newIntent.putExtra("create_view_edit", 2);
                newIntent.putExtra("sighting_id", sighting.getId());
                startActivity(newIntent);
            }
        });
    }

    public void editSighting()
    {
        edit.setVisibility(View.GONE);
        sightingName.setText(sighting.getName());
        sightingSize.setText(sighting.getSize());
        sightingType.setText(sighting.getType());
        sightingColor.setText(sighting.getColor());
        sightingLocation.setText(sighting.getLocation());
        sightingMisc.setText(sighting.getMisc());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                sighting.setName(sightingName.getText().toString());
                sighting.setSize(sightingSize.getText().toString());
                sighting.setType(sightingType.getText().toString());
                sighting.setColor(sightingColor.getText().toString());
                sighting.setLocation(sightingLocation.getText().toString());
                sighting.setMisc(sightingMisc.getText().toString());
                db.updateSighting(sighting);
                Intent newIntent = new Intent(context, MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db.deleteSighting(sighting);
                Intent newIntent = new Intent(context, MainActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }
}
