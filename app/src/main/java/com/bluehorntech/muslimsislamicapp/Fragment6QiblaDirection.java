package com.bluehorntech.muslimsislamicapp;

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


import java.util.List;

import com.bluehorntech.muslimsislamicapp.common.GPSTracker;
import com.bluehorntech.muslimsislamicapp.qibladirection.DirectionView;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("NewApi") public class Fragment6QiblaDirection extends Fragment implements LocationListener , android.view.View.OnClickListener {
	// this Fragment will be called from MainActivity
	
	private GPSTracker gpsTracker;
	private double latitude;
	private double longitude;
	private LinearLayout direcCantainer;
	private DirectionView dirViw;
	private SensorManager mySensorManager;
	private boolean sersorRunning = false;
	private float newAngel = 0;
	protected float screenOrientation = 0;
	private ProgressDialog progressDialog;
	private TextView textViewIdication;
	private Button btnBack;
	
	private AdView adView;
	public String AD_UNIT_ID="";
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_qibla_find, container, false);
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
         
        //setScreenViews();
		direcCantainer = (LinearLayout)rootView.findViewById(R.id.cantainer_layout);
		dirViw = new DirectionView(getActivity());
		direcCantainer.addView(dirViw);
		dirViw.invalidate();
		progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Getting your Location Via GPRS OR Network");
        progressDialog.show();
		gpsTracker = new GPSTracker(getActivity(),Fragment6QiblaDirection.this);
		setScreenOretationListener();
	    
	   
        return rootView;
    }

	
	
//	private void setScreenViews() {
//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
//		
//		textViewIdication = (TextView)findViewById(R.id.textview_oriantation_notifiation);
//		
//		
//	}

	private void setScreenOretationListener() {
		mySensorManager = (SensorManager)getActivity().getSystemService(Context.SENSOR_SERVICE);
		
        List<Sensor> mySensors = mySensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        
        if(mySensors.size() > 0){
        	mySensorManager.registerListener(mySensorEventListener, mySensors.get(0), SensorManager.SENSOR_DELAY_GAME);
        	sersorRunning = true;
        	
        }
//        else{
//        	textViewIdication.setText("Compass will not work on this device. No ORIENTATION Sensor found at this device.");
//        	AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
//        	alert.setTitle("Error!");
//        	alert.setMessage("No ORIENTATION Sensor found at this device. ");
//        	alert.setPositiveButton("Ok", new OnClickListener() {
//				public void onClick(DialogInterface arg0, int arg1) {
//					arg0.dismiss();
//				}
//
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					
//				}
//			});
//        	alert.show();
//        	sersorRunning = false;
//        	
//        }
		
	}
	
	
	public void onBackPressed() {
		finish();
		onBackPressed();
		
	}

	public void onResume() {
		
		try {
			gpsTracker.loctionUpdate();
		} catch (Exception e) {
		}
		
		super.onResume();
	}
	
	@Override
	public void onPause() {
		try {
			gpsTracker.stopUsingGPS();
			gpsTracker.stopSelf();
			
		} catch (Exception e) {
		}
		super.onPause();
	}
	
	public void finish() {
		try {
			gpsTracker.stopUsingGPS();
			gpsTracker.stopSelf();
//			if(sersorrunning){
//				mySensorManager.unregisterListener(mySensorEventListener);	
//			}
		} catch (Exception e) {
		}
		
		finish();
	}

	@Override
	public void onLocationChanged(Location location) {
		
		if(location!=null)//&& countChnageLocation<=2)
		{
			if(progressDialog!=null)
				progressDialog.dismiss();
			latitude = location.getLatitude();
			longitude = location.getLongitude();
			double logiTudeCa = 39.82613;
		    double latiTudeCa = 21.44812;
		    newAngel = (float) (Math.atan2((logiTudeCa-(longitude)),(latiTudeCa- (latitude)))*(180/Math.PI));//(float) Math.toDegrees(Math.atan2(39.82613-172.26563, 21.44812-8.05923));;//lon,lat
		    if(newAngel < 0){
		    	newAngel += 360;
		     }
		   // Toast.makeText(getParent(), String.valueOf(newAngel), Toast.LENGTH_LONG).show();
			dirViw.updateDirection((360-screenOrientation)+(newAngel));
		
		}
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	private SensorEventListener mySensorEventListener = new SensorEventListener(){

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			screenOrientation = event.values[0];
			Log.e("+++++++++++", String.valueOf(screenOrientation));
			dirViw.updateDirection((360-screenOrientation)+(newAngel));
		}
    };

	@Override
	public void onClick(View clickedView) {
		
		if(clickedView == btnBack)
		{
			finish();
			
		}
		
	}
}