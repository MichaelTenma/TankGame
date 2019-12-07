package com.lindont.TankGame.tools;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class JFrameCenteral {
	
	/*
	 * JFrame 居中
	 * 获取屏幕中点，以及JFrame的中心
	 * 以此为原点，画JFrame
	 * 
	 */
	private static JFrameCenteral jfc = null;
	private JFrameCenteral(){
		
	}
	
	public static synchronized JFrameCenteral getJFrameCenteral(){
		if(JFrameCenteral.jfc == null){
			JFrameCenteral.jfc = new JFrameCenteral();
		}
		return JFrameCenteral.jfc;
	}
	
	public int[] getLocation(int width,int height,JFrame jf){
		int screenSize[] = JFrameCenteral.getScreenSize();
		int screenWidth = screenSize[0],screenHeight = screenSize[1];
		int[] location = this.middleLocaltion(width, height, screenWidth, screenHeight);
		return location;
	}
	
	public static int[] getScreenSize(){
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int)screensize.getWidth();
		int screenHeight = (int)screensize.getHeight();
		int[] size = {screenWidth,screenHeight};
		return size;
	}
	
	public int[] middleLocaltion(int width,int height,int screenWidth,int screenHeight) {
		int middleLocation_X = this.getMiddleLocation(width); 
		int middleLocation_Y = this.getMiddleLocation(height);
		
		int screenMiddleLocation_X = this.getMiddleLocation(screenWidth); 
		int screenMiddleLocation_Y = this.getMiddleLocation(screenHeight); 
		
		int location_X=0,location_Y=0;
		
		location_X = middle(middleLocation_X, screenMiddleLocation_X);
		location_Y = middle(middleLocation_Y, screenMiddleLocation_Y);

		int location[] = {location_X,location_Y};
		return location;
	}
	
	public int middle(int middleLocation,int screenMiddleLocation){
		return  (screenMiddleLocation - middleLocation);
	}
	
	public int getMiddleLocation(int length){
		return (int)(length/2);
	}
	
	
	
}
