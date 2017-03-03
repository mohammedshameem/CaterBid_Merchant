package com.xminds.easyhomefix_merchant.Holder;

import java.io.Serializable;
import java.util.List;

public class UserHolder implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 7165262006536140775L;
	private String id;
	private String uuid;
	private String email;
	private String firstName;
	private String lastName;
	private String mobile;
	private String profileImage;
	private String countryCode;
	private Integer mobileNotification;
	private Integer emailNotification;
	private Integer mobileVerified;
	private String createdDate;
	private String loginType;
	private String facebookId;
	private String googlePlusId;
	private String userType;
	private int numberOfFixesCompleted;
	private float avrgStarRating;
	private String companyName;
	private List<FixerJobCategories> fixerJobCategories;
	private String udId;
	private int onlyUdId;
	private String categoryId;
	private int unreadNotifications;

	public float getAvrgStarRating() {
		return avrgStarRating;
	}

	public void setAvrgStarRating(float avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}


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

	public Integer getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(Integer mobileVerified) {
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

	public Integer getMobileNotification() {
		return mobileNotification;
	}

	public void setMobileNotification(Integer mobileNotification) {
		this.mobileNotification = mobileNotification;
	}

	public Integer getEmailNotification() {
		return emailNotification;
	}

	public void setEmailNotification(Integer emailNotification) {
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

	public int getNumberOfFixesCompleted() {
		return numberOfFixesCompleted;
	}

	public void setNumberOfFixesCompleted(int numberOfFixesCompleted) {
		this.numberOfFixesCompleted = numberOfFixesCompleted;
	}


	public void setAvrgStarRating(int avrgStarRating) {
		this.avrgStarRating = avrgStarRating;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<FixerJobCategories> getFixerJobCategories() {
		return fixerJobCategories;
	}

	public void setFixerJobCategories(
			List<FixerJobCategories> fixerJobCategories) {
		this.fixerJobCategories = fixerJobCategories;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public int getUnreadNotifications() {
		return unreadNotifications;
	}

	public void setUnreadNotifications(int unreadNotifications) {
		this.unreadNotifications = unreadNotifications;
	}

}
