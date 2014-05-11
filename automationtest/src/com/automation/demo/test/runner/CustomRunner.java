package com.automation.demo.test.runner;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;


public class CustomRunner extends InstrumentationTestRunner {

    @Override
    public void onCreate(Bundle arguments) {
    	  super.onCreate(arguments);
    }
}
