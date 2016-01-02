package com.bluehorntech.muslimsislamicapp;


import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.quransection.LearnWordsActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
//import com.accuretech.ifarz.SplashScreenActivity;
import com.google.android.gms.ads.AdView;


public class SurahRecitation extends Activity {

	
	
	public String AD_UNIT_ID= "";
	 private AdView adView;
	 
	 private MediaPlayer mp;
	 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surah_recitation);
		AD_UNIT_ID=this.getResources().getString(R.string.banner_id);
		
		setVolumeControlStream(AudioManager.STREAM_MUSIC);
        
        adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adlayout);
	    layout.addView(adView);
        
	    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
		
        Button btn1= (Button)findViewById(R.id.btn_surah_fatiha);
	    btn1.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(SurahRecitation.this,SurahFatiha.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    Button btn2= (Button)findViewById(R.id.btn_surah_mulk);
	    btn2.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(SurahRecitation.this,SurahMulk.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    Button btn3= (Button)findViewById(R.id.btn_surah_rahman);
	    btn3.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(SurahRecitation.this,SurahRahman.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    Button btn4= (Button)findViewById(R.id.btn_surah_waqia);
	    btn4.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(SurahRecitation.this,SurahWaqia.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    
	    Button btn5= (Button)findViewById(R.id.btn_surah_yaseen);
	    btn5.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(SurahRecitation.this,SurahYaseen.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
        
        
	}



	


}