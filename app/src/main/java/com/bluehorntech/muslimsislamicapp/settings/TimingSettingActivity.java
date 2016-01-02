package com.bluehorntech.muslimsislamicapp.settings;


import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


public class TimingSettingActivity extends Activity implements OnClickListener{
	

	private Button btnBack;
	private LinearLayout layoutItnaAshari;
	private ImageView ivCheckIntaAsh;
	private LinearLayout layoutKarachiUniv;
	private ImageView ivCheckKarachiUniv;
	private LinearLayout layoutIslamicSoc;
	private ImageView ivCheckIslamicSoc;
	private LinearLayout layoutMusliWorld;
	private ImageView ivCheckMusliWorld;
	private LinearLayout layoutUmmAl;
	private ImageView ivCheckUmmAl;
	private LinearLayout layoutEgyptAuth;
	private ImageView ivCheckEgyptAuth;
	private LinearLayout layoutTehranUniv;
	private ImageView ivCheckTehranUniv;
	private LinearLayout layoutShafiiJur;
	private ImageView ivCheckShafiiJur;
	private LinearLayout layoutHanfiJur;
	private ImageView ivCheckHanfiJur;
	private LinearLayout layoutNoAdjust;
	private ImageView ivCheckNoAdjust;
	private LinearLayout layoutMiddleNight;
	private ImageView ivCheckMiddleNight;
	private LinearLayout layout7ThNight;
	private ImageView ivCheck7ThNight;
	private LinearLayout layout24Hour;
	private ImageView ivCheck24Hour;
	private LinearLayout layout12Hour;
	private ImageView ivCheck12Hour;
	private Button btnSaveSetting;
	private SharedPreferences ifarzSharedPref;
    
	private AdView adView;
	public String AD_UNIT_ID= "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timing_setting);
		AD_UNIT_ID=this.getResources().getString(R.string.banner_id);
		ifarzSharedPref = getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, MODE_PRIVATE);
		
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
		
		layoutItnaAshari = (LinearLayout)findViewById(R.id.layout_itna_ash);
		layoutItnaAshari.setOnClickListener(this);
		ivCheckIntaAsh = (ImageView)findViewById(R.id.imageview_check_itna_ash);
		
		layoutKarachiUniv = (LinearLayout)findViewById(R.id.layout_karchi_univ);
		layoutKarachiUniv.setOnClickListener(this);
		ivCheckKarachiUniv= (ImageView)findViewById(R.id.imageview_check_karah_univ);
		
		layoutIslamicSoc = (LinearLayout)findViewById(R.id.layout_islamic_soc);
		layoutIslamicSoc.setOnClickListener(this);
		ivCheckIslamicSoc= (ImageView)findViewById(R.id.imageview_check_islamic_soc);
		
		layoutMusliWorld = (LinearLayout)findViewById(R.id.layout_musl_world);
		layoutMusliWorld.setOnClickListener(this);
		ivCheckMusliWorld= (ImageView)findViewById(R.id.imageview_check_musl_world);
		
		layoutUmmAl = (LinearLayout)findViewById(R.id.layout_umm_al);
		layoutUmmAl.setOnClickListener(this);
		ivCheckUmmAl= (ImageView)findViewById(R.id.imageview_check_umm_al);
		
		layoutEgyptAuth = (LinearLayout)findViewById(R.id.layout_egypt_authr);
		layoutEgyptAuth.setOnClickListener(this);
		ivCheckEgyptAuth= (ImageView)findViewById(R.id.imageview_check_egypt_authr);
		
		layoutTehranUniv = (LinearLayout)findViewById(R.id.layout_univ_tehran);
		layoutTehranUniv.setOnClickListener(this);
		ivCheckTehranUniv= (ImageView)findViewById(R.id.imageview_check_univ_of_terhan);
		
		layoutShafiiJur = (LinearLayout)findViewById(R.id.layout_shafii);
		layoutShafiiJur.setOnClickListener(this);
		ivCheckShafiiJur= (ImageView)findViewById(R.id.imageview_check_shafii);

		layoutHanfiJur = (LinearLayout)findViewById(R.id.layout_hanfi);
		layoutHanfiJur.setOnClickListener(this);
		ivCheckHanfiJur= (ImageView)findViewById(R.id.imageview_check_hanfi);
		
		layoutNoAdjust = (LinearLayout)findViewById(R.id.layout_no_adju);
		layoutNoAdjust.setOnClickListener(this);
		ivCheckNoAdjust= (ImageView)findViewById(R.id.imageview_check_no_adju);
		
		layoutMiddleNight = (LinearLayout)findViewById(R.id.layout_middle_of_night);
		layoutMiddleNight.setOnClickListener(this);
		ivCheckMiddleNight = (ImageView)findViewById(R.id.imageview_check_middle_of_night);
		
		layout7ThNight = (LinearLayout)findViewById(R.id.layout_7th_night);
		layout7ThNight.setOnClickListener(this);
		ivCheck7ThNight = (ImageView)findViewById(R.id.imageview_check_7th_night);
		
		layout24Hour = (LinearLayout)findViewById(R.id.layout_24_hour);
		layout24Hour.setOnClickListener(this);
		ivCheck24Hour = (ImageView)findViewById(R.id.imageview_check_24_hour);
		
		layout12Hour = (LinearLayout)findViewById(R.id.layout_12_hour);
		layout12Hour.setOnClickListener(this);
		ivCheck12Hour = (ImageView)findViewById(R.id.imageview_check_12_hour);
		
		btnSaveSetting = (Button)findViewById(R.id.btn_save_timing_setting);
		btnSaveSetting.setOnClickListener(this);
		
		setCalcMethodCheck();
	}

	

	private void setCalcMethodCheck() {
		int calMethod = ifarzSharedPref.getInt(ConstantsClass.CALCULATION_METHOD, 1);
		if(calMethod == 0)
			ivCheckIntaAsh.setVisibility(View.VISIBLE);
		else if(calMethod == 1)
			ivCheckKarachiUniv.setVisibility(View.VISIBLE);
		else if(calMethod == 2)
			ivCheckIslamicSoc.setVisibility(View.VISIBLE);
		else if(calMethod == 3)
			ivCheckMusliWorld.setVisibility(View.VISIBLE);
		else if(calMethod == 4)
			ivCheckUmmAl.setVisibility(View.INVISIBLE);
		else if(calMethod == 5)
			ivCheckEgyptAuth.setVisibility(View.VISIBLE);
		else if(calMethod == 6)
			ivCheckTehranUniv.setVisibility(View.VISIBLE);
		
		int jusristicCalMethod = ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0);
		if(jusristicCalMethod == 0)
			ivCheckShafiiJur.setVisibility(View.VISIBLE);
		else if(jusristicCalMethod == 1)
			ivCheckHanfiJur.setVisibility(View.VISIBLE);
		
		int latitudeMethod = ifarzSharedPref.getInt(ConstantsClass.LATITUDE_METHOD, 0);
		if(latitudeMethod == 0)
			ivCheckNoAdjust.setVisibility(View.VISIBLE);
		else if(latitudeMethod == 1)
			ivCheckMiddleNight.setVisibility(View.VISIBLE);
		else if(latitudeMethod == 2)
			ivCheck7ThNight.setVisibility(View.VISIBLE);
		
		int timeFormate = ifarzSharedPref.getInt(ConstantsClass.TIME_FORMATE, 1);
		if(timeFormate == 0)
			ivCheck24Hour.setVisibility(View.VISIBLE);
		else if(timeFormate == 1)
			ivCheck12Hour.setVisibility(View.VISIBLE);
		

	}



	@Override
	public void onClick(View clickedView) {
		
		if(clickedView == btnBack)
		{
			finish();
			
		}
		else if(clickedView == btnSaveSetting)
		{
			try {
				Editor prefereceEditor = ifarzSharedPref.edit();
				prefereceEditor.putInt(ConstantsClass.CALCULATION_METHOD, getCalculationMethod());
				prefereceEditor.putInt(ConstantsClass.JUSRISTIC_METHOD, getJuristicMethods());
				prefereceEditor.putInt(ConstantsClass.LATITUDE_METHOD, getLatitudeMethods());
				prefereceEditor.putInt(ConstantsClass.TIME_FORMATE, getTimeFormate());
				prefereceEditor.commit();
				finish();
			} catch (Exception e) {
			}
			
			
		}
		else if(clickedView == layoutItnaAshari)
		{
			resetCalculatedMathodChecks();
			ivCheckIntaAsh.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutKarachiUniv)
		{
			resetCalculatedMathodChecks();
			ivCheckKarachiUniv.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutIslamicSoc)
		{
			resetCalculatedMathodChecks();
			ivCheckIslamicSoc.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutMusliWorld)
		{
			resetCalculatedMathodChecks();
			ivCheckMusliWorld.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutUmmAl)
		{
			resetCalculatedMathodChecks();
			ivCheckUmmAl.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutEgyptAuth)
		{
			resetCalculatedMathodChecks();
			ivCheckEgyptAuth.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutTehranUniv)
		{
			resetCalculatedMathodChecks();
			ivCheckTehranUniv.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutShafiiJur)
		{
			resetAserChecks();
			ivCheckShafiiJur.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutHanfiJur)
		{
			resetAserChecks();
			ivCheckHanfiJur.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutNoAdjust)
		{
			resetAdjustChecks();
			ivCheckNoAdjust.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layoutMiddleNight)
		{
			resetAdjustChecks();
			ivCheckMiddleNight.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layout7ThNight)
		{
			resetAdjustChecks();
			ivCheck7ThNight.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layout24Hour)
		{
			resettimeFormateChacks();
			ivCheck24Hour.setVisibility(View.VISIBLE);
		}
		else if(clickedView == layout12Hour)
		{
			resettimeFormateChacks();
			ivCheck12Hour.setVisibility(View.VISIBLE);
		}
	}



	private int getTimeFormate() {
		if(ivCheck24Hour.getVisibility()==View.VISIBLE)
			return 0;
		if(ivCheck12Hour.getVisibility()==View.VISIBLE)
			return 1;
		return -1;
	}



	private int getLatitudeMethods() {
		if(ivCheckNoAdjust.getVisibility()==View.VISIBLE)
			return 0;
		if(ivCheckMiddleNight.getVisibility()==View.VISIBLE)
			return 1;
		if(ivCheck7ThNight.getVisibility()==View.VISIBLE)
			return 2;
		return -1;
	}



	private int getJuristicMethods() {
		if(ivCheckShafiiJur.getVisibility()==View.VISIBLE)
			return 0;
		if(ivCheckHanfiJur.getVisibility()==View.VISIBLE)
			return 1;
		return -1;
	}



	private int getCalculationMethod() {

		if(ivCheckIntaAsh.getVisibility()==View.VISIBLE)
			return 0;
		if(ivCheckKarachiUniv.getVisibility()==View.VISIBLE)
			return 1;
		if(ivCheckIslamicSoc.getVisibility()==View.VISIBLE)
			return 2;
		if(ivCheckMusliWorld.getVisibility()==View.VISIBLE)
			return 3;
		if(ivCheckUmmAl.getVisibility()==View.VISIBLE)
			return 4;
		if(ivCheckEgyptAuth.getVisibility()==View.VISIBLE)
			return 5;
		if(ivCheckTehranUniv.getVisibility()==View.VISIBLE)
			return 6;
		return -1;
	}



	private void resettimeFormateChacks() {
		ivCheck24Hour.setVisibility(View.INVISIBLE);
		ivCheck12Hour.setVisibility(View.INVISIBLE);
	}



	private void resetAdjustChecks() {
		ivCheckNoAdjust.setVisibility(View.INVISIBLE);
		ivCheckMiddleNight.setVisibility(View.INVISIBLE);
		ivCheck7ThNight.setVisibility(View.INVISIBLE);
		
	}



	private void resetAserChecks() {
		ivCheckShafiiJur.setVisibility(View.INVISIBLE);
		ivCheckHanfiJur.setVisibility(View.INVISIBLE);
	}



	private void resetCalculatedMathodChecks() {
		
		ivCheckIntaAsh.setVisibility(View.INVISIBLE);
		ivCheckKarachiUniv.setVisibility(View.INVISIBLE);
		ivCheckIslamicSoc.setVisibility(View.INVISIBLE);
		ivCheckMusliWorld.setVisibility(View.INVISIBLE);
		ivCheckUmmAl.setVisibility(View.INVISIBLE);
		ivCheckEgyptAuth.setVisibility(View.INVISIBLE);
		ivCheckTehranUniv.setVisibility(View.INVISIBLE);
	}

	
	

}