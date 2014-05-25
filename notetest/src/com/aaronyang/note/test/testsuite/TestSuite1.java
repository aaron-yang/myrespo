package com.aaronyang.note.test.testsuite;

import junit.framework.TestSuite;

import com.aaronyang.note.test.testscripts.login.AK1;
import com.aaronyang.note.test.testscripts.login.AK2;

public class TestSuite1 {
	/**
	 * add classes to suite
	 * @return
	 */
	public static TestSuite getTestSuite() {
		TestSuite suite = new TestSuite();
		suite.addTestSuite(AK1.class);
		suite.addTestSuite(AK2.class);
		return suite;
	}

}
