package com.lindont.TankGame.v0_1;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.*;

import com.lindont.TankGame.tools.JFrameCenteral;

public class TankGame extends JFrame{

	private int[] tankOrigin = {100,100};
	private int[] emeryTankOrigin = {50,50};
	
	private Tank tank = new Tank("Tenma",Color.CYAN, 5, 5, 3, 5,tankOrigin);
	private Tank emeryTank = new Tank("emeryTank",Color.ORANGE,5, 5, 3, 5,emeryTankOrigin);
	
	private Tank[] tankArray = new Tank[2];
	private int[][] tankKey = new int[tankArray.length][4];
	private int[][] origin = new int[tankArray.length][2];
	
	public static void main(String[] args) {
		new TankGame();
	}

	private static final long serialVersionUID = -6419851625569185559L;
	GameJPanel jp = null;
	static int width=618,height=382;
	
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
		tankArray[0] = tank;
		tankArray[1] = emeryTank;
		
		
		int[] key_1 = {KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT};
		int[] key_2 = {KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_A,KeyEvent.VK_D};
		tankKey[0] = key_1;
		tankKey[1] = key_2;
		
		origin[0] = tankOrigin;
		origin[1] = emeryTankOrigin;

		String[] userName = new String[tankArray.length];
		for(int i = 0;i < tankArray.length;i++){
			Tank tank = tankArray[i];
			if(tank!=null){
				userName[i] = tank.getTankName();
				int x = origin[i][0],y = origin[i][1];
				tank.setOrigin(x,y);
//				System.out.println("(" + x + "," + y + ")");
			}else{
				System.out.println("空坦克");
			}
		}
		//设置用户列表
		Data_ControlTank.setUserName(userName);
		
		this.setTitle("TankGame");
		this.setSize(TankGame.width, TankGame.height);
		int location[] = JFrameCenteral.getJFrameCenteral().getLocation(TankGame.width, TankGame.height, this);
		this.setLocation(location[0], location[1]);
		this.setVisible(true);
//		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jp = new GameJPanel(tankArray,TankGame.width,TankGame.height);
		jp.setTankKey(tankKey);
		this.addKeyListener(jp);
		new Thread(jp).start();
		
		for(int i = 0;i < tankArray.length;i++){
			Tank tankTemp = tankArray[i];
			tankTemp.setDirect(1);
			ControlTank ct = new ControlTank();
//			ct.setJp(jp);
			ct.setTank(tankTemp);
			ct.setTankKey(tankKey[i]);
			new Thread(ct).start();
		}

		
		this.add(jp);
		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
//				try {
//					Thread.sleep(1000);
//				} catch (InterruptedException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				System.out.println("Closed");
				new Data_ControlTank().toString();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}



}

