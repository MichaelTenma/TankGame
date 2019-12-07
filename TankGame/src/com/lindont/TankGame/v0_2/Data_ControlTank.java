package com.lindont.TankGame.v0_2;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

/*
 * �������ڼ�¼̹�˵��ƶ���Ϣ
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
			//�ж��Ƿ���ӳɹ�
		}else{
			return false;//�������ǰ�Ѿ����ڸö���ֱ�ӷ���false
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
			//�������TANKGAME_CODE.failCode,��֤��ɾ���ɹ�
		}else{
			return false;//��������ڸö���ֱ�ӷ���false
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
		int result = TANKGAME_CODE.failCode;//������
		for(int i = 0;i < actionList.size();i++){
			if(actionList.get(i) == td){
				result = i;//���ڸö��󣬲��ҷ��ظö����λ��
			}
		}
		return result;
	}
	
}

class TankData{
	private String tankName = "";
	private int command = 0;//01234�������ҹ���
	
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
