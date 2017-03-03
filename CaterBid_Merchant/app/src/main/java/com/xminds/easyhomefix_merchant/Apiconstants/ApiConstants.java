package com.xminds.easyhomefix_merchant.Apiconstants;


public interface ApiConstants {
	
	
	String STATUS = "status";

	String DATA = "data";

	String SUCCESS = "success";

	String ERROR = "error";

	String SESSION_ID = "session_id";

	String NO_INTERNET = "no_internet";

	String FAILED = "failed";

	static final String BASE_URL = "http://api-s.easyhome.xminds.in/v1/"; // STAGING
	
	//static final String BASE_URL = "http://api.easyhomefix.com/v1/"; //PRODUCTION

	interface Header {

		String HEADER_TOKEN = "EASY-X-TOKEN";

	}
	
	interface EmailSignUp{
		
		String EMAIL_SIGN_UP_URL = BASE_URL + "user/fixer/signup";
		String EMAIL = "email";
		String PASSWORD = "password";
		String FIRSTNAME = "firstName";
		String LASTNAME = "lastName";
		String COMPANYNAME="companyName";		
		
	}

	interface GoogleSignUp{
		String GOOGLE_SIGN_UP_URL = BASE_URL + "user/google-plus-login";
		String ACCESS_TOKEN="accessToken";
		String USERTYPE = "userType";
	}
	interface Facebooklogin {
			
			String LOGIN_URL = BASE_URL + "user/facebook-login";
			String ACCESSTOKEN= "accessToken";
			String USERTYPE="userType";
		}
	
	interface Login {
		String LOGIN_URL = BASE_URL + "user/login";
		String EMAIL = "email";
		String PASSWORD = "password";
		String USERTYPE = "userType";
		String DEVICE_TOKEN = "deviceToken";
		String DEVICE_TYPE = "deviceType";
	}
	
	interface AddQuote{
		String QUOTE_URL=BASE_URL + "fixer/quote/add";
		String QUOTE_AMOUNT="quoteAmount";
		String JOB_ID="jobId";
	}

	interface AvailableNewJobsInterface{
	
	String AVAILABLE_NEW_JOBS_URL=BASE_URL + "fixer/jobs/available";
	String OFFSET = "offset";
	String LIMIT = "limit";
	String JOBCATEGORIES = "jobCategories";
	
	}
	
	interface QuotedJobsInterface{
		
		String QUOTED_JOBS_URL=BASE_URL + "fixer/jobs/quoted";
		String OFFSET = "offset";
		String LIMIT = "limit";
		String JOBCATEGORIES = "jobCategories";
	}
	
	interface Ongoing{
		String QUOTE_URL=BASE_URL + "fixer/job/list/ongoing";
		String OFFSET = "offset";
		String LIMIT = "limit";
	}
	
	interface AddCategoryInterface{
		String ADD_CATEGORY_URL=BASE_URL + "fixer/catgory/";
		String ADD_CATEGORY_END_URL = "/add";
	}
	interface RemoveCategoryInterface{
		String REMOVE_CATEGORY_URL=BASE_URL + "fixer/catgory/";
		String REMOVE_CATEGORY_END_URL = "/remove";
	}
	
	interface CompletedCancelled{
		String COMP_CNCL_URL=BASE_URL + "fixer/job/list/completed-and-cancelled";
		String OFFSET = "offset";
		String LIMIT = "limit";
	}
	
	interface SendOtp {

		String UPDATE_USER_URL = BASE_URL + "otp/send";
		String PREFIX = "prefix";
		String MOBILE = "mobile";
		String RETRY = "retry";

	}
	
	interface Verifyotp {
		String VERIFY_URL = BASE_URL + "otp/verify";
		String OTP = "otp";

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

	interface NotificationListInterface{
		
		String NOTIFICATION_LIST_URL=BASE_URL+ "user/notification/list";
		String OFFSET = "offset";
		String LIMIT = "limit";
	}
	interface UpdateNotificationInterface{
		
		String UPDATE_NOTIFICATION_URL=BASE_URL+ "notification/";
		String UPDATE_NOTIFICATION_END_URL="/read";
	}

	interface TransactionHistoryInterface{
		String TRANSACTION_HISTORY_URL=BASE_URL+ "fixer/payment/history";
	}
	
	interface Reviews {

		String REVIEWJOB_URL = BASE_URL + "fixer/";
		String REVIEW_END_URL="/review/list";
	}

	interface RequestPaymentInterface{
		String REQUEST_PAYMENT_URL = BASE_URL + "job/";
		String REQUEST_PAYMENT_TAIL_URL = "/request-payment";
	}
	
	interface UpadateJobStaus{
		
		String UPDATE_JOB_STATUS=BASE_URL +"job/";
		String UPDATE_JOB_END_URL="/status/read";
	}
	interface HasAccountInterface{
		String HAS_ACCOUNT_URL = BASE_URL + "user/has-account";
	}
	interface ChatInterface {

		String CHAT_LIST_URL = BASE_URL + "quote/";
		String CHAT_LIST_URL_APPEND = "/chat/list";

	}
	interface ChatStausInterface {

		String CHAT_STATUS_LIST_URL = BASE_URL + "quote/";
		String CHAT_STATUS_LIST_URL_APPEND = "/chat/add";
		String CHAT_TEXT = "chatText";
		String CHAT_TYPE = "chatTypeId";

	}
	interface UploadImageInterface{
		
		String UPLOAD_IMAGE_URL = BASE_URL + "asset/new";
		String LINKTO = "linkTo";	
		String LINKTO_ID ="linkToId";
		String IMAGE_FILE = "imageFile";
	}
	interface UserDetailsInterface{
		String USER_DETAILS_URL = BASE_URL + "user/me";
	}
	interface CashInterface{
		String ACCEPT_CASH_URL = BASE_URL + "payment/";
		String ACCEPT_CASH_TAIL_URL = "/cash/accept";
		String REJECT_CASH_URL = BASE_URL + "payment/";
		String REJECT_CASH_TAIL_URL = "/cash/reject";
	}
	interface RequestReviewInterface{
		String REQUEST_REVIEW_URL = BASE_URL + "job/";
		String REQUEST_REVIEW_TAIL_URL = "/request-review";
	}
	interface jobItemInterface{
		
		String JOB_ITEM_URL=BASE_URL+ "job/";
		//String JOB_ID= "jobId";
	}
	interface LogoutInterface{
		String LOGOUT_URL = BASE_URL + "user/logout";
	}
}
