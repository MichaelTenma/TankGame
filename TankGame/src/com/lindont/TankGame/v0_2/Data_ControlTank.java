package com.lindont.TankGame.v0_2;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

/*
 * 该类用于记录坦克的移动信息
 * 
 */
class Data_ControlTank{
	public static Vector<TankData> actionList = new Vector<TankData>();
	
	private static Vector<TankData> getActionList() {
		return actionList;
	}


	private static void setActionList(Vector<TankData> actionList) {
		Data_ControlTank.actionList = actionList;
	}


	public boolean addAction(TankData td){
		Vector<TankData> actionList = Data_ControlTank.getActionList();
		int result = this.checkActionList(actionList,td);
		if(result==TANKGAME_CODE.failCode){
			actionList.add(td);
			Data_ControlTank.setActionList(actionList);
			return (this.checkActionList(actionList,td)!=TANKGAME_CODE.failCode);
			//判断是否添加成功
		}else{
			return false;//在添加以前已经存在该对象，直接返回false
		}
	}
	
	public void clearAction(String tankName){
		if(Data_ControlTank.actionList.size()>0){
			Vector<TankData> tdVector = this.getTankDataByName(tankName);
			if(tdVector.size()>0){
				this.removeAction(tdVector.get(0));
			}
		}
	}
	
	public boolean removeAction(TankData td){
		Vector<TankData> actionList = Data_ControlTank.getActionList();
		int result = this.checkActionList(actionList,td);
		if(result!=TANKGAME_CODE.failCode){
			actionList.remove(td);
			Data_ControlTank.setActionList(actionList);
			return (this.checkActionList(actionList,td)==TANKGAME_CODE.failCode);
			//如果等于TANKGAME_CODE.failCode,则证明删除成功
		}else{
			return false;//如果不存在该对象，直接返回false
		}
	}
	
	public Vector<TankData> getTankDataByName(String tankName){
		Vector<TankData> temp = Data_ControlTank.getActionList(),actionListByName = new Vector<TankData>();
		for(int i = 0;i < temp.size();i++){
			TankData td;
			try {
				td = temp.get(i);
				if(td.getTankName().equals(tankName)){
					if(this.checkActionList(actionListByName,td)==TANKGAME_CODE.failCode){
						actionListByName.add(td);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return actionListByName;
	}
	
	private int checkActionList(Vector<TankData> actionList,TankData td){
//		Vector<TankData> actionList = Data_ControlTank.getActionList();
		int result = TANKGAME_CODE.failCode;//不存在
		for(int i = 0;i < actionList.size();i++){
			if(actionList.get(i) == td){
				result = i;//存在该对象，并且返回该对象的位置
			}
		}
		return result;
	}
	
}

class TankData{
	private String tankName = "";
	private int command = 0;//01234上下左右攻击
	
	public TankData(String tankName,int command){
		this.setTankName(tankName);
		this.setCommand(command);
	}
	
	public String getTankName() {
		return tankName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
}
