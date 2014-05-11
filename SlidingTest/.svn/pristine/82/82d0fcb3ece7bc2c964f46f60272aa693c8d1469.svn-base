package com.slidingtest;

import com.slidingtest.constant.CommonFields;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LockActivity extends Activity {
    
    private EditText password = null;
    private Button enter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        password = (EditText) findViewById(R.id.password);
        enter = (Button) findViewById(R.id.enter);
        enter.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences sp = LockActivity.this.getSharedPreferences(CommonFields.CONFIGURETION,
                        Context.MODE_PRIVATE);
                if("".equals(password.getText().toString())){
                    Toast.makeText(v.getContext(), "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else if(sp.getString(CommonFields.KEY, "").equals(password.getText().toString())){
                    Intent intent = new Intent(LockActivity.this,MainActivity.class);
                    startActivity(intent);
                    LockActivity.this.finish();
                }else{
                    Toast.makeText(v.getContext(), "输入的密码不正确！", Toast.LENGTH_SHORT).show(); 
                }
            }
            
        });
    }

}
