package com.lindont.TankGame.v0_2;

import java.util.Vector;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class Data_ControlObstacle{
	public static Vector<ForbiddenObstacle> forbiddenArea = new Vector<ForbiddenObstacle>();
	
	public String toString(){
		for(int i = 0;i < forbiddenArea.size();i++){
			ForbiddenObstacle fo = forbiddenArea.get(i);
			System.out.println(fo.getObstacleName()+"x("+fo.getOrigin()[0]+","+(fo.getOrigin()[0]+fo.getThingWidth())+")y("+fo.getOrigin()[1]+","+(fo.getOrigin()[1]+fo.getThingHeight())+")");
		}
		return null;
	}

	private static Vector<ForbiddenObstacle> getForbiddenArea() {
		return forbiddenArea;
	}

//	private static void setForbiddenArea(Vector<ForbiddenObstacle> forbiddenArea) {
//		Data_ControlObstacle.forbiddenArea = forbiddenArea;
//	}
	
	public boolean addForbiddenObstacle(ForbiddenObstacle forbiddenObstacle){
		Vector<ForbiddenObstacle> forbiddenArea = Data_ControlObstacle.getForbiddenArea();
		boolean pass = this.checkForbiddenArea(forbiddenArea,forbiddenObstacle)==TANKGAME_CODE.failCode;//�����ڸö�����ѹ������
		if(pass){
			forbiddenArea.add(forbiddenObstacle);
			pass = this.checkForbiddenArea(forbiddenArea,forbiddenObstacle)!=TANKGAME_CODE.failCode;//���ڸö�����ѹ�����ݳɹ�
		}
		return pass;
	}
	
	public boolean removeForbiddenObstacle(ForbiddenObstacle forbiddenObstacle){
//		Vector<ForbiddenObstacle> forbiddenArea = Data_ControlObstacle.getForbiddenArea();		
		
		int result = this.checkForbiddenArea(Data_ControlObstacle.forbiddenArea,forbiddenObstacle);
		if(result!=TANKGAME_CODE.failCode){
			//Ҫ�������ֳ�ȥ
			//�Ȼ�ȡ��index
			String name = forbiddenObstacle.getObstacleName();
			int type = forbiddenObstacle.getType();
			boolean pass = false;
//			Vector<ForbiddenObstacle> temp = new Vector<ForbiddenObstacle>();
			
			System.out.println("_forbiddenArea.size=" + Data_ControlObstacle.forbiddenArea.size());
			
			for(int i = 0;i < Data_ControlObstacle.forbiddenArea.size();i++){
				ForbiddenObstacle tempForbiddenObstacle = Data_ControlObstacle.forbiddenArea.get(i);
				if(tempForbiddenObstacle.getObstacleName().equals(name)&&tempForbiddenObstacle.getType()==type){
//					index = i;
//					System.out.println("�ҵ��ϰ���" + i);
					forbiddenArea.remove(i);
//					//Ϊʲô�޷�ɾ��
					
					//������������Ϊ̹�˱����ó���false֮�󣬻�û�б��������ȴ����Ӧ��Ϣ¼������
//					Data_ControlObstacle.setForbiddenArea(forbiddenArea);
//					pass = (this.checkForbiddenArea(forbiddenArea,forbiddenObstacle)==TANKGAME_CODE.failCode);
//					break;
				}
			}
			
//			if(Data_ControlObstacle.forbiddenArea.size()==temp.size()){
//				System.out.println("������ͬ");
//			}else{
				System.out.println("$forbiddenArea.size=" + Data_ControlObstacle.forbiddenArea.size());
//				System.out.println("temp.size" + temp.size());
//			}
//			Data_ControlObstacle.setForbiddenArea(temp);
			pass = (this.checkForbiddenArea(Data_ControlObstacle.forbiddenArea,forbiddenObstacle)==TANKGAME_CODE.failCode);
			return pass;
			//�������TANKGAME_CODE.failCode,��֤��ɾ���ɹ�
		}else{
			return false;//��������ڸö���ֱ�ӷ���false
		}
	}
	
	public Vector<ForbiddenObstacle> getForbiddenObstacleByName(String tankName){
		Vector<ForbiddenObstacle> temp = Data_ControlObstacle.getForbiddenArea(),forbiddenAreaByName = new Vector<ForbiddenObstacle>();
		for(int i = 0;i < temp.size();i++){
			ForbiddenObstacle forbiddenObstacle = temp.get(i);
			if(forbiddenObstacle.getObstacleName().equals(tankName)){
				if(this.checkForbiddenArea(forbiddenAreaByName,forbiddenObstacle)==TANKGAME_CODE.failCode){
					forbiddenAreaByName.add(forbiddenObstacle);
				}
			}
		}
		return forbiddenAreaByName;
	}
	
	public Vector<ForbiddenObstacle> getForbiddenObstacleByType(int type){
		Vector<ForbiddenObstacle> temp = Data_ControlObstacle.getForbiddenArea(),forbiddenAreaByType = new Vector<ForbiddenObstacle>();
		for(int i = 0;i < temp.size();i++){
			ForbiddenObstacle forbiddenObstacle = temp.get(i);
			if(forbiddenObstacle.getType() == type){
				if(this.checkForbiddenArea(forbiddenAreaByType,forbiddenObstacle)==TANKGAME_CODE.failCode){
					forbiddenAreaByType.add(forbiddenObstacle);
				}
			}
		}
		return forbiddenAreaByType;
	}

	public int checkForbiddenArea(Vector<ForbiddenObstacle> forbiddenArea,ForbiddenObstacle forbiddenObstacle){
//		Vector<ForbiddenObstacle> forbiddenArea = Data_ControlObstacle.getforbiddenArea();
		int result = TANKGAME_CODE.failCode;
		for(int i = 0;i < forbiddenArea.size();i++){
			if(forbiddenArea.get(i)==forbiddenObstacle){
				result = i;
			}
		}
		return result;
	}
	
	
}
class ForbiddenObstacle{
	private String obstacleName = "";//�ϰ��������
	private int origin[] = new int[2];//�ϰ����ԭ��
	private int thingWidth = 0,thingHeight = 0;//�ϰ���Ŀ��
	private int type = TANKGAME_CODE.thingType_OBSTACLE;
	
	public ForbiddenObstacle(String obstacleName,int origin[],int thingWidth,int thingHeight,int type){
		this.setObstacleName(obstacleName);
		this.setOrigin(origin);
		this.setThingWidth(thingWidth);
		this.setThingHeight(thingHeight);
		this.setType(type);
	}

	public int[] getOrigin() {
		return origin;
	}

	public void setOrigin(int[] origin) {
		this.origin = origin;
	}

	public int getThingWidth() {
		return thingWidth;
	}

	public void setThingWidth(int thingWidth) {
		this.thingWidth = thingWidth;
	}

	public int getThingHeight() {
		return thingHeight;
	}

	public void setThingHeight(int thingHeight) {
		this.thingHeight = thingHeight;
	}

	public String getObstacleName() {
		return obstacleName;
	}

	public void setObstacleName(String obstacleName) {
		this.obstacleName = obstacleName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
}

