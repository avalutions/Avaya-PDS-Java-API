package com.avalutions.dialer.common;

public enum CallType {
    All(65),
    Inbound(73),
    Outbound(79),
    Blend(66),
    Managed(77);
    
    int value;
    
    private CallType(int value) {
    	this.value = value;
    }
    
    public int getValue() {
    	return value;
    }
}
