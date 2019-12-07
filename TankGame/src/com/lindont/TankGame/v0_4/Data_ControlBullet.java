package com.lindont.TankGame.v0_4;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlBullet extends Data_ControlThing<Bullet>{
	public static Vector<Bullet> bulletList = new Vector<Bullet>();
	
	public boolean add(Bullet bullet){
		return super.add(Data_ControlBullet.bulletList,bullet);
	}
	
	public Bullet get(int index){
		return super.get(Data_ControlBullet.bulletList,index);
	}
	
	public boolean remove(Bullet bullet){
		
		int result = this.checkVector(Data_ControlBullet.bulletList,bullet);
		if(result!=TANKGAME_CODE.failCode){
			Data_ControlBullet.bulletList.remove(bullet);
			return (this.checkVector(Data_ControlBullet.bulletList,bullet)==TANKGAME_CODE.failCode);
			//如果等于TANKGAME_CODE.failCode,则证明删除成功
		}else{
			return false;//如果不存在该对象，直接返回false
		}
	}

	public Vector<Bullet> getByName(String tankName){
		Vector<Bullet> bulletListByName = new Vector<Bullet>();
		for(int i = 0;i < Data_ControlBullet.bulletList.size();i++){
			Bullet bullet = Data_ControlBullet.bulletList.get(i);
			if(bullet.getName().equals(tankName)){
				if(this.checkVector(bulletListByName,bullet)==TANKGAME_CODE.failCode){
					bulletListByName.add(bullet);
				}
			}
		}
		return bulletListByName;
	}

	public int checkVector(Vector<Bullet> tVector,Bullet bullet){
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < tVector.size();i++){
			if(tVector.get(i)==bullet){
				result = i;
			}
		}
		return result;
	}
	

}
