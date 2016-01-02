package com.bluehorntech.muslimsislamicapp.settings;

//import java.util.Locale;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//
//import com.accuretech.ifarz.R;
//import com.accuretech.ifarz.SplashScreenActivity;
//import com.accuretech.ifarz.more.MoreTabGroupActivity;
//
//public class ChangeLang extends Activity implements OnClickListener{
//
//	
//	
//	
//	private Button btnBack;
//	private Button setEnbtn;
//	private Button setFrbtn;
//	private Button setArbtn;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_chagelang);
//		
//		setScreenViews();
//	}
//
//	private void setScreenViews() {
//		
//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
//	
//		
//		setEnbtn = (Button) findViewById(R.id.btn_changelang_english); 
//		setEnbtn.setOnClickListener(this);
//		
//		setFrbtn = (Button) findViewById(R.id.btn_changelang_french); 
//		setFrbtn.setOnClickListener(this);
//		
//		setArbtn = (Button) findViewById(R.id.btn_changelang_arabic); 
//		setArbtn.setOnClickListener(this);
//	}
//
//	@Override
//	public void onClick(View clickedView) {
//		if(clickedView == btnBack)
//		{
//			finish();
//			
//		}
//		
//		
//		else if(clickedView == setFrbtn)
//		{
//			
//			            Locale locale_fr = new Locale("fr"); 
//			            Locale.setDefault(locale_fr);
//			            Configuration config_fr = new Configuration();
//			            config_fr.locale = locale_fr;
//			            getBaseContext().getResources().updateConfiguration(config_fr, getBaseContext().getResources().getDisplayMetrics());
//			        
//			            Intent intent = new Intent(ChangeLang.this, SplashScreenActivity.class);
//			            startActivity(intent);
//			            finish();
//			
//			
//		}
//		
//		else if(clickedView == setArbtn)
//		{
//			
//			            Locale locale_ar = new Locale("ar"); 
//			            Locale.setDefault(locale_ar);
//			            Configuration config_ar = new Configuration();
//			            config_ar.locale = locale_ar;
//			            getBaseContext().getResources().updateConfiguration(config_ar, getBaseContext().getResources().getDisplayMetrics());
//			        
//			            Intent intent = new Intent(ChangeLang.this, SplashScreenActivity.class);
//			            startActivity(intent);
//			            finish();
//			
//			
//		}
//		
//		else if(clickedView == setEnbtn)
//		{
//			
//			            Locale locale_en = new Locale("en"); 
//			            Locale.setDefault(locale_en);
//			            Configuration config_en = new Configuration();
//			            config_en.locale = locale_en;
//			            getBaseContext().getResources().updateConfiguration(config_en, getBaseContext().getResources().getDisplayMetrics());
//			        
//			            Intent intent = new Intent(ChangeLang.this, SplashScreenActivity.class);
//			            startActivity(intent);
//			            finish();
//			
//			
//		}
//		
//	}
//
//}
