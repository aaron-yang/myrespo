package com.aaronyang.note.test.entity;

public class TestCaseEntity {
	 
	public enum TestCaseStatus {
	        UNKNOWN {
	            @Override
	            public String toString() {
	                return "Unknown";
	            }
	        }, 
	        PASSED {
	            @Override
	            public String toString() {
	                return "Passed";
	            }
	        }, 
	        FAILURE{
	            @Override
	            public String toString() {
	                return "Failure";
	            }
	        },
	        ERROR{
	            @Override
	            public String toString() {
	                return "Error";
	            }
	        }
	    }
	 
	private String testCaseId = "0";
	private String priority = "P1";
	
	public TestCaseEntity(String testCaseId, String priority) {
		super();
		this.testCaseId = testCaseId;
		this.priority = priority;
	}

	public String getTestCaseId() {
		return testCaseId;
	}

	public String getPriority() {
		return priority;
	}
	
	
}
