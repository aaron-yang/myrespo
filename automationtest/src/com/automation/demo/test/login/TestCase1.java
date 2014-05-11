package com.automation.demo.test.login;


import com.automation.demo.test.BasicTestCase;
import com.automation.demo.test.R;
import com.automation.deom.test.elements.LoginActivityElements;

public class TestCase1 extends BasicTestCase{

	@Override
	protected void setUp() throws Exception{
		// TODO Auto-generated method stub
		super.setUp("AU-1","P1");
	}
	
	public void testTestCase1() {
		String errorMsg = getInstrumentation().getContext().getString(R.string.empty_account_error);
		getUIUtil().clickOnView(LoginActivityElements.getLoginButton(getUIUtil()));
		assertTrue("Error Message didn't appear!",getUIUtil().searchText(errorMsg,true));
	}

}
