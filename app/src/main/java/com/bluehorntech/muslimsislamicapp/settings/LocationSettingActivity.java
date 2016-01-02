package com.bluehorntech.muslimsislamicapp.settings;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.bluehorntech.muslimsislamicapp.common.CustomHttpClient;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class LocationSettingActivity extends Activity implements OnClickListener, OnItemClickListener{

	private ListView listViewLocation;
	private ArrayList<JSONObject> addressesJosnArrayList;
	private LocationListAdaptor listLocationAdaptor;
	private Button btnBack;
	private EditText eidtTextSearchLocation;
	private Button btnSearchLocation;
	private Context activtContext;
	private SharedPreferences ifarzSharedPref;

	private AdView adView;
	public String AD_UNIT_ID= "";
	 
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		AD_UNIT_ID=this.getResources().getString(R.string.banner_id);
		adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adlayout);
	    layout.addView(adView);
        
	    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
        
        
        
		ifarzSharedPref = getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, MODE_PRIVATE);
		activtContext  = getParent();
		addressesJosnArrayList = new ArrayList<JSONObject>();
		setScreenViews();
	}

	private void setScreenViews() {
//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
//		
		btnSearchLocation = (Button)findViewById(R.id.btn_search_location);
		btnSearchLocation.setOnClickListener(this);
		
		listViewLocation = (ListView)findViewById(R.id.listview_locations);
		listViewLocation.setOnItemClickListener(this);
		listLocationAdaptor = new LocationListAdaptor(this);
		listViewLocation.setAdapter(listLocationAdaptor);
		
		eidtTextSearchLocation = (EditText)findViewById(R.id.edittext_search_loation);
	}
	
	private class LocationListAdaptor extends BaseAdapter
	{

		private LayoutInflater layoutInflator;

		LocationListAdaptor(Context context)
		{
			layoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		@Override
		public int getCount() {
			if(addressesJosnArrayList!=null)
				return addressesJosnArrayList.size();
			else
				return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int location, View convertedView, ViewGroup viewGroup) {
			if(convertedView == null)
				convertedView = layoutInflator.inflate(R.layout.layout_location_item, null);
			TextView textViewLocationName = (TextView) convertedView.findViewById(R.id.textview_location_city);
		
			
			try {
				textViewLocationName.setText(addressesJosnArrayList.get(location).getString("formatted_address"));
			} catch (JSONException e) {
			}
			return convertedView;
		}
		
	}

	@Override
	public void onClick(View clickedView) {
		if(clickedView == btnBack)
		{
			finish();
		}
		else if(clickedView == btnSearchLocation)
		{
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(eidtTextSearchLocation.getWindowToken(), 0);
			if(!eidtTextSearchLocation.getText().toString().trim().equals(""))
			{
				addressesJosnArrayList.clear();
				new LoationsLoadigThread(eidtTextSearchLocation.getText().toString().trim()).execute();
			}
		}
	}
	
	private class LoationsLoadigThread extends AsyncTask<Void, Void, String>
	{
		private String locationAddress;
		LoationsLoadigThread(String address)
		{
			this.locationAddress = address;
		}
		@Override
		protected String doInBackground(Void... arg0)
		{
			String result  = null;
			
			try {
				result = CustomHttpClient.executeGet("http://maps.googleapis.com/maps/api/geocode/json?address="+locationAddress.replace(" ", "%20")+"&sensor=false");
			} catch (Exception e) {
			}
			
			return result;
		}
		@Override
		protected void onPostExecute(String result) 
		{
			if(result!=null)
			{
				try {
					JSONArray tempResJson = new JSONObject(result).getJSONArray("results");
					if(tempResJson.length()==0)
					{
						showLocationErrorDialog();
					}
					for (int i = 0; i < tempResJson.length(); i++) {
						addressesJosnArrayList.add(tempResJson.getJSONObject(i));
					}
					listLocationAdaptor.notifyDataSetChanged();
				} catch (JSONException e) {
					showLocationErrorDialog();
				}
			}
			else
			{
				AlertDialog.Builder errorAlert  = new AlertDialog.Builder(activtContext);
				errorAlert.setTitle("Error!");
				errorAlert.setMessage("Network not found!");
				errorAlert.setPositiveButton("Ok", null);
				errorAlert.show();
			}
			super.onPostExecute(result);
		}
		
	}
	private void showLocationErrorDialog() {
		AlertDialog.Builder errorAlert  = new AlertDialog.Builder(activtContext);
		errorAlert.setTitle("Invalid Address!");
		errorAlert.setMessage("No record found for current address.");
		errorAlert.setPositiveButton("Ok", null);
		errorAlert.show();
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int location, long arg3) {
		
		if(location<addressesJosnArrayList.size())
		{
			try {
				ifarzSharedPref.edit().putString(ConstantsClass.LOCATION_NAME,addressesJosnArrayList.get(location).getString("formatted_address")).commit();
				ifarzSharedPref.edit().putFloat(ConstantsClass.LATI_KEY
						, (float) addressesJosnArrayList.get(location).getJSONObject("geometry").getJSONObject("location").getDouble("lat")).commit();
				ifarzSharedPref.edit().putFloat(ConstantsClass.LATI_KEY
						, (float) addressesJosnArrayList.get(location).getJSONObject("geometry").getJSONObject("location").getDouble("lat")).commit();
				ifarzSharedPref.edit().putFloat(ConstantsClass.LONG_KEY
						, (float) addressesJosnArrayList.get(location).getJSONObject("geometry").getJSONObject("location").getDouble("lng")).commit();
				finish();
			} catch (Exception e) {
			}
			
		}
		
	}
}