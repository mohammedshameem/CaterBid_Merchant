package com.xminds.easyhomefix.Holder;

import java.io.Serializable;

public class ChatList implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 990028810217056316L;
	private String id;
    private UserHolder userId;
    private String chatDateTime;
    private String chatText;
    private String chatImage;
    private int chatTypeId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public UserHolder getUserId() {
		return userId;
	}
	public void setUserId(UserHolder userId) {
		this.userId = userId;
	}
	public String getChatDateTime() {
		return chatDateTime;
	}
	public void setChatDateTime(String chatDateTime) {
		this.chatDateTime = chatDateTime;
	}
	public String getChatText() {
		return chatText;
	}
	public void setChatText(String chatText) {
		this.chatText = chatText;
	}
	public String getChatImage() {
		return chatImage;
	}
	public void setChatImage(String chatImage) {
		this.chatImage = chatImage;
	}
	public int getChatTypeId() {
		return chatTypeId;
	}
	public void setChatTypeId(int chatTypeId) {
		this.chatTypeId = chatTypeId;
	}
    
    
}
