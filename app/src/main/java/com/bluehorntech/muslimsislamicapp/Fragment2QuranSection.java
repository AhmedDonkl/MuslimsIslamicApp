package com.bluehorntech.muslimsislamicapp;



import java.io.File;

import com.bluehorntech.muslimsislamicapp.quransection.LearnWordsActivity;
import com.bluehorntech.muslimsislamicapp.quransection.QuezActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.*;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Fragment2QuranSection extends Fragment implements OnClickListener {
	// this Fragment will be called from MainActivity
	 public String AD_UNIT_ID= "";
	 private AdView adView;
	@SuppressLint("NewApi") @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.quranic_section, container, false);
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

        Button btn1= (Button)rootView.findViewById(R.id.btn_learn_words);
	    btn1.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),LearnWordsActivity.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    
	    Button btn2= (Button)rootView.findViewById(R.id.btn_quran_quz);
	    btn2.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),QuezActivity.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    Button btn3= (Button)rootView.findViewById(R.id.btn_view_quran);
	    btn3.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),showquran.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    
	    Button btn4= (Button)rootView.findViewById(R.id.btn_listen_quran);
	    btn4.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),SurahRecitation.class);
	                //startActivity(intent);
	                startActivity(intent);      
	                //finish();
	        }

	    });
	    
	    
        
        return rootView;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
}