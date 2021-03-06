package com.csit551.appinventors.jaynote.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csit551.appinventors.jaynote.Database.DatabaseManager;
import com.csit551.appinventors.jaynote.Database.NotesModel;
import com.csit551.appinventors.jaynote.R;

public class NoteActivity extends Activity
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
                int id = intent.getIntExtra("note_id", -1);
                if(id > 0)
                {
                    note = db.getNoteById(id);
                    viewNote();
                }
            }
            else if(val == 2)
            {
                int id = intent.getIntExtra("note_id", -1);
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
                if(!noteName.getText().toString().equals("")) {
                    Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                    v.startAnimation(alphanim);
                    String name = noteName.getText().toString();
                    String body = noteBody.getText().toString();
                    db.insertNote(name, body);
                    Toast toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT);
                    toast.show();
                    setResult(RESULT_OK);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(context, "Please provide a name for the note!", Toast.LENGTH_SHORT);
                    toast.show();
                }
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
                finish();
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
                if(!noteName.getText().toString().equals("")) {
                    Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                    v.startAnimation(alphanim);
                    note.setName(noteName.getText().toString());
                    note.setBody(noteBody.getText().toString());
                    db.updateNote(note);
                    Toast toast = Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent newIntent = new Intent(context, NoteListActivity.class);
                    startActivity(newIntent);
                    finish();
                }
                else {
                    Toast toast = Toast.makeText(context, "Please provide a name for the note!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Animation alphanim = AnimationUtils.loadAnimation(context, R.anim.alph);
                v.startAnimation(alphanim);
                db.deleteNote(note);
                Toast toast = Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT);
                toast.show();
                Intent newIntent = new Intent(context, NoteListActivity.class);
                startActivity(newIntent);
                finish();
            }
        });
    }
}
