package br.fir.sd.dimag.messenger.model;

import java.io.Serializable;

import br.fir.sd.dimag.messenger.model.enums.UserStatus;



public class User implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String nickname;
	private UserStatus userStatus;
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
	
	@Override
	public int hashCode() {
		return login.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj instanceof User){
			User user = (User) obj;
			if (user.getLogin().equals(this.getLogin())){
				return true;
			}
		}
		return false;
	}
}
