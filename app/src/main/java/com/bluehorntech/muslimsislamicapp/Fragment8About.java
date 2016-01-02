package com.bluehorntech.muslimsislamicapp;

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

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Fragment8About extends Fragment {
	// this Fragment will be called from MainActivity
	private AdView adView;
	public String AD_UNIT_ID="";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_about, container, false);
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

//        Button btn1= (Button)rootView.findViewById(R.id.btn_learn_words);
//	    btn1.setOnClickListener(new OnClickListener() 
//	    {   public void onClick(View v) 
//	        {   
//	            Intent intent = new Intent(getActivity(),LearnWordsActivity.class);
//	                //startActivity(intent);
//	                startActivity(intent);      
//	                //finish();
//	        }
//
//	    });
        
//        Button btn1= (Button)rootView.findViewById(R.id.btn_donate);
//	    btn1.setOnClickListener(new OnClickListener() 
//	    {   public void onClick(View v) 
//	        {   
//	    	Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/cgi-bin/webscr"));
//            startActivity(browserIntent);
//	        }
//
//	    });
	    
	    
        ImageView img = (ImageView)rootView.findViewById(R.id.imageView3);
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://bluehorntech.com"));
                startActivity(intent);
            }
        });
        
        
        ImageView img2 = (ImageView)rootView.findViewById(R.id.imageView2);
        img2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            	Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","info@bluehorntech.com", null));
            startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });
	   
        
        return rootView;
    }

	
}