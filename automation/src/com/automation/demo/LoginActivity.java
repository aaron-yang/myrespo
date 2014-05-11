package com.automation.demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {
	
	private EditText name,password;
	private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    
    private void initView(){
    	name = (EditText)findViewById(R.id.name);
    	password = (EditText)findViewById(R.id.password);
    	loginBtn = (Button)findViewById(R.id.loginBtn);
    	loginBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if("".equals(name.getText().toString()) || "".equals(password.getText().toString())){
					Toast.makeText(getApplicationContext(), "Name or password shoule be not empty!", Toast.LENGTH_LONG).show();;
				}else if("user".equals(name.getText().toString()) && "123".equals(password.getText().toString())){
					Intent intent = new Intent(LoginActivity.this,MainActivity.class);
					startActivity(intent);
				}else if("admin".equals(name.getText().toString()) && "321".equals(password.getText().toString())){
					Intent intent = new Intent(LoginActivity.this,AdminMainActivity.class);
					startActivity(intent);
				}else{
					AlertDialog.Builder builder = new Builder(LoginActivity.this);
					builder.setMessage("The name or password is invalid, please try again!");
					builder.setPositiveButton("OK", new OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					builder.create().show();;
				}
			}
		});
    }
    
}
