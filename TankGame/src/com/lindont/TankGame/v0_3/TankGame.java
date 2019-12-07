package com.lindont.TankGame.v0_3;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.*;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

public class TankGame extends JFrame{

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
		int tankTimes = 12;//要加入的坦克数量
		TANKGAME_CODE.userNumber = 1;//设置用户的数量
		int x = 50;
		for(int i = 0; i < tankTimes;i++){
			Tank tank = new Tank();
			tank.setTankName("_" + i+"Tank");
			Color tankColor = Color.ORANGE;
			
			tank.setX(x);
			x+=50;
			tank.setY(50);
			
			tank.setCommand(TANKGAME_CODE.tankDownCode);
			tank.setTankAct(true);
			if(i < TANKGAME_CODE.userNumber){
				tankColor = Color.CYAN;
				if(i == 1){
					tank.setUserOne(false);
				}
//				System.out.println("tank" + tank.getBulletPutSpeed());
				tank.setUser(true);
			}
			
			tank.setTankColor(tankColor);
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
		//启动刷新线程,定时刷新面板
		new Thread(jp).start();
		this.add(jp);
		
	}
}

