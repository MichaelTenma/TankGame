package com.lindont.TankGame.v0_3;

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
		//------------------���ñ�����ɫ----------------
		int screenSize[] = JFrameCenteral.getScreenSize();
		TankGame.setWidth(screenSize[0]);
		TankGame.setHeight(screenSize[1]);
		this.setBackground(Color.gray);
		//------------------------------------------
		
		if(Data_NowTank.tankVector.size()>1){
			for(int i = 0;i < Data_NowTank.tankVector.size();i++){
				Tank tankTemp = Data_NowTank.tankVector.get(i);
				if(tankTemp.isLive()){
					tankTemp.setPanelWidth(this.getWidth());
					tankTemp.setPanelHeight(this.getHeight());
					
					
					/*-------------------����ˢ��Ƶ���������ӵ��Ķ�ʱ���----------------
					 * ��putBulletTimesΪ�㣬��̹�����һ���ӵ�
					 * 
					 */
					int putBulletTimes = tankTemp.getPutBulletTimes();
					if(putBulletTimes<=0){
						putBulletTimes = tankTemp.getBulletPutSpeed()/this.repaintTime;
//						System.out.println("Ŀǰ" + tankTemp.getTankName() +"�ӵ���仹��ȴ�" + putBulletTimes*this.repaintTime + "ms");
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
					Vector<Bullet> bulletList = dcb.getBulletByName(tankTemp.getTankName());
					for(int k = 0;k < bulletList.size();k++){
						Bullet bullet = bulletList.get(k);
						if(bullet.isLive()){
//							System.out.println("�ӵ�׼������");
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
			if(Data_NowTank.tankVector.size()>0){
				this.isRunThread = false;
//				g.drawString(Data_NowTank.tankVector.get(0).getTankName() + " win", 100, 100);
				System.out.println("Over");
			}
		}
	}

	private int repaintTime = 50;

	private boolean isRunThread = true;
	@Override
	public void run() {
		while(this.isRunThread){
			System.out.println("���߳�����" + Thread.activeCount());
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
					
					//����AI
					AI ai = new AI();
					ai.work(tankTemp, this.repaintTime);
					

					//���ӵ������л���ӵ���Ȼ����Ƴ���
					Data_ControlBullet dcb = new Data_ControlBullet();
					Vector<Bullet> bulletList = dcb.getBulletByName(tankTemp.getTankName());
					for(int k = 0;k < bulletList.size();k++){
						Bullet bullet = bulletList.get(k);
						if(bullet.isLive()){
//							System.out.println("�ӵ�׼������");
							int xBullet = bullet.getX(),yBullet = bullet.getY();
//							System.out.println(xBullet+"_" + yBullet);
							if(!(xBullet>0&&yBullet>0&xBullet<width&&yBullet<height)){
//								System.out.println("�走�����ɣ�ɱ����");
								bullet.setLive(false);
							}
						}else{
//							System.out.println("�ӵ�׼���Ƴ�");
							dcb.removeBullet(bullet);
						}
					
					}
					
					int x = tankTemp.getX(),y = tankTemp.getY();
					width -= tankTemp.getTankWidth();
					height -= tankTemp.getTankHeight();
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
						dnt.removeTank(tankTemp.getTankName());
						
						Data_ControlObstacle dco = new Data_ControlObstacle();
						boolean pass = dco.remove(tankTemp.getTankName());
						
						System.out.println("���" + tankTemp.getTankName() +(pass?"�ɹ�":"ʧ��"));
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
					//��Ҳ��֪��ԭ����ʲô����������������
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
}


