package com.example.storage;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;

public class PhoneReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Bundle bundle = arg1.getExtras();
		String phoneNo = "";
		String phoneState = "";
		Date time = new Date(0);
		if(bundle != null)
			phoneState = bundle.getString(TelephonyManager.EXTRA_STATE);
		
		if(phoneState.equals(TelephonyManager.EXTRA_STATE_RINGING)){
			phoneNo = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
			Intent i = new Intent(arg0,SmsPhoneResultActivity.class);
			Bundle b = new Bundle();
			b.putInt("requestCode", 1);
			b.putString("phoneNo", phoneNo);
			i.putExtras(b);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			arg0.startActivity(i);
		}
	}

}
