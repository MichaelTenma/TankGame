package com.lindont.TankGame.v0_1;

import java.awt.Color;
import java.awt.Graphics;

import com.lindont.TankGame.tools.JFrameCenteral;

public class Tank{
	
	public String getTankName() {
		return tankName;
	}

	public void setTankName(String tankName) {
		this.tankName = tankName;
	}

	public int getRunSpeed() {
		return runSpeed;
	}

	public void setRunSpeed(int runSpeed) {
		this.runSpeed = runSpeed;
	}

	public int getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = runSpeed + bulletSpeed;
	}

	public int getBulletTimes() {
		return bulletTimes;
	}

	public void setBulletTimes(int bulletTimes) {
		this.bulletTimes = bulletTimes;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	String tankName = "NULL";//坦克名称
	int runSpeed = 5;//坦克运动速度
	private int bulletSpeed = runSpeed + 5;//子弹速度
	int bulletTimes = 3;//单位时间发送子弹的数量
	int life = 5;//生命
	
	
	int tankWidth = 0,tankHeight = 0;//坦克的宽高
	
	public int getTankWidth() {
		return tankWidth;
	}
	public void setTankWidth(int tankWidth) {
		this.tankWidth = tankWidth;
	}
	public int getTankHeight() {
		return tankHeight;
	}
	public void setTankHeight(int tankHeight) {
		this.tankHeight = tankHeight;
	}
	private int direct = 0;//坦克的方向	
	//	0-向上	1-向下	2-向左	3-向右
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct){
		this.direct = direct;
	}
	public Tank(String tankName,Color tankColor,int runSpeed,int bulletSpeed,int bulletTimes,int life,int[] origin){
		//origin
		int x = origin[0];
		int y = origin[1];
		this.setOrigin(x, y);
		this.setTankName(tankName);
		this.setTankColor(tankColor);
		this.setRunSpeed(runSpeed);
		this.setBulletSpeed(bulletSpeed);
		this.setBulletTimes(bulletTimes);
		this.setLife(life);
	}
	private static int areaWidth = 0;
	private static int areaHeight = 0;
	//坦克的活动区域宽高
	public static int getAreaWidth() {
		return areaWidth;
	}
	public void setAreaWidth(int areaWidth) {
		Tank.setjFrameWidth(areaWidth);
		Tank.areaWidth = areaWidth - this.tankWidth;
	}
	public static int getAreaHeight() {
		return areaHeight;
	}
	public void setAreaHeight(int areaHeight) {
		Tank.setjFrameHeight(areaHeight);
		Tank.areaHeight = areaHeight - this.tankHeight;
	}
	//------------------------窗口宽高_start----------------------
	private static int jFrameWidth = 0;
	private static int jFrameHeight = 0;
	public static int getjFrameWidth() {
		return jFrameWidth;
	}
	public static void setjFrameWidth(int jFrameWidth) {
		Tank.jFrameWidth = jFrameWidth;
	}
	public static int getjFrameHeight() {
		return jFrameHeight;
	}
	public static void setjFrameHeight(int jFrameHeight) {
		Tank.jFrameHeight = jFrameHeight;
	}
	//-----------------------窗口宽高_end------------------------
	private int[] origin = new int[2];
	public void setOrigin(int x,int y){
		//限制范围
		if(x >= 0 && y >= 0 && x<= Tank.areaWidth && y <= Tank.areaHeight){
			this.origin[0] = x;
			this.origin[1] = y;
		}
	}
	
	public int[] getOrigin(){
		return this.origin;
	}
	
	private Color tankColor = null;
	
	public Color getTankColor() {
		return tankColor;
	}

	public void setTankColor(Color tankColor) {
		this.tankColor = tankColor;
	}

	public void drawTank(){
		Graphics g = this.getG();
		int jFraemWidth = Tank.getjFrameWidth();
		
		g.setColor(this.getTankColor());
		//设置坦克的左轮宽，以此为基准画坦克
		int tankWidth_Ball=jFraemWidth/80;
		//获取坦克坐标原点
		int[] origin = this.getOrigin();
		int x = origin[0],y = origin[1];
		
		int tankHeight_Ball = 6 * tankWidth_Ball;
		int tankWidth_CenterRect = (int)((2*tankWidth_Ball/0.382)*0.618),tankHeight_CenterRect = 4 * tankWidth_Ball;
		int tankX_Ball = x+tankWidth_Ball,tankY_Ball = y+(tankHeight_Ball-tankHeight_CenterRect)/2;
		int tankWidth_Oval = (int)(1.3*tankWidth_CenterRect*0.618),tankHeight_Oval = tankWidth_Oval;
		
		//获取中圆圆心坐标
		JFrameCenteral jFrameCenteral = JFrameCenteral.getJFrameCenteral();
		int tankX_Ball_right= tankX_Ball+tankWidth_CenterRect;
		int tankY_Ball_right = y;
		int []temp = null;
		
		if(this.getDirect()==2||this.getDirect()==3){
			temp = this.replace(tankWidth_Ball,tankHeight_Ball);
			tankWidth_Ball = temp[0];
			tankHeight_Ball = temp[1];
			
			temp = this.replace(tankWidth_CenterRect,tankHeight_CenterRect);
			tankWidth_CenterRect = temp[0];
			tankHeight_CenterRect = temp[1];
			
			tankX_Ball_right = x;
			tankY_Ball_right += tankWidth_CenterRect;
		}
		
		int[] tankLocation_Oval = jFrameCenteral.middleLocaltion(tankWidth_Oval, tankHeight_Oval, tankWidth_CenterRect, tankHeight_CenterRect);
		int tankX_Oval = tankLocation_Oval[0],tankY_Oval = tankLocation_Oval[1];
		tankX_Oval += tankX_Ball;
		tankY_Oval += tankY_Ball;
		
		int tankWidth_Power = (int) (tankWidth_Oval*0.2);
		int tankX_line = tankX_Oval + tankWidth_Oval/2-tankWidth_Power/2,tankY_line = tankY_Oval + tankHeight_Oval/2;
		int tankY_line_end = y;
		
		g.fill3DRect(x, y, tankWidth_Ball, tankHeight_Ball, false);
		g.fill3DRect(tankX_Ball, tankY_Ball, tankWidth_CenterRect, tankHeight_CenterRect,false);
		
		//中圆
		g.fillOval(tankX_Oval, tankY_Oval, tankWidth_Oval, tankHeight_Oval);
		
		//--------------以中圆圆心画炮台start-----------------
		int tankYEnd_Power = tankY_line_end - tankY_line;
		int tankXEnd_Power = tankWidth_Power;
		if(this.getDirect() == 1){
			tankY_line += (int)(tankHeight_Ball/2);
		}
		if(this.getDirect() == 2 || this.getDirect() == 3){
			temp = this.replace(tankXEnd_Power,tankYEnd_Power);
			tankXEnd_Power = temp[0];
			tankYEnd_Power = temp[1];
			if(this.getDirect() == 3){
				tankXEnd_Power += tankWidth_Ball;
			}
		}
		g.fill3DRect(tankX_line, tankY_line, tankXEnd_Power,tankYEnd_Power, false);
		//--------------以中圆圆心画炮台end-----------------
		//右边车轮
		g.fill3DRect(tankX_Ball_right, tankY_Ball_right, tankWidth_Ball, tankHeight_Ball, false);
		//描边
		g.setColor(Color.white);
		g.drawRect(x, y, tankWidth_Ball, tankHeight_Ball);
		g.drawRect(tankX_Ball_right, tankY_Ball_right, tankWidth_Ball, tankHeight_Ball);
		
		this.tankWidth = 2*tankWidth_Ball + tankWidth_CenterRect;
		this.tankHeight = tankHeight_Ball;
		if(this.getDirect() == 2 || this.getDirect() == 3){
			this.tankWidth = tankWidth_Ball;
			this.tankHeight = 2*tankHeight_Ball + tankHeight_CenterRect;
		}
//		g.fill3DRect(0, 0, this.tankWidth, this.tankHeight , false);
	}
	private int[] replace(int a,int b){
		int temp = a;a=b;b=temp;
		int[] result = {a,b};
		return result;
	}

	
	private boolean thisTankIsMove = false;
	
	
	public boolean isThisTankIsMove() {
		return thisTankIsMove;
	}

	public void setThisTankIsMove(boolean thisTankIsMove) {
		this.thisTankIsMove = thisTankIsMove;
	}

	public int checkDirect(int keyCode,int[] key){
		int direct = this.getDirect();
		if(keyCode == key[0]){
			direct = 0;
		}else if(keyCode == key[1]){
			direct = 1;
		}else if(keyCode == key[2]){
			direct = 2;
		}else if(keyCode == key[3]){
			direct = 3;
		}
//		System.out.println("change direct");
		return direct;
	}
	
	public void tankMove(int direct){
			int result[] = this.getOrigin();
			int x = result[0];
			int y = result[1];
			if(direct==0){
				y-=this.runSpeed;
			}else if(direct==1){
				y+=this.runSpeed;
			}else if(direct==2){
				x-=this.runSpeed;
			}else if(direct==3){
				x+=this.runSpeed;
			}
			System.out.println(this.getTankName() + ":(" + x +"," + y + ")");
			this.setDirect(direct);
			this.setOrigin(x, y);
	}
	
	private Graphics g = null;
	public Graphics getG() {
		return g;
	}
	public void setG(Graphics g) {
		this.g = g;
	}


}
