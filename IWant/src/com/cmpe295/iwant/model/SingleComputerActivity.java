package com.cmpe295.iwant.model;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import com.cmpe295.iwant.R;

public class SingleComputerActivity  extends Activity {
	
	// JSON node keys
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_PHONE_MOBILE = "mobile";
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer_list_item);
        // get the action bar
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Item Details");
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
        // getting intent data
        Intent in = getIntent();
        
        // Get JSON values from previous intent
        String name = in.getStringExtra(TAG_NAME);
        String email = in.getStringExtra(TAG_EMAIL);
        String mobile = in.getStringExtra(TAG_PHONE_MOBILE);
        
        // Displaying all values on the screen
        TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblEmail = (TextView) findViewById(R.id.email_label);
        TextView lblMobile = (TextView) findViewById(R.id.mobile_label);
        
        lblName.setText(name);
        lblEmail.setText(email);
        lblMobile.setText(mobile);
    }//private static final String TAG_NAME = "name";
	//private static final String TAG_ID = "id";
	//private static final String TAG_SHORT_DESC = "shortDesc";
	//private static final String TAG_BRAND_NAME = "brandName";
	//@Override
  /*  public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.computer_list_item);*/
        
        // getting intent data
      //  Intent in = getIntent();
        
        // Get JSON values from previous intent
     /*   String name = in.getStringExtra(TAG_NAME);
        String the_id = in.getStringExtra(TAG_ID);
        String shortDesc = in.getStringExtra(TAG_SHORT_DESC);
        String brandName = in.getStringExtra(TAG_BRAND_NAME);*/
        
        // Displaying all values on the screen
       /*  TextView lblName = (TextView) findViewById(R.id.name_label);
        TextView lblId = (TextView) findViewById(R.id.short_desc_label);
       TextView lblshortDesc = (TextView) findViewById(R.id.short_desc_label);
        TextView lblbrandName = (TextView) findViewById(R.id.brand_name_label);*/
        
      /*  lblName.setText(name);
        lblId.setText(the_id);
        lblshortDesc.setText(shortDesc);
        lblbrandName.setText(brandName);*/
    
}
