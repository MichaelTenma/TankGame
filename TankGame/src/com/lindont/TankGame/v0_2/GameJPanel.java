package com.lindont.TankGame.v0_2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JPanel;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

class GameJPanel extends JPanel implements KeyListener,Runnable{

	private static final long serialVersionUID = -5639096786594375789L;
	
	private int[][] tankKey = null;
	
	public int[][] getTankKey() {
		return tankKey;
	}

	public void setTankKey(int[][] tankKey) {
		this.tankKey = tankKey;
	}

	public void paint(Graphics g){
		super.paint(g);
		//------------------设置背景颜色----------------
		int screenSize[] = JFrameCenteral.getScreenSize();
		TankGame.setWidth(screenSize[0]);
		TankGame.setHeight(screenSize[1]);
		this.setBackground(Color.gray);
		//------------------------------------------
		
		if(Data_NowTank.tankVector.size()>0){
			for(int i = 0;i < Data_NowTank.tankVector.size();i++){
				Tank tankTemp = Data_NowTank.tankVector.get(i);
				if(tankTemp.isLive()){
					tankTemp.setPanelWidth(this.getWidth());
					tankTemp.setPanelHeight(this.getHeight());
					
					
					/*-------------------利用刷新频率来进行子弹的定时添加----------------
					 * 若putBulletTimes为零，则坦克添加一颗子弹
					 * 
					 */
					int putBulletTimes = tankTemp.getPutBulletTimes();
					if(putBulletTimes<=0){
						putBulletTimes = tankTemp.getBulletPutSpeed()/this.repaintTime;
//						System.out.println("目前" + tankTemp.getTankName() +"子弹填充还需等待" + putBulletTimes*this.repaintTime + "ms");
						//-------------------------填充子弹START----------------------------
						int bulletSizeOnTank = tankTemp.getBulletSizeOnTank();//坦克目前拥有的子弹数
						int bulletSize = tankTemp.getBulletSize();//坦克最大可以添加的子弹数
						//检验坦克是否可以继续添加子弹
						if(bulletSizeOnTank < bulletSize){
							bulletSizeOnTank++;
							tankTemp.setBulletSizeOnTank(bulletSizeOnTank);
						}
						//-------------------------填充子弹E N D----------------------------
					}else{
						putBulletTimes--;
					}
					tankTemp.setPutBulletTimes(putBulletTimes);
					
					//注意线程同步的问题
					//----------------------------向actionList获取动作_开始------------------------------
					Data_ControlTank dct = new Data_ControlTank();
					Vector<TankData> tdVector = dct.getTankDataByName(tankTemp.getTankName());
					for(int j = 0;j < tdVector.size();j++){
						TankData temp = tdVector.get(j);
						int command = temp.getCommand();
//						System.out.println(this.tankName + "正在执行运动信息" + command);
						tankTemp.runAction(command);
					}
					//----------------------------向actionList获取动作_结束------------------------------
					
					tankTemp.drawTank(g);
					//从子弹队列中获得子弹，然后绘制出来
					Data_ControlBullet dcb = new Data_ControlBullet();
					Vector<Bullet> bulletList = dcb.getBulletByName(tankTemp.getTankName());
					for(int k = 0;k < bulletList.size();k++){
						Bullet bullet = bulletList.get(k);
						if(bullet.isLive()){
//							System.out.println("子弹准备就绪");
							bullet.drawBullet(g);
							if(bullet.bulletRunTimes>0){
								if(bullet.isLive()){
									bullet.bulletMove();
								}
							}
//							System.out.println(xBullet+"_" + yBullet);
						}
					
					}
				}
			}
				
		}else{
//			System.out.println("Over");
		}

		

	}

	private int repaintTime = 50;

	@Override
	public void run() {
		while(true){
//			System.out.println("总线程数：" + Thread.activeCount());
			try {
				Thread.sleep(this.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for(int i = 0; i < Data_NowTank.tankVector.size();i++){
				Tank tankTemp = Data_NowTank.tankVector.get(i);
				if(tankTemp.isLive()){
					int width = this.getWidth();
					int height = this.getHeight();

					//从子弹队列中获得子弹，然后绘制出来
					Data_ControlBullet dcb = new Data_ControlBullet();
					Vector<Bullet> bulletList = dcb.getBulletByName(tankTemp.getTankName());
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
							dcb.removeBullet(bullet);
						}
					
					}
					
					int x = tankTemp.getX(),y = tankTemp.getY();
					width -= tankTemp.getTankWidth();
					height -= tankTemp.getTankHeight();
					if(!(x>0&&y>0&x<width&&y<height)){
						if(x<0-1)x=0;
						if(y<0-1)y=0;
						if(x>width+1)x=width;
						if(y>height+1)y=height;
						tankTemp.setX(x);
						tankTemp.setY(y);
						if(Data_ControlTank.actionList.size()>0){
							Data_ControlTank dct = new Data_ControlTank();
							String tankName = tankTemp.getTankName();
							Vector<TankData> tdVector = dct.getTankDataByName(tankName);
							if(tdVector.size()>0){
								dct.removeAction(tdVector.get(0));
							}
							
						}
					}
				}else{
					if(Data_NowTank.tankVector.size()>1){
						Data_NowTank dnt = new Data_NowTank();
						dnt.removeTank(tankTemp.getTankName());
						
						Data_ControlObstacle dco = new Data_ControlObstacle();
						int origin[] = {tankTemp.getX(),tankTemp.getY()};
						ForbiddenObstacle forbiddenObstacle = new ForbiddenObstacle(tankTemp.getTankName(),origin,tankTemp.getTankWidth(), tankTemp.getTankHeight(),TANKGAME_CODE.thingType_TANK);
						boolean tempPass = dco.removeForbiddenObstacle(forbiddenObstacle);
						System.out.println("清除" + tempPass);
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
		TankData td = this.checkKeyCode(e.getKeyCode());
		if(td!=null){
			Data_ControlTank dct = new Data_ControlTank();
			dct.addAction(td);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		TankData td = this.checkKeyCode(e.getKeyCode());
		if(td!=null){
			Data_ControlTank dct = new Data_ControlTank();
			dct.removeAction(td);
		}
	}
	
	public TankData checkKeyCode(int keyCode){
		TankData td = null;
		for(int i = 0;i < TANKGAME_CODE.userNumber;i++){
			for(int j = 0;j < TANKGAME_CODE.KEYCODE_USER[i].length;j++){
				if(keyCode == TANKGAME_CODE.KEYCODE_USER[i][j]){
					Tank temp = Data_NowTank.tankVector.get(i);
					if(temp.isUser()){
						td = new TankData(temp.getTankName(),j);
						Data_ControlTank dct = new Data_ControlTank();
						dct.clearAction(temp.getTankName());
					}
				}
			}
		}
		return td;
	}
}


