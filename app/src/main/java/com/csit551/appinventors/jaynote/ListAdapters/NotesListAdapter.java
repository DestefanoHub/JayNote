package com.csit551.appinventors.jaynote.ListAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csit551.appinventors.jaynote.Database.NotesModel;
import com.csit551.appinventors.jaynote.R;

import java.util.ArrayList;

/**
 * Created by Andrew on 3/22/2015.
 */
public class NotesListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<NotesModel> notes;
    private TextView nameText;
/*
    private TextView bodyText;
*/

    public NotesListAdapter(ArrayList<NotesModel> notes, Context context)
    {
        this.context = context;
        this.notes = notes;
    }

    public int getCount()
    {
        return notes.size();
    }

    public Object getItem(int position)
    {
        return notes.get(position);
    }

    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = convertView;
        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.note_adapter, null);
        }

        nameText = (TextView) view.findViewById(R.id.name);
/*
        bodyText = (TextView) view.findViewById(R.id.body);
*/
        NotesModel note = notes.get(position);
        nameText.setText(note.getName());
/*
        bodyText.setText(note.getBody());
*/

        return view;



/*        I took the body out because it it expands way too much
        The alternative is of course to find a way way it only demonstrates
        the first line - Cina*/
    }
}
