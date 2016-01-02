package com.bluehorntech.muslimsislamicapp.qibladirection;


import java.io.IOException;
import java.io.InputStream;

import com.bluehorntech.muslimsislamicapp.Fragment6QiblaDirection;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DirectionView extends View {

	private float direction = 0;
	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private boolean firstDraw;
	private Bitmap campassBitmap;
	
	public DirectionView(Context fragment6QiblaDirection) {
		super(fragment6QiblaDirection);
		campassBitmap = getBitmapFromAsset(fragment6QiblaDirection, "map_compass.png");
		init();
	}
	public static Bitmap getBitmapFromAsset(Context context, String strName) {
	    AssetManager assetManager = context.getAssets();

	    InputStream istr;
	    Bitmap bitmap = null;
	    try {
	        istr = assetManager.open(strName);
	        bitmap = BitmapFactory.decodeStream(istr);
	    } catch (IOException e) {
	        return null;
	    }

	    return bitmap;
	}
	
	
	private void init(){
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(3);
		paint.setColor(Color.BLUE);
		paint.setTextSize(30);
		
		firstDraw = true;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		int cxCompass = getMeasuredWidth()/2;
		int cyCompass = getMeasuredHeight()/2;
//		float radiusCompass;
//		
//		if(cxCompass > cyCompass){
//			radiusCompass = (float) (cyCompass * 0.9);
//		}
//		else{
//			radiusCompass = (float) (cxCompass * 0.9);
//		}
//		canvas.drawCircle(cxCompass, cyCompass, radiusCompass, paint);
//		canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);
//		
//		if(!firstDraw){
//		
//			canvas.drawLine(cxCompass, cyCompass, 
//					(float)(cxCompass + radiusCompass * Math.sin((double)(-direction) * 3.14/180)), 
//					(float)(cyCompass - radiusCompass * Math.cos((double)(-direction) * 3.14/180)), 
//					paint);
//		
//			canvas.drawText(String.valueOf(direction), cxCompass, cyCompass, paint);
//		}
////		
		canvas.rotate(direction, cxCompass, cyCompass);
		canvas.drawBitmap(campassBitmap,cxCompass-campassBitmap.getWidth()/2,cyCompass-campassBitmap.getHeight()/2, paint);
	}
	
	public void updateDirection(float dir)
	{
		firstDraw = false;
		direction = Math.abs(dir);
		invalidate();
	}
	

}
