package com.csit551.appinventors.jaynote.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2015.
 */
public class DatabaseManager
{
    private static final String DB_NAME = "JayNote_app_db";
    private static final String DB_TABLE_SIGHTINGS = "sightings";
    private static final String DB_TABLE_NOTES = "notes";
    private static final int DB_VERSION = 1;
    private static final String CREATE_TABLE_SIGHTINGS = "CREATE TABLE " + DB_TABLE_SIGHTINGS + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT NOT NULL, size TEXT, type TEXT, color TEXT, date TEXT, time TEXT, location TEXT);";
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + DB_TABLE_NOTES + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name TEXT, body TEXT);";
    private SQLHelper sqlHelper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c)
    {
        this.context = c;
        sqlHelper=new SQLHelper(c);
        this.db = sqlHelper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException
    {
        sqlHelper = new SQLHelper(context);
        db = sqlHelper.getReadableDatabase();
        return this;
    }

    public void close(){
        sqlHelper.close();
    }

    public void insertSighting(String sName, String sSize, String sType, String sColor, String sDate, String sTime, String sLocation)
    {
        ContentValues newSighting = new ContentValues();
        newSighting.put("name", sName);
        newSighting.put("size", sSize);
        newSighting.put("type", sType);
        newSighting.put("color", sColor);
        newSighting.put("date", sDate);
        newSighting.put("time", sTime);
        newSighting.put("location", sLocation);
        try
        {
            db.insertOrThrow(DB_TABLE_SIGHTINGS, null, newSighting);
        }
        catch(Exception e) {
            Log.v("Insert_Sighting", e.getMessage());
        }
        db.close();
    }

    public void updateSighting(SightingsModel sighting)
    {
        try {
            ContentValues updateSighting = new ContentValues();
            updateSighting.put("name", sighting.getName());
            updateSighting.put("size", sighting.getSize());
            updateSighting.put("type", sighting.getType());
            updateSighting.put("color", sighting.getColor());
            updateSighting.put("date", sighting.getDate());
            updateSighting.put("time", sighting.getTime());
            updateSighting.put("location", sighting.getLocation());
            String args[] = new String[]{Integer.toString(sighting.getId())};
            db.update(DB_TABLE_SIGHTINGS, updateSighting, "id=?", args);
            db.close();
        }
        catch(Exception e)
        {
            Log.v("Update_Sighting", e.getMessage());
        }
    }

    public void deleteSighting(SightingsModel sighting)
    {
        try {
            String args[] = new String[]{Integer.toString(sighting.getId())};
            db.delete(DB_TABLE_SIGHTINGS, "id=?", args);
            db.close();
        }
        catch(Exception e)
        {
            Log.v("Delete_Sighting", e.getMessage());
        }
    }

    public SightingsModel getSightingById(int id)
    {
        SightingsModel sighting = null;
        try {
            String[] args = new String[]{Integer.toString(id)};
            String[] columns = new String[]{"id", "name", "size", "type", "color", "date", "time", "location"};
            Cursor cursor = db.query(DB_TABLE_SIGHTINGS, columns, "id=?", args, null, null, null, null);
            cursor.moveToFirst();
            sighting = new SightingsModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        }
        catch(Exception e)
        {
            Log.v("Get_One_Sighting", e.getMessage());
        }
        return sighting;
    }

    public ArrayList<SightingsModel> getAllSightings()
    {
        ArrayList<SightingsModel> sightings = new ArrayList<>();
        String[] columns = new String[]{"id", "name", "size", "type", "color", "date", "time", "location"};
        try {
            Cursor cursor = db.query(DB_TABLE_SIGHTINGS, columns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                sightings.add(new SightingsModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                cursor.moveToNext();
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        catch(Exception e)
        {
            Log.v("Get_All_Sightings", e.getMessage());
        }
        return sightings;
    }

    public void insertNote(String name, String body)
    {
        ContentValues newNote = new ContentValues();
        newNote.put("name", name);
        newNote.put("body", body);
        try
        {
            db.insertOrThrow(DB_TABLE_NOTES, null, newNote);
        }
        catch(Exception e) {
            Log.v("Insert_Note", e.getMessage());
        }
        db.close();
    }

    public void updateNote(NotesModel note)
    {
        try {
            ContentValues updateNote = new ContentValues();
            updateNote.put("name", note.getName());
            updateNote.put("body", note.getBody());
            String args[] = new String[]{Integer.toString(note.getId())};
            db.update(DB_TABLE_NOTES, updateNote, "id=?", args);
            db.close();
        }
        catch(Exception e)
        {
            Log.v("Update_Notes", e.getMessage());
        }
    }

    public void deleteNote(NotesModel note)
    {
        try {
            String args[] = new String[]{Integer.toString(note.getId())};
            db.delete(DB_TABLE_NOTES, "id=?", args);
            db.close();
        }
        catch(Exception e)
        {
            Log.v("Delete_Note", e.getMessage());
        }
    }

    public NotesModel getNoteById(int id)
    {
        NotesModel note = null;
        try {
            String[] args = new String[]{Integer.toString(id)};
            String[] columns = new String[]{"id", "text"};
            Cursor cursor = db.query(DB_TABLE_NOTES, columns, "id=?", args, null, null, null, null);
            cursor.moveToFirst();
            note= new NotesModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
        }
        catch(Exception e)
        {
            Log.v("Get_One_Note", e.getMessage());
        }
        return note;
    }

    public ArrayList<NotesModel> getAllNotes()
    {
        ArrayList<NotesModel> notes = new ArrayList<>();
        String[] columns = new String[]{"id", "text"};
        try {
            Cursor cursor = db.query(DB_TABLE_NOTES, columns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                notes.add(new NotesModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2)));
                cursor.moveToNext();
            }
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        catch(Exception e)
        {
            Log.v("Get_All_Notes", e.getMessage());
        }
        return notes;
    }




    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c){
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_TABLE_SIGHTINGS);
            db.execSQL(CREATE_TABLE_NOTES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Database upgrade", "The database " + DB_NAME + " is being upgraded, all data will be purged.");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_SIGHTINGS);
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_NOTES);
            onCreate(db);
        }
    }
}
