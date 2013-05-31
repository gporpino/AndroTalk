/*
 * Copyright (C) 2008 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.fir.sd.dimag.messenger;

import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.fir.sd.dimag.messenger.exception.WebServiceException;
import br.fir.sd.dimag.messenger.model.ChatMessage;
import br.fir.sd.dimag.messenger.model.User;
import br.fir.sd.dimag.messenger.util.WebUtil;

public class ChatActivity extends Activity implements Runnable {

	private EditText mCurrentMessage;
	private EditText mBodyText;
	private static User userOfChat;
	private static String chatId;

	private Stack<ChatMessage> chatMessages;
	private int i = 0;
	private Thread thread;
	
	boolean flagStop = false;  
    public void stopThread() {  
         flagStop = true;  
    } 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);

		chatMessages = new Stack<ChatMessage>();

		mCurrentMessage = (EditText) findViewById(R.id.currentMessage);
		mBodyText = (EditText) findViewById(R.id.body);

		Button confirmButton = (Button) findViewById(R.id.send);

		confirmButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				// setResult(RESULT_OK);
				// finish();
				ChatMessage message = new ChatMessage();
				message.setText(mCurrentMessage.getText().toString());
				message.setUserLoginFrom(AndroTalkActivity.getCurrentLoggedUser().getLogin());

				chatMessages.push(message);
				try {
					WebUtil.requestSendMessage(chatId.substring(0), AndroTalkActivity
							.getCurrentLoggedUser(), mCurrentMessage.getText()
							.toString());
				} catch (WebServiceException e) {
					// ProgressDialog dialog =
					// ProgressDialog.show(ChatActivity.this,
					// "Mensagem não enviada", "Mensagem não enviada");
					// dialog.show();
				}
				mCurrentMessage.setText("");
				updateChatMessages();
			}

		});

		if (savedInstanceState != null) {
			userOfChat = (User)(savedInstanceState != null ? savedInstanceState
					.getSerializable(AndroTalkActivity.CHAT_USER_LOGIN) : null);
		}
		if (userOfChat == null) {
			Bundle extras = getIntent().getExtras();
			userOfChat = (User)(extras != null ? extras
					.getSerializable(AndroTalkActivity.CHAT_USER_LOGIN) : null);
		}
		// fazer tratamento de falha ao criar a conversa
		if (userOfChat == null) {
			finish();
		}

		if (savedInstanceState != null) {
			chatId = savedInstanceState != null ? savedInstanceState
					.getString(AndroTalkActivity.CHAT_ID) : null;
		}

		if (chatId == null) {
			Bundle extras = getIntent().getExtras();
			chatId = extras != null ? extras.getString(AndroTalkActivity.CHAT_ID)
					: null;
		}
		
		if (chatId == null) {
			try {
				chatId = WebUtil.requestChat(AndroTalkActivity.getCurrentLoggedUser(),
						userOfChat);
				
			} catch (WebServiceException e) {
				ProgressDialog dialog = ProgressDialog.show(this,
						"Usuario Ocupado", "Usuario Ocupado");
				dialog.show();
				finish();
			}
		}
		
		TextView textView = (TextView)findViewById(R.id.chatTitle);
		textView.setText("Andro Talk: Conversa de "+ AndroTalkActivity.getCurrentLoggedUser()+" com "+ userOfChat);
		
		thread = new Thread(this);
		thread.start();
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onDestroy() {
		try {
			WebUtil.requestDeativeChat(chatId);
		} catch (WebServiceException e) {
			// TODO colocar tratamento de erro
		}
		stopThread();
		clearChatData();
		super.onDestroy();
	}
	
	private void clearChatData(){
		chatId = null;
		userOfChat = null;
		chatMessages.clear();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		userOfChat = (User) savedInstanceState.getSerializable(AndroTalkActivity.CHAT_USER_LOGIN);
		chatId = savedInstanceState.getCharSequence(AndroTalkActivity.CHAT_ID).toString();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable(AndroTalkActivity.CHAT_USER_LOGIN, userOfChat);
		outState.putString(AndroTalkActivity.CHAT_ID, chatId);
	}
	
	public void updateChatMessages() {
		String str = "";

		for (ChatMessage message : chatMessages) {
			if (userOfChat.equals(message.getUserLoginFrom())) {
				str +=  userOfChat + " say: " + message
						+ "\n";
			} else {
				str += AndroTalkActivity.getCurrentLoggedUser() + " say:  " + message + "\n";
			}

		}
		mBodyText.setText("Update: " + (i++) + "\n " + str);
//		Scroller scroller = new Scroller(this);
//		scroller.startScroll(mBodyText.getScrollX(), mBodyText.getScrollY() ,  mBodyText.getScrollX()+1, mBodyText.getScrollY()+1);
//		mBodyText.setScroller(scroller);
		mBodyText.invalidate();
	}

	public void run() {
		flagStop = false;
		while (true) {
			
			if ( flagStop){
				System.out.flush();
				return;
			}
			try {
				
				if (!WebUtil.requestCheckUserStatusOnline(AndroTalkActivity.getCurrentLoggedUser())){
//					AndroTalk.logoff();
					finish();
					finishActivity(AndroTalkActivity.ACTIVITY_USERS);
				}
				
				if (WebUtil.requestGetAtiveChatId(chatId) != null){

					List<ChatMessage> list = WebUtil.requestMessagesFromServer(
							AndroTalkActivity.getCurrentLoggedUser(), chatId.substring(0));
					for (ChatMessage message : list) {
						message.setUserLoginFrom(userOfChat.getLogin());
						chatMessages.push(message);
					}
					
					messageHandler.sendEmptyMessage(0);
					Thread.sleep(3000);
				}else{
					finish();
				}
				
			} catch (WebServiceException e) {

			} catch (InterruptedException e) {
				
			}
		}
	}

	// Instantiating the Handler associated with the main thread.
	private Handler messageHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			updateChatMessages();
		}

	};

}
