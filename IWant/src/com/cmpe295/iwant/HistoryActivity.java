package com.cmpe295.iwant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.cmpe295.iwant.adapter.SimpleArrayAdapter;
import com.cmpe295.iwant.model.AdTrackInfo;
import com.cmpe295.iwant.model.SingleComputerActivity;
import com.cmpe295.iwant.service.ServiceHandler;
 
public class HistoryActivity extends ListActivity {
 
	
	private ProgressDialog pDialog;
	private static String url = "http://api.androidhive.info/contacts/";
	//private static String url = "http://10.0.2.2:8080/iwant/products/1234";
 // upc
	//name
	//categoryLevel1
	//categoryLevel2
	//categoryLevel3
	//categoryLevel4
	//shortDescription
	//longDescription
	//brandName
	//thumbnailImage
	//mediumImage
	//modelNumber
	
	// JSON Node names
	/*private static final String COMPUTERS = "data";
    private static final String TAG_UPC = "upc";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_CAT_LEVEL_1 = "categoryLevel1";
    private static final String TAG_CAT_LEVEL_2 = "categoryLevel2";
    private static final String TAG_CAT_LEVEL_3 = "categoryLevel3";
    private static final String TAG_CAT_LEVEL_4 = "categoryLevel4";
    private static final String TAG_SHORT_DESC = "shortDescription";
    private static final String TAG_LONG_DESC = "longDescription";
    private static final String TAG_BRAND_NAME = "brandName";
    private static final String TAG_THUMB_IMAGE = "thumbnailImage";
    private static final String TAG_MED_IMAGE = "mediumImage";
    private static final String TAG_MODEL_NUM = "modelNumber";*/
	
	// JSON Node names
	private static final String TAG_CONTACTS = "contacts";
	private static final String TAG_ID = "id";
	private static final String TAG_NAME = "name";
	private static final String TAG_EMAIL = "email";
	private static final String TAG_ADDRESS = "address";
	private static final String TAG_GENDER = "gender";
	private static final String TAG_PHONE = "phone";
	private static final String TAG_PHONE_MOBILE = "mobile";
	private static final String TAG_PHONE_HOME = "home";
	private static final String TAG_PHONE_OFFICE = "office";

 
    // contacts JSONArray
    JSONArray computers = null;
 
    //Hashmap for ListView
    ArrayList<HashMap<String, String>> computerList;
 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history);
		computerList = new ArrayList<HashMap<String, String>>();
		 
        // get the action bar
        ActionBar actionBar = getActionBar();
        actionBar.setTitle("History");
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setIcon(R.drawable.ic_launcher);
       // setListAdapter(new SimpleArrayAdapter(this, REQUEST));
		ListView lv = getListView();

		// Listview on item click listener
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String name = ((TextView) view.findViewById(R.id.name))
						.getText().toString();
				String cost = ((TextView) view.findViewById(R.id.email))
						.getText().toString();
				String description = ((TextView) view.findViewById(R.id.mobile))
						.getText().toString();

				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						SingleComputerActivity.class);
				in.putExtra(TAG_NAME, name);
				in.putExtra(TAG_EMAIL, cost);
				in.putExtra(TAG_PHONE_MOBILE, description);
				startActivity(in);

			}
		});

		// Calling async task to get json
		new GetContacts().execute();
	}

	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(HistoryActivity.this);
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					computers = jsonObj.getJSONArray(TAG_CONTACTS);

					// looping through All Contacts
					for (int i = 0; i < computers.length(); i++) {
						JSONObject c = computers.getJSONObject(i);
						
						String id = c.getString(TAG_ID);
						String name = c.getString(TAG_NAME);
						String email = c.getString(TAG_EMAIL);
						String address = c.getString(TAG_ADDRESS);
						String gender = c.getString(TAG_GENDER);

						// Phone node is JSON Object
						JSONObject phone = c.getJSONObject(TAG_PHONE);
						String mobile = phone.getString(TAG_PHONE_MOBILE);
						String home = phone.getString(TAG_PHONE_HOME);
						String office = phone.getString(TAG_PHONE_OFFICE);

						// tmp hashmap for single contact
						HashMap<String, String> contact = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						contact.put(TAG_ID, id);
						contact.put(TAG_NAME, name);
						contact.put(TAG_EMAIL, email);
						contact.put(TAG_PHONE_MOBILE, mobile);

						// adding contact to contact list
						computerList.add(contact);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			ListAdapter adapter = new SimpleAdapter(
					HistoryActivity.this, computerList,
					R.layout.list_item, new String[] { TAG_NAME, TAG_EMAIL,
							TAG_PHONE_MOBILE }, new int[] { R.id.name,
							R.id.email, R.id.mobile });

			setListAdapter(adapter);
		}
	}
     // Listview on item click listener
    /*    lv.setOnItemClickListener(new OnItemClickListener() {

     			@Override
     			public void onItemClick(AdapterView<?> parent, View view,
     					int position, long id) {
     				// getting values from selected ListItem
     				String name = ((TextView) view.findViewById(R.id.name))
     						.getText().toString();
     				
     				String the_id = ((TextView) view.findViewById(R.id.the_id))
     						.getText().toString();
     			

     				// Starting single contact activity
     				Intent in = new Intent(getApplicationContext(),
     						SingleComputerActivity.class);
     				in.putExtra(TAG_NAME, name);
     				in.putExtra(TAG_SHORT_DESC, the_id);
     				startActivity(in);
     				
     				/*String short_desc = ((TextView) view.findViewById(R.id.short_desc))
     						.getText().toString();
     				String brand_name = ((TextView) view.findViewById(R.id.brand_name))
     						.getText().toString();

     				// Starting single contact activity
     				Intent in = new Intent(getApplicationContext(),
     						SingleComputerActivity.class);
     				in.putExtra(TAG_NAME, name);
     				in.putExtra(TAG_SHORT_DESC, cost);
     				in.putExtra(TAG_BRAND_NAME, description);
     				startActivity(in);*/

     		/*	}
     		});
     		
		// Calling async task to get json
		new GetComputers().execute();*/
		
	//	new GetFromServerTask(this).execute("http://10.0.2.2:8080/iwant/products/1234");
	//}
	
	/**
     * Async task class to get json by making HTTP call
     * */
/*    private class GetComputers extends AsyncTask<Void, Void, Void> {
 
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HistoryActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
 
        @Override
        protected Void doInBackground(Void... arg0) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();*/
 
            // Making a request to url and getting response
           /* String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);
 
            Log.d("Response: ", "> " + jsonStr);
 
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);*/
                     
                    // Getting JSON Array node
                 //   computers = jsonObj.getJSONArray("contacts");
                  
 
                    // looping through All Contacts
                   /* for (int i = 0; i < computers.length(); i++) {
                        JSONObject c = computers.getJSONObject(i);*/
                         
                    //    String upc = c.getString(TAG_UPC);
                     /*   String id = c.getString(TAG_ID);
                        String name = c.getString(TAG_NAME);
                        String cat1 = c.getString(TAG_CAT_LEVEL_1);
                        String cat2 = c.getString(TAG_CAT_LEVEL_2);
                        String cat3 = c.getString(TAG_CAT_LEVEL_3);
                        String cat4 = c.getString(TAG_CAT_LEVEL_4);
                        String shortdesc = c.getString(TAG_SHORT_DESC);
                        String longdesc = c.getString(TAG_LONG_DESC);
                        String brandName = c.getString(TAG_BRAND_NAME);
                        String thumb_im = c.getString(TAG_THUMB_IMAGE);
                        String med_im = c.getString(TAG_MED_IMAGE);
                        String model_num = c.getString(TAG_MODEL_NUM);*/
                        
                        // tmp hashmap for single contact
                    //    HashMap<String, String> computer = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                     /*    computer.put(TAG_ID, id);
                        computer.put(TAG_NAME, name);
                       computer.put(TAG_UPC, upc);
                        computer.put(TAG_CAT_LEVEL_1, cat1);
                        computer.put(TAG_CAT_LEVEL_2, cat2);
                        computer.put(TAG_CAT_LEVEL_3, cat3);
                        computer.put(TAG_CAT_LEVEL_4, cat4);
                        computer.put(TAG_SHORT_DESC, shortdesc);
                        computer.put(TAG_LONG_DESC, longdesc);
                        computer.put(TAG_BRAND_NAME, brandName);
                        computer.put(TAG_THUMB_IMAGE, thumb_im);
                        computer.put(TAG_MED_IMAGE, med_im);
                        computer.put(TAG_MODEL_NUM, model_num);*/
                        
 
                        // adding contact to contact list
                    /*    computerList.add(computer);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);*/
            // Dismiss the progress dialog
            /* if (pDialog.isShowing())
                pDialog.dismiss();
           *
             * Updating parsed JSON data into ListView
             * */
           /* ListAdapter adapter = new SimpleAdapter(
                    HistoryActivity.this, computerList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_SHORT_DESC,
                    		TAG_BRAND_NAME }, new int[] { R.id.name,
                            R.id.short_desc, R.id.brand_name });
           ListAdapter adapter = new SimpleAdapter(
                    HistoryActivity.this, computerList,
                    R.layout.list_item, new String[] { TAG_NAME, TAG_ID,
                    		}, new int[] { R.id.name,
                            R.id.the_id });
            setListAdapter(adapter);
        }
 
    }*/
	
	/*private class GetFromServerTask extends AsyncTask<String, Void, String> {
        Context localContext;
        public GetFromServerTask(Context context) {
            super();
            localContext = context;
        }
		protected String doInBackground(String... args) {
			String result = null;
			try {
				Log.d("logcat", "Calling the GET service");
				Log.d("logcat", "endpoint" + args[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpGet request = new HttpGet(args[0]);
				// request.addHeader("deviceId", deviceId);
				ResponseHandler<String> handler = new BasicResponseHandler();
				try {
					result = httpclient.execute(request, handler);
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				httpclient.getConnectionManager().shutdown();
				//Log.i("logcat", result);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return result;
		}

		protected void onPostExecute(String result) {
			Log.d("logcat", "Got the result" + result);
			try {
				JSONObject jsonObj = new JSONObject(result);
				jsonObj = new JSONObject(jsonObj.get("data").toString());
				REQUEST[0] = result;
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
 
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
 
		//get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();
 
	}*/
 
}