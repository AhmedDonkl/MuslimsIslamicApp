package com.bluehorntech.muslimsislamicapp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.IslamicChronology;


import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
//import com.bluehorntech.islamicportal2.common.FacebookShaing;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) public class Fragment4IslamicEvents extends Fragment implements OnClickListener {
	
	private LinearLayout layoutContainer;
	private String[] eventsNamesArray;
	private LayoutInflater layoutInfator;
	private DateTime dateTimeObj;
	private Calendar calenderObj;
	private String[] monthsNameArray;
	private ProgressDialog loadingPrgresBAr;
	private SharedPreferences ifarzSharedPref;
	
	public Fragment4IslamicEvents(){}
	
	private AdView adView;
	public String AD_UNIT_ID= "";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.islamic_events, container, false);
        AD_UNIT_ID=getActivity().getResources().getString(R.string.banner_id);
        EasyTracker.getInstance(getActivity()).activityStart(getActivity()); 
        
        adView = new AdView(getActivity());
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.adlayout);
	    layout.addView(adView);
        
	    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
        
        
        
        ifarzSharedPref = getActivity().getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, 0);
		loadingPrgresBAr = new ProgressDialog(getActivity());
		loadingPrgresBAr.setMessage("Loading...");
		loadingPrgresBAr.show();
         
        return rootView;
    }
	
	
private void setScreenViews() {
		
		monthsNameArray = getResources().getStringArray(R.array.months_name_array);
		eventsNamesArray = getResources().getStringArray(R.array.events_names_array);
		layoutContainer = (LinearLayout)getView().findViewById(R.id.layout_events_container);
		layoutContainer.removeAllViews();
	    layoutInfator = (LayoutInflater)getActivity().getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for (int eventIndex = 0; eventIndex < eventsNamesArray.length; eventIndex++)
		{
			LinearLayout tempLayout = (LinearLayout)layoutInfator.inflate(R.layout.rowitem_event, null);
			TextView textViewEventName = (TextView)tempLayout.findViewById(R.id.textView_event_name);
			textViewEventName.setText(eventsNamesArray[eventIndex]);
			layoutContainer.addView(tempLayout);
			GregorianCalendar eventDate = getEventDate(eventIndex);
			if(eventDate.compareTo(calenderObj)<0)
			{
				tempLayout.setVisibility(View.GONE);
			}
			TextView textViewEventDate = (TextView)tempLayout.findViewById(R.id.textView_event_time);
			textViewEventDate.setText(getConvertedDateStrig(eventDate));
			tempLayout.setTag(eventIndex);
			tempLayout.setOnClickListener(this);
			if(eventDate.equals(""))
				tempLayout.setVisibility(View.GONE);
				
			
			
		}
	}

	private String getConvertedDateStrig(GregorianCalendar covertedDate) 
	{
		return monthsNameArray[covertedDate.get(Calendar.MONTH)] + " " +covertedDate.get(Calendar.DAY_OF_MONTH) 
				+ ", "+covertedDate.get(Calendar.YEAR);
	}

	@Override
	public void onResume() {
		new LoadObjetsThread().execute();
		super.onResume();
	}
	
	private GregorianCalendar getEventDate(int eventIndex) {
		if(eventIndex == 0)
		{
			return dateTimeObj.withDate(dateTimeObj.getYear(), 1, 1).toGregorianCalendar();
		}
		else if(eventIndex == 1)
		{
			return dateTimeObj.withDate(dateTimeObj.getYear(), 1, 10).toGregorianCalendar();
			
		}
		else if(eventIndex == 2)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 3, 12).toGregorianCalendar();
			
		}
		else if(eventIndex == 3)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 7, 27).toGregorianCalendar();
			
		}
		else if(eventIndex == 4)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 8, 15).toGregorianCalendar();
			
		}
		else if(eventIndex == 5)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 9, 1).toGregorianCalendar();
		
		}
		else if(eventIndex == 6)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 9, 27).toGregorianCalendar();
			
		}
		else if(eventIndex == 7)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 10, 1).toGregorianCalendar();
			
		}
		else if(eventIndex == 8)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear(), 12, 10).toGregorianCalendar();
			
		}
		if(eventIndex == 9)
		{
			
			return dateTimeObj.withDate(dateTimeObj.getYear()+1, 1, 1).toGregorianCalendar();
			
		}
		else if(eventIndex == 10)
		{
			return dateTimeObj.withDate(dateTimeObj.getYear()+1, 1, 10).toGregorianCalendar();
			
		}
		else if(eventIndex == 11)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 3, 12).toGregorianCalendar();
			
		}
		else if(eventIndex == 12)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 7, 27).toGregorianCalendar();
			
		}
		else if(eventIndex == 13)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 8, 15).toGregorianCalendar();
			
		}
		else if(eventIndex == 14)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 9, 1).toGregorianCalendar();
			
		}
		else if(eventIndex == 15)
		{
			return dateTimeObj.withDate(dateTimeObj.getYear()+1, 9, 27).toGregorianCalendar();
			
		}
		else if(eventIndex == 16)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 10, 1).toGregorianCalendar();
			
		}
		else if(eventIndex == 17)
		{
			return  dateTimeObj.withDate(dateTimeObj.getYear()+1, 12, 10).toGregorianCalendar();
			
		}
		return null;
	}

	private class LoadObjetsThread extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... arg0) {

			
			calenderObj = Calendar.getInstance();
			Chronology islamicCalender = IslamicChronology.getInstanceUTC();
			dateTimeObj = new DateTime(islamicCalender);
			return null;
		}
		@Override
		protected void onPostExecute(Void result) {
			try {
				if(loadingPrgresBAr!=null)
					loadingPrgresBAr.dismiss();
				setScreenViews();
			} catch (Exception e) {
			}
			
			super.onPostExecute(result);
		}
	}

//	@Override
//	public void onClick(View clickedView) {
//		
//		try {
//			int clickedIndex = Integer.valueOf(clickedView.getTag().toString());
//			shareOnFaebook(getConvertedDateStrig(getEventDate(clickedIndex)), eventsNamesArray[clickedIndex]);
//		} catch (Exception e) {
//		}
//	}
//	private void shareOnFaebook(final String eventTiming, final String eventName)
//	{
//		String[] shareOptions = {"Facebook","Twitter","Cancel"};
//		AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
//	    builder.setTitle("Share");
//	    builder.setItems(shareOptions, new DialogInterface.OnClickListener() {
//	               public void onClick(DialogInterface dialog, int which) {
//	               
//	            	   if(which==0)
//	            	   {
//	            		   if (isNetworkAvailable()) {
//	            			   Bundle postParams = new Bundle();
//		            	        postParams.putString("name", "iFarz");
//		            	        String sharedText ="Islamic event "+ eventName +" will be in "+ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "")
//		            	        			+" at "+eventTiming + ". (Calculated by iFarz)";
//		            	        postParams.putString("message",sharedText);
//		            	        
//		            	      
//		            	        FacebookShaing facebookClassObj = new FacebookShaing(getParent());
//		            	        facebookClassObj.signInWithFacebook(postParams);
//	            			} else {
//	            				Toast.makeText(getParent(), "No Network Connection Available", Toast.LENGTH_LONG).show();
//	            			}
//	            		   	
//	            	   }
//	            	   else if(which == 1)
//	            	   {
//	            		   if (isNetworkAvailable()) {
//	            			   String sharedText ="Islamic event "+ eventName +" will be in "+ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "")
//	            	        			+" at "+eventTiming + ". (Calculated by iFarz)";
//	            				Twitt twitt = new Twitt(getParent(), getResources().getString(R.string.tweet_consumer_key)
//	            						, getResources().getString(R.string.tweet_secret_key));
//	            				twitt.shareToTwitter(sharedText);
//	            			} else {
//	            				Toast.makeText(getParent(), "No Network Connection Available", Toast.LENGTH_LONG).show();
//	            			}
//	            		   
//	            	   }
//	            	   else if(which == 2)
//	            	   {
//	            		   dialog.dismiss();
//	            	   }
//	           }
//	    });
//	    builder.create();
//	    builder.show();
//		
//	}
	public boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			return false;
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
