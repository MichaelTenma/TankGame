package com.lindont.TankGame.v0_3;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlObstacle{
	public static Vector<ForbiddenObstacle> forbiddenArea = new Vector<ForbiddenObstacle>();
	
	public void add(ForbiddenObstacle forbiddenObstacle){
		if(!forbiddenArea.contains(forbiddenObstacle)){
//			System.out.println("正在加入障碍物tank" + forbiddenObstacle.getTank().getTankName() );
			//不存在该对象
			forbiddenArea.add(forbiddenObstacle);
		}
	}
	public boolean remove(String name){
		boolean pass = false;
//		System.out.println("name-" + name);
		for(int i = 0;i < forbiddenArea.size();i++){
			ForbiddenObstacle fo = forbiddenArea.get(i);
			Tank tank = fo.getTank();
//			System.out.println(tank.getTankName());
			if(tank.getTankName().equals(name)){
				forbiddenArea.remove(fo);
//				System.out.println("已经清除" + tank.getTankName());
				pass = !forbiddenArea.contains(fo);
			}
		}
		return pass;
		
	}
}
class ForbiddenObstacle{
	private Tank tank = null;
	
	private int type = TANKGAME_CODE.thingType_OBSTACLE;
	
	public ForbiddenObstacle(Tank tank){
		this.setTank(tank);
		this.setType(TANKGAME_CODE.thingType_TANK);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}
}

