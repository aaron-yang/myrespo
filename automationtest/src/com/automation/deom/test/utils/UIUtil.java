package com.automation.deom.test.utils;

import android.app.Activity;
import android.app.Instrumentation;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class UIUtil {

	private Solo solo;

	public UIUtil(Instrumentation instrumentation, Activity activity) {
		solo = new Solo(instrumentation, activity);
	}

	public void clearEditText(EditText editText) {
		solo.clearEditText(editText);

	}

	public void enterText(EditText editText, String text) {
		solo.enterText(editText, text);
	}

	public void clickOnText(String text) {
		solo.clickOnText(text);
	}

	public void clickOnView(View view) {
		solo.clickOnView(view);
	}

	public void waitForText(String text, int minimumNumberOfMatches,
			long timeout) {
		solo.waitForText(text, minimumNumberOfMatches, timeout);
	}

	public Activity getCurrentActivity() {
		return (Activity) solo.getCurrentActivity();
	}

	public boolean searchText(String text, boolean onlyVisible) {
		return solo.searchText(text, onlyVisible);
	}

	public void finalize() throws Throwable {
		solo.finalize();
	}

	public void finishOpenedActivities() {
		solo.finishOpenedActivities();
	}
	
	/**
	 * Environment.getExternalStorageDirectory() + "/Robotium-Screenshots/"
	 * @param name
	 */
	public void takeScreenShot(String name){
		solo.takeScreenshot(name);
	}

}
