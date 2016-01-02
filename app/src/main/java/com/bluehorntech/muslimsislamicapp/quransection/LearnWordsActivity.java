package com.bluehorntech.muslimsislamicapp.quransection;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.DatabaseHandler;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
//import com.accuretech.ifarz.SplashScreenActivity;
import com.google.android.gms.ads.AdView;


public class LearnWordsActivity extends Activity implements OnClickListener{

	private Button btnBack;
	private Button btnTranslate;
	private ImageButton imageBtnNextWord;
	private ImageButton imageBtnPrevWord;
	private ImageView wordImageView;
	private int wordCurrentImage = 1;
	private int wordsImagesLimitLength = 348;
	private DatabaseHandler dbHandlerObj;
	private TextView wordTranslationTextview;
	
	public String AD_UNIT_ID= "";
	 private AdView adView;
	 
	 

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_words);
		getDBObj();
		setScreenViews();
		
		AD_UNIT_ID=this.getResources().getString(R.string.banner_id);
        
        adView = new AdView(this);
	    adView.setAdSize(AdSize.BANNER);
	    adView.setAdUnitId(AD_UNIT_ID);
	    
	    LinearLayout layout = (LinearLayout)findViewById(R.id.adlayout);
	    layout.addView(adView);
        
	    AdRequest adRequest = new AdRequest.Builder().build();

        // Start loading the ad in the background.
        adView.loadAd(adRequest);
		
	}

	private void getDBObj() {
		new Thread(new Runnable() 
		{
			@Override
			public void run() {
				dbHandlerObj = new DatabaseHandler(LearnWordsActivity.this);
			}
		}).run();
		
		
	}

	private void setScreenViews() {
//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
		
		wordImageView = (ImageView)findViewById(R.id.imageview_arabic_word);
		
		btnTranslate = (Button)findViewById(R.id.btn_translate_words);
		btnTranslate.setOnClickListener(this);
		imageBtnNextWord = (ImageButton)findViewById(R.id.imagebtn_forword_selection);
		imageBtnNextWord.setOnClickListener(this);
		imageBtnPrevWord = (ImageButton)findViewById(R.id.imagebtn_back_selection);
		imageBtnPrevWord.setOnClickListener(this);
		
		wordTranslationTextview = (TextView)findViewById(R.id.textView_translation);
		getImageBitmap();
	}

	@Override
	public void onClick(View clickedView) {
		
		if(clickedView == btnBack)
		{
			finish();
		}
		else if(clickedView == btnTranslate)
		{
			if(dbHandlerObj!=null)
			{
				String traslation = dbHandlerObj.getWordsInfo(wordCurrentImage);
				wordTranslationTextview.setText(traslation);
			}
		}
		else if(clickedView == imageBtnNextWord)
		{
			wordTranslationTextview.setText("");
			if(wordCurrentImage<wordsImagesLimitLength)
				wordCurrentImage++;
			else 
				wordCurrentImage = 1;
			
			getImageBitmap();
			
		}
		else if(clickedView == imageBtnPrevWord)
		{
			wordTranslationTextview.setText("");
			if(wordCurrentImage>0)
				wordCurrentImage--;
			else 
				wordCurrentImage = wordsImagesLimitLength;
			
			getImageBitmap();
		}
	}

	private void getImageBitmap() {
		InputStream bitmap=null;

		try 
		{
		    bitmap = getAssets().open(String.valueOf(wordCurrentImage)+".png");
		    Bitmap bit = BitmapFactory.decodeStream(bitmap);
		    wordImageView.setImageBitmap(bit);
		} catch (Exception e) {
		} 
		finally 
		{
		    if(bitmap!=null)
				try {
					bitmap.close();
				} catch (Exception e) {
				}
		}
		
	}

}