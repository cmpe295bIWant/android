package com.cmpe295.iwant;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
 
public class CategoryActivity extends Activity {

	ImageButton imageAutomobile;
	ImageButton imageJewelry;
	ImageButton imageCameras;
	ImageButton imageComputers;
	ImageButton imageVideoGames;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.category);
	 
	        // get the action bar
	        ActionBar actionBar = getActionBar();
	 
	        // Enabling Back navigation on Action Bar icon
	        actionBar.setDisplayHomeAsUpEnabled(true);
	        actionBar.setIcon(R.drawable.ic_category);
	     //   addListenerOnAutomobile();
		//	addListenerOnCameras();
		//	addListenerOnJewelry();
	        addListenerOnComputers();
		//	addListenerOnVideoGames();
			imageComputers = (ImageButton) findViewById(R.id.imageAutomobile);
			imageComputers = (ImageButton) findViewById(R.id.imageCameras);
			imageComputers = (ImageButton) findViewById(R.id.imageJewelry);
			imageComputers = (ImageButton) findViewById(R.id.imageVideoGames);
			
			
	 
	       // txtQuery = (TextView) findViewById(R.id.txtQuery);
	 
	        //handleIntent(getIntent());
	    }
	 @Override
	    protected void onNewIntent(Intent intent) {
	        setIntent(intent);
	        handleIntent(intent);
	    }
	 
	 /**
	     * Handling intent data
	     */
	    private void handleIntent(Intent intent) {
	        
	 
	    }
	
	/*    public void addListenerOnAutomobile() {

	    	imageAutomobile = (ImageButton) findViewById(R.id.imageAutomobile);
			
			
	    	imageAutomobile.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					//Intent intent = new Intent(CategoryActivity.this, AutomobileActivity.class);
					//startActivity(intent);
							
			
				}

			});
		}*/
	    
	    public void addListenerOnComputers() {

	    	imageComputers = (ImageButton) findViewById(R.id.imageComputers);
			
			
	    	imageComputers.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					Intent intent = new Intent(CategoryActivity.this, ComputersActivity.class);
					startActivity(intent);
							
			
				}

			});
		}
	    
}