package com.lindont.TankGame.v0_4;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lindont.TankGame.tools.TANKGAME_CODE;


public class Map {
	/*	地图文件格式
	 * 简单文件头
	 * MapType:binMap;//文件后缀
	 * MapName:abc;
	 * MapSaveTime:201607201804;
	 * UserNumber:1;
	 * Tank:tankName,x,y,isLive,speed,time,direct,life,tankColor,bulletSize,bulletPutSpeed,bulletSpeed,bulletSizeOnTank,isUser,isUserOne
	 * Obstacle:obstacleName,x,y,isLive,width,height,color,isCanDie
	 *  //Bullet:bulletName,x,y,width,height,tankName
	 *	通过坦克的名称获取tank对象，并传入
	 */
	
	private static final String[] mapStringSingal = {"MapType","MapName","MapSaveTime","UserNumber","Tank","Obstacle","Bullet"};
	private static final String mapType = "binMap";
	private String thisType = "";
	private boolean pass = false;
	private int runTimes = 1;

	void readMap(String link){
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(link)));
			String temp = "";
			Map map = new Map();
			GameJPanel.clearData();
			while((temp = br.readLine())!=null){
//				temp = DES.decrypt(temp, key);//解密
				String[] str = temp.split(":");
				
				if(str[1].matches("\\w+")){
					if(map.runTimes>0){
						if(str[0].equals(mapStringSingal[0])&&str[1].equals(mapType)){
							//type必须在文件头
							System.out.println("正在读取地图..");
							pass = true;
						}else{
							System.out.println("非法地图");
							break;
						}
						map.runTimes--;
					}
					
					if(pass){
						if(str[0].equals(mapStringSingal[1])){
							//然后必须是mapName
							System.out.println("正在读取地图" + str[1]);
						}else if(str[0].equals(mapStringSingal[2])){
							System.out.println("地图保存于" + str[1]);
						}
						
						else if(str[0].equals(mapStringSingal[3])){
							TANKGAME_CODE.userNumber = Integer.parseInt(str[1]);//设置用户的数量
							System.out.println("TANKGAME_CODE.userNumber:" + TANKGAME_CODE.userNumber);
						}
					}
					
				}else{
					
					String[] data = str[1].split(",");
					if(str[0].equals(mapStringSingal[4])){
						System.out.println("正在读取地图信息-" + mapStringSingal[4]);
						Tank tank = new Tank();
			//Tank:tankName,x,y,isLive,speed,time,direct,life,tankColor,bulletSize,bulletPutSpeed,bulletSpeed,bulletSizeOnTank,isUser,isUserOne
						tank.setName(data[0]);
						tank.setX(Integer.parseInt(data[1]));
						tank.setY(Integer.parseInt(data[2]));
						tank.setLive(this.stringToBoolean(data[3]));
						tank.setSpeed(Integer.parseInt(data[4]));
						tank.setTime(Integer.parseInt(data[5]));
						tank.setDirect(Integer.parseInt(data[6]));
						tank.setLife(Integer.parseInt(data[7]));
						int tempColor = Integer.parseInt(data[8]);
						Color c = new Color(tempColor);
						tank.setColor(c);
						tank.setBulletSize(Integer.parseInt(data[9]));
						tank.setBulletPutSpeed(Integer.parseInt(data[10]));
						tank.setBulletSpeed(Integer.parseInt(data[11]));
						tank.setBulletSizeOnTank(Integer.parseInt(data[12]));
						tank.setUser(this.stringToBoolean(data[13]));
						tank.setUserOne(this.stringToBoolean(data[14]));

						Data_NowTank dnt = new Data_NowTank();
						dnt.add(tank);
						
					}else if(str[0].equals(mapStringSingal[5])){
						System.out.println("正在读取地图信息-" + mapStringSingal[5]);
						//Obstacle:obstacleName,x,y,isLive,width,height,color,isCanDie

							String obstacleName = data[0];
							int x = Integer.parseInt(data[1]),y = Integer.parseInt(data[2]);
							boolean isLive = this.stringToBoolean(data[3]);
							
							int width = Integer.parseInt(data[4]),height = Integer.parseInt(data[5]);
							int tempColor = Integer.parseInt(data[6]);
							Color color = new Color(tempColor);
							boolean isCanDie = this.stringToBoolean(data[7]);
							
							int obstacleType =  Integer.parseInt(data[8]);
							Obstacle obstacle = new Obstacle(obstacleName,x,y,width,height,isLive,color,isCanDie,obstacleType);
							
							
							Data_NowObstacle dno = new Data_NowObstacle();
							if(obstacleType==TANKGAME_CODE.obstacleType_Wall){
								Wall wall = new Wall(obstacle);
								obstacle = wall;
							}
							obstacle.firstRun(obstacle);
							dno.add(obstacle);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
				System.out.println("pass:" + pass);
				if(pass){
					GameJPanel.checkRun();
				}else{
					System.out.println("读取异常");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	void writeMap(String link){
		BufferedWriter bw = null;
		try {
			File f = new File(link);
			bw = new BufferedWriter(new FileWriter(f));
//			String nextLineString = "\r\n";
			
			String str = f.getName().substring(f.getName().lastIndexOf(".")+1);
			str = this.formatWrite(0,str,true);
			
			str+= this.formatWrite(1,f.getName().substring(f.getName().lastIndexOf(".")-1),true);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss"); 
			String time = sdf.format(new Date());
			str+= this.formatWrite(2,time,true);
			
			for(int i = 0;i < Data_NowTank.tankVector.size();i++){
				Tank tank = Data_NowTank.tankVector.get(i);
				if(tank.isUser()&&TANKGAME_CODE.userNumber<=2){
					TANKGAME_CODE.userNumber++;
				}
			}
			str+= this.formatWrite(3,TANKGAME_CODE.userNumber + "",true);
			
			//写tank
			for(int i = 0;i < Data_NowTank.tankVector.size();i++){
				Tank tank = Data_NowTank.tankVector.get(i);
				//Tank:tankName,x,y,isLive,speed,time,direct,life,tankColor,bulletSize,bulletPutSpeed,bulletSpeed,bulletSizeOnTank,isUser,isUserOne
				String tankName = tank.getName();
				int x = tank.getX(),y = tank.getY();
				String isLive = this.booleanToString(tank.isLive());
				int speed = tank.getSpeed(),tankTime = tank.getTime();
				int direct = tank.getDirect();
				int life = tank.getLife();
				int tankColor = tank.getColor().getRGB();
				int bulletSize = tank.getBulletSize();
				int bulletPutSpeed = tank.getBulletPutSpeed();
				int bulletSpeed = tank.getBulletSpeed();
				int bulletSizeOnTank = tank.getBulletSizeOnTank();
				String isUser = this.booleanToString(tank.isUser());
				String isUserOne = this.booleanToString(tank.isUserOne());
				
				String outputString = String.format("%s,%d,%d,%s,%d,%d,%d,%d,%d,%d,%d,%d,%d,%s,%s", tankName,x,y,isLive,speed,tankTime,direct,life,tankColor,bulletSize,bulletPutSpeed,bulletSpeed,bulletSizeOnTank,isUser,isUserOne);
				str+=this.formatWrite(4, outputString,true);
			}
			
			//写障碍物
			for(int i = 0;i < Data_NowObstacle.obstacleVector.size();i++){
				//Obstacle:obstacleName,x,y,isLive,width,height,color,isCanDie,obstacleType
				Obstacle obstacle = Data_NowObstacle.obstacleVector.get(i);
				String obstacleName = obstacle.getName();
				int x = obstacle.getX(),y = obstacle.getY();
				String isLive = this.booleanToString(obstacle.isLive());
				int width = obstacle.getWidth(),height = obstacle.getHeight();
				int color = obstacle.getColor().getRGB();
				String isCanDie = this.booleanToString(obstacle.isCanDie);
				int obstacleType = obstacle.obstacleType;
				
				String outputString = String.format("%s,%d,%d,%s,%d,%d,%d,%s,%d",obstacleName,x,y,isLive,width,height,color,isCanDie,obstacleType);
				str+=this.formatWrite(5, outputString,true);
			}
			
			
			
			bw.write(str);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String booleanToString(boolean bool){
		return  bool?"true":"false";
	}
	
	public boolean stringToBoolean(String s){
		return s.equals("true")?true:false;
	}
	
	public String formatWrite(int index,String outputString){
		return mapStringSingal[index] + ":" + outputString + "\r\n";
	}
//	private String key = "A1B2C3D4E5F60708";//密钥
	
	public String formatWrite(int index,String outputString,boolean pass){
		outputString = mapStringSingal[index] + ":" + outputString;
		String encryptData = "";
		return (!pass?encryptData:outputString) + "\r\n";
	}

	public boolean isPass() {
		return pass;
	}

	public void setPass(boolean pass) {
		this.pass = pass;
	}

	public String getThisType() {
		return thisType;
	}

	public void setThisType(String thisType) {
		this.thisType = thisType;
	}

	public int getRunTimes() {
		return runTimes;
	}

	public void setRunTimes(int runTimes) {
		this.runTimes = runTimes;
	}
}
