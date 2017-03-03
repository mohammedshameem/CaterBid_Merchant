package com.xminds.easyhomefix.Holder;

import java.io.Serializable;
import java.util.List;

public class ChatData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7976302781021436998L;
	private List<ChatList> chats;
	private ChatList jobChat;
	private ChatList chat;
	
	
	public List<ChatList> getChats() {
		return chats;
	}
	public void setChats(List<ChatList> chats) {
		this.chats = chats;
	}
	public ChatList getJobChat() {
		return jobChat;
	}
	public void setJobChat(ChatList jobChat) {
		this.jobChat = jobChat;
	}
	public ChatList getChat() {
		return chat;
	}
	public void setChat(ChatList chat) {
		this.chat = chat;
	}

	
}
