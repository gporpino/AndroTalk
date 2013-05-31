package br.fir.sd.dimag.messenger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;

import br.fir.sd.dimag.messenger.model.Chat;
import br.fir.sd.dimag.messenger.model.ChatMessage;
import br.fir.sd.dimag.messenger.model.User;
import br.fir.sd.dimag.messenger.model.enums.MessageStatus;
import br.fir.sd.dimag.messenger.model.enums.UserStatus;

public class AndroTalkService {
	
	
	private static List<User> users;
	private static List<Chat> chats; 

	static{
		users = new ArrayList<User>();
		chats = new ArrayList<Chat>();
	}
	
//	public User logonWithNickname(String userLogin,String userNickname){
//		User user = new User();
//		user.setLogin(userLogin);
//		user.setNickname(userNickname);
//		user.setUserStatus(UserStatus.ONLINE);
//		if (!users.contains(user)){
//			users.add(user);
//		}
//		return user;
//	}
//	
	public boolean logon(String userLogin){
		User user = new User();
		
		user.setLogin(userLogin);
		user.setNickname(userLogin);
		user.setUserStatus(UserStatus.ONLINE);
		if (!users.contains(user)){
			users.add(user);
			return true;
		}else{
			User user2 = users.get(users.indexOf(user));
			if (user2.getUserStatus() == UserStatus.OFFLINE){
				user2.setUserStatus(UserStatus.ONLINE);
				return true;
			}
		}
		return false;
	}
	
	public boolean logoff(String userLogin){
		
		for (User user:users){
			if (user.getLogin().equals(userLogin)){
				user.setUserStatus(UserStatus.OFFLINE);
				return true;
			}
		}
		return false;
	}
	
	public boolean sendMessage(String chatId,String userLoginFrom , String message ){
		Chat chat = null;
		for(Chat chat2: chats){
			if (chat2.getId().equals(chatId)){
				chat = chat2;
				break;
			}
		}
		if (chat != null){
			ChatMessage msg = new ChatMessage();
			msg.setMessageStauts(MessageStatus.RECIVED_FROM_ORIGIN);
			msg.setUserLoginFrom(userLoginFrom);
			msg.setText(message);
			chat.addMessage(msg);
			return true;
		}
		return false;
	}
	
	public String getMessagesToUserByChat(String userLogin, String chatId) {
		String string = "";
		for (Chat chat: chats){
			if (chat.isAtive() && chatId.equals(chat.getId())){
				for (User user2:chat.getUsers()){
					if (userLogin.equals(user2.getLogin())){
						
						List<ChatMessage> list = chat.getNotSentToUserMessages(user2);
						if (list != null && list.size() > 0){
							 ChatMessage chatMessage =  list.get(0);
							 chatMessage.setMessageStauts(MessageStatus.SENT_TO_DESTINY);
							 
							 string += chatMessage.getText()+"#";
						}
						
					}
				}
			}
		}
		if (string.length() >0){
			string = string.substring(0,string.length()-1);
		}else{
			string = "null";
		}
		return string;
	}
	
	public String getListOfOnlineUserLogin(){
		String usersLogins = new String();
		for (User user : users) {
			if (user.getUserStatus() == UserStatus.ONLINE){
				usersLogins += user.getLogin()+"#";
			}
		}
		if (usersLogins.length() > 0){
			usersLogins = usersLogins.substring(0, usersLogins.length() -1);
		}else{
			usersLogins = "null";
		}
		return usersLogins;
	}
	
	public String getListOfOfflineUserLogin(){
		String usersLogins = new String();
		for (User user : users) {
			if (user.getUserStatus() == UserStatus.OFFLINE){
				usersLogins += user.getLogin()+"#";
			}
		}
		if (usersLogins.length() > 0){
			usersLogins = usersLogins.substring(0, usersLogins.length() -1);
		}else{
			usersLogins = "null";
		}
		return usersLogins;
	}
	
	public boolean checkUserStatusOnline(String userLogin){
		
		for (User user : users) {
			if (user.getLogin().equals(userLogin)){
				if (user.getUserStatus() == UserStatus.ONLINE){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getListOfOnlineUserLoginByChatId(String chatId){
		String usersLogins = new String();
		for (Chat chat : chats) {
			if (chat.getId().equals(chatId)){
				for (User user : chat.getUsers()) {
					usersLogins += user.getLogin()+"#";
				}
			}
		}
		
		if (usersLogins.length() > 0){
			usersLogins = usersLogins.substring(0, usersLogins.length() -1);
		}else{
			usersLogins = "null";
		}
		
		return usersLogins;
	}
	
	public String getUserNickname(String userLogin){
		return null;
	}
	
//	public String startChat(String userLoginOrigin, String userLoginDestiny){
//		
//		User userOrigin = null;
//		User userDestiny = null;
//		for (User user:users){
//			if (userOrigin == null || userDestiny == null){
//				if (user.getLogin().equals(userLoginOrigin)){
//					userOrigin = user;
//				}else if (user.getLogin().equals(userLoginDestiny)) {
//					userDestiny = user;
//				}
//			}else{
//				//quando preencher os dois users parar.
//				break;
//			}
//		}
//		if (userOrigin != null && userDestiny != null){
//			Chat chat = getChatByUsersLogin(userOrigin.getLogin(),userDestiny.getLogin());
//			if (chat == null){
//				chat = new Chat();
//				chat.addUser(userOrigin);
//				chat.addUser(userDestiny);
//			}
//			if (!hasAtiveChatWithUser(userOrigin.getLogin()) && !hasAtiveChatWithUser(userDestiny.getLogin())){
//				chat.ative();
//			}
//			if (!chats.contains(chat)){
//				chats.add(chat);
//			
//			}
//			return chat.getId().toString();
//		}
//		return "";
//	}
	
public Chat startChat(String userLoginOrigin, String userLoginDestiny){
		Chat chat = new Chat();
		User user = new User();
		user.setLogin("Teste");
		chat.addUser(user);
		user = new User();
		user.setLogin("Teste 2");
		user.setUserStatus(UserStatus.OFFLINE);
		
		chat.setId("2000");
		
		return chat;
	}
	
	private Chat getChatByUsersLogin(String userLoginOrigin, String userLoginDestiny){
		for (Chat chat : chats) {
			User userOrigin = null;
			User userDestiny = null;
			for (User user: chat.getUsers()){
				if (user.getLogin().equals(userLoginOrigin)){
					userOrigin = user;
				}
				if (user.getLogin().equals(userLoginDestiny)){
					userDestiny = user;
				}
			}
			if (userOrigin != null && userDestiny != null){
				return chat;
			}
		}
		return null;
	}
	
	public String getAtiveChatIdByUser(String userLogin){
		for (Chat chat: chats){
			if (chat.isAtive()){
				for (User user2:chat.getUsers()){
					if (userLogin.equals(user2.getLogin())){
						return chat.getId();
					}
				}
			}
		}
		for (Chat chat: chats){
			if (!chat.isAtive()){
				for (User user2:chat.getUsers()){
					if (userLogin.equals(user2.getLogin())){
						chat.ative();
						return chat.getId();
					}
				}
			}
		}
		return "null";
	}
	
	public boolean deativeChat(String chatId){
		for (Chat chat: chats){
			if (chat.getId().equals(chatId)){
				chats.remove(chat);
				//chats.add(chats.size(), chat);
				return true;
				
			}
		}
		
		return false;
	}
	
	public String getAtiveChatId(String chatId){
		for (Chat chat: chats){
			if (chat.getId().equals(chatId)){
				return chat.getId();
			}
		}
		return "null";
	}
	
	
	public boolean hasAtiveChatWithUser(String userLogin){
		for (Chat chat: chats){
			if (chat.isAtive()){
				for (User user2:chat.getUsers()){
					if (userLogin.equals(user2.getLogin())){
						return true;
					}
				}
			}
		}
		return false;
	}
	
}
