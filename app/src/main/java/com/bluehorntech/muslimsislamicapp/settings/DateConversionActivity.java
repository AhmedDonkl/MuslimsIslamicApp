package com.bluehorntech.muslimsislamicapp.settings;


import java.util.GregorianCalendar;

import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.chrono.IslamicChronology;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.DateCoverter;
import com.bluehorntech.muslimsislamicapp.dateconversion.HijriCalendar;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class DateConversionActivity extends Activity implements OnClickListener {

	private static final String YEAR_TAG = "year";
	private static final String MONTH_TAG = "month";
	private static final String DAY_TAG = "day";
	private EditText editTextYearHijri;
	private EditText editTextMonthHijri;
	private EditText editTextDayHijri;
	private EditText editTextYearGregorian;
	private EditText editTextMonthGregorian;
	private EditText editTextDayGregorian;
	private Button btnHijriTOGregorian;
	private Button btnGregorianTohijri;
	private Button btnBack;
	private AdView adView;
	public String AD_UNIT_ID= "";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_date_conversion);
		DateCoverter.writeIslamicDate();
		
		adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    AD_UNIT_ID= this.getResources().getString(R.string.banner_id);
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adlayout);
	    layout.addView(adView);
        
	    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
		
		setScreenViews();
	}

	private void setScreenViews() {

//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
		
		editTextYearHijri = (EditText)findViewById(R.id.edittext_hijri_year);
		editTextMonthHijri = (EditText)findViewById(R.id.edittext_hijri_month);
		editTextDayHijri = (EditText)findViewById(R.id.edittext_hijri_day);
		
		editTextYearGregorian = (EditText)findViewById(R.id.edittext_gregorian_year);
		editTextMonthGregorian = (EditText)findViewById(R.id.edittext_gregorian_month);
		editTextDayGregorian = (EditText)findViewById(R.id.edittext_gregorian_day);
		
		btnHijriTOGregorian = (Button)findViewById(R.id.btn_hijri_to_greg);
		btnHijriTOGregorian.setOnClickListener(this);
		btnGregorianTohijri = (Button)findViewById(R.id.btn_gregorian_to_hijri);
		btnGregorianTohijri.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View clickedView) {
		if(clickedView == btnBack)
		{
			finish();
			
		}
		else if(clickedView == btnHijriTOGregorian)
		{
			if(editTextYearHijri.getText().toString().trim().equals("") 
					|| editTextMonthHijri.getText().toString().trim().equals("")
					|| editTextDayHijri.getText().toString().trim().equals(""))
			{
				Toast.makeText(getParent(), "Please fill All Gregorians Date!", Toast.LENGTH_LONG).show();
			}
			else if(Integer.valueOf(editTextYearHijri.getText().toString().trim())<1 )
			{
				Toast.makeText(getParent(), "Please Enter correct Year!", Toast.LENGTH_LONG).show();
			}
			else if(Integer.valueOf(editTextMonthHijri.getText().toString().trim())<1|| Integer.valueOf(editTextMonthHijri.getText().toString().trim())>12)
			{
				Toast.makeText(getParent(), "Please Enter correct Month!", Toast.LENGTH_LONG).show();
			}
			else if(Integer.valueOf(editTextDayHijri.getText().toString().trim())<1|| Integer.valueOf(editTextDayHijri.getText().toString().trim())>31)
			{
				Toast.makeText(getParent(), "Please Enter correct day of month!", Toast.LENGTH_LONG).show();
			}
			else
			{
				try {
						
						 
						try {
							 Chronology hijri = IslamicChronology.getInstanceUTC();
						     DateTime dtISOw = new DateTime(hijri);
							 GregorianCalendar covertedDate =  dtISOw.withDate(Integer.valueOf(editTextYearHijri.getText().toString().trim())
									 , Integer.valueOf(editTextMonthHijri.getText().toString().trim())
									 , Integer.valueOf(editTextDayHijri.getText().toString().trim())).toGregorianCalendar();
							 if(covertedDate!=null)
							 {
								editTextYearGregorian.setText(String.valueOf(covertedDate.get(GregorianCalendar.YEAR)));
								editTextMonthGregorian.setText(String.valueOf(covertedDate.get(GregorianCalendar.MONTH)+1));
								editTextDayGregorian.setText(String.valueOf(covertedDate.get(GregorianCalendar.DAY_OF_MONTH)));
							 }
							
						} catch (Exception e) {
							Toast.makeText(getParent(), "Invalid date! Input date is less then limit date", Toast.LENGTH_LONG).show();
						}
					    					
						
				} catch (Exception e) {
				}
				
				
			}
		}
		else if(clickedView == btnGregorianTohijri)
		{
			if(editTextYearGregorian.getText().toString().trim().equals("") 
					|| editTextMonthGregorian.getText().toString().trim().equals("")
					|| editTextDayGregorian.getText().toString().trim().equals(""))
			{
				Toast.makeText(getParent(), "Please fill All Gregorians Date!", Toast.LENGTH_LONG).show();
			}
			else
			{
				
				try {
					Chronology iso = ISOChronology.getInstance();
			        Chronology hijri = IslamicChronology.getInstance();

			        LocalDate todayIso = new LocalDate(Integer.valueOf(editTextYearGregorian.getText().toString().trim())
			        		, Integer.valueOf( editTextMonthGregorian.getText().toString().trim())
			        		, Integer.valueOf(editTextDayGregorian.getText().toString().trim()), iso);
			        LocalDate todayHijri = null;
		        	todayHijri = new LocalDate(todayIso.toDate(), hijri);
		        	 if(todayHijri!=null)
				        {
							editTextYearHijri.setText(String.valueOf(todayHijri.getYear()));
							editTextMonthHijri.setText(String.valueOf(todayHijri.getMonthOfYear()));
							editTextDayHijri.setText(String.valueOf(todayHijri.getDayOfMonth()));
				        }
				} catch (Exception e) {
					Toast.makeText(getParent(), "Invalid date! Input date is less then limit date", Toast.LENGTH_LONG).show();
				}
		       
//				HijriCalendar hicriCalendar = new HijriCalendar(Integer.valueOf(editTextYearGregorian.getText().toString().trim())
//						,Integer.valueOf( editTextMonthGregorian.getText().toString().trim())
//						, Integer.valueOf(editTextDayGregorian.getText().toString().trim()));
		       
				
				
			}
		}
	}
	private JSONObject calcGregorianDate(JSONObject hijriCalInputJson,int yearHijToCalculate, int monthHijToCalculate, int dayHijToCalculate)
	{
		
		try {
			int startYear = hijriCalInputJson.getInt(YEAR_TAG);
			int startMonth = 1;
			int startDay = 1;
			boolean continueLoop = true;
			HijriCalendar hijriCal = new HijriCalendar();
			while (continueLoop) {
				hijriCal.gergrionToHijri(startYear, startMonth, startDay);
				if(hijriCal.getHijriYear()==yearHijToCalculate)
				{
					for (int i = 1; i <=31; i++) 
					{
						startDay = i;
						hijriCal.gergrionToHijri(startYear, startMonth, startDay);
						if(hijriCal.getHijriYear()==yearHijToCalculate 
								&& hijriCal.getHijriMonth()==monthHijToCalculate 
								&& hijriCal.getHijriDay()==dayHijToCalculate)
						{
							hijriCalInputJson.put(YEAR_TAG, startYear);
							hijriCalInputJson.put(MONTH_TAG, startMonth);
							hijriCalInputJson.put(DAY_TAG, startDay);
							Log.e("=======", String.valueOf(startYear)+"-"+ String.valueOf(startMonth)+"-" +String.valueOf(startDay));
							return hijriCalInputJson;
						}
						else if(startDay==31)
						{
							i = 1;
							if(startMonth == 12)
							{
								startMonth = 1;
								
							}
							else
							{
								startMonth++;
							}
						}
						else if(hijriCal.getHijriYear()>yearHijToCalculate)
							startYear--;
							
					}
				}
				else if(hijriCal.getHijriYear()<yearHijToCalculate)
				{
					startYear++;
				}
				else if(hijriCal.getHijriYear()>yearHijToCalculate)
				{
					startYear--;
				}
				
			}
		} catch (Exception e) {
		}
		
		return hijriCalInputJson;
	
	}
	private JSONObject calculateGregorianDate(JSONObject hijriCalInputJson,int yearHijToCalculate, int monthHijToCalculate, int dayHijToCalculate)
	{
		
		try {
			
			HijriCalendar hijriCal = new HijriCalendar(hijriCalInputJson.getInt(YEAR_TAG)
					, hijriCalInputJson.getInt(MONTH_TAG), hijriCalInputJson.getInt(DAY_TAG));
			
			if(hijriCal.getHijriDay()==dayHijToCalculate)
				{
					if(hijriCal.getHijriMonth()==monthHijToCalculate)
					{
						if(hijriCal.getHijriYear()==yearHijToCalculate)
						{
							return hijriCalInputJson;
						}
						else if(hijriCal.getHijriYear()<yearHijToCalculate)
						{
							
							hijriCalInputJson.put(YEAR_TAG, hijriCalInputJson.getInt(YEAR_TAG)+1);
							return calculateGregorianDate(hijriCalInputJson
									,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
						}
						else if(hijriCal.getHijriYear()>yearHijToCalculate)
						{
							if(hijriCalInputJson.getInt(YEAR_TAG)==1)
								hijriCalInputJson.put(YEAR_TAG, 5000);
							hijriCalInputJson.put(YEAR_TAG, hijriCalInputJson.getInt(YEAR_TAG)-1);
							return calculateGregorianDate(hijriCalInputJson
									,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
						}
					}
					else if(hijriCal.getHijriMonth()<monthHijToCalculate)
					{
						if(hijriCalInputJson.getInt(MONTH_TAG)==12)
							hijriCalInputJson.put(MONTH_TAG, 1);
						hijriCalInputJson.put(MONTH_TAG, hijriCalInputJson.getInt(MONTH_TAG)+1);
						return calculateGregorianDate(hijriCalInputJson
							,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
					}
					else if(hijriCal.getHijriMonth()>monthHijToCalculate)
					{
						if(hijriCalInputJson.getInt(MONTH_TAG)==1)
							hijriCalInputJson.put(MONTH_TAG, 12);
						hijriCalInputJson.put(MONTH_TAG, hijriCalInputJson.getInt(MONTH_TAG)-1);
						return calculateGregorianDate(hijriCalInputJson
							,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
					}
							
				}
				else if(hijriCal.getHijriDay()<dayHijToCalculate)
				{
					if(hijriCalInputJson.getInt(DAY_TAG)==31)
						hijriCalInputJson.put(DAY_TAG, 1);
					hijriCalInputJson.put(DAY_TAG, hijriCalInputJson.getInt(DAY_TAG)+1);
					return calculateGregorianDate(hijriCalInputJson
						,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
				}
				else if(hijriCal.getHijriDay()>dayHijToCalculate)
				{
					if(hijriCalInputJson.getInt(DAY_TAG)==1)
						hijriCalInputJson.put(DAY_TAG, 31);
					hijriCalInputJson.put(DAY_TAG, hijriCalInputJson.getInt(DAY_TAG)-1);
					return calculateGregorianDate(hijriCalInputJson
						,yearHijToCalculate,monthHijToCalculate,dayHijToCalculate);
				}
				
		} catch (Exception e) {
		}
		return hijriCalInputJson;
			
		
	}

}
