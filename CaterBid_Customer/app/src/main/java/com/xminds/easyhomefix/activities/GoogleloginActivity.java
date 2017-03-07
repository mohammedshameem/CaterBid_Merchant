package com.xminds.easyhomefix.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.easyhomefix.customer.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.xminds.easyhomefix.Apiconstants.ApiConstants;
import com.xminds.easyhomefix.Holder.EmailSignupBaseHolder;
import com.xminds.easyhomefix.Holder.JobCreationBaseHolder;
import com.xminds.easyhomefix.Holder.OnFailureErrorHolder;
import com.xminds.easyhomefix.Managers.UserManager;
import com.xminds.easyhomefix.app.EasyHomeFixApp;
import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;
import com.xminds.easyhomefix.dao.UserDAO;
import com.xminds.easyhomefix.utils.UtilValidate;
import com.xminds.easyhomefix.utils.Utils;
import com.xminds.easyhomefix.webservice.AsyncTaskCallBack;

/**
 * Created by sini on 6/3/17.
 */
public class GoogleloginActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener {
    private SignInButton signInButton;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog progressDialog;
    private int requestcode=1000;
    private GoogleLoginCallBack googleLoginCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.googlelogin_layout);
        initViews();
        initManager();
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("367842739261-1kmaus5794i41ok2f59ahn17r1on4lbh.apps.googleusercontent.com")
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        signInButton.setOnClickListener(signInListener);
    }

    private void initManager() {
        googleLoginCallBack=new GoogleLoginCallBack();
    }

    View.OnClickListener signInListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, 100);
        }
    };

    private void initViews() {
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        progressDialog=new ProgressDialog(GoogleloginActivity.this);
        progressDialog.setMessage("Please wait..");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 100) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.e("value", "handleSignInResult:" + new Gson().toJson(result));
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String accessToken = result.getSignInAccount().getIdToken();
            Log.e("RESULT", new Gson().toJson(acct));
            if(CurrentlyLoggedUserDAO.getInstance().isAnyUserInCurrentlyLoggedUserTable()){
                if(CurrentlyLoggedUserDAO.getInstance().getUdId() != null &&
                        CurrentlyLoggedUserDAO.getInstance().getLoginType().equalsIgnoreCase("udId")){
                    String udId=CurrentlyLoggedUserDAO.getInstance().getUdId();
                    UserManager.getInstance().googleSignIn(GoogleloginActivity.this, accessToken, "Customer", udId,
                            googleLoginCallBack, requestcode);
                }
            }else{
                UserManager.getInstance().googleSignIn(GoogleloginActivity.this, accessToken, "Customer", null,
                        googleLoginCallBack, requestcode);
            }
        } else {

        }
    }

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
                                        Intent otpIntent = new Intent(GoogleloginActivity.this,
                                                EnterOTPActivity.class);
                                        startActivity(otpIntent);
                                        finish();
                                    }else{
                                        CurrentlyLoggedUserDAO.getInstance().deleteAllUserRows();
                                        CurrentlyLoggedUserDAO.getInstance().InsertCurrentlyloggedUserDetails(emailSignupBaseHolder);
                                        CurrentlyLoggedUserDAO.getInstance().InsertDeviceToken(EasyHomeFixApp.getGcmRegId(),
                                                emailSignupBaseHolder.getData().getUser().getId());

                                        Intent loginIntent = new Intent(
                                                GoogleloginActivity.this,
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
                        Toast.makeText(GoogleloginActivity.this, emailSignupBaseHolder.isSuccess()+"", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(GoogleloginActivity.this, onFailureErrorHolder.getData().get(0).getMessage(), Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(GoogleloginActivity.this, "Login Failure....", Toast.LENGTH_LONG).show();
                    }

                    Intent loginIntent = new Intent(GoogleloginActivity.this,SignUpandLoginActivity.class);
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
                Utils.showDialog(GoogleloginActivity.this, "Please check your internet connection.", "ERROR");
            }
            else
            {
                Utils.showDialog(GoogleloginActivity.this, ""+result, "ERROR");
            }
            progressDialog.dismiss();
        }

        @Override
        public void onFinish(int requestcode, JobCreationBaseHolder jobCreationBaseHolder, int i) {
            progressDialog.dismiss();
        }


    }
}
