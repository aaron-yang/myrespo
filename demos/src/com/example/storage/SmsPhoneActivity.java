package com.example.storage;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class SmsPhoneActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_phone);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sms_phone, menu);
		return true;
	}

}
