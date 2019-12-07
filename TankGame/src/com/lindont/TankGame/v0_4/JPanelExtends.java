package com.lindont.TankGame.v0_4;

import java.awt.Graphics;
import java.util.Vector;

import javax.swing.JPanel;

import com.lindont.TankGame.tools.TANKGAME_CODE;

class JPanelExtends extends JPanel{
	
	private static final long serialVersionUID = 1L;
	protected int repaintTime = 50;
	
	public void paint(Graphics g){
		super.paint(g);
	}
	
	public void readTank(Graphics g){
		for(int i = 0;i < Data_NowTank.tankVector.size();i++){
			Tank tankTemp = Data_NowTank.tankVector.get(i);
			if(tankTemp.isLive()){
				tankTemp.setPanelWidth(this.getWidth());
				tankTemp.setPanelHeight(this.getHeight());
				
				/*-------------------利用刷新频率来进行子弹的定时添加----------------
				 * 若putBulletTimes为零，则坦克添加一颗子弹
				 */
				int putBulletTimes = tankTemp.getPutBulletTimes();
				if(putBulletTimes<=0){
					putBulletTimes = tankTemp.getBulletPutSpeed()/this.repaintTime;
//					System.out.println("目前" + tankTemp.getTankName() +"子弹填充还需等待" + putBulletTimes*this.repaintTime + "ms");
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
				
				//----------------------------坦克获取动作_开始------------------------------
				boolean isTankAct = tankTemp.isTankAct();
				if(isTankAct){
					int command = tankTemp.getCommand();
					tankTemp.runAction(command);
				}
				//----------------------------坦克获取动作_结束------------------------------
				
				tankTemp.drawTank(g);
				//从子弹队列中获得子弹，然后绘制出来
				Data_ControlBullet dcb = new Data_ControlBullet();
				Vector<Bullet> bulletList = dcb.getByName(tankTemp.getName());
				for(int k = 0;k < bulletList.size();k++){
					Bullet bullet = bulletList.get(k);
					if(bullet.isLive()){
//						System.out.println("子弹准备就绪");
						bullet.drawBullet(g);
						if(bullet.bulletRunTimes>0){
							if(bullet.isLive()){
								bullet.bulletMove();
							}
						}
//						System.out.println(xBullet+"_" + yBullet);
					}
				
				}
			}
		}
	}
	
	public void readObstacle(Graphics g){
		for(int i=0;i < Data_NowObstacle.obstacleVector.size();i++){
			Data_NowObstacle dno = new Data_NowObstacle();
			Obstacle obstacle = dno.get(i);
			if(obstacle.isLive){
				if(obstacle.obstacleType==TANKGAME_CODE.obstacleType_Wall){
					Wall wall = (Wall)obstacle;
					wall.drawObstacle(g);
//					System.out.println("曾画否，到面板绘己");
				}
			}else{
				dno.remove(obstacle);
				
				Data_ControlObstacle dco = new Data_ControlObstacle();
				dco.remove(obstacle.getName(),obstacle.getType());
			}
		}
	}
}
