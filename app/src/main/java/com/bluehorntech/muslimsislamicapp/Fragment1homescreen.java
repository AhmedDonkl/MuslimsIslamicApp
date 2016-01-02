package com.bluehorntech.muslimsislamicapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Fragment1homescreen extends Fragment {
	// this Fragment will be called from MainActivity
	public Fragment1homescreen(){}
	private boolean doubleBackToExitPressedOnce = false;
	private AdView adView;
	public String AD_UNIT_ID= "";
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		
		  
        View rootView = inflater.inflate(R.layout.home_screen, container, false);
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
        
//        rootView.setFocusableInTouchMode(true);
//        rootView.requestFocus();
//        rootView.setOnKeyListener(new View.OnKeyListener() {
//                @Override
//                public boolean onKey(View v, int keyCode, KeyEvent event) {
//                    Log.i("", "keyCode: " + keyCode);
//                    if( keyCode == KeyEvent.KEYCODE_BACK ) {
//                            Log.i("", "onKey Back listener is working!!!");
//                        getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                        return true;
//                    } else {
//                        return false;
//                    }
//                }
//        });
				
        
        return rootView;
    }
	
	
	
//	@Override
//	public void onBackPressed() {
//	    if (doubleBackToExitPressedOnce) {
//	       super.onBackPressed();
//	        return;
//	    }
//	    this.doubleBackToExitPressedOnce = true;
//	    Toast.makeText(getActivity(), "Press again to Exit", Toast.LENGTH_SHORT).show();
//	}
}