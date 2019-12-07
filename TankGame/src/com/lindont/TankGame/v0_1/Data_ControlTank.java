package com.lindont.TankGame.v0_1;

import java.util.ArrayList;

/*
 * �������ڼ�¼̹�˵��ƶ���Ϣ
 * 
 */
class Data_ControlTank {
	
	//ʹ�ø���֮ǰ������ѹ���û��������飬���������쳣
	private static String[] userName = null;
	
	public static String[] getUserName() {
		return userName;
	}

	public static void setUserName(String[] userName) {
		Data_ControlTank.userName = userName;
	}

	private static ArrayList<ArrayList<TankData>> userActionList = new ArrayList<ArrayList<TankData>>();
	//����̹ͬ�˵Ķ������ݴ���
	//0Ϊ��һ���û�������̹��
	//1Ϊ�ڶ����û�������̹��
	//�����û��Ĳ�ͬ���ڼ��̿��ƴ���Ĳ�ͬ
	//һ��WASDJ
	//���෽����Լ�С����1
	
	//����������ݷ���
	

	
	public static ArrayList<ArrayList<TankData>> getUserActionList() {
		return userActionList;
	}

	private static void setUserActionList(int index,ArrayList<TankData> tankData) {
		ArrayList<ArrayList<TankData>> userActionList = Data_ControlTank.userActionList;
		userActionList.add(index,tankData);
		Data_ControlTank.userActionList = userActionList;
	}

	public void putTankData(TankData td){
		//����̹�����ƽ�����ѹ�벻ͬ�Ķ���֮��
		ArrayList<TankData> tankData = new ArrayList<TankData>();
		int result = this.checkTankData(tankData, td);
		if(result == -1){
			this.setTankData(tankData, td);
			Data_ControlTank.setUserActionList(this.findUserId(td.getTankName()), tankData);
		}
	}
	
	
	public int findUserId(String userName){
		String[] usernameArray = Data_ControlTank.getUserName();
		int result = -1;
		for(int i = 0;i < usernameArray.length;i++){
			if(usernameArray[i].equals(userName)){
				result = i;
			}
		}
		//����и��û����ڣ��򷵻�����
		//���򷵻�-1
		return result;
	}
	
	
	public boolean removeTankData(TankData td){
		String userName = td.getTankName();
		int index = this.findUserId(userName);
		ArrayList<TankData> temp = Data_ControlTank.getUserActionList().get(index);
		boolean pass = false;
		if(temp.size() > 0){
			temp.remove(0);
			if(this.checkTankData(temp,td) == -1){
				pass = true;
			}
		}
		Data_ControlTank.setUserActionList(index, temp);
		this.toString(temp);
		//���޸ĺ������ѹ��ȥ
		return pass;
	}
	
	
//	private ArrayList<TankData> tankData = new ArrayList<TankData>();
	/*
	 * tankName
	 * Direct
	 * ����
	 * 
	 */
//	public ArrayList<TankData> getTankData() {
//		return tankData;
//	}

	private void setTankData(ArrayList<TankData> tdArrayList,TankData td){
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		boolean pass = this.checkTankData(tdArrayList, td)<=0;
		if(pass){
		tdArrayList.add(td);
			System.out.println("TankName:" + td.getTankName() + "	KeyCode:" + td.getKeyCode());
		}
		else{
			System.out.println("�Ѿ����ڸ���Ϣ");
		}
			
//		if(this.checkTankData(tdArrayList, td)>=0){
//			System.out.println("�ɹ�ѹ��");
//		}else{
//			System.out.println("ѹ��ʧ��");
//		}

	}
	
	private int checkTankData(ArrayList<TankData> tankData,TankData td){
		int result = -1;
//		ArrayList<TankData> tankData = this.getTankData();
//		System.out.println("size:" + tankData.size());
		
		//����һ��arrayList����ֹ�����ظ�����
		for(int i = 0;i < tankData.size();i++){
			TankData temp = tankData.get(i);
			String tankName = temp.getTankName();
			String tempTankName = td.getTankName();
//			System.out.println("TEST-" + tankName + "___" + tempTankName);
			if(tankName.equals(tempTankName) && temp.getKeyCode() == td.getKeyCode()){
				result = i;
				System.out.println("��������");
			}
		}
		
//		if(result >= 0){
//			result = -result;
//		}
		return result;
	}
	
//	public boolean removeTankData(ArrayList<TankData> td,int index){
////		ArrayList<TankData> td = this.tankData; 
//		int length = td.size();
//		td.remove(index);
//		boolean result = false;
//		if(td.size() == length - 1){
//			result = true;
//		}
//		
//		this.toString();
//		return result;
//	}
	
	public ArrayList<TankData> getTankDataByTankName(String tankName){
//		ArrayList<TankData> tankData,
		//����̹ͬ�˵����ݷֿ�
		//��ȡ��Ӧ̹�˵Ķ�������
		
		int index = this.findUserId(tankName);
		ArrayList<TankData> tankDataTemp = new ArrayList<TankData>();
		if(index >= 0){
			ArrayList<ArrayList<TankData>> userActionList = Data_ControlTank.getUserActionList();
			if(userActionList.size()>index){
				System.out.println("userActionListSize:" + userActionList.size());
				ArrayList<TankData> tankData = userActionList.get(index);
				for(int i = 0;i < tankData.size();i++){
					TankData temp = tankData.get(i);
					if(temp.getTankName().equals(tankName)){
						tankDataTemp.add(temp);
					}
				}
			}

		}else{
			System.out.println("�����ڸ�̹��");
		}
		//Ҫ���зǿռ���
		return tankDataTemp;
	}
	
	public String toString(ArrayList<TankData> td){
//		ArrayList<TankData> td = this.tankData; 
		System.out.println("----------------------TankData--Start-------------------------");
		for(int i = 0; i < td.size();i++){
			TankData t = td.get(i);
			System.out.println(i + "-TankName:" + t.getTankName() + "_KeyCode:" + t.getKeyCode());
		}
		System.out.println("----------------------TankData--E n d-------------------------");
		return null;
	}
	
}

class TankData{
	private String tankName = "";
	private int keyCode = 0;
	
	public TankData(String tankName,int keyCode){
		this.setTankName(tankName);
		this.setKeyCode(keyCode);
	}
	
	public String getTankName() {
		return tankName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public int getKeyCode() {
		return keyCode;
	}
	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}
}
