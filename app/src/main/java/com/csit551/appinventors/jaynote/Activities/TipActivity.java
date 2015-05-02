package com.csit551.appinventors.jaynote.Activities;
/*made by Cina*/
import android.app.ExpandableListActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.support.v7.app.ActionBar;
/*
import android.support.v7.app.AppCompatDelegate;
*/

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
/*        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);*/

        SimpleExpandableListAdapter tiplistAdapter =
        new SimpleExpandableListAdapter(this,
            createParent(),R.layout.tip_parent_adapter,
            new String[] { "Group Item" }, new int[] { R.id.tip_parent},
            createChild(), R.layout.tip_child_adapter,
            new String[] {"Sub Item"}, new int[] { R.id.tip_child});
        setListAdapter( tiplistAdapter);

    }    @SuppressWarnings("unchecked")
         private List createParent() {
        ArrayList result = new ArrayList();
        HashMap m = new HashMap();
        m.put( "TipCategory","Shelter");
        result.add( m );
        HashMap n = new HashMap();
        n.put( "TipCategory","Fire");
        result.add( n );
        HashMap o = new HashMap();
        o.put("TipCategory", "Food");
        result.add(o);
        HashMap p = new HashMap();
        p.put("TipCategory", "Water");
        result.add(p);
        HashMap q = new HashMap();
        q.put("TipCategory", "First Aid");
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
        outer.add( inner );/*arraylist/add*/
        inner = new ArrayList();
        String[] fires = res.getStringArray(R.array.fire);
        for (String fire : fires) {
            HashMap<String, String> fireh = new HashMap<String, String>();
            fireh.put("Child", fire);
            inner.add(fireh);/*pass hashmap adapter.*/
        }
        outer.add( inner );
        inner = new ArrayList();
        String[] foods = res.getStringArray(R.array.food);
        for (String food : foods) {
            HashMap<String, String> foodh = new HashMap<String, String>();
            foodh.put("Child", food);
            inner.add(foodh);/*pass hashmap adapter.*/
        }
        outer.add( inner );
        inner = new ArrayList();
        String[] waters = res.getStringArray(R.array.water);
        for (String water : waters) {
            HashMap<String, String> waterh = new HashMap<String, String>();
            waterh.put("Child", water);
            inner.add(waterh);/*pass hashmap adapter.*/
        }
        outer.add( inner );
        inner = new ArrayList();
        String[] firstaids = res.getStringArray(R.array.firstaid);
        int i=0;
        while (i<firstaids.length) {
            HashMap<String, String> firstaidh = new HashMap<String, String>();
            firstaidh.put("Child", fires[i]);
            inner.add( firstaidh );/*pass hashmap adapter.*/
            i++;
        }
        outer.add( inner );
        return outer;
    }

    public void  onContentChanged  () {
        System.out.println("onContentChanged");
        super.onContentChanged();
    }
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
        System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " + childPosition);
        return true;
    }

    public void  onGroupExpand  (int groupPosition) {
        try{
            System.out.println("Group expanding Listener => groupPosition" + groupPosition);
        }catch(Exception e){
            System.out.println(" groupPosition +" + e.getMessage());
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tip, menu);
        return true;
    }*/

   /* @Override*/
/*    public boolean onOptionsItemSelected(MenuItem item) {
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
