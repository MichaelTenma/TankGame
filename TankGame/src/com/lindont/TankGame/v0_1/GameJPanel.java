package com.lindont.TankGame.v0_1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.lindont.TankGame.tools.JFrameCenteral;

class GameJPanel extends JPanel implements KeyListener,Runnable{

	private static final long serialVersionUID = -5639096786594375789L;
	
	private Tank[] tankArray = null;
	
	public Tank[] getTankArray() {
		return tankArray;
	}

	public void setTankArray(Tank[] tankArray) {
		this.tankArray = tankArray;
	}

	public GameJPanel(Tank[] tank,int jFrameWidth,int jFrameHeight){
		this.setjFrameWidth(jFrameWidth);
		this.setjFrameHeight(jFrameHeight);
		
		this.setTankArray(tank);
		
		for(int i = 0;i < tank.length;i++){
			tank[i].setAreaWidth(this.getjFrameWidth());
			tank[i].setAreaHeight(this.getjFrameHeight());
		}
		
		this.repaint();
	}
	

	
	
	int jFrameWidth = 0,jFrameHeight = 0;
	
	private int[][] tankKey = null;
	
	public int[][] getTankKey() {
		return tankKey;
	}

	public void setTankKey(int[][] tankKey) {
		this.tankKey = tankKey;
	}

	
	public int getjFrameHeight() {
		return jFrameHeight;
	}

	public void setjFrameHeight(int jFrameHeight) {
		this.jFrameHeight = jFrameHeight;
	}
	
	public int getjFrameWidth() {
		return jFrameWidth;
	}

	public void setjFrameWidth(int jFrameWidth) {
		this.jFrameWidth = jFrameWidth;
	}

	public void paint(Graphics g){
		super.paint(g);
		//------------------设置背景颜色----------------
		int screenSize[] = JFrameCenteral.getScreenSize();
		TankGame.setWidth(screenSize[0]);
		TankGame.setHeight(screenSize[1]);
		this.setBackground(Color.gray);
		//------------------------------------------
		
		for(int i = 0;i < tankArray.length;i++){
			tankArray[i].setG(g);
			tankArray[i].setAreaWidth(this.getjFrameWidth());
			tankArray[i].setAreaHeight(this.getjFrameHeight());
			tankArray[i].drawTank();
		}
		

	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	private byte tankId = -1;
	
	private void newTankId(){
		this.tankId = -1;
	}
	
	private boolean checkTankKey(int thisKeyCode){
		int[][] tankKey = this.getTankKey();
		boolean result = false;
		this.newTankId();
		for(int i = 0;i < tankKey.length;i++){
			for(int j = 0;j < tankKey[i].length;j++){
				if(tankKey[i][j]==thisKeyCode){
					result = true;
					this.tankId = (byte)i;
					break;
				}
			}
		}
		return result;
	}
	


	@Override
	public void keyPressed(KeyEvent e) {
		int thisKeyCode = e.getKeyCode();
		if(checkTankKey(thisKeyCode)){
//			this.e = e;
//			tg.removeKeyListener(this);
			//如果tankKey 中含有该keyCode才键动作加入
			Tank tank = this.getTankArray()[this.tankId];
			this.newTankId();//初始化tankId
			Data_ControlTank dct = new Data_ControlTank();
			TankData td = new TankData(tank.getTankName(),thisKeyCode);
//			System.out.println("正在压入数据");
			dct.putTankData(td);
//			dct.setTankData(td);
		}
		
	}
	

	@Override
	public void keyReleased(KeyEvent e) {
		
		int thisKeyCode = e.getKeyCode();
		if(checkTankKey(thisKeyCode)){
//			tg.addKeyListener(this);
			Tank tank = this.getTankArray()[this.tankId];
			this.newTankId();//初始化tankId
			Data_ControlTank dct = new Data_ControlTank();
			TankData td = new TankData(tank.getTankName(),thisKeyCode);
			boolean pass = dct.removeTankData(td);
//			int index = dct.checkTankData(td, td);
//			if(index>=0){
//				boolean pass = dct.removeTankData(index);
				if(!pass){
					System.out.println("动作存在但删除动作失败");
				}else{
					System.out.println("动作存在且删除动作成功");
				}
////				dct.toString();
////				System.out.println(index);
//			}
		}

	}
//	private KeyEvent e = null;
	
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(55);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
			
	
			
//			if(this.e!=null){
//				GCThread gc = new GCThread();
//				gc.e = this.e;
//				gc.jp = this;
//				Thread gct = new Thread(gc);
//				if(!gct.isAlive()){
//					gct.start();
//				}
//			}

			
		}
	}
}


//---------------------ControlTank--------------------------
class ControlTank implements Runnable{


	private Tank tank = null;
	private int[] tankKey = null;
	
	public Tank getTank() {
		return tank;
	}

	public void setTank(Tank tank) {
		this.tank = tank;
	}

	public int[] getTankKey() {
		return tankKey;
	}

	public void setTankKey(int[] tankKey) {
		this.tankKey = tankKey;
	}

	public void tankStart(){
		while(true){
			Data_ControlTank dct = new Data_ControlTank();
			ArrayList<ArrayList<TankData>> tempData = Data_ControlTank.getUserActionList();
			if(tempData!=null){
				ArrayList<TankData> tankData = dct.getTankDataByTankName(tank.getTankName());
				if(tankData!=null){
					try {
						Thread.sleep(55);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if(tankData.size()>0){
						TankData td = tankData.get(0);
						int keyCode = td.getKeyCode();
						tank.tankMove(tank.checkDirect(keyCode, this.getTankKey()));
						System.out.println("------------" + tankData.size());
						System.out.println("Move");
					}
				}
			}
			
		}
	}

	@Override
	public void run() {
		this.tankStart();
	}

}


//class GCThread implements Runnable{
//	GameJPanel jp = null;
////	TankGame tg = null;
//	KeyEvent e = null;
//	public void run() {
//			try {
//				Thread.sleep(400);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			
//			jp.keyReleased(e);
////			jp.keyPressed(e);
//				//定时处理掉多余的动作
//	}
//}