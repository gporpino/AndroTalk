package br.fir.sd.dimag.messenger.model;

import br.fir.sd.dimag.messenger.model.enums.MessageStatus;

public class ChatMessage {
	private String id;
	private String userLoginFrom;
	
	public String getUserLoginFrom() {
		return userLoginFrom;
	}

	public void setUserLoginFrom(String userLoginFrom) {
		this.userLoginFrom = userLoginFrom;
	}

	private MessageStatus messageStauts;
	
	private String text;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	

	public MessageStatus getMessageStauts() {
		return messageStauts;
	}

	public void setMessageStauts(MessageStatus messageStauts) {
		this.messageStauts = messageStauts;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text.toString();
	}
}
