package com.example.storage;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Bundle bundle = arg1.getExtras();
		String msgBody = "";
		String sendAddr = "";
		Date time = new Date(0);
		if(bundle != null){
			Object[] pdus = (Object[]) bundle.get("pdus");
			SmsMessage[] smsMsgs = new SmsMessage[pdus.length];
			for(int i=0; i < pdus.length; i++){
				smsMsgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
				msgBody += smsMsgs[i].getDisplayMessageBody();
			}
			sendAddr = smsMsgs[0].getDisplayOriginatingAddress();
			time = new Date(smsMsgs[0].getTimestampMillis());
		}
		Intent i = new Intent(arg0,SmsPhoneResultActivity.class);
		Bundle b = new Bundle();
		b.putInt("requestCode", 0);
		b.putString("sendAddr", sendAddr);
		b.putString("time", time.toLocaleString());
		b.putString("msgBody", msgBody);
		i.putExtras(b);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		arg0.startActivity(i);
	}

}
