package br.fir.sd.dimag.messenger.model;

public class ChatMessage {
	private String id;
	private String userLoginFrom;
	private String text;
	
	public void setUserLoginFrom(String userLoginFrom) {
		this.userLoginFrom = userLoginFrom;
	}

	public String getUserLoginFrom() {
		return userLoginFrom;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
