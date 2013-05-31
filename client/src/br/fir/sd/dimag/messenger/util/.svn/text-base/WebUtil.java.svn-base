package br.fir.sd.dimag.messenger.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;
import org.xmlpull.v1.XmlPullParserException;

import br.fir.sd.dimag.messenger.exception.WebServiceException;
import br.fir.sd.dimag.messenger.model.ChatMessage;
import br.fir.sd.dimag.messenger.model.User;

public class WebUtil {
	
	

	private static String NAMESPACE = "http://messenger.dimag.sd.fir.br";
//	private static String URL = "http://androtalk.gotdns.com:8080/AndroTalkServer/services/AndroTalk";
	private static String URL = "http://192.168.1.4:8080/AndroTalkServer/services/AndroTalk";
	private static AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(
			URL);

	public static String requestLogon(User user) throws WebServiceException {

		String SOAP_ACTION = "";
		String METHOD_NAME = "logon";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLogin", user.getLogin());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString();
			if (!str.equals("true")){
				throw new Exception("Usuário já logado");
			}
		} catch (Exception e) {
			throw new WebServiceException( e.getMessage());
		}
		
		return str;
	}
	
	public static void requestLogoff(User user) throws WebServiceException {

		String SOAP_ACTION = "";
		String METHOD_NAME = "logoff";
		
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLogin", user.getLogin());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString();
			if (!str.equals("true")){
				throw new Exception("Falha ao fazer logoff");
			}
		} catch (Exception e) {
			throw new WebServiceException( e.getMessage());
		}
		
	}
	
	public static String requestListOfOnlineUsers() {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getListOfOnlineUserLogin";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString(); 
			if (str.equals("null")){
				str = "";
			}
		} catch (Exception e) {
			str = e.getMessage();
		}
		
		return str;
	}
	
	public static String requestListOfOfflineUsers() {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getListOfOfflineUserLogin";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString();
			if (str.equals("null")){
				str = "";
			}
		} catch (Exception e) {
			str = e.getMessage();
		}
		
		return str;
	}
	
	public static String requestChat(User userOrigin, User userDestiny ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "startChat";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLoginOrigin", userOrigin.getLogin());
		request.addProperty("userLoginDestiny",userDestiny.getLogin());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
//		androidHttpTransport.reset();
		
		String chatId = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			chatId = result.toString(); 
		} catch (Exception e) {
			throw new WebServiceException("Usuário ocupado");
		}
		
		return chatId;
	}
	
	public static void requestDeativeChat(String chatId ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "deativeChat";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("chatId", chatId);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
//		androidHttpTransport.reset();
		
		boolean bool = false;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			bool = Boolean.parseBoolean(result.toString());
			if (!bool){
				new  WebServiceException("Não foi possivel desativar a conversa");
			}
		} catch (Exception e) {
			throw new WebServiceException(e.getMessage());
		}
		
	}
	
	public static String requestGetAtiveChatId(String chatId ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getAtiveChatId";

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("chatId", chatId);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
//		androidHttpTransport.reset();
		
		String chatResultId = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			
			if (!result.toString().equals("null")){
				chatResultId = result.toString();
			}
		} catch (Exception e) {
			throw new WebServiceException(e.getMessage());
		}
		return chatResultId;
	}
	
	public static void requestSendMessage(String chatId,User userFrom , String message ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "sendMessage";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("chatId", chatId);
		request.addProperty("userLoginFrom", userFrom.getLogin());
		request.addProperty("message", message);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString();
			if (!str.equals("true")){
				throw new WebServiceException("Mensagem não enviada");
			}
		} catch (Exception e) {
			throw new WebServiceException("Mensagem não enviada");
		}
		
	}
	
	public static List<ChatMessage> requestMessagesFromServer(User userLogged ,String chatId ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getMessagesToUserByChat";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLogin", userLogged.getLogin());
		request.addProperty("chatId", chatId);
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
//		androidHttpTransport.reset();
		
		String str = null;
		List<ChatMessage> list = new ArrayList<ChatMessage>(); 
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString();
			if (str != null && !str.equals("null")){
				String[] messages = str.split("#"); 
			
				for (String message:messages){
					ChatMessage chatMessage = new ChatMessage();
					chatMessage.setText(str);
					list.add(chatMessage);
				}
			}
		} catch (Exception e) {
			throw new WebServiceException("Mensagem não enviada");
		}
		
		return list;
	}
	
	public static String requestGetAtiveChatByUser(User user ) throws WebServiceException {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getAtiveChatIdByUser";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLogin", user.getLogin());
		
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
		
		
//		androidHttpTransport.reset();
		
		String str = null;
		
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			if (!result.toString().equals("null")){
				str = result.toString();
			}
			
		} catch (Exception e) {
			throw new WebServiceException("Problemas as pegar o chatId");
		}
		
		return str;
	}

	private synchronized static Object callWebService(String SOAP_ACTION,
			SoapSerializationEnvelope envelope) throws IOException,
			XmlPullParserException, SoapFault {
		androidHttpTransport.call(SOAP_ACTION, envelope);
		Object result = envelope.getResponse();
		return result;
	}
	
	public static String requestListOfOnlineUserLoginByChatId(String chatId) {
		
		String SOAP_ACTION = "";
		String METHOD_NAME = "getListOfOnlineUserLoginByChatId";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("chatId", chatId);
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString(); 
		} catch (Exception e) {
			str = e.getMessage();
		}
		
		return str;
	}

	public static boolean requestCheckUserStatusOnline(User user) {
		String SOAP_ACTION = "";
		String METHOD_NAME = "checkUserStatusOnline";
		

		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		request.addProperty("userLogin", user.getLogin());
		
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.setOutputSoapObject(request);
		
//		androidHttpTransport.reset();
		
		String str = null;
		try {
			Object result = callWebService(SOAP_ACTION, envelope); 
			str = result.toString(); 
			if (str.equals("true")){
				return true;
			}
		} catch (Exception e) {
			str = e.getMessage();
		}
		
		return false;
	}
	
	
}
