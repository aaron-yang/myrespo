package com.automation.demo.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class OrderedTestSuite extends TestSuite {
    
    @SuppressWarnings("unchecked")
	@Override
    public void addTestSuite(@SuppressWarnings("rawtypes") Class testClass) {
            super.addTestSuite(testClass);
    }
    
    @Override
    public void addTest(Test test) {
        super.addTest(test);
    }

}
