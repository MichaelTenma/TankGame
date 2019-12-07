package com.lindont.TankGame.v0_4;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_NowObstacle extends Data_ControlThing<Obstacle>{
	public static Vector<Obstacle> obstacleVector = new Vector<Obstacle>();
	
	public boolean add(Obstacle obstacle){
		return super.add(Data_NowObstacle.obstacleVector,obstacle);
	}
	
	public boolean remove(Obstacle obstacle){
		return super.remove(Data_NowObstacle.obstacleVector,obstacle);
	}
	
	public Obstacle get(int index){
		return super.get(Data_NowObstacle.obstacleVector,index);
	}
	
	public int checkVector(Obstacle obstacle){
		return super.checkVector(Data_NowObstacle.obstacleVector, obstacle);
	}
	
	public Vector<Obstacle> getByName(String name){
		Vector<Obstacle> obstacleListByName = new Vector<Obstacle>();
		for(int i = 0;i < Data_ControlBullet.bulletList.size();i++){
			Obstacle obstacle = Data_NowObstacle.obstacleVector.get(i);
			if(obstacle.getName().equals(name)){
				if(this.checkVector(obstacleListByName,obstacle)==TANKGAME_CODE.failCode){
					obstacleListByName.add(obstacle);
				}
			}
		}
		return obstacleListByName;
	}
	
	public int checkVector(Vector<Obstacle> tVector,Obstacle obstacle){
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < tVector.size();i++){
			if(tVector.get(i)==obstacle){
				result = i;
			}
		}
		return result;
	}
}
