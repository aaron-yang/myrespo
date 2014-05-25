package com.aaronyang.note.test.runner;

import junit.framework.TestSuite;

import com.aaronyang.note.test.testsuite.TestSuite1;

/**
 * in order to avoid rewrite all login again in different runner,
 * so extends CustomRunner,put common logic in CustomRunner
 * @author hiworld
 *
 */
public class Runner1 extends CustomRunner {
	
	
	/**
	 * add suites to runner
	 */
    @Override
    public TestSuite getAllTests() {
        TestSuite allSuites = new TestSuite();
        allSuites.addTest(TestSuite1.getTestSuite()); 
        return allSuites;
    }
}
