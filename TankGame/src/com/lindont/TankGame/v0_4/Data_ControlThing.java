package com.lindont.TankGame.v0_4;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlThing<T extends Thing> {
	
	//复写所有的方法
	protected boolean add(Vector<T> tVector,T t){
		Vector<T> tankVector = tVector;
		boolean pass = this.checkVector(tankVector,t)==TANKGAME_CODE.failCode;//不存在该对象，则压入数据
		if(pass){
			tankVector.add(t);
			pass = this.checkVector(tankVector,t)!=TANKGAME_CODE.failCode;//存在该对象，则压入数据成功
		}
		return pass;
	}
	
	protected boolean remove(Vector<T> tVector,T t){
		if(t != null){
			tVector.remove(t);
			return (this.checkVector(tVector,t)==TANKGAME_CODE.failCode);
			//如果等于TANKGAME_CODE.failCode,则证明删除成功
		}else{
			return false;//如果不存在该对象，直接返回false
		}
	}
	
	protected T getByName(Vector<T> tVector,String name){
		boolean result = tVector.contains(name)?true:false;
		T temp = null;
		if(result){
			temp = tVector.get(this.checkVectorIndexByName(tVector, name));
		}
		return temp;//使用该函数注意非空判断
	}
	
	protected T get(Vector<T> tVector,int index){
		Vector<T> temp = tVector;
		return temp.get(index);
	}


	protected int checkVector(Vector<T> tVector,T t){
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < tVector.size();i++){
			if(tVector.get(i)==t){
				result = i;
				break;
			}
		}
		return result;
	}
	
	protected int checkVectorIndexByName(Vector<T> tVector,String name){
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < tVector.size();i++){
			if(tVector.get(i).getName().equals(name)){
				result = i;
				break;
			}
		}
		return result;
	}
	
	protected Vector<T> clear(Vector<T> tVector){
		return (tVector = new Vector<T>());
	}
	


}
