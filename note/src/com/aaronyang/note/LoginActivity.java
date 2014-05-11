package com.aaronyang.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private EditText emailET, passwordET;
	private Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();
	}

	private void initViews(){
		emailET = (EditText) findViewById(R.id.emailET);
		passwordET = (EditText) findViewById(R.id.passwordET);
		loginBtn = (Button) findViewById(R.id.loginBtn);
		loginBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if("1".equals(emailET.getText().toString()) && "1".equals(passwordET.getText().toString())){
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra("isAdminUser", true);
					startActivity(intent);
					LoginActivity.this.finish();
				}else if("2".equals(emailET.getText().toString()) && "2".equals(passwordET.getText().toString())){
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					intent.putExtra("isAdminUser", false);
					startActivity(intent);
					LoginActivity.this.finish();
				}else{
					Toast.makeText(getApplicationContext(), "Email or password is not match!", Toast.LENGTH_LONG).show();
				}
				
			}
			
		});
	}

}
