package com.bluehorntech.muslimsislamicapp.timing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.ConstantsClass;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver implements OnCompletionListener
{
	private MediaPlayer mMediaPlayer;
	private SimpleDateFormat dateFormater;
	private SharedPreferences ifarzSharedPref;
	private int curretDay = 0;
	private Context actContext;
	 @Override
	 public void onReceive(Context context, Intent intent)
	 {
		 this.actContext = context;
		 ifarzSharedPref = context.getSharedPreferences(ConstantsClass.IFARZ_SHAREDPREFERENCE, context.MODE_PRIVATE);
		 //Bundle bundle = intent.getExtras();
		 showNotification(context,intent);
	   try {
	    
	    // if(bundle!=null && bundle.getInt(ALARM_TAG) == true)
	        {
	        	playSound(context, getAlarmUri());
	        }
	    } catch (Exception e) {
	    }
	   setAlarm(context);
	   
	 }
	 
	 private void showNotification(Context context, Intent dataIntent) {
		 try {
			 	Intent callActivityIntent = new Intent(context, TimingActivity.class);
			    PendingIntent pIntent = PendingIntent.getActivity(context, 0, callActivityIntent, 0);
			    NotificationCompat.Builder noti = new NotificationCompat.Builder(context)
			        .setContentTitle(getNamzName(dataIntent))
			        .setContentText(getNamzTime(dataIntent)).setSmallIcon(R.drawable.ic_launcher)
			        .setContentIntent(pIntent);
			    NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			    noti.setAutoCancel(true);
			    notificationManager.notify(3968, noti.build());	
		} catch (Exception e) {
		}
		 
	}

	private String getNamzTime(Intent dataIntent) {
		
		try {
			int namzCode = dataIntent.getExtras().getInt(ConstantsClass.ALARM_TAG);
			if(namzCode==1)
				return ifarzSharedPref.getString(ConstantsClass.FAJAR_TIMING_TAG, "");
			else if(namzCode==2)
				return ifarzSharedPref.getString(ConstantsClass.SHROUK_TIMIG_TAG, "");
			else if(namzCode==3)
				return ifarzSharedPref.getString(ConstantsClass.ZOHR_TIMING_TAG, "");
			else if(namzCode==4)
				return ifarzSharedPref.getString(ConstantsClass.ASR_TIMING_TAG, "");
			else if(namzCode==5)
				return ifarzSharedPref.getString(ConstantsClass.MAGRIB_TIMING_TAG, "");
			else if(namzCode==6)
				return ifarzSharedPref.getString(ConstantsClass.ISHA_TIMING_TAG, "");

		} catch (Exception e) {
		}
		return "";
	}

	private String getNamzName(Intent intent) {
		try {
			int namzCode = intent.getExtras().getInt(ConstantsClass.ALARM_TAG);
			if(namzCode == 1)
			{
				return ConstantsClass.FAJAR_TAG;
			}
			else if(namzCode == 2)
			{
				return ConstantsClass.SHROUK_TAG;
			}
			else if(namzCode == 3)
			{
				return ConstantsClass.ZOHR_TAG;
			}

			else if(namzCode == 4)
			{
				return ConstantsClass.ASR_TAG;
			}

			else if(namzCode == 5)
			{
				return ConstantsClass.MAGRIB_TAG;
			}

			else if(namzCode == 6)
			{
				return ConstantsClass.ISHA_TAG;
			}

		} catch (Exception e) {
		}
		return "";
	}

	private void playSound(Context context, Uri alert) {
	        mMediaPlayer = new MediaPlayer();
	        mMediaPlayer.setOnCompletionListener(this);
	        try {
	            mMediaPlayer.setDataSource(context, alert);
	            final AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
	            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
	                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
	                mMediaPlayer.prepare();
	                mMediaPlayer.start();
	                Log.e("=======", "PlayerStart");
	            }
	        } catch (IOException e) {
	        }
	    }
	 
	    private Uri getAlarmUri() {
	        Uri alert = Uri.parse("android.resource://com.accuretech.ifarz/"+R.raw.azaan);
	        if (alert == null)
	        {
	            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
	            if (alert == null) 
	            {
	                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
	            }
	        }
	        return alert;
	    }
	    
	    private void setAlarm(Context context) {
			dateFormater = new SimpleDateFormat("hh:mm a");
			Date curretDate = new Date();
			curretDate.setMinutes(curretDate.getMinutes()+ifarzSharedPref.getInt(ConstantsClass.ALARM_BEFORE, 0)+5);
			int namzTag = 0;
			if(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false) && curretDate.compareTo(parseDate(1,curretDay)) <= 0)
			{
				namzTag = 1;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false) && curretDate.compareTo(parseDate(2,curretDay)) <= 0)
			{
				namzTag = 2;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false) && curretDate.compareTo(parseDate(3,curretDay)) <= 0)
			{
				namzTag = 3;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false) && curretDate.compareTo(parseDate(4,curretDay)) <= 0)
			{
				namzTag = 4;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false) && curretDate.compareTo(parseDate(5,curretDay)) <= 0)
			{
				namzTag = 5;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false) && curretDate.compareTo(parseDate(6,curretDay)) <= 0)
			{
				namzTag = 6;
			}
			else if(ifarzSharedPref.getBoolean(ConstantsClass.FAJAR_TAG, false) 
					|| ifarzSharedPref.getBoolean(ConstantsClass.SHROUK_TAG, false)
					|| ifarzSharedPref.getBoolean(ConstantsClass.ZOHR_TAG, false)
					|| ifarzSharedPref.getBoolean(ConstantsClass.ASR_TAG, false)
					|| ifarzSharedPref.getBoolean(ConstantsClass.MAGRIB_TAG, false)
					|| ifarzSharedPref.getBoolean(ConstantsClass.ISHA_TAG, false))
			{
				setPrayerTimes(context);
				curretDay ++;
			}
			
			if(namzTag!=0)
			{
				Date alarmAt = parseDate(namzTag,curretDay);
				alarmAt.setMinutes(alarmAt.getMinutes()-ifarzSharedPref.getInt(ConstantsClass.ALARM_BEFORE, 0));
				AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				PendingIntent pendingIntent = PendingIntent.getBroadcast(context
						, 83968, new Intent(context, AlarmReceiver.class).putExtra(ConstantsClass.ALARM_TAG,namzTag)
						, PendingIntent.FLAG_CANCEL_CURRENT);
				alarmMgr.set(AlarmManager.RTC_WAKEUP,alarmAt.getTime(), pendingIntent);
			}
			else if(curretDay==1)
				setAlarm(context);

			
		}

		private Date parseDate(int i,int currentDay ) {
			String timeString = "";
			if(i==1)
				timeString = ifarzSharedPref.getString(ConstantsClass.FAJAR_TIMING_TAG, "00:00 AM");
			else if(i==2)
				timeString = ifarzSharedPref.getString(ConstantsClass.SHROUK_TIMIG_TAG, "00:00 AM");
			else if(i==3)
				timeString = ifarzSharedPref.getString(ConstantsClass.ZOHR_TIMING_TAG, "00:00 AM");
			else if(i==4)
				timeString = ifarzSharedPref.getString(ConstantsClass.ASR_TIMING_TAG, "00:00 AM");
			else if(i==5)
				timeString = ifarzSharedPref.getString(ConstantsClass.MAGRIB_TIMING_TAG, "00:00 AM");
			else if(i==6)
				timeString = ifarzSharedPref.getString(ConstantsClass.ISHA_TIMING_TAG, "00:00 AM");
			
			try {
				Date dateParesed = dateFormater.parse(timeString);
				
				Date datePar = new Date();
				datePar.setDate(datePar.getDate()+currentDay);
				datePar.setHours(dateParesed.getHours());
				datePar.setMinutes(dateParesed.getMinutes());
				return datePar;
			} catch (Exception e) {
			}
			return new Date();
		}

		private void setPrayerTimes(Context context) {
			
			PrayTime prayerTimeCalculator = new PrayTime();
			prayerTimeCalculator.setCalcMethod(ifarzSharedPref.getInt(ConstantsClass.CALCULATION_METHOD, 1));
			prayerTimeCalculator.setAsrJuristic(ifarzSharedPref.getInt(ConstantsClass.JUSRISTIC_METHOD, 0));
			prayerTimeCalculator.setAdjustHighLats(ifarzSharedPref.getInt(ConstantsClass.LATITUDE_METHOD, 0));
			prayerTimeCalculator.setTimeFormat(ifarzSharedPref.getInt(ConstantsClass.TIME_FORMATE, 1));
			Calendar curentCal = Calendar.getInstance();
			curentCal.add(Calendar.DAY_OF_MONTH, 1);
			ArrayList<String> parayersTimes = prayerTimeCalculator.getPrayerTimes(curentCal
					,  ifarzSharedPref.getFloat(ConstantsClass.LATI_KEY, 0.0f), ifarzSharedPref.getFloat(ConstantsClass.LONG_KEY, 0.0f)
					, ifarzSharedPref.getFloat(ConstantsClass.TIMEZONE_KEY, 0f));
			
			try {
				Editor prefEditor = ifarzSharedPref.edit();
				prefEditor.putString(ConstantsClass.FAJAR_TIMING_TAG,parayersTimes.get(0));
				prefEditor.putString(ConstantsClass.SHROUK_TIMIG_TAG,parayersTimes.get(1));
				prefEditor.putString(ConstantsClass.ZOHR_TIMING_TAG,parayersTimes.get(2));
				prefEditor.putString(ConstantsClass.ASR_TIMING_TAG,parayersTimes.get(3));
				prefEditor.putString(ConstantsClass.MAGRIB_TIMING_TAG,parayersTimes.get(4));
				prefEditor.putString(ConstantsClass.ISHA_TIMING_TAG,parayersTimes.get(6));
				prefEditor.commit();
			} catch (Exception e) {
			}
			
		}

		@Override
		public void onCompletion(MediaPlayer mp) {
			if(ifarzSharedPref.getBoolean(ConstantsClass.DUA_IS_ON, false))
			{
				 mMediaPlayer = new MediaPlayer();
			        try {
			            mMediaPlayer.setDataSource(actContext, getDuaSrc());
			            final AudioManager audioManager = (AudioManager) actContext.getSystemService(Context.AUDIO_SERVICE);
			            if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
			                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
			                mMediaPlayer.prepare();
			                mMediaPlayer.start();
			                Log.e("=======", "PlayerDuaStart");
			            }
			        } catch (IOException e) {
			        }
			}
			
		}

		private Uri getDuaSrc() {
			 Uri alert = Uri.parse("android.resource://com.accuretech.ifarz/"+R.raw.dua);
		        if (alert == null)
		        {
		            alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		            if (alert == null) 
		            {
		                alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
		            }
		        }
		        return alert;
		}
}