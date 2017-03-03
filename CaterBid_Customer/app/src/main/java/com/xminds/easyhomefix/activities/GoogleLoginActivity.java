/*
package com.xminds.easyhomefix.activities;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Holder.UserTokenHolder;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;


public class GoogleLoginActivity extends Activity implements ConnectionCallbacks, 
	OnConnectionFailedListener,ResultCallback<LoadPeopleResult> {
	
	private GoogleApiClient mGoogleApiClient;
	
	private static final String TAG = "EasyHomeFix-Customer";
	private static final int STATE_DEFAULT = 0;
    private static final int STATE_SIGN_IN = 1;
    private static final int STATE_IN_PROGRESS = 2;

    private static final int RC_SIGN_IN = 0;

    private static final String SAVED_PROGRESS = "sign_in_progress";
    
 // Client ID for a web server that will receive the auth code and exchange it for a
    // refresh token if offline access is requested.
    private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";

    // Base URL for your token exchange server, no trailing slash.
    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";
    
    private int mSignInProgress;

    // Used to store the PendingIntent most recently returned by Google Play
    // services until the user clicks 'sign in'.
    private PendingIntent mSignInIntent;
    private static final String EXCHANGE_TOKEN_URL = SERVER_BASE_URL + "/exchangetoken";

    // URL where the client should POST the serverAuthCode so that the server can exchange
    // it for a refresh token,
    private static final String SELECT_SCOPES_URL = SERVER_BASE_URL + "/selectscopes";
    private static final int EMAIL_PERMISSIOM_REQUEST_CODE = 125; 

    private int mSignInError;

    private boolean mRequestServerAuthCode = false;

    private boolean mServerHasToken = true;
    
    private String accessToken;
    
	private GoogleLoginCallBack googleLoginCallBack;
	 private UserTokenHolder tokenholder;
	int requestcode = 0;
	ProgressDialog progressDialog;
	private boolean onAsyncTask = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		progressDialog=ProgressDialog.show(GoogleLoginActivity.this, null, null);
		initManager();
		if (savedInstanceState != null) {
            mSignInProgress = savedInstanceState
                    .getInt(SAVED_PROGRESS, STATE_DEFAULT);
        }
		mGoogleApiClient = buildGoogleApiClient();
		mSignInProgress = STATE_SIGN_IN;
        mGoogleApiClient.connect();
	}
	
	private void initManager() {
		// TODO Auto-generated method stub
		googleLoginCallBack=new GoogleLoginCallBack();
		tokenholder=new UserTokenHolder();
	}

	private GoogleApiClient buildGoogleApiClient() {
        // When we build the GoogleApiClient we specify where connected and
        // connection failed callbacks should be returned, which Google APIs our
        // app uses and which OAuth 2.0 scopes our app requests.
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);

        return builder.build();
    }
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onStart");
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onStop");
		if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		Log.e(TAG, "onConnected");
		// TODO Auto-generated method stub
		 // Retrieve some profile information to personalize our app for the user.
        */
/*Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
        Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
        .setResultCallback(this);
        Log.e("@@@@@@@@@@@@@@@@@",currentUser.getDisplayName());*//*

        Log.e("@@@@@@@@@@@@@@@@@",Plus.AccountApi.getAccountName(mGoogleApiClient));
		// Indicate that the sign in process is complete.
		mSignInProgress = STATE_DEFAULT;
		progressDialog.dismiss();
		new RetrieveTokenTask().execute(Plus.AccountApi.getAccountName(mGoogleApiClient));
	}
	
	@Override
	public void onResult(LoadPeopleResult peopleData) {
		// TODO Auto-generated method stub
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onResult");
		new RetrieveTokenTask().execute(Plus.AccountApi.getAccountName(mGoogleApiClient));
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onConnectiionFailed");
		// TODO Auto-generated method stub
		Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());

        if (result.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
            // An API requested for GoogleApiClient is not available. The device's current
            // configuration might not be supported with the requested API or a required component
            // may not be installed, such as the Android Wear application. You may need to use a
            // second GoogleApiClient to manage the application's optional APIs.
            Log.e(TAG, "API Unavailable.");
        } else if (mSignInProgress != STATE_IN_PROGRESS) {
            // We do not have an intent in progress so we should store the latest
            // error resolution intent for use when the sign in button is clicked.
            mSignInIntent = result.getResolution();
            mSignInError = result.getErrorCode();

            if (mSignInProgress == STATE_SIGN_IN) {
                // STATE_SIGN_IN indicates the user already clicked the sign in button
                // so we should continue processing errors until the user is signed in
                // or they click cancel.
                resolveSignInError();
            }
        }
        progressDialog.dismiss();

        // In this sample we consider the user signed out whenever they do not have
        // a connection to Google Play services.
        //onSignedOut();
	}



	@Override
	public void onConnectionSuspended(int cause) {
		// TODO Auto-generated method stub
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onConnectionSuspend");
		mGoogleApiClient.connect();
	}

	

	
	private void resolveSignInError() {
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "resolveSignInError");
        if (mSignInIntent != null) {
            // We have an intent which will allow our user to sign in or
            // resolve an error.  For example if the user ngoogleSignupcallbackeeds to
            // select an account to sign in with, or if they need to consent
            // to the permissions your app is requesting.

            try {
                // Send the pending intent that we stored on the most recent
                // OnConnectionFailed callback.  This will allow the user to
                // resolve the error currently preventing our connection to
                // Google Play services.
                mSignInProgress = STATE_IN_PROGRESS;
                startIntentSenderForResult(mSignInIntent.getIntentSender(),
                        RC_SIGN_IN, null, 0, 0, 0);
            } catch (SendIntentException e) {
                Log.e(TAG, "Sign in intent could not be sent: "
                        + e.getLocalizedMessage());
                // The intent was canceled before it was sent.  Attempt to connect to
                // get an updated ConnectionResult.
                mSignInProgress = STATE_SIGN_IN;
                mGoogleApiClient.connect();
            }
        } else {
            // Google Play services wasn't able to provide an intent for some
            // error types, so we show the default Google Play services error
            // dialog which may still start an intent on our behalf if the
            // user can resolve the issue.
            createErrorDialog().show();
        }
    }

	private Dialog createErrorDialog() {
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "createErrorDialoge");
        if (GooglePlayServicesUtil.isUserRecoverableError(mSignInError)) {
            return GooglePlayServicesUtil.getErrorDialog(
                    mSignInError,
                    this,
                    RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            Log.e(TAG, "Google Play services resolution cancelled");
                            mSignInProgress = STATE_DEFAULT;                  
                        }
                    });
        } else {
            return new AlertDialog.Builder(this)
                    .setMessage("Google Play services is not available.  This application will close.")
                    .setPositiveButton("Close",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.e(TAG, "Google Play services error could not be "
                                            + "resolved: " + mSignInError);
                                    mSignInProgress = STATE_DEFAULT;
                                    
                                }
                            }).create();
        }
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//super.onActivityResult(requestCode, resultCode, data);
		Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "onActivityResult "+"requestCode : "+requestCode+" resultCode : "+resultCode);
		switch (requestCode) {
        case RC_SIGN_IN:
            if (resultCode == RESULT_OK) {
                // If the error resolution was successful we should continue
                // processing errors.
                mSignInProgress = STATE_SIGN_IN;
            } else {
                // If the error resolution was not successful or the user canceled,
                // we should stop processing errors.
                mSignInProgress = STATE_DEFAULT;
            }

            if (!mGoogleApiClient.isConnecting()) {
            	Log.e("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@", "mGoogleApiClient.isConnecting" );
                // If Google Play services resolved the issue with a dialog then
                // onStart is not called so we need to re-attempt connection here.
                mGoogleApiClient.connect();
            }
            break;
       case EMAIL_PERMISSIOM_REQUEST_CODE:
        	new RetrieveTokenTask().execute(Plus.AccountApi.getAccountName(mGoogleApiClient));
        	
    	default:
    		break;
    }
		

		  if (resultCode == RESULT_CANCELED)
		  {
			  Intent in=new Intent(GoogleLoginActivity.this,SignUpandLoginActivity.class);
			  startActivity(in);
			  finish();
		  }
	}

	
	private class RetrieveTokenTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			progressDialog=ProgressDialog.show(GoogleLoginActivity.this, null, null);
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub

			try {
				
				Log.e(TAG, "TRY Scope : "+params[0]+"___"+Plus.SCOPE_PLUS_LOGIN);
			  //URL url = new URL("https://www.googleapis.com/oauth2/v1/userinfo");
			  // get Access Token with Scopes.PLUS_PROFILE
			  accessToken = GoogleAuthUtil.getToken(GoogleLoginActivity.this,params[0],"oauth2:profile email");
			} catch (UserRecoverableAuthException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();                  
			  Intent recover = e.getIntent();
			  startActivityForResult(recover, 125);
			  //return "";
			} catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			} catch (GoogleAuthException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
			}
			Log.e(TAG, "Access______token_______________"+accessToken);
			return accessToken;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			//super.onPostExecute(result);
			progressDialog.dismiss();
			if(onAsyncTask){
				if(UtilValidate.isNotNull(accessToken)){
						Log.e(TAG, "Accesstoken__CALLBACK"+accessToken+"___");
						progressDialog=ProgressDialog.show(GoogleLoginActivity.this, null, null);
						if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
							if(CurrentlyLoggedUserDAO.getInstance().getUdId() != null &&
									CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")){
								String udId=CurrentlyLoggedUserDAO.getInstance().getUdId();
								UserManager.getInstance().googleSignIn(GoogleLoginActivity.this, accessToken, "Customer", udId, 
										googleLoginCallBack, requestcode);
							}
						}else{
							UserManager.getInstance().googleSignIn(GoogleLoginActivity.this, accessToken, "Customer", null,
									googleLoginCallBack, requestcode);
						}
				}
			}
		}
		
	}
	
	
	// Call back for Google Login
	
	public class GoogleLoginCallBack implements AsyncTaskCallBack {

		@Override
		public void onFinish(int responseCode, Object result) {
			// TODO Auto-generated method stub

			
			if (responseCode == requestcode) {
				EmailSignupBaseHolder emailSignupBaseHolder = (EmailSignupBaseHolder) result;
				if (UtilValidate.isNotNull(emailSignupBaseHolder)) {
					if (emailSignupBaseHolder.isSuccess()) {
						

						Log.e("@@@@@@@@@@@@@@@@@@@","IS SUCCESS");
						 UserDAO.getInstance().deleteAllUserRows();
						 UserDAO.getInstance().InsertUserDetails(emailSignupBaseHolder);
						 						 
						if(UtilValidate.isNotNull(emailSignupBaseHolder.getData())){
							if(UtilValidate.isNotNull(emailSignupBaseHolder.getData().getUser())){


								if(emailSignupBaseHolder.getData().getUser().getMobileVerified() != null){
									//Check whether mobile verified
									if(emailSignupBaseHolder.getData().getUser().getMobileVerified() == 0){
										Intent otpIntent = new Intent(GoogleLoginActivity.this,
												EnterOTPActivity.class);
										startActivity(otpIntent);
										finish();
									}else{	
										CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
										CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
										CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
												emailSignupBaseHolder.getData().getUser().getId());
										
										Intent loginIntent = new Intent(
												GoogleLoginActivity.this,
												LandingPageActivity.class);
										startActivity(loginIntent);
										finish();
									}
								}															
							}
						}
						progressDialog.dismiss();
					}
					
					else
					{
						Toast.makeText(GoogleLoginActivity.this, emailSignupBaseHolder.isSuccess()+"", Toast.LENGTH_LONG).show();
						progressDialog.dismiss();
					}
				}
				

			}
			else
			{
				progressDialog.dismiss();
				OnFailureErrorHolder onFailureErrorHolder =(OnFailureErrorHolder) result;
				if(UtilValidate.isNotNull(onFailureErrorHolder))
				{
					if(UtilValidate.isNotNull(onFailureErrorHolder.getData()) && UtilValidate.isNotEmpty(onFailureErrorHolder.getData())){
						Toast.makeText(GoogleLoginActivity.this, onFailureErrorHolder.getData().get(0).getMessage(), Toast.LENGTH_LONG).show();
					}else{
						Toast.makeText(GoogleLoginActivity.this, "Login Failure....", Toast.LENGTH_LONG).show();
					}
					
					Intent loginIntent = new Intent(GoogleLoginActivity.this,SignUpandLoginActivity.class);
					startActivity(loginIntent);
					finish();
				}
			}
		}

		@Override
		public void onFinish(int responseCode, String result) {
			// TODO Auto-generated method stub
			if(result.equalsIgnoreCase(ApiConstants.NO_INTERNET))
			{
				Utils.showDialog(GoogleLoginActivity.this, "Please check your internet connection.", "ERROR");
			}
			else
			{
				Utils.showDialog(GoogleLoginActivity.this, ""+result, "ERROR");
			}
			progressDialog.dismiss();
		}

		@Override
		public void onFinish(int requestcode,
				JobCreationBaseHolder jobCreationBaseHolder, int i) {
			// TODO Auto-generated method stub
			progressDialog.dismiss();
		}
		
	}
}
*/
