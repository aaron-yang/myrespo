package com.example.storage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button internalStorage,externalStorage,webview,optionsMenu,notification,mediaplayer1,mediaplayer2,smsPhone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.internalStorage:
			startActivity(InternalStorageActivity.class);
			break;
		case R.id.externalStorage:
			startActivity(ExternalStorageActivity.class);
			break;
		case R.id.webview:
			startActivity(WebViewActivity.class);
			break;
		case R.id.optionsMenu:
			startActivity(OptionsMenuActivity.class);
			break;
		case R.id.notification:
			startActivity(NotificationActivity.class);
			break;
		case R.id.mediaplayer1:
			startActivity(MediaPlayerFromResouceActivity.class);
			break;
		case R.id.mediaplayer2:
			startActivity(MediaPlayerFromWebActivity.class);
			break;	
		case R.id.smsPhone:
			startActivity(SmsPhoneActivity.class);
			break;	
		}
		
	}

	private void findViews(){
		internalStorage = (Button) findViewById(R.id.internalStorage);
		internalStorage.setOnClickListener(this);
		externalStorage = (Button) findViewById(R.id.externalStorage);
		externalStorage.setOnClickListener(this);
		webview = (Button)findViewById(R.id.webview);
		webview.setOnClickListener(this);
		optionsMenu = (Button) findViewById(R.id.optionsMenu);
		optionsMenu.setOnClickListener(this);
		notification = (Button) findViewById(R.id.notification);
		notification.setOnClickListener(this);
		mediaplayer1 = (Button) findViewById(R.id.mediaplayer1);
		mediaplayer1.setOnClickListener(this);
		mediaplayer2 = (Button) findViewById(R.id.mediaplayer2);
		mediaplayer2.setOnClickListener(this);
		smsPhone = (Button) findViewById(R.id.smsPhone);
		smsPhone.setOnClickListener(this);
	}
	
	private void startActivity(Class<?> cls){
		Intent intent = new Intent(this,cls);
		startActivity(intent);
	}

}
