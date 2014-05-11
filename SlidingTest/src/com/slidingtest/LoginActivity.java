package com.slidingtest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.slidingtest.constant.CommonFields;

public class LoginActivity extends Activity {
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        sp = getSharedPreferences(CommonFields.CONFIGURETION, Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                if (sp.getBoolean(CommonFields.SWITCHER_STATUS, false) && !"".equals(sp.getString(CommonFields.KEY, ""))) {
                    Intent intent = new Intent(LoginActivity.this, LockActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                LoginActivity.this.finish();
            }

        }, 1500);
    }

}
