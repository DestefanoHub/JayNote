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
import com.csit551.appinventors.jaynote.Database.NotesModel;
import com.csit551.appinventors.jaynote.ListAdapters.NotesListAdapter;
import com.csit551.appinventors.jaynote.R;

import java.util.ArrayList;


public class NoteListActivity extends ActionBarActivity
{
    private DatabaseManager db;
    private Context context;
    private Intent intent;
    private ListView notesList;
    private Button newNote;
    private ArrayList<NotesModel> notes;
    private static final int REQUEST_CODE_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_list);

        db = new DatabaseManager(NoteListActivity.this.getApplicationContext());
        context = this.getBaseContext();

        notesList = (ListView) findViewById(R.id.note_list);
        notes = db.getAllNotes();
        notesList.setAdapter(new NotesListAdapter(notes, this));
        //Set listener to view existing notes
        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, NoteActivity.class);
                //view an existing note
                intent.putExtra("create_view_edit", 1);
                NotesModel note = (NotesModel) notesList.getAdapter().getItem(position);
                intent.putExtra("note_id", note.getId());
                startActivity(intent);
            }
        });

        newNote = (Button) findViewById(R.id.note_button);
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoteActivity.class);
                //create a new note
                intent.putExtra("create_view_edit", 0);
                intent.putExtra("code", REQUEST_CODE_NOTE);
                startActivityForResult(intent, REQUEST_CODE_NOTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_NOTE)
        {
            notesList = (ListView) findViewById(R.id.note_list);
            notes = db.getAllNotes();
            notesList.setAdapter(new NotesListAdapter(notes, this));
            //Set listener to view existing notes
            notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, NoteActivity.class);
                    //view an existing note
                    intent.putExtra("create_view_edit", 1);
                    NotesModel note = (NotesModel) notesList.getAdapter().getItem(position);
                    intent.putExtra("note_id", note.getId());
                    startActivityForResult(intent, REQUEST_CODE_NOTE);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note_list, menu);
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
            Intent intent = new Intent(NoteListActivity.this.getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        /*if(id == R.id.action_links)
        {
            Intent intent = new Intent(NoteListActivity.this.getApplicationContext(), LinkListActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_tips)
        {
            Intent intent = new Intent(NoteListActivity.this.getApplicationContext(), TipActivity.class);
            startActivity(intent);
            finish();
        }*/

        return super.onOptionsItemSelected(item);
    }
}
