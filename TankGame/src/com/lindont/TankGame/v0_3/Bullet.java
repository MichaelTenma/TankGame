package com.lindont.TankGame.v0_3;

import java.awt.Color;
import java.awt.Graphics;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

class Bullet{
	//子弹类
	private int x = 0,y = 0;
	private int bulletWidth = 2,bulletHeight = bulletWidth;//正方形容易操作,与炮筒大小一致
	private int speed = 10,time = 1;
	private boolean isLive = true;
	private Color bulletColor = Color.CYAN;//与坦克的默认颜色一致
	
	private Tank tank = null;//拥有该子弹的坦克名称
	
	public String bulletName = "";
	public int bulletRunTimes = 1;//子弹运行的次数
	
	public Bullet(Tank tank){
		this.bulletName = tank.getTankName();
		this.tank = tank;
		this.speed = tank.getBulletSpeed();//tank.getSpeed() + tank.getBulletSpeed();
		JFrameCenteral jfc = JFrameCenteral.getJFrameCenteral();
		
		int[] location = jfc.middleLocaltion(this.getBulletWidth(),this.getBulletHeight(),this.tank.getTankWidth(),this.tank.getTankHeight());
		
		this.x = location[0] + this.tank.getX();
		this.y = location[1] + this.tank.getY();
		int command = tank.getDirect();
		switch (command) {
		case TANKGAME_CODE.tankUpCode:
			this.y-=tank.getTankHeight()/2-5;
			break;
		case TANKGAME_CODE.tankDownCode:
			this.y+=tank.getTankHeight()/2+5;
			break;
		case TANKGAME_CODE.tankLeftCode:
			this.x-=tank.getTankWidth()/2-5;
			break;
		case TANKGAME_CODE.tankRightCode:
			this.x+=tank.getTankWidth()/2+5;
			break;
		default:
			break;
		}
		
		this.direct = command;
		this.bulletColor = tank.getTankColor();
	}

	public void drawBullet(Graphics g){
		g.setColor(this.getBulletColor());
		g.fill3DRect(this.x,this.y,this.tank.getAttackWidth(),this.tank.getAttackWidth(),false);
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

	public int getBulletWidth() {
		return bulletWidth;
	}

	public void setBulletWidth(int bulletWidth) {
		this.bulletWidth = bulletWidth;
	}

	public int getBulletHeight() {
		return bulletHeight;
	}

	public void setBulletHeight(int bulletHeight) {
		this.bulletHeight = bulletHeight;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public boolean isLive() {
		return isLive;
	}

	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}

	public Color getBulletColor() {
		return bulletColor;
	}

	public void setBulletColor(Color bulletColor) {
		this.bulletColor = bulletColor;
	}
	
	private int direct = TANKGAME_CODE.tankUpCode;

	public void bulletMove(){
		boolean pass = false;
		int command = this.getDirect();
		switch (command) {
		case TANKGAME_CODE.tankUpCode:
			this.y-=this.speed;
			pass = true;
			break;
		case TANKGAME_CODE.tankDownCode:
			this.y+=this.speed;
			pass = true;
			break;
		case TANKGAME_CODE.tankLeftCode:
			this.x-=this.speed;
			pass = true;
			break;
		case TANKGAME_CODE.tankRightCode:
			this.x+=this.speed;
			pass = true;
			break;
		default:
			break;
		}
		
		
		if(pass){
			for(int k = 0;k < Data_ControlObstacle.forbiddenArea.size();k++){
				ForbiddenObstacle temp = Data_ControlObstacle.forbiddenArea.get(k);
				Tank tankTemp = temp.getTank();
				int obstacleX = tankTemp.getX(),obstacleY = tankTemp.getY();
				int tempX = obstacleX-this.bulletWidth;
				int tempY = obstacleY-this.bulletHeight;
				int tempEndX = obstacleX + tankTemp.getTankWidth(),tempEndY = obstacleY + tankTemp.getTankHeight();
				//判断坦克是否进入禁区
				if(this.x>tempX&&this.y>tempY&&this.x<tempEndX&&this.y<tempEndY){
					this.isLive = false;
					if(!tankTemp.getTankName().equals(this.bulletName)){
						for(int i = 0;i < Data_NowTank.tankVector.size();i++){
							Tank tank = Data_NowTank.tankVector.get(i);
							if(tank.getTankName().equals(tankTemp.getTankName())){
								if(tank.getLife()<=0){
									//如果碰到坦克，即让坦克死亡
									if(tank.isUser()){
										int tempUser = 0;
										if(!tank.isUserOne()){
											tempUser = 1;
										}
										TANKGAME_CODE.removeUser(tempUser);
									}
									tank.setLive(false);
								}else{
									//扣一滴血
									tank.setLife(tank.getLife()-1);
								}
								
//								System.out.println("Over-障碍物:" + temp.getObstacleName());
							}
						}
					}
				}
			}
		}
		

//		System.out.printf("\nbullet------(%d,%d)",this.x,this.y);
	}

	public int getDirect() {
		return direct;
	}

	public void setDirect(int direct) {
		this.direct = direct;
	}

}
