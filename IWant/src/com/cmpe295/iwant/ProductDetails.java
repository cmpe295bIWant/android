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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.cmpe295.iwant.model.AdTrackInfo;

public class ProductDetails extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_ad_time_tracker);
		new GetFromServerTask(this)
		.execute("http://localhost:8080/iwant");
	}

	private class GetFromServerTask extends AsyncTask<String, Void, String> {
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
				// Log.i("logcat", result);
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
				JSONArray array = jsonObj.getJSONArray("data");
				Map<String, AdTrackInfo> map = new HashMap<String, AdTrackInfo>();
				
				for (int i = 0; i < array.length(); i++) {
					JSONObject obj = (JSONObject) array.get(i);
					if (!obj.getString("location").equals("null")) {
						AdTrackInfo adTrackInfo = map.get(obj.getString("location"));
						if (adTrackInfo == null) {
							adTrackInfo = new AdTrackInfo();
							map.put(obj.getString("location"), adTrackInfo);
						}
						adTrackInfo.setClicks(adTrackInfo.getClicks()
								+ obj.getLong("clicks"));
						adTrackInfo.setClickthroughs(adTrackInfo
								.getClickthroughs()
								+ obj.getLong("clicksThrough"));
						adTrackInfo.setImpressions(adTrackInfo.getImpressions()
								+ obj.getLong("impressions"));
						obj=null;
					}
				}
				array=null;
				Iterator<Map.Entry<String, AdTrackInfo>> itr = map.entrySet()
						.iterator();
				List<String> locations = new ArrayList<String>();
				while (itr.hasNext()) {
					Map.Entry<String, AdTrackInfo> temp = itr.next();
					locations.add(temp.getKey());
					//adClickSeries.add(locations.size() - 1, temp.getValue()
							//.getClicks());
					//adClickThroughsSeries.add(locations.size() - 1, temp
							//.getValue().getClickthroughs());
					//adImpressionsSeries.add(locations.size() - 1, temp
							//.getValue().getImpressions());
				}
				//map.clear();
map=null;
				// Creating a dataset to hold each series
				//XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				//dataset.addSeries(adClickSeries);
				//dataset.addSeries(adClickThroughsSeries);
				//dataset.addSeries(adImpressionsSeries);
				
				// Creating XYSeriesRenderer to customize adClickSeries
				/*XYSeriesRenderer adCLicksRenderer = new XYSeriesRenderer();
				adCLicksRenderer.setColor(Color.BLUE);
				adCLicksRenderer.setPointStyle(PointStyle.CIRCLE);
				adCLicksRenderer.setFillPoints(true);
				adCLicksRenderer.setLineWidth(3);
				adCLicksRenderer.setDisplayChartValues(true);
				adCLicksRenderer.setChartValuesTextSize(20f);

				// Creating XYSeriesRenderer to customize adClickThroughsSeries
				XYSeriesRenderer adClickThroughsRenderer = new XYSeriesRenderer();
				adClickThroughsRenderer.setColor(Color.DKGRAY);
				adClickThroughsRenderer.setPointStyle(PointStyle.CIRCLE);
				adClickThroughsRenderer.setFillPoints(true);
				adClickThroughsRenderer.setLineWidth(3);
				adClickThroughsRenderer.setDisplayChartValues(true);
				adClickThroughsRenderer.setChartValuesTextSize(20f);

				// Creating XYSeriesRenderer to customize adImpressionsSeries
				XYSeriesRenderer adImpressionsRenderer = new XYSeriesRenderer();
				adImpressionsRenderer.setColor(Color.RED);
				adImpressionsRenderer.setPointStyle(PointStyle.CIRCLE);
				adImpressionsRenderer.setFillPoints(true);
				adImpressionsRenderer.setLineWidth(3);
				adImpressionsRenderer.setDisplayChartValues(true);
				adImpressionsRenderer.setChartValuesTextSize(20f);

				// Creating a XYMultipleSeriesRenderer to customize the whole
				// chart
				XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
				multiRenderer.setXLabels(0);
				multiRenderer
						.setChartTitle("AdClicks, AdClickThroughs and AdImpressions Vs Time Chart");
				multiRenderer.setXTitle("Location");
				multiRenderer.setYTitle("Count");
				multiRenderer.setAxisTitleTextSize(20f);
				multiRenderer.setPointSize(5f);
				multiRenderer.setLegendTextSize(20f);
				multiRenderer.setZoomButtonsVisible(true);
				multiRenderer.setGridColor(Color.BLACK);
				for (int i = 0; i < locations.size(); i++) {
					multiRenderer.addXTextLabel(i, locations.get(i));
				}
				locations.clear();
locations=null;
				// Note: The order of adding dataseries to dataset and renderers
				// to multipleRenderer
				// should be same
				multiRenderer.addSeriesRenderer(adCLicksRenderer);
				multiRenderer.addSeriesRenderer(adClickThroughsRenderer);
				multiRenderer.addSeriesRenderer(adImpressionsRenderer);
				multiRenderer.setBackgroundColor(Color.GRAY);

				// Creating an intent to plot line chart using dataset and
				// multipleRenderer
				Intent intent = ChartFactory.getLineChartIntent(
						getBaseContext(), dataset, multiRenderer);*/

				// Start Activity
				//startActivity(intent);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}