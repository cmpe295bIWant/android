package com.cmpe295.iwant;

import com.cmpe295.iwant.adapter.SimpleArrayAdapter;
 
import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
 
public class ComputersActivity extends ListActivity {
 
	static final String[] MOBILE_OS = 
               new String[] { "Android", "iOS", "WindowsMobile", "Blackberry"};
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 // get the action bar
        ActionBar actionBar = getActionBar();
 
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.computers);
		setListAdapter(new SimpleArrayAdapter(this, MOBILE_OS));
		
	}
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
 
	}
 
}