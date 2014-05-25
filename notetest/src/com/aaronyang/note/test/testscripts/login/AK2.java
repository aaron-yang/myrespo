package com.aaronyang.note.test.testscripts.login;

import com.aaronyang.note.test.BasicTestCase;

public class AK2 extends BasicTestCase{
	@Override
	protected void setUp() throws Exception{
		// TODO Auto-generated method stub
		super.setUp("AK-2","P0");
	}
	
	public void testAK2() {
		uiHelper.getElementLogin().enterEmail("abc");
		uiHelper.getElementLogin().enterPassword("def");
		uiHelper.getElementLogin().clickLoginButton();
		assertTrue(uiHelper.getSoloWrapper().searchText("Email or password is not match!", true));
	}
}
