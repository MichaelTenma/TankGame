package com.lindont.TankGame.v0_4;

import java.awt.Color;

import com.lindont.TankGame.tools.TANKGAME_CODE;

public class Thing implements Cloneable{
	protected String name = "";
	protected int x = 0;
	protected int y = 0;
	protected int width = 0,height = 0;
	protected int type = TANKGAME_CODE.thingType_OBSTACLE;
	protected Color color = TANKGAME_CODE.basicColor;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public Object clone() {
        Object o = null;  
        try {  
            o = super.clone();  
        } catch (CloneNotSupportedException e) {  
            e.printStackTrace();  
        }  
        return o;  
	}
	
	public String toString(){
		return String.format("Name:%s x:%d y:%d width:%d height:%d", name,x,y,width,height);
	}
	

}
