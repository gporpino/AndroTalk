package br.fir.sd.dimag.messenger.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import br.fir.sd.dimag.messenger.datastructure.ArrayListWithMaxSize;
import br.fir.sd.dimag.messenger.model.enums.ChatStatus;
import br.fir.sd.dimag.messenger.model.enums.MessageStatus;

public class Chat {
	private String id;
	private ArrayListWithMaxSize<User> users;
	private Stack<ChatMessage> messages;
	private ChatStatus chatStatus;
	
	public Chat() {
		users = new ArrayListWithMaxSize<User>(2);
		messages = new Stack<ChatMessage>();
		id = "" +new Random().nextInt();
		chatStatus = ChatStatus.WAITING;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public boolean addUser(User user){
		return users.add(user);
	}
	
	public void addMessage(ChatMessage message){
		messages.push(message);
	}
	
	public List<ChatMessage> getMessages(){
		return messages;
	}
	
	public List<User> getUsers(){
		return users;
	}
	
	public void ative() {
		chatStatus = ChatStatus.ATIVE;
	}
	
	public void waiting() {
		chatStatus = ChatStatus.WAITING;
	}
	
	public boolean isAtive() {
	    if (chatStatus == ChatStatus.ATIVE){
	    	return true;
	    }
	    return false;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		for (User user:users){
			hashCode += user.hashCode();
		}
		return hashCode;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Chat){
			Chat chat = (Chat)obj;
			
			boolean isEqualUser0 = chat.users.get(0).equals(this.users.get(0));
			boolean isEqualUser1 = chat.users.get(1).equals(this.users.get(1));
			
			if (isEqualUser0 && isEqualUser1){
				return true;
			}
		}
		return false;
	}
}
