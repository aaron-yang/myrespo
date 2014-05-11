package com.automation.demo.test.runner;

import junit.framework.TestSuite;

import com.automation.demo.test.OrderedTestSuite;
import com.automation.demo.test.testsuite.TestSuite1;

public class Runner1 extends CustomRunner {
    @Override
    public TestSuite getAllTests() {
        OrderedTestSuite customSuite = new OrderedTestSuite();
        customSuite.addTest(TestSuite1.getTestSuite()); 
        return customSuite;
    }
}
