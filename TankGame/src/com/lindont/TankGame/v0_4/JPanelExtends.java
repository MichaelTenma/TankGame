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
				
				/*-------------------����ˢ��Ƶ���������ӵ��Ķ�ʱ���----------------
				 * ��putBulletTimesΪ�㣬��̹�����һ���ӵ�
				 */
				int putBulletTimes = tankTemp.getPutBulletTimes();
				if(putBulletTimes<=0){
					putBulletTimes = tankTemp.getBulletPutSpeed()/this.repaintTime;
//					System.out.println("Ŀǰ" + tankTemp.getTankName() +"�ӵ���仹��ȴ�" + putBulletTimes*this.repaintTime + "ms");
					//-------------------------����ӵ�START----------------------------
					int bulletSizeOnTank = tankTemp.getBulletSizeOnTank();//̹��Ŀǰӵ�е��ӵ���
					int bulletSize = tankTemp.getBulletSize();//̹����������ӵ��ӵ���
					//����̹���Ƿ���Լ�������ӵ�
					if(bulletSizeOnTank < bulletSize){
						bulletSizeOnTank++;
						tankTemp.setBulletSizeOnTank(bulletSizeOnTank);
					}
					//-------------------------����ӵ�E N D----------------------------
				}else{
					putBulletTimes--;
				}
				tankTemp.setPutBulletTimes(putBulletTimes);
				
				//----------------------------̹�˻�ȡ����_��ʼ------------------------------
				boolean isTankAct = tankTemp.isTankAct();
				if(isTankAct){
					int command = tankTemp.getCommand();
					tankTemp.runAction(command);
				}
				//----------------------------̹�˻�ȡ����_����------------------------------
				
				tankTemp.drawTank(g);
				//���ӵ������л���ӵ���Ȼ����Ƴ���
				Data_ControlBullet dcb = new Data_ControlBullet();
				Vector<Bullet> bulletList = dcb.getByName(tankTemp.getName());
				for(int k = 0;k < bulletList.size();k++){
					Bullet bullet = bulletList.get(k);
					if(bullet.isLive()){
//						System.out.println("�ӵ�׼������");
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
//					System.out.println("�����񣬵����漺");
				}
			}else{
				dno.remove(obstacle);
				
				Data_ControlObstacle dco = new Data_ControlObstacle();
				dco.remove(obstacle.getName(),obstacle.getType());
			}
		}
	}
}
