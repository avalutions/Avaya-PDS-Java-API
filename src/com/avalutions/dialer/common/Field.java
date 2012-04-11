package com.avalutions.dialer.common;

public abstract class Field {
    private String name;
	private int xPosition;
    private int yPosition;
    private int width;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getxPosition() {
		return xPosition;
	}
	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	public int getyPosition() {
		return yPosition;
	}
	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public Field(String name, int xPosition, int yPosition, int width) {
		this.name = name;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
	}

}
