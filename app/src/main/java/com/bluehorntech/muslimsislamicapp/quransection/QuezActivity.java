package com.bluehorntech.muslimsislamicapp.quransection;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Currency;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bluehorntech.muslimsislamicapp.R;
import com.bluehorntech.muslimsislamicapp.common.DatabaseHandler;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
//import com.accuretech.ifarz.R;
import com.google.android.gms.ads.AdView;


public class QuezActivity extends Activity implements OnClickListener{

	private Button btnBack;
	private ImageView wordImageView;
	private int wordCurrentImage = 1;
	private int wordsImagesLimitLength = 348;
	private DatabaseHandler dbHandlerObj;
	private TextView selecctionResultTv;
	private Button btnQuizResult;
	private Random randuGenerator;
	private Button btnMeaningOne;
	private Button btnMeaningTwo;
	private Button btnMeaningThree;
	private String correctWordMeaning;
	private int quesCount = 0;
	private int quesCountLimit = 20;
	private int correctAns = 0;
	
	public String AD_UNIT_ID= "";
	 private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		randuGenerator = new Random();
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
				dbHandlerObj = new DatabaseHandler(QuezActivity.this);
			}
		}).run();
		
		
	}

	private void setScreenViews() {
//		btnBack = (Button)findViewById(R.id.bt_back);
//		btnBack.setOnClickListener(this);
//		btnBack.setVisibility(View.VISIBLE);
//		btnBack.setText(getResources().getString(R.string.finish));
		
		wordImageView = (ImageView)findViewById(R.id.imageview_arabic_word);
		
//		btnQuizResult = (Button)findViewById(R.id.btn_quz_result);
//		btnQuizResult.setOnClickListener(this);
		
		btnMeaningOne = (Button)findViewById(R.id.btn_meaning_option_one);
		btnMeaningOne.setOnClickListener(this);
		btnMeaningTwo = (Button)findViewById(R.id.btn_meaning_option_two);
		btnMeaningTwo.setOnClickListener(this);
		btnMeaningThree = (Button)findViewById(R.id.btn_meaning_option_three);
		btnMeaningThree.setOnClickListener(this);
		
		selecctionResultTv = (TextView)findViewById(R.id.textView_result_info);
		getImageBitmap();
		setWordMeanings();
	}

	

	@Override
	public void onClick(View clickedView) {
		
		if(clickedView == btnBack)
		{
			//showResult();
			
		}
		else if(clickedView == btnQuizResult)
		{
			
			//showResult();
			
		}
		else if(clickedView == btnMeaningOne)
		{
			if(checkQuesLimit())
			{
				if(btnMeaningOne.getText().toString().equals(correctWordMeaning))
				{
					correctAns++;
					selecctionResultTv.setTextColor(Color.GREEN);
					selecctionResultTv.setText("Right");
				}
				else
				{
					selecctionResultTv.setTextColor(Color.RED);
					selecctionResultTv.setText("Wrong");
				}
				quesCount++;
				setNextWord();
			}
		}
		else if(clickedView == btnMeaningTwo)
		{
			if(checkQuesLimit())
			{
				if(btnMeaningTwo.getText().toString().equals(correctWordMeaning))
				{
					correctAns++;
					selecctionResultTv.setTextColor(Color.GREEN);
					selecctionResultTv.setText("Right");
				}
				else
				{
					selecctionResultTv.setTextColor(Color.RED);
					selecctionResultTv.setText("Wrong");
				}
				quesCount++;
				setNextWord();
			}
		}
		else if(clickedView == btnMeaningThree)
		{
			if(checkQuesLimit())
			{
				if(btnMeaningThree.getText().toString().equals(correctWordMeaning))
				{
					correctAns++;
					selecctionResultTv.setTextColor(Color.GREEN);
					selecctionResultTv.setText("Right");
				}
				else
				{
					selecctionResultTv.setTextColor(Color.RED);
					selecctionResultTv.setText("Wrong");
				}
				quesCount++;
				setNextWord();
			}
		}
		
	}

//	private void showResult() {
//		 AlertDialog.Builder scoreDialog = new AlertDialog.Builder(getParent());
//		 scoreDialog.setTitle("Score Card");
//		 
//		 String percetage ;
//		 if(quesCount == 0)
//			 percetage = String.valueOf(new DecimalFormat("#.##").format((100.00/1)*correctAns));
//		 else
//			 percetage = String.valueOf(new DecimalFormat("#.##").format((100.00/quesCount)*correctAns));
//			 
//		 scoreDialog.setMessage("Yor Score is ...\n"+"Correct: "+String.valueOf(correctAns)
//				 +"\nIncorrect: "+String.valueOf(quesCount - correctAns)
//				 +"\nPercentage: "+percetage+"%");
//		 scoreDialog.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dilog, int arg1) {
//				dilog.dismiss();
//				AlertDialog.Builder qusLimitDialog = new AlertDialog.Builder(getParent());
//				qusLimitDialog.setMessage("Do you want to Continue?");
//				qusLimitDialog.setTitle("End of Words");
//				 
//				qusLimitDialog.setPositiveButton("No",new DialogInterface.OnClickListener() {
//					@Override
//					public void onClick(DialogInterface arg0, int arg1) {
//						finish();
//					}
//				});
//				qusLimitDialog.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
//				@Override
//				public void onClick(DialogInterface arg0, int arg1) {
//						correctAns = 0;
//						quesCount = 0;
//						getImageBitmap();
//						setWordMeanings();
//					}
//				});
//				 qusLimitDialog.show();
//				
//			}
//		 });
//		 scoreDialog.show();
//		
//	}

	private boolean checkQuesLimit() {
		
		if(quesCount>=quesCountLimit)
		{
			//showResult();
			
			return false;
		}
		else
			return true;
		
	}

	private void setNextWord() {
		
		Handler nextWorldHandler = new Handler();
		nextWorldHandler.postDelayed(new Runnable() 
		{
			@Override
			public void run() 
			{
				
				getImageBitmap();
				setWordMeanings();
			}
		}, 1000);
	}

	private void getImageBitmap() {
		wordCurrentImage = randuGenerator.nextInt(wordsImagesLimitLength)+1;
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
	private void setWordMeanings() {
		selecctionResultTv.setText("");
		correctWordMeaning = dbHandlerObj.getWordsInfo(wordCurrentImage);
		Log.e("======", correctWordMeaning);
		int loc = randuGenerator.nextInt(3);
		if(loc==0)
			btnMeaningOne.setText(correctWordMeaning);
		else
			btnMeaningOne.setText(dbHandlerObj.getWordsInfo(randuGenerator.nextInt(wordsImagesLimitLength)));
		if(loc==1)
			btnMeaningTwo.setText(correctWordMeaning);
		else
			btnMeaningTwo.setText(dbHandlerObj.getWordsInfo(randuGenerator.nextInt(wordsImagesLimitLength)));
		if(loc==2)
			btnMeaningThree.setText(correctWordMeaning);
		else
			btnMeaningThree.setText(dbHandlerObj.getWordsInfo(randuGenerator.nextInt(wordsImagesLimitLength)));
	}

}