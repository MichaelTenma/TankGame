package com.lindont.TankGame.v0_4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

class GameJPanel extends JPanelExtends implements KeyListener,Runnable{

	private static final long serialVersionUID = -5639096786594375789L;
	
	private static GameJPanel gameJPanel = null;
	
	private GameJPanel(){
		
	}
	
	public static synchronized GameJPanel getGameJPanel(){
		if(GameJPanel.gameJPanel==null){
			GameJPanel.gameJPanel = new GameJPanel();
		}
		return GameJPanel.gameJPanel;
	}

	public void paint(Graphics g){
		super.paint(g);
		//------------------设置背景颜色----------------
		int screenSize[] = JFrameCenteral.getScreenSize();
		TankGame.width = screenSize[0];
		TankGame.height = screenSize[1];
		this.setBackground(Color.gray);
		//-------------------------------------------
		this.onPaint(g);
	}
	
	public void putObstacle(Graphics g,int tankSize){
		//当目前拥有的坦克数量小于tankSize时，该函数将不再执行
		if(Data_NowObstacle.obstacleVector.size()>tankSize){
			super.readObstacle(g);
		}
	}

	public void onPaint(Graphics g){
		this.putObstacle(g, 0);
		this.putTank(g,1);
	}

	public void putTank(Graphics g,int tankSize){
		if(Data_NowTank.tankVector.size()>tankSize){
			super.readTank(g);
		}else{
			if(Data_NowTank.tankVector.size()>0){
				this.isRunThread = false;
//				g.drawString(Data_NowTank.tankVector.get(0).getTankName() + " win", 100, 100);
//				System.out.println("Over");
			}
		}
	}

	private boolean isRunThread = true;
	private boolean isRunAI = true;
	@Override
	public void run() {
		while(this.isRunThread){
//			System.out.println("总线程数：" + Thread.activeCount());
			try {
				Thread.sleep(super.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for(int i = 0; i < Data_NowTank.tankVector.size();i++){
				Tank tankTemp = Data_NowTank.tankVector.get(i);
				if(tankTemp.isLive()){
					int width = this.getWidth();
					int height = this.getHeight();
					
					if(isRunAI){
						//启动AI
						AI ai = new AI();
						ai.work(tankTemp, super.repaintTime);
					}

					//从子弹队列中获得子弹，然后绘制出来
					Data_ControlBullet dcb = new Data_ControlBullet();
					Vector<Bullet> bulletList = dcb.getByName(tankTemp.getName());
					for(int k = 0;k < bulletList.size();k++){
						Bullet bullet = bulletList.get(k);
						if(bullet.isLive()){
//							System.out.println("子弹准备就绪");
							int xBullet = bullet.getX(),yBullet = bullet.getY();
//							System.out.println(xBullet+"_" + yBullet);
							if(!(xBullet>0&&yBullet>0&xBullet<width&&yBullet<height)){
//								System.out.println("妈蛋，还飞，杀死你");
								bullet.setLive(false);
							}
						}else{
//							System.out.println("子弹准备移除");
							dcb.remove(bullet);
						}
					}
					
					int x = tankTemp.getX(),y = tankTemp.getY();
					width -= tankTemp.getWidth();
					height -= tankTemp.getHeight();
					if(x<0||y<0||x>width||y>height){
						
						int speed = tankTemp.getSpeed();
						switch (tankTemp.getDirect()) {
						case TANKGAME_CODE.tankUpCode:
							y+=speed;
							break;
						case TANKGAME_CODE.tankDownCode:
							y-=speed;
							break;
						case TANKGAME_CODE.tankLeftCode:
							x+=speed;
							break;
						case TANKGAME_CODE.tankRightCode:
							x-=speed;
							break;
						default:
							break;
						}
						
						tankTemp.setX(x);
						tankTemp.setY(y);

					}
				}else{
					if(Data_NowTank.tankVector.size()>1){
							Data_NowTank dnt = new Data_NowTank();
							dnt.remove(tankTemp);
							
							Data_ControlObstacle dco = new Data_ControlObstacle();
							boolean pass = dco.remove(tankTemp.getName(),tankTemp.getType());
							System.out.println("清除" + tankTemp.getName() +(pass?"成功":"失败"));
					}
				}
			}
			this.repaint();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.checkKeyCode(e.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.checkKeyCode(e.getKeyCode(), false);
	}
	
	public void checkKeyCode(int keyCode,boolean isTankAct){
		for(int i = TANKGAME_CODE.startNumber;i < TANKGAME_CODE.userNumber;i++){
			for(int j = 0;j < TANKGAME_CODE.KEYCODE_USER[i].length;j++){
				if(keyCode == TANKGAME_CODE.KEYCODE_USER[i][j]){
					//-----------------------------------START--------------------------------------
					int tempI = i,tempUser = TANKGAME_CODE.userNumber - TANKGAME_CODE.startNumber;
					if(tempUser != TANKGAME_CODE.userNumber){
						tempI-=tempUser;
					}
					//-----------------------------------E N D--------------------------------------
					Tank temp = Data_NowTank.tankVector.get(tempI);
					if(temp.isUser()){
						temp.setCommand(j);
						temp.setTankAct(isTankAct);
//						System.out.println(temp.getTankName() + "_" + temp.isTankAct());
					}
				}
			}
		}
	}


	private void setRunThread(boolean isRunThread) {
		this.isRunThread = isRunThread;
	}
	
	public static void checkRun(){
		if(!TankGame.gameThread.isAlive()){
			gameJPanel.setRunThread(true);
			try {
				TankGame.gameThread.start();
			} catch (Exception e) {
			}
		}
	}
	
	public static void closeThread(){
		//关闭线程
		gameJPanel.setRunThread(false);
	}
	
	public static void clearData(){
		Data_NowTank dnt = new Data_NowTank();
		Data_NowTank.tankVector = dnt.clear(Data_NowTank.tankVector);
		
		clearBullet();
		
		Data_ControlObstacle dco = new Data_ControlObstacle();
		Data_ControlObstacle.forbiddenArea = dco.clear(Data_ControlObstacle.forbiddenArea);
		
		Data_NowObstacle dno = new Data_NowObstacle();
		Data_NowObstacle.obstacleVector = dno.clear(Data_NowObstacle.obstacleVector);
	}

	
	public static void clearBullet(){
		Data_ControlBullet dcb = new Data_ControlBullet();
		Data_ControlBullet.bulletList = dcb.clear(Data_ControlBullet.bulletList);
	}
	boolean isRunAI() {
		return isRunAI;
	}

	void setRunAI(boolean isRunAI) {
		this.isRunAI = isRunAI;
	}
}
