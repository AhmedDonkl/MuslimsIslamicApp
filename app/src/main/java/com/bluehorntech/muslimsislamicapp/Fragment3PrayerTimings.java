package com.bluehorntech.muslimsislamicapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.sax.RootElement;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.bluehorntech.muslimsislamicapp.common.CustomHttpClient;
import com.bluehorntech.muslimsislamicapp.common.GPSTracker;
import com.bluehorntech.muslimsislamicapp.timing.AlarmReceiver;
import com.bluehorntech.muslimsislamicapp.timing.PrayTime;
import com.bluehorntech.muslimsislamicapp.timing.TimingActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) public class Fragment3PrayerTimings extends Fragment implements LocationListener, OnCheckedChangeListener, OnClickListener{
	// this Fragment will be called from MainActivity
	public Fragment3PrayerTimings(){}
	private PrayTime prayerTimeCalculator;
	private TextView textViewFajarTime;
	private TextView textViewShrkTime;
	private TextView textViewZohrTime;
	private TextView textViewAsrTime;
	private TextView textViewMaghribTime;
	private TextView textViewEshaTime;
	private GPSTracker gpsTracker;
	private SharedPreferences ifarzSharedPref;
	private int countChnageLocation = 0;
	private ArrayList<String> parayersTimes;
	private CheckBox checkBoxFajar;
	private CheckBox checkBoxShrouk;
	private CheckBox checkBoxZohr;
	private CheckBox checkBoxAsr;
	private CheckBox checkBoxMaghrib;
	private CheckBox checkBoxIsha;
	private SimpleDateFormat dateFormater;
	private int curretDay = 0;
	private TextView textViewHeaderDate;
	private String[] monthsNameArray;
	private String[] islamicMonthsNameArray;
	private LinearLayout layoutShafiiJur;
	private LinearLayout layoutHanfiJur;
	private ImageView ivCheckShafiiJur;
	private ImageView ivCheckHanfiJur;
	
	private AdView adView;
	public String AD_UNIT_ID= "";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.prayer_timings, container, false);
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
        
        ifarzSharedPref = getActivity().getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, countChnageLocation);
		monthsNameArray = getResources().getStringArray(R.array.months_name_array);
		islamicMonthsNameArray = getResources().getStringArray(R.array.months_islamic_name_array);
		if(ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "").equals(""))
			gpsTracker = new GPSTracker(getActivity(),Fragment3PrayerTimings.this);
		prayerTimeCalculator = new PrayTime();
		//setScreenViews();
		
		
		textViewFajarTime = (TextView)rootView.findViewById(R.id.textView_fajar_namz_time);
		textViewFajarTime.setOnClickListener(this);
		textViewShrkTime = (TextView)rootView.findViewById(R.id.textView_shrouk_namz_time);
		textViewShrkTime.setOnClickListener(this);
		textViewZohrTime = (TextView)rootView.findViewById(R.id.textView_zohr_namz_time);
		textViewZohrTime.setOnClickListener(this);
		textViewAsrTime = (TextView)rootView.findViewById(R.id.textView_asr_namz_time);
		textViewAsrTime.setOnClickListener(this);
		textViewMaghribTime = (TextView)rootView.findViewById(R.id.textView_maghrib_namz_time);
		textViewMaghribTime.setOnClickListener(this);
		textViewEshaTime = (TextView)rootView.findViewById(R.id.textView_isha_namz_time);
		textViewEshaTime.setOnClickListener(this);
		
		checkBoxFajar = (CheckBox)rootView.findViewById(R.id.checkbox_fajar_timer);
		checkBoxFajar.setOnCheckedChangeListener(this);
		checkBoxShrouk = (CheckBox)rootView.findViewById(R.id.checkbox_shrouk_timer);
		checkBoxShrouk.setOnCheckedChangeListener(this);
		checkBoxZohr = (CheckBox)rootView.findViewById(R.id.checkbox_zohr_timer);
		checkBoxZohr.setOnCheckedChangeListener(this);
		checkBoxAsr = (CheckBox)rootView.findViewById(R.id.checkbox_asr_timer);
		checkBoxAsr.setOnCheckedChangeListener(this);
		checkBoxMaghrib = (CheckBox)rootView.findViewById(R.id.checkbox_maghrib_timer);
		checkBoxMaghrib.setOnCheckedChangeListener(this);
		checkBoxIsha = (CheckBox)rootView.findViewById(R.id.checkbox_isha_timer);
		checkBoxIsha.setOnCheckedChangeListener(this);
		
		try {
			checkBoxFajar.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false));
			checkBoxShrouk.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false));
			checkBoxZohr.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false));
			checkBoxAsr.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false));
			checkBoxMaghrib.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false));
			checkBoxIsha.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false));
		} catch (Exception e) {
		}
		
//		textViewHeaderDate = (TextView)findViewById(R.id.textView_header_date);
//		Calendar dateObj = Calendar.getInstance();
//		textViewHeaderDate.setText(monthsNameArray[dateObj.get(Calendar.MONTH)] + " " +dateObj.get(Calendar.DAY_OF_MONTH) 
//					+ ", "+dateObj.get(Calendar.YEAR));
	//	
//		setHeaderTitle();
		
		//////////////////
		
		layoutShafiiJur = (LinearLayout)rootView.findViewById(R.id.layout_shafii);
		layoutShafiiJur.setOnClickListener(this);
		ivCheckShafiiJur= (ImageView)rootView.findViewById(R.id.imageview_check_shafii);

		layoutHanfiJur = (LinearLayout)rootView.findViewById(R.id.layout_hanfi);
		layoutHanfiJur.setOnClickListener(this);
		ivCheckHanfiJur= (ImageView)rootView.findViewById(R.id.imageview_check_hanfi);
		int jusristicCalMethod = ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0);
		if(jusristicCalMethod == 0)
			ivCheckShafiiJur.setVisibility(View.VISIBLE);
		else if(jusristicCalMethod == 1)
			ivCheckHanfiJur.setVisibility(View.VISIBLE);
		////////////
	
		
        return rootView;
    }

private void setAlarm() {
		
		dateFormater = new SimpleDateFormat("hh:mm a");
		Date curretDate = new Date();
		curretDate.setMinutes(curretDate.getMinutes()+ifarzSharedPref.getInt(ConstantsClass.ALARM_BEFORE, 0)+5);
		int namzTag = 0;
		if(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false) && curretDate.compareTo(parseDate(1,curretDay)) <= 0)
		{
			namzTag = 1;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false) && curretDate.compareTo(parseDate(2,curretDay)) <= 0)
		{
			namzTag = 2;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false) && curretDate.compareTo(parseDate(3,curretDay)) <= 0)
		{
			namzTag = 3;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false) && curretDate.compareTo(parseDate(4,curretDay)) <= 0)
		{
			namzTag = 4;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false) && curretDate.compareTo(parseDate(5,curretDay)) <= 0)
		{
			namzTag = 5;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false) && curretDate.compareTo(parseDate(6,curretDay)) <= 0)
		{
			namzTag = 6;
		}
		else if(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false) 
				|| ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false)
				|| ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false)
				|| ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false)
				|| ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false)
				|| ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false))
		{
			setPrayerTimes(getActivity());
			curretDay ++;
		}
		if(namzTag!=0)
		{
			Date alarmAt = parseDate(namzTag,curretDay);
			alarmAt.setMinutes(alarmAt.getMinutes()-ifarzSharedPref.getInt(ConstantsClass.ALARM_BEFORE, 0));
			AlarmManager alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity()
					, 86968, new Intent(getActivity()
					, AlarmReceiver.class).putExtra(ConstantsClass.ALARM_TAG,namzTag), PendingIntent.FLAG_UPDATE_CURRENT);
			alarmMgr.set(AlarmManager.RTC_WAKEUP,alarmAt.getTime(), pendingIntent);
		}
		else if(curretDay==1)
			setAlarm();

		
	}

private Date parseDate(int i,int currentDay ) {
	String timeString = "";
	if(i==1)
		timeString = ifarzSharedPref.getString(ConstantsClass.FAJAR_TIMING_TAG, "00:00 AM");
	else if(i==2)
		timeString = ifarzSharedPref.getString(ConstantsClass.SHROUK_TIMIG_TAG, "00:00 AM");
	else if(i==3)
		timeString = ifarzSharedPref.getString(ConstantsClass.ZOHR_TIMING_TAG, "00:00 AM");
	else if(i==4)
		timeString = ifarzSharedPref.getString(ConstantsClass.ASR_TIMING_TAG, "00:00 AM");
	else if(i==5)
		timeString = ifarzSharedPref.getString(ConstantsClass.MAGRIB_TIMING_TAG, "00:00 AM");
	else if(i==6)
		timeString = ifarzSharedPref.getString(ConstantsClass.ISHA_TIMING_TAG, "00:00 AM");
	
	try {
		Date dateParesed = dateFormater.parse(timeString);
		
		Date datePar = new Date();
		datePar.setDate(datePar.getDate()+currentDay);
		datePar.setHours(dateParesed.getHours());
		datePar.setMinutes(dateParesed.getMinutes());
		return datePar;
	} catch (Exception e) {
	}
	return new Date();
}

private void setTimeZone() {
	new TimeZoneRequestThread().execute();
}

private void setPrayerTimes() {
	
	prayerTimeCalculator.setCalcMethod(ifarzSharedPref.getInt(ConstantsClass.CALCULATION_METHOD, 1));
	prayerTimeCalculator.setAsrJuristic(ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0));
	prayerTimeCalculator.setAdjustHighLats(ifarzSharedPref.getInt(ConstantsClass.LATITUDE_METHOD, 0));
	prayerTimeCalculator.setTimeFormat(ifarzSharedPref.getInt(ConstantsClass.TIME_FORMATE, 1));
	float timeZone = ifarzSharedPref.getFloat(ConstantsClass.TIMEZONE_KEY, 0f);
	if(timeZone<0)
		timeZone+=1;
	parayersTimes = prayerTimeCalculator.getPrayerTimes(Calendar.getInstance()
			,  ifarzSharedPref.getFloat(ConstantsClass.LATI_KEY, 0.0f), ifarzSharedPref.getFloat(ConstantsClass.LONG_KEY, 0.0f)
			, timeZone);
	if(parayersTimes!=null)
	{
		textViewFajarTime.setText(parayersTimes.get(0));
		textViewShrkTime.setText(parayersTimes.get(1));
		textViewZohrTime.setText(parayersTimes.get(2));
		textViewAsrTime.setText(parayersTimes.get(3));
		textViewMaghribTime.setText(parayersTimes.get(4));
		textViewEshaTime.setText(parayersTimes.get(6));
	}
	try {
		Editor prefEditor = ifarzSharedPref.edit();
		prefEditor.putString(ConstantsClass.FAJAR_TIMING_TAG,parayersTimes.get(0));
		prefEditor.putString(ConstantsClass.SHROUK_TIMIG_TAG,parayersTimes.get(1));
		prefEditor.putString(ConstantsClass.ZOHR_TIMING_TAG,parayersTimes.get(2));
		prefEditor.putString(ConstantsClass.ASR_TIMING_TAG,parayersTimes.get(3));
		prefEditor.putString(ConstantsClass.MAGRIB_TIMING_TAG,parayersTimes.get(4));
		prefEditor.putString(ConstantsClass.ISHA_TIMING_TAG,parayersTimes.get(6));
		prefEditor.commit();
	} catch (Exception e) {
	}

	try {
		setAlarm();
	} catch (Exception e) {
	}
	
}
private void setPrayerTimes(Context context) {
	
	PrayTime prayerTimeCalculator = new PrayTime();
	prayerTimeCalculator.setCalcMethod(ifarzSharedPref.getInt(ConstantsClass.CALCULATION_METHOD, 1));
	prayerTimeCalculator.setAsrJuristic(ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0));
	prayerTimeCalculator.setAdjustHighLats(ifarzSharedPref.getInt(ConstantsClass.LATITUDE_METHOD, 0));
	prayerTimeCalculator.setTimeFormat(ifarzSharedPref.getInt(ConstantsClass.TIME_FORMATE, 1));
	Calendar curentCal = Calendar.getInstance();
	curentCal.add(Calendar.DAY_OF_MONTH, 1);
	ArrayList<String> parayersTimes = prayerTimeCalculator.getPrayerTimes(curentCal
			,  ifarzSharedPref.getFloat(ConstantsClass.LATI_KEY, 0.0f), ifarzSharedPref.getFloat(ConstantsClass.LONG_KEY, 0.0f)
			, ifarzSharedPref.getFloat(ConstantsClass.TIMEZONE_KEY, 0f));
	
	try {
		Editor prefEditor = ifarzSharedPref.edit();
		prefEditor.putString(ConstantsClass.FAJAR_TIMING_TAG,parayersTimes.get(0));
		prefEditor.putString(ConstantsClass.SHROUK_TIMIG_TAG,parayersTimes.get(1));
		prefEditor.putString(ConstantsClass.ZOHR_TIMING_TAG,parayersTimes.get(2));
		prefEditor.putString(ConstantsClass.ASR_TIMING_TAG,parayersTimes.get(3));
		prefEditor.putString(ConstantsClass.MAGRIB_TIMING_TAG,parayersTimes.get(4));
		prefEditor.putString(ConstantsClass.ISHA_TIMING_TAG,parayersTimes.get(6));
		prefEditor.commit();
	} catch (Exception e) {
	}
	
}
//private void setScreenViews() {
//	
//	textViewFajarTime = (TextView)rootView.findViewById(R.id.textView_fajar_namz_time);
//	textViewFajarTime.setOnClickListener(this);
//	textViewShrkTime = (TextView)getView().findViewById(R.id.textView_shrouk_namz_time);
//	textViewShrkTime.setOnClickListener(this);
//	textViewZohrTime = (TextView)getView().findViewById(R.id.textView_zohr_namz_time);
//	textViewZohrTime.setOnClickListener(this);
//	textViewAsrTime = (TextView)getView().findViewById(R.id.textView_asr_namz_time);
//	textViewAsrTime.setOnClickListener(this);
//	textViewMaghribTime = (TextView)getView().findViewById(R.id.textView_maghrib_namz_time);
//	textViewMaghribTime.setOnClickListener(this);
//	textViewEshaTime = (TextView)getView().findViewById(R.id.textView_isha_namz_time);
//	textViewEshaTime.setOnClickListener(this);
//	
//	checkBoxFajar = (CheckBox)getView().findViewById(R.id.checkbox_fajar_timer);
//	checkBoxFajar.setOnCheckedChangeListener(this);
//	checkBoxShrouk = (CheckBox)getView().findViewById(R.id.checkbox_shrouk_timer);
//	checkBoxShrouk.setOnCheckedChangeListener(this);
//	checkBoxZohr = (CheckBox)getView().findViewById(R.id.checkbox_zohr_timer);
//	checkBoxZohr.setOnCheckedChangeListener(this);
//	checkBoxAsr = (CheckBox)getView().findViewById(R.id.checkbox_asr_timer);
//	checkBoxAsr.setOnCheckedChangeListener(this);
//	checkBoxMaghrib = (CheckBox)getView().findViewById(R.id.checkbox_maghrib_timer);
//	checkBoxMaghrib.setOnCheckedChangeListener(this);
//	checkBoxIsha = (CheckBox)getView().findViewById(R.id.checkbox_isha_timer);
//	checkBoxIsha.setOnCheckedChangeListener(this);
//	
//	try {
//		checkBoxFajar.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false));
//		checkBoxShrouk.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false));
//		checkBoxZohr.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false));
//		checkBoxAsr.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false));
//		checkBoxMaghrib.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false));
//		checkBoxIsha.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false));
//	} catch (Exception e) {
//	}
//	
////	textViewHeaderDate = (TextView)findViewById(R.id.textView_header_date);
////	Calendar dateObj = Calendar.getInstance();
////	textViewHeaderDate.setText(monthsNameArray[dateObj.get(Calendar.MONTH)] + " " +dateObj.get(Calendar.DAY_OF_MONTH) 
////				+ ", "+dateObj.get(Calendar.YEAR));
////	
////	setHeaderTitle();
//	
//	//////////////////
//	
//	layoutShafiiJur = (LinearLayout)getView().findViewById(R.id.layout_shafii);
//	layoutShafiiJur.setOnClickListener(this);
//	ivCheckShafiiJur= (ImageView)getView().findViewById(R.id.imageview_check_shafii);
//
//	layoutHanfiJur = (LinearLayout)getView().findViewById(R.id.layout_hanfi);
//	layoutHanfiJur.setOnClickListener(this);
//	ivCheckHanfiJur= (ImageView)getView().findViewById(R.id.imageview_check_hanfi);
//	int jusristicCalMethod = ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0);
//	if(jusristicCalMethod == 0)
//		ivCheckShafiiJur.setVisibility(View.VISIBLE);
//	else if(jusristicCalMethod == 1)
//		ivCheckHanfiJur.setVisibility(View.VISIBLE);
//	////////////
//}
//private void setHeaderTitle()
//{
//	if(!ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "").equals(""))
//	{
//		TextView textViewHeaderTitle = (TextView)findViewById(R.id.textView_header_app_name);
//		textViewHeaderTitle.setGravity(Gravity.LEFT);
//		textViewHeaderTitle.setText(ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, ""));
//	}
//}
//@Override
//public void onActivityResult(int requestCode, int resultCode, Intent data) {
//  super.onActivityResult(requestCode, resultCode, data);
//  try {
//	  Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
//	} catch (Exception e) {
//	}
// 
//}
public void onBackPressed() {
	finish();
	
	
}
//@Override
//protected void onResume() {
//	new LoadObjetsThread().execute();
//	try {
//		setTimeZone();
//		setHeaderTitle();
//		setPrayerTimes();
//		if(gpsTracker!=null)
//			gpsTracker.loctionUpdate();
//	} catch (Exception e) {
//	}
//	super.onResume();
//}
public void finish() {
	try {
		if(gpsTracker!=null)
		{
			gpsTracker.stopUsingGPS();
			gpsTracker.stopSelf();
		}
		
	} catch (Exception e) {
	}
	
	
}
@Override
public void onLocationChanged(Location location) {
	
	countChnageLocation++;
	if(location!=null && countChnageLocation<=3)
		if(ifarzSharedPref.getFloat(ConstantsClass.LATI_KEY, 0.0f)!=location.getLatitude() || ifarzSharedPref.getFloat(ConstantsClass.LONG_KEY, 0.0f)!=location.getLongitude())
		{	
			ifarzSharedPref.edit().putFloat(ConstantsClass.LATI_KEY, (float) location.getLatitude()).commit();
			ifarzSharedPref.edit().putFloat(ConstantsClass.LONG_KEY, (float) location.getLongitude()).commit();
			setTimeZone();
			setPrayerTimes();
			new LocationsLoadigThread(String.valueOf(location.getLatitude()+","+location.getLongitude())).execute();
		}
	if(countChnageLocation>=3)
		{
			try {
				gpsTracker.stopUsingGPS();
				gpsTracker.stopSelf();
			} catch (Exception e) {
			}
		}
}

@Override
public void onProviderDisabled(String provider) {
	
}

@Override
public void onProviderEnabled(String provider) {
	
}

@Override
public void onStatusChanged(String provider, int status, Bundle extras) {
	
}


class TimeZoneRequestThread  extends AsyncTask<Void, Void, String>
{

	@Override
	protected String doInBackground(Void... arg0) {
		try {
			String responceJsonString = CustomHttpClient.executeGet("http://www.earthtools.org/timezone/"
					+String.valueOf(ifarzSharedPref.getFloat(ConstantsClass.LATI_KEY, (float) 0.0))+"/"+String.valueOf(ifarzSharedPref.getFloat(ConstantsClass.LONG_KEY, (float) 0.0)));
			
			return responceJsonString;
		} catch (Exception e) {
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		if(result!=null)
		try {
//			ifarzSharedPref.edit().putInt(TIMEZONE_KEY, new JSONObject(result).getInt("gmtOffset")).commit();
			
			InputStream inStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
	        try {
	            XmlPullParser parser = Xml.newPullParser();
	            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
	            parser.setInput(inStream, null);
	            parser.nextTag();
	            parser.require(XmlPullParser.START_TAG, null, "timezone");
	            while (parser.next() != XmlPullParser.END_TAG) {
	                if (parser.getEventType() != XmlPullParser.START_TAG) 
	                {
	                    continue;
	                }
	                String name = parser.getName();
	                if (name.equals("offset")) {
	                	if (parser.next() == XmlPullParser.TEXT) 
	                	{
	                		ifarzSharedPref.edit().putFloat(ConstantsClass.TIMEZONE_KEY, Float.valueOf(parser.getText())).commit();
	                    }
	                	
	                } 
	                else 
	                {
	                    skip(parser);
	                }
	            }  
	        } finally {
	        }
	        setPrayerTimes();
			//setHeaderTitle();
		} catch (Exception e) {
		}
		
		super.onPostExecute(result);
	}
	private void skip(XmlPullParser parser) throws XmlPullParserException, IOException 
	{
	    if (parser.getEventType() != XmlPullParser.START_TAG) 
	    {
	        throw new IllegalStateException();
	    }
	    int depth = 1;
	    while (depth != 0) {
	        switch (parser.next()) {
	        case XmlPullParser.END_TAG:
	            depth--;
	            break;
	        case XmlPullParser.START_TAG:
	            depth++;
	            break;
	        }
	    }
	 }
}

@Override
public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	try {
		if(buttonView == checkBoxFajar)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.FAJAR_TAG, isChecked).commit();
		}
		else if(buttonView == checkBoxShrouk)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.SHROUK_TAG, isChecked).commit();
		}
		else if(buttonView == checkBoxZohr)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.ZOHR_TAG, isChecked).commit();
		}
		else if(buttonView == checkBoxAsr)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.ASR_TAG, isChecked).commit();
		}
		else if(buttonView == checkBoxMaghrib)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.MAGRIB_TAG, isChecked).commit();
		}
		else if(buttonView == checkBoxIsha)
		{
			ifarzSharedPref.edit().putBoolean(ConstantsClass.ISHA_TAG, isChecked).commit();
		}
		setAlarm();
		curretDay = 0;
	} catch (Exception e) {
	}
	
}
private class LocationsLoadigThread extends AsyncTask<Void, Void, String>
{
	private String locationAddress;
	LocationsLoadigThread(String address)
	{
		this.locationAddress = address;
	}
	@Override
	protected String doInBackground(Void... arg0)
	{
		String result  = null;
		
		try {
			result = CustomHttpClient.executeGet("http://maps.googleapis.com/maps/api/geocode/json?latlng="+locationAddress.replace(" ", "%20")+"&sensor=false");
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
				if(tempResJson.length()>0)
				{
					try {
						ifarzSharedPref.edit().putString(ConstantsClass.LOCATION_NAME,tempResJson.getJSONObject(0).getString("formatted_address")).commit();
						
					} catch (Exception e) {
					}
					
				}
			} catch (JSONException e) {
			}
		}
		
		super.onPostExecute(result);
	}
	
}

@Override
public void onClick(View clickedView) {
//	if(clickedView == textViewFajarTime)
//	{
//		shareOnFaebook(textViewFajarTime.getText().toString(),ConstantsClass.FAJAR_TAG);
//	}
//	else if(clickedView == textViewShrkTime)
//	{
//		shareOnFaebook(textViewShrkTime.getText().toString(),ConstantsClass.SHROUK_TAG);
//	}
//	else if(clickedView == textViewZohrTime)
//	{
//		shareOnFaebook(textViewZohrTime.getText().toString(),ConstantsClass.ZOHR_TAG);
//	}
//	else if(clickedView == textViewAsrTime)
//	{
//		shareOnFaebook(textViewAsrTime.getText().toString(),ConstantsClass.ASR_TAG);
//	}
//	else if(clickedView == textViewMaghribTime)
//	{
//		shareOnFaebook(textViewMaghribTime.getText().toString(),ConstantsClass.MAGRIB_TAG);
//	}
//	else if(clickedView == textViewEshaTime)
//	{
//		shareOnFaebook(textViewEshaTime.getText().toString(),ConstantsClass.ISHA_TAG);
//	}
	 if(clickedView == layoutShafiiJur)
	{
		resetAserChecks();
		ivCheckShafiiJur.setVisibility(View.VISIBLE);
		ifarzSharedPref.edit().putInt(ConstantsClass.JUSRISTIC_METHOD, getJuristicMethods()).commit();
		setPrayerTimes();
	}
	else if(clickedView == layoutHanfiJur)
	{
		resetAserChecks();
		ivCheckHanfiJur.setVisibility(View.VISIBLE);
		ifarzSharedPref.edit().putInt(ConstantsClass.JUSRISTIC_METHOD, getJuristicMethods()).commit();
		setPrayerTimes();
	}
	
}
private void resetAserChecks() {
	ivCheckShafiiJur.setVisibility(View.INVISIBLE);
	ivCheckHanfiJur.setVisibility(View.INVISIBLE);
}
private int getJuristicMethods() {
	if(ivCheckShafiiJur.getVisibility()==View.VISIBLE)
		return 0;
	if(ivCheckHanfiJur.getVisibility()==View.VISIBLE)
		return 1;
	return -1;
}

//private void shareOnFaebook(final String namazTiming, final String namazTag)
//{
//	String[] shareOptions = {"Facebook","Twitter","Cancel"};
//	AlertDialog.Builder builder = new AlertDialog.Builder(getParent());
//    builder.setTitle("Share");
//    builder.setItems(shareOptions, new DialogInterface.OnClickListener() {
//               public void onClick(DialogInterface dialog, int which) {
//               
//            	   if(which==0)
//            	   {
//            		   if (isNetworkAvailable()) {
//            				Calendar dateObj = Calendar.getInstance();
//	            		   	String currenDateString = monthsNameArray[dateObj.get(Calendar.MONTH)] + " " +dateObj.get(Calendar.DAY_OF_MONTH) 
//	            						+ ", "+dateObj.get(Calendar.YEAR);
//	            		   	Bundle postParams = new Bundle();
//	            	        postParams.putString("name", "iFarz");
//	            	        String sharedText = namazTag +" namaz timing in "+ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "")
//	            	        			+" at "+currenDateString+" is "+ namazTiming + " (Calculated by iFarz)";
//	            	        postParams.putString("message",sharedText);
//	            	        
//	            	      
//	            	        FacebookShaing facebookClassObj = new FacebookShaing(getParent());
//	            	        facebookClassObj.signInWithFacebook(postParams);
//            			} else {
//            				Toast.makeText(getParent(), "No Network Connection Available", Toast.LENGTH_LONG).show();
//            			}
//            		   
//            	   }
////            	   else if(which == 1)
////            	   {
////            		   	
////            		   if (isNetworkAvailable()) {
////            			   Calendar dateObj = Calendar.getInstance();
////	            		   	String currenDateString = monthsNameArray[dateObj.get(Calendar.MONTH)] + " " +dateObj.get(Calendar.DAY_OF_MONTH) 
////	            						+ ", "+dateObj.get(Calendar.YEAR);
////	            	        String sharedText = namazTag +" namaz timing in "+ifarzSharedPref.getString(ConstantsClass.LOCATION_NAME, "")
////	            	        			+" at "+currenDateString+" is "+ namazTiming + "(Calculated by iFarz)";
////            				Twitt twitt = new Twitt(getParent(), getResources().getString(R.string.tweet_consumer_key)
////            						, getResources().getString(R.string.tweet_secret_key));
////            				twitt.shareToTwitter(sharedText);
////            			} else {
////            				Toast.makeText(getParent(), "No Network Connection Available", Toast.LENGTH_LONG).show();
////            			}
////            		   
////            	   }
//            	   else if(which == 2)
//            	   {
//            		   dialog.dismiss();
//            	   }
//           }
//    });
//    builder.create();
//    builder.show();
//	
//}
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
private class LoadObjetsThread extends AsyncTask<Void, Void, LocalDate>
{

	@Override
	protected LocalDate doInBackground(Void... arg0) {
		Chronology iso = ISOChronology.getInstanceUTC();
        Chronology hijri = IslamicChronology.getInstanceUTC();
        Calendar currentDate = Calendar.getInstance();
        LocalDate todayIso = new LocalDate(currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH), iso);
        LocalDate todayHijri = new LocalDate(todayIso.toDate(), hijri);
        return todayHijri;
//		
//		Chronology islamicCalender = IslamicChronology.getInstanceUTC();
//		DateTime dateTimeObj = new DateTime(islamicCalender);
//		return dateTimeObj;
	}
	@Override
	protected void onPostExecute(LocalDate result) {
		if(result!=null)
			textViewHeaderDate.setText(islamicMonthsNameArray[result.getMonthOfYear()] + " " +result.getDayOfMonth() 
				+ ", "+result.getYear());
		super.onPostExecute(result);
	}
}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onLocationChanged(Location location) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onProviderDisabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onProviderEnabled(String provider) {
//		// TODO Auto-generated method stub
//		
//	}

//	@Override
//	public void onStatusChanged(String provider, int status, Bundle extras) {
//		// TODO Auto-generated method stub
//		
//	}
}

