package com.aaronyang.note.test.testscripts.login;

import com.aaronyang.note.test.BasicTestCase;

public class AK1 extends BasicTestCase{
	
	@Override
	protected void setUp() throws Exception{
		// TODO Auto-generated method stub
		super.setUp("AK-1","P1");
	}
	
	public void testAK1() {
		uiHelper.getElementLogin().enterEmail("");
		uiHelper.getElementLogin().enterPassword("");
		uiHelper.getElementLogin().clickLoginButton();
		assertTrue(uiHelper.getSoloWrapper().searchText("Email or password can not be empty!", true));
	}
}
