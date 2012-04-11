package com.avalutions.dialer.common;

import java.util.ArrayList;
import java.util.List;

public class DataField extends Field {
    private String value;
    private boolean isDirty;
    private int length;
    private String type;
    private List<String> possibleValues;
    
	public DataField(String name, Integer xPosition, Integer yPosition, int width, String type) {
		super(name, xPosition, yPosition, width);
		this.type = type;
		this.possibleValues = new ArrayList<String>();
	}
    
	public DataField(String name, Integer xPosition, Integer yPosition, int width, String type, String value) {
		this(name, xPosition, yPosition, width, type);
		this.value = value;
	}
	
	public void addPossibleValue(String value) {
		possibleValues.add(value);
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		if(this.value != value) {
			isDirty = true;
		}
		this.value = value;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getPossibleValues() {
		return possibleValues;
	}
	public void setPossibleValues(List<String> possibleValues) {
		this.possibleValues = possibleValues;
	}
	public boolean isDirty() {
		return isDirty;
	}
}
