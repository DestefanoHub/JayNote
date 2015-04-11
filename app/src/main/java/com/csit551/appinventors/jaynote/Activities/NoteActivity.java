package com.csit551.appinventors.jaynote.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csit551.appinventors.jaynote.Database.DatabaseManager;
import com.csit551.appinventors.jaynote.Database.NotesModel;
import com.csit551.appinventors.jaynote.R;


public class NoteActivity extends ActionBarActivity
{
    private DatabaseManager db;
    private Context context;
    private NotesModel note;
    private Intent intent;
    private EditText noteName;
    private EditText noteBody;
    private Button save;
    private Button delete;
    private Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        db = new DatabaseManager(NoteActivity.this.getApplicationContext());
        context = this.getBaseContext();

        noteName = (EditText) findViewById(R.id.note_name_edit);
        noteBody = (EditText) findViewById(R.id.note_content);
        save = (Button) findViewById(R.id.save);
        edit = (Button) findViewById(R.id.edit);
        delete = (Button) findViewById(R.id.delete);

        intent = this.getIntent();
        if(intent.hasExtra("create_view_edit"))
        {
            int val = intent.getIntExtra("create_view_edit", -1);
            if(val == 0)
            {
                newNote();
            }
            else if(val == 1)
            {
                int id = intent.getIntExtra("note_id", 0);
                if(id > 0)
                {
                    note = db.getNoteById(id);
                    viewNote();
                }
            }
            else if(val == 2)
            {
                int id = intent.getIntExtra("note_id", 0);
                if(id > 0)
                {
                    note = db.getNoteById(id);
                    editNote();
                }
            }
        }
    }

    public void newNote()
    {
        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String name = noteName.getText().toString();
                String body = noteBody.getText().toString();
                db.insertNote(name, body);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    public void viewNote()
    {
        save.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        noteName.setText(note.getName());
        noteBody.setText(note.getBody());
        //make the edittexts uneditable
        noteName.setEnabled(false);
        noteName.setFocusable(false);
        noteBody.setEnabled(false);
        noteBody.setFocusable(false);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent newIntent = new Intent(context, NoteActivity.class);
                newIntent.putExtra("create_view_edit", 2);
                newIntent.putExtra("note_id", note.getId());
                startActivity(newIntent);
            }
        });
    }

    public void editNote()
    {
        edit.setVisibility(View.GONE);
        noteName.setText(note.getName());
        noteBody.setText(note.getBody());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                note.setName(noteName.getText().toString());
                note.setBody(noteBody.getText().toString());
                db.updateNote(note);
                setResult(RESULT_OK);
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                db.deleteNote(note);
                setResult(RESULT_OK);
                finish();
            }
        });
    }










    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
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
