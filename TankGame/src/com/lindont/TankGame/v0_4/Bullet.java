package com.lindont.TankGame.v0_4;

import java.awt.Graphics;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

class Bullet extends Thing{
	//子弹类
	private int speed = 10,time = 1;
	private boolean isLive = true;
	private Tank tank = null;//拥有该子弹的坦克名称
	public int bulletRunTimes = 1;//子弹运行的次数
	
	public Bullet(Tank tank){
		this.setType(TANKGAME_CODE.thingType_BULLET);
		
		this.name = tank.getName();
		this.tank = tank;
		this.speed = tank.getBulletSpeed();//tank.getSpeed() + tank.getBulletSpeed();
		JFrameCenteral jfc = JFrameCenteral.getJFrameCenteral();
		
		int[] location = jfc.middleLocaltion(this.width,this.height,this.tank.getWidth(),this.tank.getHeight());
		
		this.x = location[0] + this.tank.getX();
		this.y = location[1] + this.tank.getY();
		int command = tank.getDirect();
		switch (command) {
		case TANKGAME_CODE.tankUpCode:
			this.y-=tank.getHeight()/2-5;
			break;
		case TANKGAME_CODE.tankDownCode:
			this.y+=tank.getHeight()/2+5;
			break;
		case TANKGAME_CODE.tankLeftCode:
			this.x-=tank.getWidth()/2-5;
			break;
		case TANKGAME_CODE.tankRightCode:
			this.x+=tank.getWidth()/2+5;
			break;
		default:
			break;
		}
		
		this.direct = command;
		this.color = tank.getColor();
	}

	public void drawBullet(Graphics g){
		g.setColor(this.color);
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
				Thing thingTemp = temp.getThing();
//				if(thingTemp.getType()==TANKGAME_CODE.thingType_TANK){
//					Tank tankTemp = (Tank)thingTemp;
					int obstacleX = thingTemp.getX(),obstacleY = thingTemp.getY();
					int tempX = obstacleX-this.width;
					int tempY = obstacleY-this.height;
					int tempEndX = obstacleX + thingTemp.getWidth(),tempEndY = obstacleY + thingTemp.getHeight();
					//判断坦克是否进入禁区
					if(this.x>tempX&&this.y>tempY&&this.x<tempEndX&&this.y<tempEndY){
						this.isLive = false;
						if(!thingTemp.getName().equals(this.name)){
							if(thingTemp.getType()==TANKGAME_CODE.thingType_TANK){
								for(int i = 0;i < Data_NowTank.tankVector.size();i++){
									Tank tank = Data_NowTank.tankVector.get(i);
									if(tank.getName().equals(thingTemp.getName())){
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
										
//										System.out.println("Over-障碍物:" + temp.getObstacleName());
									}
								}
							}else if(thingTemp.getType()==TANKGAME_CODE.thingType_OBSTACLE){
								Obstacle obstacle = (Obstacle) thingTemp;
								if(obstacle.isCanDie()){
									obstacle.setLive(false);
								}
							}
						}
					}
//				}
				
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
