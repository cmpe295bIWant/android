package com.cmpe295.iwant;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;

import com.cmpe295.iwant.adapter.TitleNavigationAdapter;
import com.cmpe295.iwant.model.SpinnerNavItem;


public class MainActivity extends Activity implements
		ActionBar.OnNavigationListener {

	/**
	 * The serialization (saved instance state) Bundle key representing the
	 * current dropdown position.
	 */
	private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";

	// Title navigation Spinner data
    private ArrayList<SpinnerNavItem> navSpinner;
    
 // Navigation adapter
    private TitleNavigationAdapter adapter;
    
 // Refresh menu item
    private MenuItem refreshMenuItem;

    ImageButton imageAutomobile;
	ImageButton imageJewelry;
	ImageButton imageCameras;
	ImageButton imageComputers;
	ImageButton imageVideoGames;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		addListenerOnComputers();
		// Set up the action bar to show a dropdown list.
		final ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Set up the dropdown list navigation in the action bar.
		//actionBar.setListNavigationCallbacks(
		// Specify a SpinnerAdapter to populate the dropdown list.
				/*new ArrayAdapter<String>(actionBar.getThemedContext(),
						android.R.layout.simple_list_item_1,
						android.R.id.text1, new String[] {
								getString(R.string.title_section1),
								getString(R.string.title_section2),
								getString(R.string.title_section3), }), this);*/
		
		// Spinner title navigation data
		
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem("Categories", R.drawable.ic_category));
        navSpinner.add(new SpinnerNavItem("History", R.drawable.ic_history));
        navSpinner.add(new SpinnerNavItem("Recommendations", R.drawable.ic_recommendation));
       // navSpinner.add(new SpinnerNavItem("Latitude", R.drawable.ic_latitude));     
        imageComputers = (ImageButton) findViewById(R.id.imageAutomobile);
		imageComputers = (ImageButton) findViewById(R.id.imageCameras);
		imageComputers = (ImageButton) findViewById(R.id.imageJewelry);
		imageComputers = (ImageButton) findViewById(R.id.imageVideoGames);
        // title drop down adapter
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
 
        // assigning the spinner navigation     
        actionBar.setListNavigationCallbacks(adapter, this);

	}

	 public void addListenerOnComputers() {

	    	imageComputers = (ImageButton) findViewById(R.id.imageComputers);
			imageComputers.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(MainActivity.this, ComputersActivity.class);
					startActivity(intent);
							
			
				}

			});
		}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the previously serialized current dropdown position.
		if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
			getActionBar().setSelectedNavigationItem(
					savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// Serialize the current dropdown position.
		outState.putInt(STATE_SELECTED_NAVIGATION_ITEM, getActionBar()
				.getSelectedNavigationIndex());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main_actions, menu);
		 // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
 
        return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onNavigationItemSelected(int position, long id) {
		// When the given dropdown item is selected, show its contents in the
		// container view.
		Fragment fragment = new DummySectionFragment();
		Bundle args = new Bundle();
		args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
		fragment.setArguments(args);
		//getSupportFragmentManager().beginTransaction()
		//		.replace(R.id.container, fragment).commit();
		if(id != 0){
			if(position == 0){
				Intent myIntent1 = new Intent(MainActivity.this, CategoryActivity.class);
				startActivity(myIntent1);
			} else if(position == 1){
				Intent myIntent1 = new Intent(MainActivity.this, HistoryActivity.class);
			    startActivity(myIntent1);
			} else {
				Intent myIntent1 = new Intent(MainActivity.this, RecommendationActivity.class);
			    startActivity(myIntent1);
			}
		}
		
		return true;
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			TextView dummyTextView = (TextView) rootView
					.findViewById(R.id.section_label);
			dummyTextView.setText(Integer.toString(getArguments().getInt(
					ARG_SECTION_NUMBER)));
			return rootView;
		}
	}
	

    /**
     * On selecting action bar icons
     * */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_search:
            // search action
            return true;
      /*  case R.id.action_location_found:
            // location found
            LocationFound();
            return true;*/
        case R.id.action_refresh:
            // refresh
        	 refreshMenuItem = item;
             // load the data from server
             new SyncData().execute();
             return true;
     /*   case R.id.action_help:
            // help action
            return true;
        case R.id.action_check_updates:
            // check for updates action
            return true;*/
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Async task to load the data from server
     * **/
    private class SyncData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            // set the progress bar view
            refreshMenuItem.setActionView(R.layout.action_progressbar);
 
            refreshMenuItem.expandActionView();
        }
 
        @Override
        protected String doInBackground(String... params) {
            // not making real request in this demo
            // for now we use a timer to wait for sometime
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
 
       
        
        @Override
        protected void onPostExecute(String result) {
            refreshMenuItem.collapseActionView();
            // remove the progress bar view
            refreshMenuItem.setActionView(null);
        }
        
       
    };
    
}
