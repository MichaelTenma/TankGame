package com.lindont.TankGame.v0_4;

import java.awt.Color;
import java.awt.Graphics;

import com.lindont.TankGame.tools.TANKGAME_CODE;

public class Obstacle extends Thing{
	//障碍物类
	protected boolean isLife = false;
	protected boolean isLive = true;
	protected boolean isCanDie = true;//该障碍物是否能死亡
	
	protected int obstacleType = TANKGAME_CODE.obstacleType_Wall;
	
	private Obstacle(String name,int x,int y,int width,int height){
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.setType(TANKGAME_CODE.thingType_OBSTACLE);
	}
	
	public Obstacle(String name, int x, int y, int width, int height, boolean isLive, Color color, boolean isCanDie,int obstacleType) {
		// TODO Auto-generated constructor stub
		this(name, x, y, width, height);
		this.isLive = isLive;
		this.color = color;
		this.isCanDie = isCanDie;
		this.obstacleType = obstacleType;
	}

	public boolean isLife() {
		return isLife;
	}
	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	
	protected void firstRun(Obstacle obstacle){
		//注意不能通过该类直接调用该函数
		//第一次运行则,将自己加入障碍物
		Data_ControlObstacle dco = new Data_ControlObstacle();
		ForbiddenObstacle forbiddenObstacle = new ForbiddenObstacle(obstacle,TANKGAME_CODE.thingType_OBSTACLE);
		dco.add(forbiddenObstacle);
//		boolean pass = dco.add(forbiddenObstacle);
//		System.out.println("加入障碍物" + (pass?"成功":"失败"));
	}

	protected boolean isCanDie() {
		return isCanDie;
	}

	protected void setCanDie(boolean isCanDie) {
		this.isCanDie = isCanDie;
	}


}

abstract class ObstacleThing extends Obstacle{
	//任何障碍物都只能继承ObstacleThing类,而不能继承Obstacle类

	public ObstacleThing(String name, int x, int y, int width, int height, boolean isLive, Color color, boolean isCanDie, int obstacleType) {
		super(name, x, y, width, height,isLive, color, isCanDie, obstacleType);
	}
	
	public abstract void drawObstacle(Graphics g);
}

class Wall extends ObstacleThing{
	
	public Wall(Obstacle obstacle) {
		super(obstacle.name, obstacle.x,obstacle.y, obstacle.width, obstacle.height,obstacle.isLive,obstacle.color,obstacle.isCanDie,obstacle.obstacleType);
//		super.obstacleType = TANKGAME_CODE.obstacleType_Wall;
	}

	public void drawObstacle(Graphics g) {
		g.setColor(this.color);
		g.fill3DRect(this.x, this.y, this.width, this.height, false);
	}
	
	public void firstRun(){
		super.firstRun(this);
	}
	
}