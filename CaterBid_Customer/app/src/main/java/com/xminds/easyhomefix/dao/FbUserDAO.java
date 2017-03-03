package com.xminds.easyhomefix.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.UserHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.constants.CurrentlyLoggedUser;
import com.xminds.easyhomefix.constants.FbUser;
import com.xminds.easyhomefix.constants.User;
import com.xminds.easyhomefix.helper.DBHelper;
import com.xminds.easyhomefix.utils.UtilValidate;

public class FbUserDAO implements FbUser {

	private static final String TAG = FbUserDAO.class.getSimpleName();

	private DBHelper dbHelper;

	private static FbUserDAO instance = null;

	String SELECT_ALL_ROWS_BY_USERID = new StringBuffer(" select *")
			.append(" from ").append(DB_TABLE_NAME).append(" where ")
			.toString();
	String SELECT_USER_DETAILS = new StringBuffer(" select *").append(" from ")
			.append(DB_TABLE_NAME).toString();

	public static FbUserDAO getInstance() {

		if (instance == null) {

			instance = new FbUserDAO();
		}

		return instance;

	}

	public FbUserDAO() {

		dbHelper = DBHelper.getInstance();

	}

	public void InsertUserDetails(EmailSignupBaseHolder emailsignupobject)

	{
		synchronized (DBHelper.lock) {
			SQLiteDatabase db = dbHelper.getWritableDatabase();
			try {
				db.beginTransaction();

				ContentValues values = new ContentValues();
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getToken()))
					values.put(User.TOKEN, emailsignupobject.getData()
							.getToken());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getId()))
					values.put(User.ID, emailsignupobject.getData().getUser()
							.getId());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getUuid()))
					values.put(User.UUID, emailsignupobject.getData().getUser()
							.getUuid());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getEmail()))
					values.put(User.EMAIL, emailsignupobject.getData()
							.getUser().getEmail());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getFirstName()))
					values.put(User.FIRST_NAME, emailsignupobject.getData()
							.getUser().getFirstName());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getLastName()))
					values.put(User.LAST_NAME, emailsignupobject.getData()
							.getUser().getLastName());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getMobile()))
					values.put(User.MOBILE, emailsignupobject.getData()
							.getUser().getMobile());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getProfileImage()))
					values.put(User.PROFILE_IMAGE, emailsignupobject.getData()
							.getUser().getProfileImage());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getCountryCode()))
					values.put(User.COUNTRY_CODE, emailsignupobject.getData()
							.getUser().getCountryCode());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getMobileVerified()))
					values.put(User.MOBILEVERIFIED, emailsignupobject.getData()
							.getUser().getMobileVerified());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getCreatedDate()))
					values.put(User.CREATEDDATE, emailsignupobject.getData()
							.getUser().getCreatedDate());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getUserType()))
					values.put(User.USERTYPE, emailsignupobject.getData()
							.getUser().getUserType());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getMobileNotification()))
					values.put(User.MOBILE_NOTIFICATION, emailsignupobject
							.getData().getUser().getMobileNotification());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getEmailNotification()))
					values.put(User.EMAIL_NOTIFICATION, emailsignupobject
							.getData().getUser().getEmailNotification());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getLoginType()))
					values.put(FbUser.LOGINTYPE, emailsignupobject.getData()
							.getUser().getLoginType());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getFacebookId()))
					values.put(FbUser.FACEBOOKID, emailsignupobject.getData()
							.getUser().getFacebookId());
				if (UtilValidate.isNotNull(emailsignupobject.getData()
						.getUser().getGooglePlusId()))
					values.put(FbUser.GOOGLEPLUSID, emailsignupobject.getData()
							.getUser().getGooglePlusId());
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

	/**
	 * 
	 * @return
	 */

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
			UserHolder userholder = new UserHolder();
			String token;
			String id;
			String userType;
			String uuid;
			String email;
			String firstname;
			String lastname;
			String mobile;
			String profileimage;
			String countrycode;
			int mobilenotification;
			int emailnotification;
			String mobileverified;
			String createddate;
			String loginType;
			String facebookId;
			String googlePlusId;
			SQLiteDatabase db = dbHelper.getReadableDatabase();
			Cursor cursor = db.rawQuery(
					new StringBuffer(SELECT_USER_DETAILS).toString(), null);
			try {
				if (cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.TOKEN))) {
							token = cursor.getString(cursor
									.getColumnIndex(User.TOKEN));
							loggeduserObject.setToken(token);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.ID))) {
							id = cursor.getString(cursor
									.getColumnIndex(User.ID));
							userholder.setId(id);
							loggeduserObject.setUser(userholder);

						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.USERTYPE))) {
							loggeduserObject.setUser(userholder);
							userType = cursor.getString(cursor
									.getColumnIndex(User.USERTYPE));
							userholder.setUserType(userType);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.UUID))) {
							uuid = cursor.getString(cursor
									.getColumnIndex(User.UUID));
							userholder.setUuid(uuid);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.EMAIL))) {
							email = cursor.getString(cursor
									.getColumnIndex(User.EMAIL));
							userholder.setEmail(email);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.FIRST_NAME))) {
							firstname = cursor.getString(cursor
									.getColumnIndex(User.FIRST_NAME));
							userholder.setFirstName(firstname);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.LAST_NAME))) {
							lastname = cursor.getString(cursor
									.getColumnIndex(User.LAST_NAME));
							userholder.setLastName(lastname);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.MOBILE))) {
							mobile = cursor.getString(cursor
									.getColumnIndex(User.MOBILE));
							userholder.setMobile(mobile);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.PROFILE_IMAGE))) {
							profileimage = cursor.getString(cursor
									.getColumnIndex(User.PROFILE_IMAGE));
							userholder.setProfileImage(profileimage);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.COUNTRY_CODE))) {
							countrycode = cursor.getString(cursor
									.getColumnIndex(User.COUNTRY_CODE));
							userholder.setCountryCode(countrycode);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.MOBILE_NOTIFICATION))) {
							mobilenotification = cursor.getInt(cursor
									.getColumnIndex(User.MOBILE_NOTIFICATION));
							userholder
									.setMobileNotification(mobilenotification);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.EMAIL_NOTIFICATION))) {
							emailnotification = cursor.getInt(cursor
									.getColumnIndex(User.EMAIL_NOTIFICATION));
							userholder.setEmailNotification(emailnotification);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.MOBILEVERIFIED))) {
							mobileverified = cursor.getString(cursor
									.getColumnIndex(User.MOBILEVERIFIED));
							//userholder.setMobileVerified(mobileverified);
							loggeduserObject.setUser(userholder);
						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(User.CREATEDDATE))) {
							createddate = cursor.getString(cursor
									.getColumnIndex(User.CREATEDDATE));
							userholder.setCreatedDate(createddate);
							loggeduserObject.setUser(userholder);
						}

						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(FbUser.LOGINTYPE))) {
							loginType = cursor.getString(cursor
									.getColumnIndex(FbUser.LOGINTYPE));
							userholder.setLoginType(loginType);
							loggeduserObject.setUser(userholder);

						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(FbUser.FACEBOOKID))) {
							facebookId = cursor.getString(cursor
									.getColumnIndex(FbUser.FACEBOOKID));
							userholder.setFacebookId(facebookId);
							loggeduserObject.setUser(userholder);

						}
						if (UtilValidate.isNotNull(cursor
								.getColumnIndex(FbUser.GOOGLEPLUSID))) {
							googlePlusId = cursor.getString(cursor
									.getColumnIndex(FbUser.GOOGLEPLUSID));
							userholder.setGooglePlusId(googlePlusId);
							;
							loggeduserObject.setUser(userholder);

						}

					}
				}
			} catch (Exception e) {
				Log.e("Currently logged fbUserDao", "Exception in dao" + e);
			} finally {

				cursor.close();
				db.close();
			}
			return loggeduserObject;
		}
	}
}
