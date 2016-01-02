package com.bluehorntech.muslimsislamicapp;



import com.bluehorntech.muslimsislamicapp.BillingHelper;
import com.bluehorntech.muslimsislamicapp.BillingService;
import com.bluehorntech.muslimsislamicapp.quransection.LearnWordsActivity;
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
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.Activity;
import android.content.Context;
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

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Fragment9Donations extends Fragment implements OnClickListener   {
	// this Fragment will be called from MainActivity
	Button btn1, btn2, btn3;
	private AdView adView;
	private Context mContext=getActivity();
	private static final String TAG = "BillingService";
	public String AD_UNIT_ID="";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_donations_main, container, false);
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
        
        
        Button btn1= (Button)rootView.findViewById(R.id.button1);
	    btn1.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),DonationActivity.class);
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