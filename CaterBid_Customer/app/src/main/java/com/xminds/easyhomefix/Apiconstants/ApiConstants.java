package com.xminds.easyhomefix.Apiconstants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.xminds.easyhomefix.dao.CurrentlyLoggedUserDAO;

public interface ApiConstants {

	String STATUS = "status";

	String DATA = "data";

	String SUCCESS = "success";

	String ERROR = "error";

	String SESSION_ID = "session_id";

	String NO_INTERNET = "no_internet";

	String FAILED = "failed";
	
	String TOOEXPENSIVE="Too Expensive";
	
	String WRONGCATEGORY="Wrong Category";
	
	String NOTTHRUSTWORTHY="Not Trustworthy";
	
	String NOTCOMPETENT="Not Competent";


	static final String BASE_URL = "http://api-s.caterbid.xminds.com/v1/"; // STAGING
	
	//static final String BASE_URL = "http://api.easyhomefix.com/v1/"; //PRODUCTION

	interface Header {

		String HEADER_TOKEN = "EASY-X-TOKEN";

	}

	interface JobCategories {

		String JOB_CATEGORY_URL = BASE_URL + "lookup/job-categories";

	}

	interface JobSubCategories {

		String JOB_SUB_CATEGORY_URL = BASE_URL
				+ "lookup/job-categories/";

		String JOB_SUB_CATEGORY_URL_APPEND="/types";

	}

	interface EmailSignUp {

		String EMAIL_SIGN_UP_URL = BASE_URL + "user/customer/signup";
		String EMAIL = "email";
		String PASSWORD = "password";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String UDID = "udId";
	}

	interface Login {
		String LOGIN_URL = BASE_URL + "user/login";
		String EMAIL = "email";
		String PASSWORD = "password";
		String USERTYPE = "userType";
		String DEVICE_TOKEN = "deviceToken";
		String DEVICE_TYPE = "deviceType";
	}

	interface UpdateUser {

		String UPDATE_USER_URL = BASE_URL + "user/update";
		String EMAIL = "email";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String MOBILE = "mobile";
		String MOBILENOTIFICATION = "mobileNotification";
		String EMAILNOTIFICATION = "emailNotification";
		String OLDPASSWORD = "oldPassword";
		String NEWPASSWORD = "newPassword";
		String REPEATPASSWORD = "repeatePassword";
		String COMPANYNAME = "companyName";

	}

	interface SendOtp {

		String UPDATE_USER_URL = BASE_URL + "otp/send";
		String PREFIX = "prefix";
		String MOBILE = "mobile";
		String RETRY = "retry";

	}

	interface JobCreation {

		String JOB_CREATION_URL = BASE_URL + "job/create";
		String GET_ADDRESS_URL = BASE_URL + "address";
		String POSTALCODE = "postalCode";
		String JOBDETAILS = "jobDetails";
		String JOBTYPEID = "jobTypeId";
		String JOBDATE = "jobDate";
		String BLOCKNUMBER="blockNumber";
		String JOBTYPE ="jobType";
		String FLOOR = "floor";
		String UNIT = "unit";
	}
	
	interface PendingJobListInterface {

		String PENDING_JOB_LIST_URL = BASE_URL + "customer/job/list/pending";
		String OFFSET="offset";
		String LIMIT="limit";

	}

	interface Verifyotp {
		String VERIFY_URL = BASE_URL + "otp/verify";
		String OTP = "otp";

	}

	interface Reviews {

		String REVIEWJOB_URL = BASE_URL + "fixer/";
		String REVIEW_END_URL="/review/list";
		String OFFSET="offset";
		String LIMIT="limit";
	}

	interface Facebooklogin {
		
		String LOGIN_URL = BASE_URL + "user/facebook-login";
		String ACCESSTOKEN= "accessToken";
		String USERTYPE="userType";
		String UDID = "udId";
	}
	
	interface OngoingJobListInterface {
		
		String ONGOING_JOB_LIST_URL= BASE_URL + "customer/job/list/ongoing";
		String OFFSET="offset";
		String LIMIT="limit";
	}
	
	interface CompletedJobListInterface{
		
		String COMPLETED_JOB_LIST_URL= BASE_URL + "customer/job/list/completed";
		String OFFSET="offset";
		String LIMIT="limit";
	}
	interface Quotes {

		String QUOTES_LIST_URL = BASE_URL + "customer/job/";
		String QUOTES_END =  "/quotes";
	}
	interface GoogleSignUp{
		String GOOGLE_SIGN_UP_URL = BASE_URL + "user/google-plus-login";
		String ACCESS_TOKEN="accessToken";
		String USERTYPE = "userType";
		String UDID = "udId";
	}
	
	interface EditFixDetails{
		String EDIT_FIX_DETAILS_URL = BASE_URL + "job/edit";
		String JOBID = "jobId";
		String BLOCK_NUMBER = "blockNumber";
		String FLOOR = "floor";
		String UNIT = "unit";
		String ROAD_BUILDING = "roadBuilding";
		String POSTAL_CODE = "postalCode";
		String JOB_DETAILS = "jobDetails";
		String JOB_TYPEID = "jobTypeId";
		String JOB_DATE = "jobDate";
	}
	
	interface AcceptQuote{
		
		String QUOTES_ACCEPT_URL = BASE_URL + "customer/quote/";
		String QUOTES_ACCEPT_END = "/accept";	
	}
	
	interface NotificationListInterface{
		
		String NOTIFICATION_LIST_URL=BASE_URL+ "user/notification/list";
		String OFFSET="offset";
		String LIMIT="limit";
		
	}
	interface ChatInterface {

		String CHAT_LIST_URL = BASE_URL + "quote/";
		String CHAT_LIST_URL_APPEND = "/chat/list";

	}
	
	interface RejectQuoteInterface{
		
		String QUOTES_REJECT_URL = BASE_URL + "customer/quote/";
		String QUOTES_REJECT_END = "/reject";	
		String REJECTCATEGORY="rejectCategory";
		
		
	}
	
	interface UploadImageInterface{
		
		String UPLOAD_IMAGE_URL = BASE_URL + "asset/new";
		String LINKTO = "linkTo";	
		String LINKTO_ID ="linkToId";
		String IMAGE_FILE = "imageFile";
	}
	
	interface UpdateNotificationInterface{
		
		String UPDATE_NOTIFICATION_URL=BASE_URL+ "notification/";
		String UPDATE_NOTIFICATION_END_URL="/read";
	}
	
	interface PayPalPaymentInterface{
		String REQUEST_PAYMENT_URL = BASE_URL + "payment/paypal";
		String QUOTE_ID = "quoteId";
		String PAYMENT_STATUS = "paymentStatus";
		String PAYMENT_RESPONSE = "paymentResponse";
		String PAYMENT_RESPONSE_CODE = "paymentResponseCode";
	}
	
	interface ReviewAddInterface{
		
		String REVIEW_ADD_URL= BASE_URL+"job/";
		String REVIEW_END_URL= "/review/add";
		String STAR="star";
		String REVIEW="review";
	}
	
	interface CompleteFix{
		
		String COMPLETE_FIX_URL = BASE_URL + "job/";
		String COMPLETE_FIX_URL_TAIL = "/status/completed";
		String JOBID = "jobId";
	}
	
	interface CancelFix{
		
		String CANCEL_FIX_URL = BASE_URL + "job/";
		String CANCEL_FIX_URL_TAIL = "/status/cancelled";
		String JOBID = "jobId";
	}
	
	interface ChatStausInterface {

		String CHAT_STATUS_LIST_URL = BASE_URL + "quote/";
		String CHAT_STATUS_LIST_URL_APPEND = "/chat/add";
		String CHAT_TEXT = "chatText";
		String CHAT_TYPE_ID = "chatTypeId";
	}
	
	interface UpadateJobStaus{
		
		String UPDATE_JOB_STATUS=BASE_URL +"job/";
		String UPDATE_JOB_END_URL="/status/read";
	}	

	interface CashPaymentInterface{
		String CASH_PAYMENT_URL = BASE_URL + "payment/cash";
		String PAYMENT_STATUS = "paymentStatus";
		String QUOTE_ID = "quoteId";
	}
	
	interface DummyUserInterface {

		String DUMMY_USER_SIGN_UP_URL = BASE_URL + "user/customer/signup/ud-id";
		String CHECK_DUMMY_USER_URL = BASE_URL + "check/ud-id";
		String UDID = "udId";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String DEVICE_TOKEN = "deviceToken";
		String DEVICE_TYPE = "deviceType"; 

	}
	
	interface UserDetailsInterface{
		String USER_DETAILS_URL = BASE_URL + "user/me";
	}
	
	interface GoogleInterface{
		
		String GOOGLE_ADDRESS_URL="http://maps.googleapis.com/maps/api/geocode/json";
		String ADDRESS="address";
		String LATLONG="latlng";
		String SENSOR="sensor";
		
	}
	
	interface AddressListInterface{
		String ADDRESS_LIST_URL = BASE_URL + "user/";
		String ADDRESS_LIST_TAIL_URL = "/addresses";
	}
	
	interface NotificationListItemInterface{
		
		String NOTIFICATION_LIST_ITEM_URL=BASE_URL+ "job/";
		String JOB_ID= "jobId";
	}
	
	interface LogoutInterface{
		String LOGOUT_URL = BASE_URL + "user/logout";
	}
}
