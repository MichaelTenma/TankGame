package com.lindont.TankGame.v0_3;

import com.lindont.TankGame.tools.TANKGAME_CODE;

public class AI {
	public void work(Tank tankTemp,int repaintTime) {
		//通过异步实现
		int time = 1000;
		if(!tankTemp.isUser()){
			if(tankTemp.aiRunTimes<=0){
				tankTemp.aiRunTimes = time/repaintTime;
				
				int temp = tankTemp.getCommand();
				int max = 7;
				int command = (int)(Math.random()*max);
				if(command>=4&&command<max){
					command = TANKGAME_CODE.tankFireCode;
				}else{
					command = TANKGAME_CODE.tankActionCode[command];
				}
				if(command != temp || command == TANKGAME_CODE.tankFireCode){
					if(command == TANKGAME_CODE.tankFireCode){
						if(!(tankTemp.getBulletSizeOnTank()>0||tankTemp.getBulletSizeOnTank()>=tankTemp.getBulletSize())){
							
						}
					}else{
						tankTemp.setCommand(command);
						tankTemp.setTankAct(true);
						temp = command;
//						System.out.println("actived");
						time = 1000;
					}

				}else{
					time = 5;
				}
			}else{
				tankTemp.aiRunTimes--;
			}
		}

	}
}
