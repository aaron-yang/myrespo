package com.example.booksearch;

public class NetResponse {
	private int code;
	private Object message;
	
	public NetResponse(int code, Object message) {
		super();
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public Object getMessage() {
		return message;
	}
	public void setMessage(Object message) {
		this.message = message;
	}
	
	
	
}