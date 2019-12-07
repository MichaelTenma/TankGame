package com.lindont.TankGame.v0_4;

import java.util.Vector;


class Data_NowTank extends Data_ControlThing<Tank>{
	
	//坦克名即为坦克的唯一身份标识，决不能重复,否则会有炫酷的特效
	//现在存有的坦克
	public static Vector<Tank> tankVector = new Vector<Tank>();
	
	public boolean add(Tank tank){
		return super.add(Data_NowTank.tankVector,tank);
	}
	
	public boolean remove(Tank tank){
		return super.remove(Data_NowTank.tankVector,tank);
	}
	
	public Tank getByName(String name){
		return super.getByName(Data_NowTank.tankVector,name);
	}
	
	public Tank get(int index){
		return super.get(Data_NowTank.tankVector,index);
	}
	
	public int checkVector(Tank tank){
		return super.checkVector(Data_NowTank.tankVector, tank);
	}
	
	/*
	public boolean addTank(Tank tank){
		Vector<Tank> tankVector = Data_NowTank.getTankVector();
		boolean pass = this.checktankVector(tankVector,tank.getName())==TANKGAME_CODE.failCode;//不存在该对象，则压入数据
		if(pass){
			tankVector.add(tank);
			pass = this.checktankVector(tankVector,tank.getName())!=TANKGAME_CODE.failCode;//存在该对象，则压入数据成功
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
			if(tankVector.get(i).getName().equals(tankName)){
				result = i;
			}
		}
		return result;
	}
	
	*/
	
}
