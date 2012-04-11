package com.avalutions.dialer.common;

import java.util.ArrayList;
import java.util.List;

public class Screen {
	private String name;
	private List<Field> fields;
	
	public Screen(String name) {
		this.name = name;
		this.fields = new ArrayList<Field>();
	}
	
	public void addField(Field field) {
		fields.add(field);
	}
	
	public List<Field> getFields() {
		return fields;
	}
	
	public String getName() {
		return name;
	}
}
