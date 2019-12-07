package com.lindont.TankGame.v0_2;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_NowTank {

	//坦克名即为坦克的唯一身份标识，决不能重复,否则会有炫酷的特效
	//现在存有的坦克
	public static Vector<Tank> tankVector = new Vector<Tank>();

	private static Vector<Tank> getTankVector() {
		return tankVector;
	}

	private static void setTankVector(Vector<Tank> tankVector) {
		Data_NowTank.tankVector = tankVector;
	}
	
	public boolean addTank(com.lindont.TankGame.v0_2.Tank tank){
		Vector<Tank> tankVector = Data_NowTank.getTankVector();
		boolean pass = this.checktankVector(tankVector,tank.getTankName())==TANKGAME_CODE.failCode;//不存在该对象，则压入数据
		if(pass){
			tankVector.add(tank);
			pass = this.checktankVector(tankVector,tank.getTankName())!=TANKGAME_CODE.failCode;//存在该对象，则压入数据成功
		}
		return pass;
	}
	
	public boolean removeTank(String tankName){
		Vector<Tank> tankVector = Data_NowTank.getTankVector();		
		Tank tank = this.getTankByName(tankName);
		if(tank != null){
			tankVector.remove(tank);
			Data_NowTank.setTankVector(tankVector);
			return (this.checktankVector(tankVector,tankName)==TANKGAME_CODE.failCode);
			//如果等于TANKGAME_CODE.failCode,则证明删除成功
		}else{
			return false;//如果不存在该对象，直接返回false
		}
	}
	
	public Tank getTankByName(String tankName){
		Vector<Tank> temp = Data_NowTank.getTankVector();
		int result = this.checktankVector(temp, tankName);
		Tank tank = null;
		if(result!=TANKGAME_CODE.failCode){
			tank = temp.get(result);
		}
		return tank;//使用该函数注意非空判断
	}
	
	public Tank getTank(int index){
		Vector<Tank> temp = Data_NowTank.getTankVector();
		return temp.get(index);
	}


	public int checktankVector(Vector<Tank> tankVector,String tankName){
//		Vector<Tank> tankVector = Data_NowTank.getTankVector();
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < tankVector.size();i++){
			if(tankVector.get(i).getTankName().equals(tankName)){
				result = i;
			}
		}
		return result;
	}
	
	
	
}
