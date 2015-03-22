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
    private static final String CREATE_TABLE_SIGHTINGS = "CREATE TABLE " + DB_TABLE_SIGHTINGS + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL);";
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + DB_TABLE_NOTES + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL);";
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

    public void insertSighting()
    {
        ContentValues newContact = new ContentValues();
        try
        {
            db.insertOrThrow(DB_TABLE_SIGHTINGS, null, );
        }
        catch(Exception e) {
            Log.v("Insert_Sighting", e.getMessage());
        }
        db.close();
    }

    public void updateSighting(SightingsModel sighting)
    {
        try {
            ContentValues updateContact = new ContentValues();
            String args[] = new String[]{Integer.toString()};
            db.update(DB_TABLE_SIGHTINGS, , "id=?", args);
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
            String args[] = new String[]{Integer.toString()};
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
            String[] columns = new String[]{};
            Cursor cursor = db.query(DB_TABLE_SIGHTINGS, columns, "id=?", args, null, null, null, null);
            cursor.moveToFirst();
            sighting = new SightingsModel();
        }
        catch(Exception e)
        {
            Log.v("Get_One_Sighting", e.getMessage());
        }
        return sighting;
    }

    public ArrayList<SightingsModel> getAllContacts()
    {
        ArrayList<SightingsModel> sightings = new ArrayList<>();
        String[] columns = new String[]{};
        try {
            Cursor cursor = db.query(DB_TABLE_SIGHTINGS, columns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                .add(new);
                cursor.moveToNext();
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        catch(Exception e)
        {
            Log.v("Get_All_Sightings", e.getMessage());
        }
        return sightings;
    }

    public void insertNote()
    {
        ContentValues newContact = new ContentValues();
        try
        {
            db.insertOrThrow(DB_TABLE_NOTES, null, );
        }
        catch(Exception e) {
            Log.v("Insert_Note", e.getMessage());
        }
        db.close();
    }

    public void updateNote(NotesModel note)
    {
        try {
            ContentValues updateContact = new ContentValues();
            String args[] = new String[]{Integer.toString()};
            db.update(DB_TABLE_NOTES, , "id=?", args);
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
            String args[] = new String[]{Integer.toString()};
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
            String[] columns = new String[]{};
            Cursor cursor = db.query(DB_TABLE_NOTES, columns, "id=?", args, null, null, null, null);
            cursor.moveToFirst();
            note= new NotesModel();
        }
        catch(Exception e)
        {
            Log.v("Get_One_Note", e.getMessage());
        }
        return note;
    }

    public ArrayList<NotesModel> getAllContacts()
    {
        ArrayList<NotesModel> notes = new ArrayList<>();
        String[] columns = new String[]{};
        try {
            Cursor cursor = db.query(DB_TABLE_NOTES, columns, null, null, null, null, null);
            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                .add(new );
                cursor.moveToNext();
            }
            if (cursor != null && !cursor.isClosed()) {
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
