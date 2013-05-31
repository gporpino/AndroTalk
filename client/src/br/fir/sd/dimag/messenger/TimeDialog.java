package br.fir.sd.dimag.messenger;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class TimeDialog {
	
	private Context context;
	private CharSequence title;
	private CharSequence message;
	private int delay;
	
	
	

	public TimeDialog(Context context, int delay, String title, String message) {
		this.context = context;
		this.delay = delay;
		this.title = title;
		this.message = message;
	}

	public void show() {
		
//		handler.sendEmptyMessage(0);
	}
}
