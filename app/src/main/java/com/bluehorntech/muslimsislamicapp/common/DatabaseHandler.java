package com.bluehorntech.muslimsislamicapp.common;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
	
	 private static String DB_PATH = "/data/data/com.bluehorntech.muslimsislamicapp/databases/";
     private static String DB_NAME = "Qurannewlatestnew.sqlite";
	 private SQLiteDatabase IslamicPortaldb;
	 private Context IslamicPortalContext;
	 
	public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, 1);
		 this.IslamicPortalContext = context;
		 
		 try {
			createDataBase();
		} catch (IOException e) {
		}
		 
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	public void openDataBase(){
		try {
		    String dbPath = DB_PATH+DB_NAME ;
		   IslamicPortaldb = SQLiteDatabase.openDatabase(dbPath, null,SQLiteDatabase.OPEN_READWRITE);
		} catch (Exception e) {
		}
	     
	 }

	public void createDataBase() throws IOException {

		boolean dbExist = checkDataBase();

		if (dbExist) {
		} else {

			this.getReadableDatabase();

			try {

				copyDataBase();

			} catch (IOException e) {

				throw new Error("Error copying database");

			}
		}

	}

	private boolean checkDataBase() {

		SQLiteDatabase checkDB = null;

		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {

		}

		if (checkDB != null) {
			checkDB.close();
		}

		return checkDB != null ? true : false;
	}

	private void copyDataBase() throws IOException {
		InputStream myInput = IslamicPortalContext.getAssets().open(DB_NAME);

		String outFileName = DB_PATH + DB_NAME;
		
		File file = new File(outFileName);
		if(!file.exists())
			file.createNewFile();
		
		OutputStream myOutput = new FileOutputStream(file);

		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer)) > 0) {
			myOutput.write(buffer, 0, length);
		}
		myOutput.flush();
		myOutput.close();
		myInput.close();

	}
	public void closeDatabase()
	{
		try {
			IslamicPortaldb.close();
			close();
		} catch (Exception e) {
		}
	}
	public String getWordsInfo(int wordCurrentImage)
	{
		openDataBase();
		JSONObject tempWordJsonObj = new JSONObject();
		String meanings = "";
		if(IslamicPortaldb!=null)
		{
			try {
				Cursor cursor = IslamicPortaldb.rawQuery("SELECT meaning FROM Words WHERE image='"+String.valueOf(wordCurrentImage)+".png"+"'", null); 
				 
 				if (cursor.moveToFirst()) {
 					
				    do {
				    	tempWordJsonObj = new JSONObject();
				    	try {
				    		
				    		tempWordJsonObj.put(cursor.getColumnName(0), cursor.getString(0));//college_id
					    	
						} catch (Exception e) {
						}
				    	
				    } while (cursor.moveToNext());
				}
			  
			    cursor.close();
			    meanings = tempWordJsonObj.getString("meaning");
			} catch (Exception e) {
				Log.e("=================",e.toString());
			}
			
		}
		closeDatabase();
		return meanings;
	}
	
	
}