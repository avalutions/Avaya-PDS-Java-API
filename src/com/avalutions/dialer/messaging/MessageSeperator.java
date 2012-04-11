package com.avalutions.dialer.messaging;

public enum MessageSeperator {
	Terminator(3),
	Delimiter(30);
	
	private final int value;
	
	private MessageSeperator(int value) {
		this.value = value;
	}
	
	char getCharacter() {
		return (char)value;
	}
}
