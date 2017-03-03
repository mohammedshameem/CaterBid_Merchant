package com.xminds.easyhomefix_merchant.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xminds.easyfix_merchant.constants.CurrentlyLoggedUser;
import com.xminds.easyfix_merchant.constants.User;
import com.xminds.easyhomefix_merchant.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix_merchant.Holder.UserHolder;
import com.xminds.easyhomefix_merchant.Holder.UserTokenHolder;
import com.xminds.easyhomefix_merchant.helper.DBHelper;
import com.xminds.easyhomefix_merchant.utils.UtilValidate;



public class CurrentlyLoggedUserDAO implements CurrentlyLoggedUser {
	private static final String TAG = CurrentlyLoggedUserDAO.class
			.getSimpleName();
	private DBHelper dbHelper;

	private static CurrentlyLoggedUserDAO instance = null;

	String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	String SELECT_ROWS_COUNT = new StringBuffer(" select count(*)")
			.append(" from ").append(DB_TABLE_NAME).toString();

	public static CurrentlyLoggedUserDAO getInstance() {

		if (instance == null) {

			instance = new CurrentlyLoggedUserDAO();
		}

		return instance;

	}

	public CurrentlyLoggedUserDAO() {

		dbHelper = DBHelper.getInstance();

	}

	public void InsertCurrentlyloggedUserDetails(
			EmailSignupBaseHolder emailsignupobject) {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();

				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getToken()))
					values.put(CurrentlyLoggedUser.TOKEN, emailsignupobject
							.getData().getToken());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getId()))
					values.put(CurrentlyLoggedUser.ID, emailsignupobject
							.getData().getUser().getId());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getUserType()))
					values.put(CurrentlyLoggedUser.USERTYPE, emailsignupobject
							.getData().getUser().getUserType());
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

	public String getHeader() {

		synchronized (DBHelper.lock) {

			String header = null;

			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(SELECT_USER_DETAILS, null);

			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						int header_index = cursor
								.getColumnIndex(CurrentlyLoggedUser.TOKEN);

						header = cursor.getString(header_index);
					}
				}

			} catch (Exception e) {

				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return header;
		}
	}

	public int getUserId() {

		synchronized (DBHelper.lock) {

			int userID = 0;
			Log.e("userid", "" + userID);
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_USER_DETAILS).toString(), null);
			try {
				if (cursor.getCount() > 0) {

					while (cursor.moveToNext()) {

						userID = cursor.getInt(cursor.getColumnIndex(User.ID));
						Log.e("userid", "" + userID);

					}
				}

			} catch (Exception e) {

				Log.e("UserDao", "Exception in dao" + e);

			} finally {

				cursor.close();
				db.close();
			}

			return userID;
		}

	}
	
	public boolean isAnyUserInCurrentlyLoggedUserTable() {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			boolean isExisting = false;
			Cursor cursor = null;

			try {
				cursor = db.rawQuery(
						new StringBuffer(SELECT_ROWS_COUNT).toString(), null);

				if (cursor != null) {

					Log.e(TAG, "cursor " + cursor);

					cursor.moveToFirst();

					if (cursor.getInt(0) == 0) {

						isExisting = false;
					}

					else {

						isExisting = true;

					}
				}

			} catch (Exception e) {
				// TODO: handle exception

				Log.e(TAG, "Exception in dao", e);

			} finally {

				cursor.close();
				db.close();

			}

			return isExisting;
		}
	}

	public void deleteAllUserRows() {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();
				Log.e("USERS", " user deleted");
				db.delete(DB_TABLE_NAME, null, null);
				Log.e("USERS ", " user deleted");
				db.setTransactionSuccessful();
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}

	public UserTokenHolder getUserDetails() {
		synchronized (DBHelper.lock) {
			UserTokenHolder loggeduserObject = new UserTokenHolder();
			UserHolder userholder=new UserHolder();
			String token;
			String id;
			String userType;
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_USER_DETAILS).toString(), null);
			try {
				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						token = cursor.getString(cursor
								.getColumnIndex(CurrentlyLoggedUser.TOKEN));
						loggeduserObject.setToken(token);
						id = cursor.getString(cursor
								.getColumnIndex(CurrentlyLoggedUser.ID));
						userholder.setId(id);
						loggeduserObject.setUser(userholder);   
						userType = cursor.getString(cursor
								.getColumnIndex(CurrentlyLoggedUser.USERTYPE));
						userholder.setUserType(userType);
						loggeduserObject.setUser(userholder);
						
					}
				}
			} catch (Exception e) {
				Log.e("Currently logged UserDao", "Exception in dao" + e);
			} finally {

				cursor.close();
				db.close();
			}
			return loggeduserObject;
		}
	}
	
	public void InsertCurrentlyloggedUserDetails(UserTokenHolder userTokenHolder) {

		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();

				if (UtilValidate.isNotNull(userTokenHolder.getToken()))
					values.put(CurrentlyLoggedUser.TOKEN, userTokenHolder.getToken());
				if (UtilValidate.isNotNull(userTokenHolder.getUser().getId()))
					values.put(CurrentlyLoggedUser.ID, userTokenHolder.getUser().getId());
				if (UtilValidate.isNotNull(userTokenHolder.getUser().getUserType()))
					values.put(CurrentlyLoggedUser.USERTYPE, userTokenHolder.getUser().getUserType());
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
	
	public void InsertDeviceToken(String deviceToken, String uId){
		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();

				if (UtilValidate.isNotNull(deviceToken))
					values.put(CurrentlyLoggedUser.DEVICE_TOKEN, deviceToken);
				db.update(DB_TABLE_NAME, values, CurrentlyLoggedUser.ID+"= ?", new String[]{uId});
				db.setTransactionSuccessful();
			} catch (Exception e) {
				Log.e(TAG, "Exception while inserting in to user table", e);
			} finally {
				db.endTransaction();
				db.close();
			}
		}
	}
	
	public String getDeviceToken(){
		String deviceToken = null;
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery(
		new StringBuffer(SELECT_USER_DETAILS).toString(), null);
		try {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					deviceToken = cursor.getString(cursor.getColumnIndex(CurrentlyLoggedUser.DEVICE_TOKEN));
				}
			}

		} catch (Exception e) {
			Log.e("UserDao", "Exception in dao" + e);
		} finally {

			cursor.close();
			db.close();
		}

		return deviceToken;
	}

}
