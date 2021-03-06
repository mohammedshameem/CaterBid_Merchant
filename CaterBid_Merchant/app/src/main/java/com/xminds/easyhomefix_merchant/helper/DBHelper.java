package com.xminds.easyhomefix_merchant.helper;



import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xminds.easyfix_merchant.constants.CurrentlyLoggedUser;
import com.xminds.easyfix_merchant.constants.FbUser;
import com.xminds.easyfix_merchant.constants.PageStatus;
import com.xminds.easyfix_merchant.constants.User;
import com.xminds.easyhomefix_merchant.app.EasyHomeFixApp;

public class DBHelper extends SQLiteOpenHelper {

	private static final String TAG = DBHelper.class.getSimpleName();

	private static final String DB_NAME = "Easyfixmerchant.db";

	private static final int DB_VERSION_NO = 1;

	private static DBHelper mInstance = null;

	public static final Object lock = new Object();

	public DBHelper() {
		super(EasyHomeFixApp.getContext(), DB_NAME, null, DB_VERSION_NO);
		// TODO Auto-generated constructor stub

	}

	public static DBHelper getInstance() {

		// Use the application context, which will ensure that you
		// don't accidentally leak an Activity's context.
		// See this article for more information: http://bit.ly/6LRzfx
		if (mInstance == null) {

			Log.i(TAG,
					"Context **************** :" + EasyHomeFixApp.getContext());
			mInstance = new DBHelper();
		}
		return mInstance;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		Log.d(TAG, "in onCreate for creating db");
		db.execSQL(new StringBuilder(" CREATE TABLE ")
				.append(User.DB_TABLE_NAME).append(" (").append(User.TOKEN)
				.append(" Varchar(20),").append(User.ID)
				.append(" Varchar(20),").append(User.UUID)
				.append(" Varchar(20),").append(User.EMAIL)
				.append(" Varchar(20),").append(User.FIRST_NAME)
				.append(" Varchar(20),").append(User.LAST_NAME)
				.append(" Varchar(20),").append(User.MOBILE)
				.append(" Varchar(20),").append(User.PROFILE_IMAGE)
				.append(" Varchar(20),").append(User.COUNTRY_CODE)
				.append(" Varchar(20),").append(User.MOBILE_NOTIFICATION)
				.append(" INTEGER,").append(User.AVERAGESTARRATING)
				.append(" INTEGER,").append(User.FIXCOMPLETED)
				.append(" INTEGER,").append(User.EMAIL_NOTIFICATION)
				.append(" INTEGER,").append(User.MOBILEVERIFIED)
				.append(" Varchar(20),").append(User.CREATEDDATE)
				.append(" Varchar(20),").append(User.USERTYPE)
				.append(" Varchar(20),").append(User.LOGINTYPE)
				.append(" Varchar(20),").append(User.FACEBOOKID)
				.append(" Varchar(20),").append(User.GOOGLEPLUSID)
				.append(" Varchar(20),").append(User.COMPANYNAME)
				.append(" Varchar(20),").append(User.CATEGORY_ID)
				.append(" Varchar(20)").append(");").toString());

		db.execSQL(new StringBuilder(" CREATE TABLE ")
				.append(CurrentlyLoggedUser.DB_TABLE_NAME).append(" (")
				.append(CurrentlyLoggedUser.TOKEN).append(" Varchar(20),")
				.append(CurrentlyLoggedUser.ID).append(" Varchar(20),")
				.append(CurrentlyLoggedUser.USERTYPE).append(" Varchar(20),")
				.append(CurrentlyLoggedUser.DEVICE_TOKEN).append(" Varchar(100)")
				.append(");").toString());

		db.execSQL(new StringBuilder(" CREATE TABLE ")
				.append(PageStatus.DB_TABLE_NAME).append(" (")
				.append(PageStatus.DETAILS_TAB_STATUS).append(" Varchar(20),")
				.append(PageStatus.DEFAULT_TAB).append(" Varchar(20)")
				.append(");").toString());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
				User.DB_TABLE_NAME).toString());
		db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
				CurrentlyLoggedUser.DB_TABLE_NAME).toString());
		db.execSQL(new StringBuilder(" DROP TABLE IF EXISTS ").append(
				PageStatus.DB_TABLE_NAME).toString());
		onCreate(db);
	}

}
