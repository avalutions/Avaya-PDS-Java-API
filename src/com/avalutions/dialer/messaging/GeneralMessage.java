package com.avalutions.dialer.messaging;

public class GeneralMessage implements Message {
	private String message;
	private boolean isError;
	
	public GeneralMessage(String message) {
		this(message, false);
	}
	
	public GeneralMessage(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public boolean isError() {
		return isError;
	}
}
