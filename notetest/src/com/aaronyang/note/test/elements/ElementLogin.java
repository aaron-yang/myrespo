package com.aaronyang.note.test.elements;

import android.widget.Button;
import android.widget.EditText;

import com.aaronyang.note.R;
import com.aaronyang.note.test.SoloWrapper;

public class ElementLogin {
	private SoloWrapper soloWrapper;
	private EditText emailEditText,passwordEditText;
	private Button loginButton;
	
	public ElementLogin(SoloWrapper soloWrapper){
		this.soloWrapper = soloWrapper;
		initViews();
	}
	
	private void initViews(){
		emailEditText = (EditText) soloWrapper.getCurrentActivity().findViewById(R.id.emailET);
		passwordEditText = (EditText) soloWrapper.getCurrentActivity().findViewById(R.id.passwordET);
		loginButton = (Button) soloWrapper.getCurrentActivity().findViewById(R.id.loginBtn);
	}
	
	public EditText getEmailEditText(){
		return emailEditText;
	}
	
	public EditText getPasswordEditText(){
		return passwordEditText;
	}
	
	private Button getLoginButton(){
		return loginButton;
	}
	
	public void enterEmail(String text){
		soloWrapper.enterText(emailEditText, text);
	}
	
	public void enterPassword(String text){
		soloWrapper.enterText(passwordEditText, text);
	}
	
	public void clickLoginButton(){
		soloWrapper.clickOnView(loginButton);
	}
	
}
