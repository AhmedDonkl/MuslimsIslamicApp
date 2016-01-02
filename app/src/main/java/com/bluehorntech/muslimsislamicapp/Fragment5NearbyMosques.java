package com.bluehorntech.muslimsislamicapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;
import com.bluehorntech.muslimsislamicapp.common.CustomHttpClient;
import com.bluehorntech.muslimsislamicapp.common.GPSTracker;
import com.bluehorntech.muslimsislamicapp.mosquefinder.MapDirectionDisplayActivity;
import com.bluehorntech.muslimsislamicapp.mosquefinder.MosqueFinderActivity;
import com.bluehorntech.muslimsislamicapp.mosquefinder.MosqueFinderTabGroupActivity;
import com.bluehorntech.muslimsislamicapp.mosquefinder.MosqueViewHolder;
import com.bluehorntech.muslimsislamicapp.quransection.LearnWordsActivity;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


@SuppressLint("NewApi") @TargetApi(Build.VERSION_CODES.HONEYCOMB) public class Fragment5NearbyMosques extends Fragment implements OnClickListener {
	
	
	
	
	public Fragment5NearbyMosques(){}
	
	private AdView adView;
	public String AD_UNIT_ID="";
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.nearby_mosques, container, false);
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
         
       
        Button btn1= (Button)rootView.findViewById(R.id.btn_nearbymosques);
	    btn1.setOnClickListener(new OnClickListener() 
	    {   public void onClick(View v) 
	        {   
	            Intent intent = new Intent(getActivity(),MosqueFinderTabGroupActivity.class);
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

