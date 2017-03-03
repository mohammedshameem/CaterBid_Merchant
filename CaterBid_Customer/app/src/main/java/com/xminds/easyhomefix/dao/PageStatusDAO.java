package com.xminds.easyhomefix.dao;

import com.xminds.easyhomefix.constants.PageStatus;
import com.xminds.easyhomefix.helper.DBHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PageStatusDAO implements PageStatus {
	private static final String TAG = PageStatusDAO.class.getSimpleName();
	private DBHelper dbHelper;
	private static PageStatusDAO instance = null;
	
	String SELECT_ALL = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();
	public static PageStatusDAO getInstance() {

		if (instance == null) {
			instance = new PageStatusDAO();
		}
		return instance;
	}
	
	public PageStatusDAO(){
		dbHelper = DBHelper.getInstance();
	}
	
	public void insertStatus(String tabStatus,String defaultTab){
		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();
				values.put(DETAILS_TAB_STATUS, tabStatus);
				values.put(DEFAULT_TAB, defaultTab);
				db.insert(DB_TABLE_NAME, null, values);
				db.setTransactionSuccessful();
				
			} catch (Exception e) {
				Log.e(TAG, "Exception while inserting in to user table", e);
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}
		
	public String getDetailsTabStatus(){
		String tabStatus = null;

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL, null);
		try {
			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					tabStatus = cursor.getString(cursor.getColumnIndex(DETAILS_TAB_STATUS));
				}
			}

		} catch (Exception e) {

			Log.e("UserDao", "Exception in dao" + e);

		} finally {

			cursor.close();
			db.close();
		}
		return tabStatus;
	}
	
	public String getCategoryNameDefaultTab(){
		String defaultTab = null;

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL, null);
		try {
			if (cursor.getCount() > 0) {

				while (cursor.moveToNext()) {
					defaultTab = cursor.getString(cursor.getColumnIndex(DEFAULT_TAB));
				}
			}

		} catch (Exception e) {

			Log.e("UserDao", "Exception in dao" + e);

		} finally {

			cursor.close();
			db.close();
		}
		return defaultTab;
	}
	
	 public void deleteAll(){
			synchronized (DBHelper.lock) {
				SQLiteDatabase db = dbHelper.getWritableDatabase();
				try {
					db.beginTransaction();

					db.delete(DB_TABLE_NAME, null, null);
					db.setTransactionSuccessful();
					
				} catch (Exception e) {
					Log.e(TAG, "Exception while inserting in to user table", e);
				} finally {
					db.endTransaction();
					db.close();
				}
			}
		}
}
