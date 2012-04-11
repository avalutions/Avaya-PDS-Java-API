package com.avalutions.dialer.common;

public abstract class Field {
    private String name;
	private Integer xPosition;
    private Integer yPosition;
    private int width;

    public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getxPosition() {
		return xPosition;
	}
	public void setxPosition(Integer xPosition) {
		this.xPosition = xPosition;
	}
	public Integer getyPosition() {
		return yPosition;
	}
	public void setyPosition(Integer yPosition) {
		this.yPosition = yPosition;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public Field(String name, Integer xPosition, Integer yPosition, int width) {
		this.name = name;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.width = width;
	}

}
