package com.bluehorntech.muslimsislamicapp.settings;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ToggleButton;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class AlarmSettingActivity extends Activity implements OnClickListener{

	private EditText editTextAlarmBefore;
	private ToggleButton toggleBtn;
	private SharedPreferences ifarzSharedPref;
	private Button btSaveSetting;
	private Button btnBack;
	
	private AdView adView;
	public String AD_UNIT_ID="";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		ifarzSharedPref = getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, MODE_PRIVATE);
		setContentView(R.layout.activity_alarm_setting);
		AD_UNIT_ID= this.getResources().getString(R.string.banner_id);
		adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
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
		
		editTextAlarmBefore = (EditText)findViewById(R.id.edittext_alar_before);
		toggleBtn = (ToggleButton)findViewById(R.id.toggleBtnDua);
		btSaveSetting = (Button)findViewById(R.id.btn_save_other_setting);
		btSaveSetting.setOnClickListener(this);
		
		try {
			editTextAlarmBefore.setText(String.valueOf(ifarzSharedPref.getInt(ConstantsClass.ALARM_BEFORE, 0)));
			toggleBtn.setChecked(ifarzSharedPref.getBoolean(ConstantsClass.DUA_IS_ON, false));
		} catch (Exception e) {
		}
	}

	

	@Override
	public void onClick(View cleckedView) {
		if(cleckedView == btnBack)
		{
			finish();
		}
		else
		if(cleckedView == btSaveSetting)
		{
			try {
				if(toggleBtn.isChecked())
				{
					ifarzSharedPref.edit().putBoolean(ConstantsClass.DUA_IS_ON, true).commit();
				}
				else 
				{
					ifarzSharedPref.edit().putBoolean(ConstantsClass.DUA_IS_ON, false).commit();
				}
			} catch (Exception e) {
			}
			try {
				int  alarmBefor = 0; 
				if(!editTextAlarmBefore.getText().toString().trim().equals(""))
					alarmBefor = Integer.valueOf(editTextAlarmBefore.getText().toString().trim());
				ifarzSharedPref.edit().putInt(ConstantsClass.ALARM_BEFORE, alarmBefor).commit();
			} catch (Exception e) {
			}
			
		}
		
	}

}