package com.lindont.TankGame.v0_4;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlObstacle{
	public static Vector<ForbiddenObstacle> forbiddenArea = new Vector<ForbiddenObstacle>();
	
	public boolean add(ForbiddenObstacle forbiddenObstacle){
		if(!forbiddenArea.contains(forbiddenObstacle)){
//			System.out.println("正在加入障碍物tank" + forbiddenObstacle.getTank().getTankName() );
			//不存在该对象
			forbiddenArea.add(forbiddenObstacle);
		}
		return forbiddenArea.contains(forbiddenObstacle);
	}
	public boolean remove(String name,int type){
		boolean pass = false;
//		System.out.println("name-" + name);
		for(int i = 0;i < forbiddenArea.size();i++){
			ForbiddenObstacle fo = forbiddenArea.get(i);
			Thing thingTemp = fo.getThing();
			if(thingTemp.getType()==type){
//				System.out.println(tank.getTankName());
				if(thingTemp.getName().equals(name)){
					forbiddenArea.remove(fo);
//					System.out.println("已经清除" + tank.getTankName());
					pass = !forbiddenArea.contains(fo);
				}
			}
		}
		return pass;
	}

	protected Vector<ForbiddenObstacle> clear(Vector<ForbiddenObstacle> tVector){
		return (tVector = new Vector<ForbiddenObstacle>());
	}

}
class ForbiddenObstacle{
	private Thing thing = null;
	
	private int type = TANKGAME_CODE.thingType_OBSTACLE;
	
	public ForbiddenObstacle(Thing thing,int type){
		this.setThing(thing);
		this.setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Thing getThing() {
		return thing;
	}

	public void setThing(Thing thing) {
		this.thing = thing;
	}


}

