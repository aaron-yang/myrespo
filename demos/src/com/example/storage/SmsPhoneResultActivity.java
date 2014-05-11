package com.example.storage;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class SmsPhoneResultActivity extends Activity {
	private TextView tvInfor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sms_phone_result);
		tvInfor = (TextView) findViewById(R.id.tvInfor);
		showResult();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sms_phone_result, menu);
		return true;
	}
	
	private void showResult(){
		Bundle bundle = this.getIntent().getExtras();
		int requestCode = bundle.getInt("requestCode");
		StringBuilder sb = new StringBuilder();
		switch(requestCode){
		case 0:
			sb.append("发送信息地址："+bundle.getString("sendAddr")+"\n");
			sb.append("发送时间："+bundle.getString("time")+"\n");
			sb.append("消息内容："+bundle.getString("msgBody")+"\n");
			tvInfor.setText(sb);
			break;
		case 1:
			sb.append("来电号码："+bundle.getString("phoneNo")+"\n");
			tvInfor.setText(sb);
			break;
		}
	}

}
