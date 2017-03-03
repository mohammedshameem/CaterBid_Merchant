package com.xminds.easyhomefix.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.easyhomefix.customer.R;
import com.xminds.easyhomefix.actionbar.TouchImageView;
import com.xminds.easyhomefix.activities.SplashScreenActivity;
import com.xminds.easyhomefix.adapter.ChatStatusAdapter.FixerHolder;




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
	 
	 public static void showImage(Activity activity,final FixerHolder fixholder,TouchImageView touchImageView){/*

	    	LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	View popupView = inflater.inflate(R.layout.imagepopup, null);
			final PopupWindow popupWindow = new PopupWindow(popupView,android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT,true);
			popupWindow.update();
			popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
			final TextView okbutton=(TextView)popupView.findViewById(R.id.okbutton);
			TouchImageView image=(TouchImageView)popupView.findViewById(R.id.image);
			
			image.setOnTouchImageViewListener(new OnTouchImageViewListener() {
				
				@Override
				public void onMove() {
					// TODO Auto-generated method stub

					PointF point = fixholder.touchImageView.getScrollPosition();
					RectF rect = fixholder.touchImageView.getZoomedRect();
					float currentZoom = fixholder.touchImageView.getCurrentZoom();
					boolean isZoomed = fixholder.touchImageView.isZoomed();
				
				}
			});
			okbutton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popupWindow.dismiss();	
					
				}
			});
			
			
			
		 
	 */}
	 
	 
	 
	 
		public static String compressImage(String imageUri,Activity activity) {

	        String filePath = getRealPathFromURI(imageUri,activity);
	        Bitmap scaledBitmap = null;

	        BitmapFactory.Options options = new BitmapFactory.Options();

//	      by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
//	      you try the use the bitmap here, you will get null.
	        options.inJustDecodeBounds = true;
	        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

	        int actualHeight = options.outHeight;
	        int actualWidth = options.outWidth;

//	      max Height and width values of the compressed image is taken as 816x612

	        float maxHeight = 816.0f;
	        float maxWidth = 612.0f;
	        float imgRatio = actualWidth / actualHeight;
	        float maxRatio = maxWidth / maxHeight;

//	      width and height values are set maintaining the aspect ratio of the image

	        if (actualHeight > maxHeight || actualWidth > maxWidth) {
	            if (imgRatio < maxRatio) {               imgRatio = maxHeight / actualHeight;                actualWidth = (int) (imgRatio * actualWidth);               actualHeight = (int) maxHeight;             } else if (imgRatio > maxRatio) {
	                imgRatio = maxWidth / actualWidth;
	                actualHeight = (int) (imgRatio * actualHeight);
	                actualWidth = (int) maxWidth;
	            } else {
	                actualHeight = (int) maxHeight;
	                actualWidth = (int) maxWidth;

	            }
	        }

//	      setting inSampleSize value allows to load a scaled down version of the original image

	        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

//	      inJustDecodeBounds set to false to load the actual bitmap
	        options.inJustDecodeBounds = false;

//	      this options allow android to claim the bitmap memory if it runs low on memory
	        options.inPurgeable = true;
	        options.inInputShareable = true;
	        options.inTempStorage = new byte[16 * 1024];

	        try {
//	          load the bitmap from its path
	            bmp = BitmapFactory.decodeFile(filePath, options);
	        } catch (OutOfMemoryError exception) {
	            exception.printStackTrace();

	        }
	        try {
	            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,Bitmap.Config.ARGB_8888);
	        } catch (OutOfMemoryError exception) {
	            exception.printStackTrace();
	        }

	        float ratioX = actualWidth / (float) options.outWidth;
	        float ratioY = actualHeight / (float) options.outHeight;
	        float middleX = actualWidth / 2.0f;
	        float middleY = actualHeight / 2.0f;

	        Matrix scaleMatrix = new Matrix();
	        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

	        Canvas canvas = new Canvas(scaledBitmap);
	        canvas.setMatrix(scaleMatrix);
	        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2, middleY - bmp.getHeight() / 2, new Paint(Paint.FILTER_BITMAP_FLAG));

//	      check the rotation of the image and display it properly
	        ExifInterface exif;
	        try {
	            exif = new ExifInterface(filePath);

	            int orientation = exif.getAttributeInt(
	                    ExifInterface.TAG_ORIENTATION, 0);
	            Log.d("EXIF", "Exif: " + orientation);
	            Matrix matrix = new Matrix();
	            if (orientation == 6) {
	                matrix.postRotate(90);
	                Log.d("EXIF", "Exif: " + orientation);
	            } else if (orientation == 3) {
	                matrix.postRotate(180);
	                Log.d("EXIF", "Exif: " + orientation);
	            } else if (orientation == 8) {
	                matrix.postRotate(270);
	                Log.d("EXIF", "Exif: " + orientation);
	            }
	            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
	                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
	                    true);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        FileOutputStream out = null;
	        String filename = getFilename();
	        try {
	            out = new FileOutputStream(filename);

//	          write the compressed bitmap at the destination specified by filename.
	            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        }

	        return filename;

	    }
		public static String getFilename() {
		    File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
		    if (!file.exists()) {
		        file.mkdirs();
		    }
		    String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
		    return uriSting;
		 
		}
		private static String getRealPathFromURI(String contentURI,Activity activity) {
	        Uri contentUri = Uri.parse(contentURI);
	        Cursor cursor = activity.getContentResolver().query(contentUri, null, null, null, null);
	        if (cursor == null) {
	            return contentUri.getPath();
	        } else {
	            cursor.moveToFirst();
	            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
	            return cursor.getString(index);
	        }
	    }
		public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		    final int height = options.outHeight;
		    final int width = options.outWidth;
		    int inSampleSize = 1;
		 
		    if (height > reqHeight || width > reqWidth) {
		        final int heightRatio = Math.round((float) height/ (float) reqHeight);
		        final int widthRatio = Math.round((float) width / (float) reqWidth);
		        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;      }       final float totalPixels = width * height;       final float totalReqPixelsCap = reqWidth * reqHeight * 2;       while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
		        inSampleSize++;
		    }
		 
		    return inSampleSize;
		}
		
	 
	
}