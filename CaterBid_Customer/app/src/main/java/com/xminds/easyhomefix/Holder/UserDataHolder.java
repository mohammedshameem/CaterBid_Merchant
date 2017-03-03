package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class UserDataHolder implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8642105464718091937L;
	private String id;
	private String uuid;
	private String email;
	private String firstName;
	private String lastName;
	private String mobile;
	private String profileImage;
	private String countryCode;
	private int mobileNotification;
	private int emailNotification;
	private int mobileVerified;
	private String createdDate;
	private String loginType;
	private String facebookId;
	private String googlePlusId;
	private String userType;
	private String udId;
	private int onlyUdId;

	
	public String getUdId() {
		return udId;
	}

	public void setUdId(String udId) {
		this.udId = udId;
	}

	public int getOnlyUdId() {
		return onlyUdId;
	}

	public void setOnlyUdId(int onlyUdId) {
		this.onlyUdId = onlyUdId;
	}
	public int getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(int mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public int getMobileNotification() {
		return mobileNotification;
	}

	public void setMobileNotification(int mobileNotification) {
		this.mobileNotification = mobileNotification;
	}

	public int getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(int emailNotification) {
		this.emailNotification = emailNotification;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getFacebookId() {
		return facebookId;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public String getGooglePlusId() {
		return googlePlusId;
	}

	public void setGooglePlusId(String googlePlusId) {
		this.googlePlusId = googlePlusId;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
