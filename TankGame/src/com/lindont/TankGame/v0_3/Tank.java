package com.lindont.TankGame.v0_3;

import java.awt.Color;
import java.awt.Graphics;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

public class Tank{
	//坦克类
	private String tankName = "null";
	private int x = 0,y = 0;//origin
	private int tankWidth = 0,tankHeight = 0;//由系统来修改，不要人为操作这里的值
	private boolean isLive = true;//坦克是否存活
	private boolean isLife = true;//是否是生命，用于后期敌人坦克攻击或者规避的方便，可能会引入人工智能算法
	private int speed = 5,time = 1;//速度等于路程除以时间,speed是路程,time是时间
//	private final int v = speed/time;//暂时速度是一个定值,以后可能会考虑到功率的问题
	private int direct = 0;//0123上下左右--4为攻击
	private int life = 3;//坦克的血量，或者说是命
	private Color tankColor = Color.CYAN;//坦克的颜色,默认天蓝色
	private int bulletSize = 5;//坦克最大子弹数
	private int bulletPutSpeed = 1000;//坦克的填弹速度,以毫秒为单位
	private int bulletSpeed = 10;//坦克的子弹速度
	private int bulletSizeOnTank = 0;//坦克上目前所拥有的子弹数
	private int attackWidth = 0;//坦克炮筒的宽度
	private int panelWidth = 0,panelHeight = 0;//活动区域的大小
	
	private int putBulletTimes = 0;//加入子弹需要等待的次数,默认为0，一般情况下程序员不能直接更改此处的值,否则可能会出错
	
	private boolean isUser = false;//坦克是否为用户，还是电脑,null为用户1
	private boolean isUserOne = true;//是否为用户1
	
	private boolean isTankAct = false;//坦克是否执行移动动作 
	private int command = TANKGAME_CODE.tankUpCode;
	
	public int aiRunTimes = 0;
	
	public int runTimes = 1;
	
	public int getAttackWidth() {
		return attackWidth;
	}
	public void setAttackWidth(int attackWidth) {
		this.attackWidth = attackWidth;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getPanelWidth() {
		return panelWidth;
	}
	public void setPanelWidth(int panelWidth) {
		this.panelWidth = panelWidth;
	}
	public int getPanelHeight() {
		return panelHeight;
	}
	public void setPanelHeight(int panelHeight) {
		this.panelHeight = panelHeight;
	}
	public int getBulletPutSpeed() {
		return bulletPutSpeed;
	}
	public void setBulletPutSpeed(int bulletPutSpeed) {
		this.bulletPutSpeed = bulletPutSpeed;
	}
	public String getTankName() {
		return tankName;
	}
	public void setTankName(String tankName) {
		this.tankName = tankName;
	}
	public int getTankWidth() {
		return tankWidth;
	}
	private void setTankWidth(int tankWidth) {
		this.tankWidth = tankWidth;
	}
	public int getTankHeight() {
		return tankHeight;
	}
	private void setTankHeight(int tankHeight) {
		this.tankHeight = tankHeight;
	}
	public boolean isLive() {
		return isLive;
	}
	public void setLive(boolean isLive) {
		this.isLive = isLive;
	}
	public boolean isLife() {
		return isLife;
	}
	public void setLife(boolean isLife) {
		this.isLife = isLife;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getDirect() {
		return direct;
	}
	public void setDirect(int direct) {
		this.direct = direct;
	}
	public int getLife() {
		return life;
	}
	public void setLife(int life) {
		this.life = life;
	}
	public Color getTankColor() {
		return tankColor;
	}
	public void setTankColor(Color tankColor) {
		this.tankColor = tankColor;
	}
	public int getBulletSize() {
		return bulletSize;
	}
	public void setBulletSize(int bulletSize) {
		this.bulletSize = bulletSize;
	}
	
	public void runAction(int command) {
		switch (command) {
		case TANKGAME_CODE.tankUpCode:
		case TANKGAME_CODE.tankDownCode:
		case TANKGAME_CODE.tankLeftCode:
		case TANKGAME_CODE.tankRightCode:
			this.setDirect(command);
			this.tankMove(command);
			break;
		case TANKGAME_CODE.tankFireCode:
			this.tankAttack();
			break;
		default:
			break;
		}
	}
	
	
	public void tankAttack() {
		//将子弹压入子弹队列
		//攻击
			if(this.bulletSizeOnTank>0){
				Data_ControlBullet dcb = new Data_ControlBullet();
				Bullet bullet = new Bullet(this);
				try {
					Thread.sleep(50);
					//发射子弹的时候增加的休眠时间，防止发射子弹过快
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(dcb.addBullet(bullet)){
//					System.out.printf(this.getTankName() + "正在打出第%d颗子弹\n",this.bulletSizeOnTank);
					this.bulletSizeOnTank--;
				}
			}
//			else{
//				System.out.println("已达到最大子弹数");
//			}

		
	}
	public void tankMove(int command) {
		//移动
		boolean pass = false;
		switch (command) {
			case TANKGAME_CODE.tankUpCode:
				this.y-=this.speed;
				pass=true;
				break;
			case TANKGAME_CODE.tankDownCode:
				this.y+=this.speed;
				pass=true;
				break;
			case TANKGAME_CODE.tankLeftCode:
				this.x-=this.speed;
				pass=true;
				break;
			case TANKGAME_CODE.tankRightCode:
				this.x+=this.speed;
				pass=true;
				break;
			default:
				break;
		}
		
		if(this.isLive){
			if(pass){
				
				for(int k = 0;k < Data_ControlObstacle.forbiddenArea.size();k++){
					ForbiddenObstacle tempForbiddenObstacle = Data_ControlObstacle.forbiddenArea.get(k);
					Tank tankTemp = tempForbiddenObstacle.getTank();
					
					if(!tankTemp.getTankName().equals(this.getTankName())){
						int obstacleX = tankTemp.getX(),obstacleY = tankTemp.getY();
						int tempX = obstacleX-this.getTankWidth();
						int tempY = obstacleY-this.getTankHeight();
						int tempEndX = obstacleX + tankTemp.getTankWidth(),tempEndY = obstacleY + tankTemp.getTankHeight();
						//判断坦克是否碰到坦克
						if(this.x>tempX&&this.y>tempY&&this.x<tempEndX&&this.y<tempEndY){
//							System.out.println(this.getTankName() + "碰撞" + tankTemp.getTankName());
//							//判断坦克从哪个方向来
//							//根据坦克来的方向，使其后退
							switch (command) {
							case TANKGAME_CODE.tankUpCode:
								this.y+=this.speed;
								break;
							case TANKGAME_CODE.tankDownCode:
								this.y-=this.speed;
								break;
							case TANKGAME_CODE.tankLeftCode:
								this.x+=this.speed;
								break;
							case TANKGAME_CODE.tankRightCode:
								this.x-=this.speed;
								break;
							default:
								break;
							}
//							
						}
					}
						
					
				}
			}
		}

//		System.out.printf("\n(%d,%d)",this.x,this.y);
	}
	public void drawTank(Graphics g){
		g.setColor(this.getTankColor());
		//设置坦克的左轮宽，以此为基准画坦克
//		int tankWidth_Ball=this.getPanelWidth()/80;
		int tankWidth_Ball=618/80;
		//获取坦克坐标原点
		int x = this.getX(),y = this.getY();
		
		int tankHeight_Ball = 6 * tankWidth_Ball;
		int tankWidth_CenterRect = (int)((2*tankWidth_Ball/0.382)*0.618),tankHeight_CenterRect = 4 * tankWidth_Ball;
		int tankX_Ball = x+tankWidth_Ball,tankY_Ball = y+(tankHeight_Ball-tankHeight_CenterRect)/2;
		int tankWidth_Oval = (int)(1.3*tankWidth_CenterRect*0.618),tankHeight_Oval = tankWidth_Oval;
		
		//获取中圆圆心坐标
		JFrameCenteral jFrameCenteral = JFrameCenteral.getJFrameCenteral();
		int tankX_Ball_right= tankX_Ball+tankWidth_CenterRect;
		int tankY_Ball_right = y;
		int []temp = null;
		
		if(this.getDirect()==2||this.getDirect()==3){
			temp = this.replace(tankWidth_Ball,tankHeight_Ball);
			tankWidth_Ball = temp[0];
			tankHeight_Ball = temp[1];
			
			temp = this.replace(tankWidth_CenterRect,tankHeight_CenterRect);
			tankWidth_CenterRect = temp[0];
			tankHeight_CenterRect = temp[1];
			
			tankX_Ball_right = x;
			tankY_Ball_right += tankWidth_CenterRect;
		}
		
		int[] tankLocation_Oval = jFrameCenteral.middleLocaltion(tankWidth_Oval, tankHeight_Oval, tankWidth_CenterRect, tankHeight_CenterRect);
		int tankX_Oval = tankLocation_Oval[0],tankY_Oval = tankLocation_Oval[1];
		tankX_Oval += tankX_Ball;
		tankY_Oval += tankY_Ball;
		
		int tankWidth_Power = (int) (tankWidth_Oval*0.2);
		int tankX_line = tankX_Oval + tankWidth_Oval/2-tankWidth_Power/2,tankY_line = tankY_Oval + tankHeight_Oval/2;
		int tankY_line_end = y;
		
		g.fill3DRect(x, y, tankWidth_Ball, tankHeight_Ball, false);
		g.fill3DRect(tankX_Ball, tankY_Ball, tankWidth_CenterRect, tankHeight_CenterRect,false);
		
		//中圆
		g.fillOval(tankX_Oval, tankY_Oval, tankWidth_Oval, tankHeight_Oval);
		
		//--------------以中圆圆心画炮台start-----------------
		int tankYEnd_Power = tankY_line_end - tankY_line;
		int tankXEnd_Power = tankWidth_Power;
		int attackWidth = tankXEnd_Power;
		
		if(this.getDirect() == TANKGAME_CODE.tankDownCode){
			tankY_line += (int)(tankHeight_Ball/2);
			attackWidth = tankXEnd_Power;
		}
		if(this.getDirect() == TANKGAME_CODE.tankLeftCode || this.getDirect() == TANKGAME_CODE.tankRightCode){
			temp = this.replace(tankXEnd_Power,tankYEnd_Power);
			tankXEnd_Power = temp[0];
			tankYEnd_Power = temp[1];
			attackWidth = tankYEnd_Power;
			if(this.getDirect() == TANKGAME_CODE.tankRightCode){
				tankXEnd_Power += tankWidth_Ball;
			}
		}
		this.setAttackWidth(attackWidth);
		g.fill3DRect(tankX_line, tankY_line, tankXEnd_Power,tankYEnd_Power, false);
		//--------------以中圆圆心画炮台end-----------------
		//右边车轮
		g.fill3DRect(tankX_Ball_right, tankY_Ball_right, tankWidth_Ball, tankHeight_Ball, false);
		//描边
		g.setColor(Color.white);
		g.drawRect(x, y, tankWidth_Ball, tankHeight_Ball);
		g.drawRect(tankX_Ball_right, tankY_Ball_right, tankWidth_Ball, tankHeight_Ball);
		
		int tankWidth = 2*tankWidth_Ball + tankWidth_CenterRect;
		int tankHeight = tankHeight_Ball;
		if(this.getDirect() == TANKGAME_CODE.tankLeftCode || this.getDirect() == TANKGAME_CODE.tankRightCode){
			tankWidth = tankWidth_Ball;
			tankHeight = 2*tankHeight_Ball + tankHeight_CenterRect;
		}
		this.setTankWidth(tankWidth);this.setTankHeight(tankHeight);
//		g.fill3DRect(0, 0,tankWidth,tankHeight , false);//测试获取到的坦克宽高
		
		
		if(runTimes>0){
			runTimes--;
			if(this.isLive){
				Data_ControlObstacle dco = new Data_ControlObstacle();
				ForbiddenObstacle forbiddenObstacle = new ForbiddenObstacle(this);
				dco.add(forbiddenObstacle);
			}
		}
		
		
	}
	private int[] replace(int a,int b){
//		int temp = a;a=b;b=temp;
		int[] result = {b,a};
		return result;
	}
	public int getBulletSizeOnTank() {
		return bulletSizeOnTank;
	}
	public void setBulletSizeOnTank(int bulletSizeOnTank) {
//		System.out.printf(this.getTankName() + "正在压入第%d颗子弹\n",bulletSizeOnTank);
		this.bulletSizeOnTank = bulletSizeOnTank;
	}
	public int getBulletSpeed() {
		return bulletSpeed;
	}
	public void setBulletSpeed(int bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}
	public boolean isUser() {
		return isUser;
	}
	public void setUser(boolean isUser) {
		this.isUser = isUser;
	}
	public boolean isUserOne() {
		return isUserOne;
	}
	public void setUserOne(boolean isUserOne) {
		this.isUserOne = isUserOne;
	}
	public int getPutBulletTimes() {
		return putBulletTimes;
	}
	public void setPutBulletTimes(int putBulletTimes) {
		this.putBulletTimes = putBulletTimes;
	}
	public int getCommand() {
		return command;
	}
	public void setCommand(int command) {
		this.command = command;
	}
	public boolean isTankAct() {
		return isTankAct;
	}
	public void setTankAct(boolean isTankAct) {
		this.isTankAct = isTankAct;
	}
	
}



