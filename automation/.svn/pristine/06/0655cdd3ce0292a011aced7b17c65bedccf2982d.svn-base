package com.automation.deom.test.elements;

import com.automation.demo.R;
import com.automation.deom.test.utils.UIUtil;

import android.widget.Button;
import android.widget.EditText;

public class LoginActivityElements {
	public static final String LABEL = "Welcome";
	
	public static EditText getNameEditText(UIUtil uiUtil){
		uiUtil.waitForText(LABEL,1,5000);
		return (EditText)uiUtil.getCurrentActivity().findViewById(R.id.name);
	}
	
	public static EditText getPasswordEditText(UIUtil uiUtil){
		uiUtil.waitForText(LABEL,1,5000);
		return (EditText)uiUtil.getCurrentActivity().findViewById(R.id.password);
	}
	
	public static Button getLoginButton(UIUtil uiUtil){
		uiUtil.waitForText(LABEL,1,5000);
		return (Button)uiUtil.getCurrentActivity().findViewById(R.id.loginBtn);
	}

}
