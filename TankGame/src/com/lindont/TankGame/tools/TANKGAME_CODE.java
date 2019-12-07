package com.lindont.TankGame.tools;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class TANKGAME_CODE{
	public static int userNumber = 0;
	public static int startNumber = 0;
	public static synchronized void removeUser(int user){
		if(user==0){
			//如果死了用户一
			TANKGAME_CODE.startNumber++;
		}else{
			//如果死了用户二
			userNumber--;
		}
	}
	
	public final static int failCode = -1;//失败代码,failCode<0
	//----------------------tank Action START-----------------------------
	public final static int tankUpCode = 0;
	public final static int tankDownCode = 1;
	public final static int tankLeftCode = 2;
	public final static int tankRightCode = 3;
	public final static int tankFireCode = 4;//坦克攻击
	
	public final static int[] tankActionCode = {tankUpCode,tankDownCode,tankLeftCode,tankRightCode,tankFireCode};
	//----------------------tank Action E N D-----------------------------
	
	//*******************************userTank KeyCode START****************************************
	//-------------------------userTank KeyCode user_1 START---------------------------------------
	public static final int KEYCODE_UP_USER_1 = KeyEvent.VK_W;
	public static final int KEYCODE_DOWN_USER_1 = KeyEvent.VK_S;
	public static final int KEYCODE_LEFT_USER_1 = KeyEvent.VK_A;
	public static final int KEYCODE_RIGHT_USER_1 = KeyEvent.VK_D;
	public static final int KEYCODE_FIRE_USER_1 = KeyEvent.VK_J;
	
	public static int[] KEYCODE_USER_1 = {KEYCODE_UP_USER_1,KEYCODE_DOWN_USER_1,KEYCODE_LEFT_USER_1,KEYCODE_RIGHT_USER_1,KEYCODE_FIRE_USER_1};
	//-------------------------userTank KeyCode user_1 E N D---------------------------------------
	
	//-------------------------userTank KeyCode user_2 START---------------------------------------
	public static final int KEYCODE_UP_USER_2 = KeyEvent.VK_UP;
	public static final int KEYCODE_DOWN_USER_2 = KeyEvent.VK_DOWN;
	public static final int KEYCODE_LEFT_USER_2 = KeyEvent.VK_LEFT;
	public static final int KEYCODE_RIGHT_USER_2 = KeyEvent.VK_RIGHT;
	public static final int KEYCODE_FIRE_USER_2 = KeyEvent.VK_ENTER;
	
	public static int[] KEYCODE_USER_2 = {KEYCODE_UP_USER_2,KEYCODE_DOWN_USER_2,KEYCODE_LEFT_USER_2,KEYCODE_RIGHT_USER_2,KEYCODE_FIRE_USER_2};
	//-------------------------userTank KeyCode user_2 E N D---------------------------------------
	
	public static int[][] KEYCODE_USER = {KEYCODE_USER_1,KEYCODE_USER_2};
	//*******************************userTank KeyCode E N D****************************************
	
	//-------------------------------------thingType START-----------------------------------------
	public final static int thingType_TANK = 0;
	public final static int thingType_BULLET = 1;
	public final static int thingType_OBSTACLE = 2;
	//-------------------------------------thingType E N D-----------------------------------------

	public static final Color basicColor = Color.CYAN;//物件的默认颜色
	
	public static final String colorSplit = "_";
	
	
	public static final int obstacleType_Wall = 0;
}
