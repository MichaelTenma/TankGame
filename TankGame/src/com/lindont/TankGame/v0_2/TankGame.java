package com.lindont.TankGame.v0_2;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

public class TankGame extends JFrame implements Runnable{

	public static void main(String[] args) {
		new TankGame();
	}

	private static final long serialVersionUID = -6419851625569185559L;
	GameJPanel jp = null;
	static int width=(int) (618*1.5),height=(int) (382*1.5);
	
	public int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		TankGame.width = width;
	}

	public int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		TankGame.height = height;
	}

	public TankGame(){
		
		int[][] tankKey = {{KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT},{KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D}};
		
		Data_NowTank dnt = new Data_NowTank();
		int tankTimes = 10;//要加入的坦克数量
		TANKGAME_CODE.userNumber=2;//设置用户的数量
		int x = 50;
		for(int i = 0; i < tankTimes;i++){
			Tank tank = new Tank();
			tank.setTankName("_" + i+"Tank");
			Color tankColor = Color.ORANGE;
			if(i < 2){
				tankColor = Color.CYAN;
			}
			tank.setTankColor(tankColor);
			tank.setX(x);
			x+=50;
			tank.setY(50);
			
			Data_ControlTank dct = new Data_ControlTank();
			TankData td = new TankData(tank.getTankName(),TANKGAME_CODE.tankDownCode);
			dct.clearAction(tank.getTankName());
			dct.addAction(td);
			if(i < TANKGAME_CODE.userNumber){
				if(i == 1){
					tank.setUserOne(false);
					tank.setTankColor(Color.ORANGE);
				}
				tank.setLife(1);
				tank.setBulletPutSpeed(100);
				System.out.println("tank" + tank.getBulletPutSpeed());
				tank.setUser(true);
			}
//			new Thread(tank).start();
			dnt.addTank(tank);
		}
		

		
		
		this.setTitle("TankGame");
		this.setSize(TankGame.width, TankGame.height);
		int location[] = JFrameCenteral.getJFrameCenteral().getLocation(TankGame.width, TankGame.height, this);
		this.setLocation(location[0], location[1]);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp = new GameJPanel();
		jp.setTankKey(tankKey);
		this.addKeyListener(jp);
		new Thread(jp).start();
		this.add(jp);

		new Thread(this).start();//AI算法线程
		
	}

	@Override
	public void run() {
		int time = 1000;
		while(true){
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//简单的坦克AI算法
			int max = 7;
			for(int i = 1;i < Data_NowTank.tankVector.size();i++){
				Tank tankTemp = Data_NowTank.tankVector.get(i);
				if(!tankTemp.isUser()){
					int temp = tankTemp.command;
					
					int command = (int)(Math.random()*max);
					if(command>=4&&command<max){
						command = TANKGAME_CODE.tankFireCode;
					}else{
						command = TANKGAME_CODE.tankActionCode[command];
					}
					if(command != temp || command == TANKGAME_CODE.tankFireCode){
						if(command == TANKGAME_CODE.tankFireCode){
							if(!(tankTemp.getBulletSizeOnTank()>0||tankTemp.getBulletSizeOnTank()>=tankTemp.getBulletSize())){
								continue;
							}
						}
						Data_ControlTank dct = new Data_ControlTank();
						TankData td = new TankData(tankTemp.getTankName(),command);
						dct.clearAction(tankTemp.getTankName());
						dct.addAction(td);
						temp = command;
//						System.out.println("actived");
						time = 1000;
					}else{
						time = 5;
					}
				}
			}


		}

	}



}

