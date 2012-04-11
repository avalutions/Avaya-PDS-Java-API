package com.avalutions.dialer.common;

public enum JobStatus {
	Active(65),
    Inactive(73);
    
    int value;
    
    private JobStatus(int value) {
    	this.value = value;
    }
    
    public int getValue() {
    	return value;
    }
}
