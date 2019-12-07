package com.lindont.TankGame.v0_2;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_NowTank {

	//̹������Ϊ̹�˵�Ψһ��ݱ�ʶ���������ظ�,��������ſ����Ч
	//���ڴ��е�̹��
	public static Vector<Tank> tankVector = new Vector<Tank>();

	private static Vector<Tank> getTankVector() {
		return tankVector;
	}

	private static void setTankVector(Vector<Tank> tankVector) {
		Data_NowTank.tankVector = tankVector;
	}
	
	public boolean addTank(com.lindont.TankGame.v0_2.Tank tank){
		Vector<Tank> tankVector = Data_NowTank.getTankVector();
		boolean pass = this.checktankVector(tankVector,tank.getTankName())==TANKGAME_CODE.failCode;//�����ڸö�����ѹ������
		if(pass){
			tankVector.add(tank);
			pass = this.checktankVector(tankVector,tank.getTankName())!=TANKGAME_CODE.failCode;//���ڸö�����ѹ�����ݳɹ�
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
			//�������TANKGAME_CODE.failCode,��֤��ɾ���ɹ�
		}else{
			return false;//��������ڸö���ֱ�ӷ���false
		}
	}
	
	public Tank getTankByName(String tankName){
		Vector<Tank> temp = Data_NowTank.getTankVector();
		int result = this.checktankVector(temp, tankName);
		Tank tank = null;
		if(result!=TANKGAME_CODE.failCode){
			tank = temp.get(result);
		}
		return tank;//ʹ�øú���ע��ǿ��ж�
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
