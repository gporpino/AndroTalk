package br.fir.sd.dimag.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import br.fir.sd.dimag.messenger.exception.WebServiceException;
import br.fir.sd.dimag.messenger.model.User;
import br.fir.sd.dimag.messenger.model.enums.UserStatus;
import br.fir.sd.dimag.messenger.util.WebUtil;

public class OnlineUsersActivity extends ListActivity implements Runnable {
	
	protected static final int START_CHAT = 1;
	protected static final int UPDATE_ONLINE_USERS = 2;
	private static List<User> usersAtServer ;
	private String toStartChatId;
	private String toStartChatUserId;
	private Thread thread;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.users_list);
		usersAtServer = new ArrayList<User>();
		
	}
	
	@Override
	protected void onDestroy() {
		AndroTalkActivity.logoff();
		super.onDestroy();
	}
	
	public class UserOnlineAdapter extends ArrayAdapter<User> {

        private List<User> items;

        public UserOnlineAdapter(Context context, int textViewResourceId, List<User> items) {
                super(context, textViewResourceId, items);
                this.items = items;
        }
        

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View v = convertView;
                
                if (v == null) {
                	
                    LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    v = vi.inflate(R.layout.row, null);
                }
                User user = items.get(position);
                if (user != null) {
                	ImageView iv = (ImageView)v.findViewById(R.id.userImage);
                    TextView tt = (TextView) v.findViewById(R.id.toptext);
                    TextView bt = (TextView) v.findViewById(R.id.bottomtext);
                    
                    v.setId(position);
                    
                    if (tt != null) {
                          tt.setText("Name: "+user.getLogin());                            }
                    if(bt != null){
                    	if (user.getUserStatus() == UserStatus.ONLINE){
                    		bt.setText("Status: Online");
                    		iv.setImageResource(R.drawable.online);
                    	}else{
                    		bt.setText("Status: Offline");
                    		iv.setImageResource(R.drawable.offline);
                    	}
                    }
                }
                return v;
        }
        
	}
	
	 @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(this, ChatActivity.class);
        
        User user = usersAtServer.get(v.getId());
        i.putExtra(AndroTalkActivity.CHAT_USER_LOGIN, user);
        startActivityForResult(i, AndroTalkActivity.ACTIVITY_CHAT);
    }
	 
	boolean flagStop = false;

	public void stopThread() {
		flagStop = true;
	} 
	 
	 public void run() {
		 flagStop = false;
    	while (true){
    		if (flagStop){
    			System.out.flush(); // liberação de recursos  
		        return;
    		}
    		
			try {
				toStartChatId = WebUtil.requestGetAtiveChatByUser(AndroTalkActivity.getCurrentLoggedUser());
				toStartChatUserId = "";
				if (toStartChatId != null && !toStartChatId.equals("")){
					String result =  WebUtil.requestListOfOnlineUserLoginByChatId(toStartChatId);
					if (result != null && !result.equals("")){
						String[] logins = result.split("#");
						for (String login: logins){
							if (login.equals(AndroTalkActivity.getCurrentLoggedUser())){
								//Passar para o proximo usuario que não eh o que está logado
								continue;
							}else{
								toStartChatUserId = login;
							}
						}
					}
					if (toStartChatUserId != null && !toStartChatUserId.equals("")){
						
				        messageHandler.sendMessage(Message.obtain(messageHandler,START_CHAT )); 
				        System.out.flush(); // liberação de recursos  
				        return;
					}
				}
				
				Thread.sleep(3000);
			} catch (WebServiceException e) {
				
			} catch (InterruptedException e) {
				
			}finally{
				messageHandler.sendMessage(Message.obtain(messageHandler,UPDATE_ONLINE_USERS ));
			}
    	}
    }
	 
	@Override
	protected void onStart() {
		super.onStart();
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		stopThread();
	}
	 
 // Instantiating the Handler associated with the main thread.
    private Handler messageHandler = new Handler() {

        

		@Override
        public void handleMessage(Message msg) {
        	
   		 	switch (msg.what) {
				case START_CHAT:
					Intent i = new Intent(OnlineUsersActivity.this, ChatActivity.class);
			        
			        i.putExtra(AndroTalkActivity.CHAT_USER_LOGIN, toStartChatUserId);
			        i.putExtra(AndroTalkActivity.CHAT_ID, toStartChatId);
			        startActivityForResult(i, AndroTalkActivity.ACTIVITY_CHAT);
			        
					break;
	
				case UPDATE_ONLINE_USERS :
					usersAtServer.clear();
					String result = WebUtil.requestListOfOnlineUsers();
					String[] logins = result.split("#");
					
					for (String string: logins){
						if (!string.equals("")){
							User user = new User();
							user.setLogin(string);
							user.setUserStatus(UserStatus.ONLINE);
							usersAtServer.add(user);
						}
					}
					usersAtServer.remove(AndroTalkActivity.getCurrentLoggedUser());
					
					result = WebUtil.requestListOfOfflineUsers();
					logins = result.split("#");
					for (String string: logins){
						if (!string.equals("")){
							User user = new User();
							user.setLogin(string);
							user.setUserStatus(UserStatus.OFFLINE);
							usersAtServer.add(user);
						}
					}
					usersAtServer.remove(AndroTalkActivity.getCurrentLoggedUser());
					
					
					UserOnlineAdapter users = new UserOnlineAdapter(OnlineUsersActivity.this, R.layout.row, usersAtServer);
		   		 	setListAdapter(users);
		   		 	
		   		 	break;
   		 	}
   		 	toStartChatId = "";
	        toStartChatUserId = "";
        }

    };
}

