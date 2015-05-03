package com.csit551.appinventors.jaynote.Activities;
/*made by Cina*/
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.support.v7.app.ActionBar;
/*
import android.support.v7.app.AppCompatDelegate;
*/


/*Main problem. Hitting back, exits the app instead of bringing it back to main
Notes exits on the back button as well.
* Links and sightings goes back to main
* */





import com.csit551.appinventors.jaynote.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TipActivity extends ExpandableListActivity
{                               /*ExpandableList is being extended*/
                                /*therefore*/
    private Toolbar toolbar;
/*
    private AppCompatDelegate delegate;
*/

    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_list);
/*        getDelegate().installViewFactory();
        getDelegate().onCreate(savedInstanceState);*/
/*        if (toolbar != null) {
*//*
            setSupportActionBar(toolbar);
*//*
            toolbar.setLogo(R.drawable.ic_launcher);
        }*/

        SimpleExpandableListAdapter tiplistAdapter =
        new SimpleExpandableListAdapter(this,
            createParent(),R.layout.tip_parent_adapter,   /*Tip category and child have to match*/
            new String[] { "TipCategory" }, new int[] { R.id.tip_parent},
            createChild(), R.layout.tip_child_adapter,
            new String[] {"Child"}, new int[] { R.id.tip_child});
        setListAdapter( tiplistAdapter);

    }    @SuppressWarnings("unchecked")
         private List createParent() {
        ArrayList result = new ArrayList();
        Resources res = this.getResources();
        String shelter = res.getString(R.string.Shelter);
        HashMap m = new HashMap();
        m.put("TipCategory", shelter);
        result.add( m );
        String fire = res.getString(R.string.Fire);
        HashMap n = new HashMap();
        n.put( "TipCategory",fire);                       /*I could probably call this from strings so translation*/
        result.add(n);
        String food = res.getString(R.string.Food);
        HashMap o = new HashMap();
        o.put("TipCategory", food);
        result.add(o);
        String water = res.getString(R.string.Water);
        HashMap p = new HashMap();
        p.put("TipCategory", water);
        result.add(p);
        String firstaid = res.getString(R.string.FirstAid);
        HashMap q = new HashMap();
        q.put("TipCategory", firstaid);
        result.add( q );
        return result;
    }
    @SuppressWarnings("unchecked")
    private List createChild() {
        ArrayList outer = new ArrayList();
        ArrayList inner = new ArrayList();
        Resources res = this.getResources();
        String[] shelters = res.getStringArray(R.array.shelter); /*grab the xml*/
        for (String shelter : shelters) {  /*stringarray.length*/
            HashMap<String, String> shelterh = new HashMap<String, String>(); /*creating hashmap adapter*/
            shelterh.put("Child", shelter); /*type and string[]*/
            inner.add(shelterh);/*pass hashmap adapter.*/
        }
        outer.add(inner);/*arraylist/add*/
        inner = new ArrayList();
        String[] fires = res.getStringArray(R.array.fire);
        for (String fire : fires) {
            HashMap<String, String> fireh = new HashMap<String, String>();
            fireh.put("Child", fire);
            inner.add(fireh);/*pass hashmap adapter.*/
        }
        outer.add(inner);
        inner = new ArrayList();
        String[] foods = res.getStringArray(R.array.food);
        for (String food : foods) {
            HashMap<String, String> foodh = new HashMap<String, String>();
            foodh.put("Child", food);
            inner.add(foodh);/*pass hashmap adapter.*/
        }
        outer.add(inner);
        inner = new ArrayList();
        String[] waters = res.getStringArray(R.array.water);
        for (String water : waters) {
            HashMap<String, String> waterh = new HashMap<String, String>();
            waterh.put("Child", water);
            inner.add(waterh);/*pass hashmap adapter.*/
        }
        outer.add(inner);
        inner = new ArrayList();
        String[] firstaids = res.getStringArray(R.array.firstaid);
        for (String firstaid : firstaids) {
            HashMap<String, String> firstaidh = new HashMap<String, String>();
            firstaidh.put("Child", firstaid);
            inner.add(firstaidh);/*pass hashmap adapter.*/
        }
        outer.add( inner );
        return outer;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip, menu);
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
            Intent intent = new Intent(TipActivity.this.getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_links)
        {
            Intent intent = new Intent(TipActivity.this.getApplicationContext(), LinkListActivity.class);
            startActivity(intent);
            finish();
        }

        if(id == R.id.action_notes)
        {
            Intent intent = new Intent(TipActivity.this.getApplicationContext(), TipActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }*/
}
