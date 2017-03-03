package com.xminds.easyhomefix_merchant.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easyhomefix.R;
import com.xminds.easyhomefix_merchant.activities.SplashScreenActivity;




public class Utils {

	private static final String Tag = Utils.class.getName();

	/**
	 * @param jsonObject
	 * @param key
	 * @return JSONObject or null if object not found
	 */
	public static JSONObject getJSONObject(JSONObject jsonObject, String key) {

		if (jsonObject.has(key)) {
			try {
				return jsonObject.getJSONObject(key);
			} catch (JSONException e) {

				Log.e(Tag,
						"Exception occured while getting json object from JsonObject ",
						e);
				return null;
			}
		}

		return null;
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @return JSONArray or null if object not found
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key) {

		if (jsonObject.has(key)) {
			try {
				return jsonObject.getJSONArray(key);
			} catch (JSONException e) {

				Log.e(Tag,
						"Exception occured while getting json array from JsonObject ",
						e);
				return null;
			}
		}

		return null;
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @return String or null if object not found
	 */
	public static String getJSONString(JSONObject jsonObject, String key) {
		if (jsonObject.has(key)) {
			try {
				return jsonObject.getString(key);
			} catch (JSONException e) {

				Log.e(Tag,
						"Exception occured while getting string from JsonObject ",
						e);
				return null;
			}
		}
		return null;
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @return String or null if object not found
	 */
	public static int getJSONint(JSONObject jsonObject, String key) {
		if (jsonObject.has(key)) {
			try {
				return jsonObject.getInt(key);
			} catch (JSONException e) {

				Log.e(Tag,
						"Exception occured while getting string from JsonObject ",
						e);
				return 0;
			}
		}
		return 0;
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @return String or null if object not found
	 */
	public static boolean getJSONBoolean(JSONObject jsonObject, String key) {
		if (jsonObject.has(key)) {
			try {
				return jsonObject.getBoolean(key);
			} catch (JSONException e) {

				Log.e(Tag,
						"Exception occured while getting string from JsonObject ",
						e);
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param context
	 * @return imeistring
	 */
	public static String getUniqueDeviceId(Context context) {

		String imeistring;
		TelephonyManager telephonyManager;

		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * getDeviceId() function Returns the unique device ID. for example,the
		 * IMEI for GSM and the MEID or ESN for CDMA phones.
		 */
		imeistring = telephonyManager.getDeviceId();

		return imeistring;

	}
	
	public static String getTimesince(String date) {

		// Date :2013-05-14 11:25:10

		SimpleDateFormat parserSDF = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
		Date datePosted = null;
		try {
			datePosted = parserSDF.parse(date);
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			Log.e(Tag, "ERROR!!!! " + e);
		}

		if (datePosted != null) {
			Date now = new Date();

			// in sec
			long diff = (now.getTime() - datePosted.getTime()) / 1000;

			long minutes = diff / 60;
			long hours = minutes / 60;
			long days = hours / 24;
			if (diff < 30) {
				return "few seconds ago";
			} else if (minutes < 1 && diff > 20) {
				return diff + " secs ago";

			} else if (minutes < 60 && minutes >= 1) {
				return minutes + " minutes ago";
			} else if (hours < 24 && hours >= 1) {
				return hours + " hours ago";
			} else if (days >= 1) {
				return hours / 24 + " days ago";
			}

		}

		return null;
	}


	/**
	 * 
	 * @param context
	 * @return imsistring
	 */
	public static String getUniqueSubscriberId(Context context) {

		String imsistring;
		TelephonyManager telephonyManager;

		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		/*
		 * getSubscriberId() function Returns the unique subscriber ID, for
		 * example, the IMSI for a GSM phone.
		 */
		imsistring = telephonyManager.getSubscriberId();
		return imsistring;

	}

	/**
	 * 
	 * @param context
	 */
	public static void showHashKey(Context context) {

		try {
			PackageInfo info = context.getPackageManager().getPackageInfo(
					context.getPackageName().toString(),
					PackageManager.GET_SIGNATURES); // Your
			// here
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.e("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {
		} catch (NoSuchAlgorithmException e) {
		}
	}

	
	

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public static String toDate(String datevalue){
		String date;
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat jobondate = new SimpleDateFormat("dd-MM-yyyy");
		try {

			date = jobondate.format(fromUser.parse(datevalue));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return datevalue;
		} 	 
	}
	public static String toTime(String datevalue){
		String date;
		SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat jobontime = new SimpleDateFormat("hh:mm");
		try {

			date = jobontime.format(fromUser.parse(datevalue));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
			return datevalue;
		} 	 
	}
	
	 public static void showDialog(Activity activity,String textString,String success){

	    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View popupView = inflater.inflate(R.layout.dialog_popup_success, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
			popupWindow.update();
			popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			final TextView sucess=(TextView)popupView.findViewById(R.id.sucess);
			final TextView quotestring=(TextView)popupView.findViewById(R.id.quotestring);
			final TextView okbutton=(TextView)popupView.findViewById(R.id.okbutton);
			
			sucess.setText(success+"");
			quotestring.setText(textString+"");
			
			okbutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					
				}
			});
			
			
			
		 
	 }
	 
	 public static void showDialogFacebook(final Activity activity,String textString,String success){

	    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View popupView = inflater.inflate(R.layout.dialog_popup_success, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
			popupWindow.update();
			popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			final TextView sucess=(TextView)popupView.findViewById(R.id.sucess);
			final TextView quotestring=(TextView)popupView.findViewById(R.id.quotestring);
			final TextView okbutton=(TextView)popupView.findViewById(R.id.okbutton);
			
			sucess.setText(success+"");
			quotestring.setText(textString+"");
			
			okbutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					activity.finish();
					Intent i=new Intent(activity,SplashScreenActivity.class);
					activity.startActivity(i);
					//activity.finish();
				}
			});
			
			
			
		 
	 }
	 

	 public static void callDialog(final Activity activity,String textString){

	    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View popupView = inflater.inflate(R.layout.dialog_popup_new, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
			popupWindow.update();
			popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			final TextView textview_phonenum=(TextView)popupView.findViewById(R.id.textview_phonenum);
			final TextView textview_call=(TextView)popupView.findViewById(R.id.textview_call);
			final TextView textview_cancel=(TextView)popupView.findViewById(R.id.textview_cancel);
			
			Log.e(Tag, "@@@@@@"+textString);
			textview_phonenum.setText(textString);
			textview_cancel.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					
				}
			});
			textview_call.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "+6593269075"));
					activity.startActivity(intent);
					
				}
			});
			
			
		 
	 }
	
}
