package com.lindont.TankGame.v0_4;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlThing<T extends Thing> {
	
	//��д���еķ���
	protected boolean add(Vector<T> tVector,T t){
		Vector<T> tankVector = tVector;
		boolean pass = this.checkVector(tankVector,t)==TANKGAME_CODE.failCode;//�����ڸö�����ѹ������
		if(pass){
			tankVector.add(t);
			pass = this.checkVector(tankVector,t)!=TANKGAME_CODE.failCode;//���ڸö�����ѹ�����ݳɹ�
		}
		return pass;
	}
	
	protected boolean remove(Vector<T> tVector,T t){
		if(t != null){
			tVector.remove(t);
			return (this.checkVector(tVector,t)==TANKGAME_CODE.failCode);
			//�������TANKGAME_CODE.failCode,��֤��ɾ���ɹ�
		}else{
			return false;//��������ڸö���ֱ�ӷ���false
		}
	}
	
	protected T getByName(Vector<T> tVector,String name){
		boolean result = tVector.contains(name)?true:false;
		T temp = null;
		if(result){
			temp = tVector.get(this.checkVectorIndexByName(tVector, name));
		}
		return temp;//ʹ�øú���ע��ǿ��ж�
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
