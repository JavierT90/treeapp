package com.treeapp.exceptions;

public class RegisterException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private int code;
	
	public RegisterException(String message, int code) {
		super(message);
		this.code = code;
	}

	@Override
	public String toString() {
		return getMessage();
	}
	
	public int getCode() {
		return code;
	}
}
