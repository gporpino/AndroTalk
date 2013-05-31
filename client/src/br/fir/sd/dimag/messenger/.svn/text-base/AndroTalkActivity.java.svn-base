package br.fir.sd.dimag.messenger;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import br.fir.sd.dimag.messenger.exception.WebServiceException;
import br.fir.sd.dimag.messenger.model.User;
import br.fir.sd.dimag.messenger.model.enums.UserStatus;
import br.fir.sd.dimag.messenger.util.WebUtil;

public class AndroTalkActivity extends Activity {
	
	 public static final int ACTIVITY_USERS=0;
	 public static final int ACTIVITY_CHAT=1;
	 public static final String CHAT_USER_LOGIN="userId";
	 public static final String CHAT_ID = "chatId";
	 private static ProgressDialog dialog;
	
	 private static User userLogged;
	 
	 private static Context context;
	 
	 public static User getCurrentLoggedUser(){
		 return userLogged;
	 }

		
	 
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		AndroTalkActivity.context = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logon_layout);
		
		final Button buttonOn = (Button)findViewById(R.id.ButtonLogon);
	 	final Button buttonLogoff = (Button)findViewById(R.id.ButtonLogoff);
	 	final EditText editText = (EditText)findViewById(R.id.EditTextLogin);
	 	final TextView textText = (TextView)findViewById(R.id.TextViewMessage);
		
		if (userLogged != null){
			buttonOn.setVisibility(Button.INVISIBLE);
			editText.setVisibility(EditText.INVISIBLE);
			
			buttonLogoff.setVisibility(Button.VISIBLE);
			textText.setVisibility(TextView.VISIBLE);
			textText.setText("Usuario logado: " + AndroTalkActivity.getCurrentLoggedUser());
			
		}else{
			buttonOn.setVisibility(Button.VISIBLE);
			editText.setVisibility(EditText.VISIBLE);
			
			buttonLogoff.setVisibility(Button.INVISIBLE);
			textText.setVisibility(TextView.VISIBLE);
			
			textText.setText("");
		}
		
		
		buttonLogoff.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				logoff();
				buttonOn.setVisibility(Button.VISIBLE);
				buttonLogoff.setVisibility(Button.INVISIBLE);
			}
			
		});

		buttonOn.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				EditText editText = (EditText)findViewById(R.id.EditTextLogin);
				
				User user = new User();
				user.setLogin( editText.getText().toString());
				user.setUserStatus(UserStatus.OFFLINE);
				
				userLogged = user;
				try{
					WebUtil.requestLogon(userLogged);
					
					Intent intent = new Intent(AndroTalkActivity.this, OnlineUsersActivity.class);
			        startActivityForResult(intent,ACTIVITY_USERS);
				}catch (WebServiceException e) {
					userLogged = null;
					 TextView textView = (TextView) findViewById(R.id.TextViewMessage);
					 textView.setText("Falha ao fazer logon: Usuario já existe.");
				}
				
			}
			
		});
		
		
	}

	public static void logoff() {
		
		try {
			WebUtil.requestLogoff(userLogged);
			userLogged = null;
			
		} catch (WebServiceException e) {
			
		}
		
	}
	
	
//	 private void addClassMappings(SoapSerializationEnvelope envelope) {
//         createWrappingResultTemplate(envelope);
//         new UserResult().register(envelope, NAMESPACE, "return");
//     }
//
//     private void createWrappingResultTemplate(SoapSerializationEnvelope envelope) {
//         // tell ksoap what type of attribute the Resulting Object has.
//         PropertyInfo info = new PropertyInfo();
//         info.name = "return";
//         info.type = new UserResult().getClass();
//         info.type = PropertyInfo.VECTOR_CLASS;
         
         
//         PropertyInfo pi = new PropertyInfo();
//         pi.setName(&quot;evnt&quot;);
//         Event e = new Event();
//         e.setName(&quot;Antalya, Turkey&quot;);
//         e.setKey(1);
//         e.setEndDate(new Date(EndDate.timeMillis()));
//         e.setStartDate(new Date(StartDate.timeMillis()));
//         e.setSubscriptionEndDate(new Date(SubscriptionEndDate.timeMillis()));
//         e.setSubscriptionStartDate(new Date(SubscriptionStartDate.timeMillis()));
//         pi.setValue(e);
//         pi.setType(Event.EVENT_CLASS);
//         request.addProperty(pi); 

         // Demonstration of using a template to describe what the result will
         // look like. KSOAP will actually clone this object and fill it with
         // the resulting information.
         // You could use a real object here as well, your choice.
         // However, If you fail to tell ksoap what to
         // expect however it will not try to map any
         // contained objects to their classes.

//         SoapObject template = new SoapObject(NAMESPACE, "getListOfOnlineUserLoginResponse");
//         template.addProperty(info, "not important what this is");
//
//         envelope.addTemplate(template);
//     }

	
}