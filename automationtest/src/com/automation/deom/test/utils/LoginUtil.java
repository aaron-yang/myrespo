package com.automation.deom.test.utils;

import com.automation.demo.test.configrationfiles.ConfigrationFile;
import com.automation.deom.test.elements.LoginActivityElements;

public class LoginUtil {
	
	public static void doLogin(String[] account,UIUtil uiUtil){
		uiUtil.waitForText(LoginActivityElements.LABEL, 1, ConfigrationFile.TIMEOUT_5);
		uiUtil.enterText(LoginActivityElements.getNameEditText(uiUtil), account[0]);
		uiUtil.enterText(LoginActivityElements.getPasswordEditText(uiUtil), account[1]);
		uiUtil.clickOnView(LoginActivityElements.getLoginButton(uiUtil));
	}

}
