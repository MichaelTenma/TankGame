package com.lindont.TankGame.v0_3;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlBullet {
	public static Vector<Bullet> bulletList = new Vector<Bullet>();

	private static Vector<Bullet> getBulletList() {
		return bulletList;
	}

	private static void setBulletList(Vector<Bullet> bulletList) {
		Data_ControlBullet.bulletList = bulletList;
	}
	
	public boolean addBullet(Bullet bullet){
		Vector<Bullet> bulletList = Data_ControlBullet.getBulletList();
		boolean pass = this.checkBulletList(bulletList,bullet)==TANKGAME_CODE.failCode;//不存在该对象，则压入数据
		if(pass){
			bulletList.add(bullet);
			pass = this.checkBulletList(bulletList,bullet)!=TANKGAME_CODE.failCode;//存在该对象，则压入数据成功
		}
		return pass;
	}
	
	public boolean removeBullet(Bullet bullet){
		Vector<Bullet> bulletList = Data_ControlBullet.getBulletList();		
		
		int result = this.checkBulletList(bulletList,bullet);
		if(result!=TANKGAME_CODE.failCode){
			bulletList.remove(bullet);
			Data_ControlBullet.setBulletList(bulletList);
			return (this.checkBulletList(bulletList,bullet)==TANKGAME_CODE.failCode);
			//如果等于TANKGAME_CODE.failCode,则证明删除成功
		}else{
			return false;//如果不存在该对象，直接返回false
		}
	}
	
	public Vector<Bullet> getBulletByName(String tankName){
		Vector<Bullet> temp = Data_ControlBullet.getBulletList(),bulletListByName = new Vector<Bullet>();
		for(int i = 0;i < temp.size();i++){
			Bullet bullet = temp.get(i);
			if(bullet.bulletName.equals(tankName)){
				if(this.checkBulletList(bulletListByName,bullet)==TANKGAME_CODE.failCode){
					bulletListByName.add(bullet);
				}
			}
		}
		return bulletListByName;
	}
	
	public int checkBulletList(Vector<Bullet> bulletList,Bullet bullet){
//		Vector<Bullet> bulletList = Data_ControlBullet.getBulletList();
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < bulletList.size();i++){
			if(bulletList.get(i)==bullet){
				result = i;
			}
		}
		return result;
	}
	

}
