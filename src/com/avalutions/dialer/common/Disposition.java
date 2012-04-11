package com.avalutions.dialer.common;

public class Disposition {
    private int code;
    private String description;
    private String script;
    
    public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getScript() {
		return script;
	}

	public Disposition(int code, String description, String script) {
    	this.code = code;
    	this.description = description;
    	this.script = script;
    }
}
