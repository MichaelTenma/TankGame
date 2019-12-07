package com.lindont.TankGame.v0_4;

import javax.swing.*;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;

public class MapEditer extends JFrame implements WindowStateListener,ActionListener,WindowListener{
	private EditPanel editPanel = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int width=(int) (618*1.8),height= TankGame.getTankGame().getHeight();
	
	private JMenuBar jmb = null;
	private JMenu[] jm = null;
	private JMenuItem[] jmi = null;
	private String[] jmString = {"文件"};
	private String[] jmiString = {"打开","保存"};
	
	static JComboBox<String> jcb = null;
//	private String jcbString[] = {"坦克","墙"};
	
	private JPanelControlThing jpct = null;//用来放控件
	
	public MapEditer(){
		editPanel = new EditPanel();
		//Tank:x,y,tankColor,direct,life,bulletSize,isUser,isUserOne
		//Obstacle:x,y,color,isCanDie,width,height
		
		jmb = new JMenuBar();
		jm = new JMenu[jmString.length];
		jmi = new JMenuItem[jmiString.length];
		
		for(int i = 0;i < jm.length && i < jmString.length;i++){
			jm[i] = new JMenu(jmString[i]);
			for(int k = 0;k < jmi.length && k < jmiString.length;k++){
				jmi[k] = new JMenuItem(jmiString[k]);
				jmi[k].addActionListener(this);
				jmi[k].setActionCommand(jmi[k].getText());
				jm[i].add(jmi[k]);
			}
			jmb.add(jm[i]);
		}
		this.setJMenuBar(jmb);
		editPanel.me =  this;
		
		jpct = JPanelControlThing.getJPanelControlThing();
		
		this.setTitle("MapEditer");
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(width, height);
		int location[] = JFrameCenteral.getJFrameCenteral().getLocation(width, height, this);
		this.setLocation(location[0], location[1]);
		this.setLayout(null);
		this.addWindowStateListener(this);
		this.addWindowListener(this);
//		this.addMouseMotionListener(editPanel);
		this.addMouseListener(editPanel);
		new Thread(editPanel).start();
//		GameJPanel.checkRun();

	}
	protected void creatAndShowGUI(){
		editPanel.changeSize();
		this.add(editPanel);
		
		jpct.setBounds(editPanel.getWidth(), 0, width-editPanel.getWidth(), editPanel.getHeight());
		this.add(jpct);
	}
	
	@Override
	public void windowStateChanged(WindowEvent e) {
		editPanel.changeSize();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.getActionCommand());
		if(e.getActionCommand().equals(jmiString[0])){
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("请选择需要编辑的游戏地图...");
			jfc.setVisible(true);
			jfc.showOpenDialog(null);
			File file = jfc.getSelectedFile();
			if(file!=null){
				String link = file.getAbsolutePath();
				if(!link.equals("")){
					new Map().readMap(link);
				}
			}
		}else if(e.getActionCommand().equals(jmiString[1])){
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("另存为...");
			jfc.setVisible(true);
			jfc.showSaveDialog(null);
			File file = jfc.getSelectedFile();
			if(file!=null){
				String link = file.getAbsolutePath();
				if(!link.equals("")){
					if(Data_NowTank.tankVector.size()>0){
						new Map().writeMap(link);
					}else{
						System.out.println("请添加坦克");
					}
				}
			}
		}
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		GameJPanel.checkRun();
		GameJPanel gameJPanel = GameJPanel.getGameJPanel();
		gameJPanel.setRunAI(false);
		
		for(int i = 0;i < Data_NowTank.tankVector.size();i++){
			Tank tank =  Data_NowTank.tankVector.get(i);
			tank.setCommand(TANKGAME_CODE.failCode);
		}
		
		
		GameJPanel.clearBullet();
		
		jpct.comboBoxOnClicked();
	}
	@Override
	public void windowClosing(WindowEvent e) {
		GameJPanel gameJPanel = GameJPanel.getGameJPanel();
		gameJPanel.setRunAI(true);
		//清理坦克等数据
//		GameJPanel.closeThread();
//		GameJPanel.clearData();
	}
	@Override
	public void windowClosed(WindowEvent e) {
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
}

class EditPanel extends JPanelExtends implements MouseMotionListener,Runnable,MouseListener{
	private static final long serialVersionUID = 1L;
	MapEditer me = null;//暂时修改,没什么卵用
	private Thing thing = null;
	private int[] origin = new int[2];
	private int[] endLocation = new int[2];
	public void paint(Graphics g){
		super.paint(g);
		
		this.setBackground(Color.gray);
		this.readTank(g);
		this.readObstacle(g);
		if(this.thing!=null){
			if(JPanelControlThing.isCanGetThing){
//				System.out.println(String.format("<%d,%d>", this.thing.x,this.thing.y));
				if(this.thing.getType()==TANKGAME_CODE.thingType_TANK){
					Tank tank = (Tank)this.thing;
					tank.drawTank(g);
				}else if(this.thing.getType()==TANKGAME_CODE.thingType_OBSTACLE){
					Obstacle obstacle = (Obstacle)this.thing;
					if(obstacle.obstacleType==TANKGAME_CODE.obstacleType_Wall){
						Wall wall = (Wall)obstacle;
						wall.drawObstacle(g);
					}
				}
			}
		}

	}
	
	public void changeSize(){
		GameJPanel jp = TankGame.jp;
		if(jp != null){
			this.setBounds(0,0,jp.getWidth(),jp.getHeight());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		endLocation[0] = e.getX();
		endLocation[0] = e.getY();
		this.repaint();		
	}

	private int tempI = 0;
	@Override
	public void mouseMoved(MouseEvent e) {
		this.thing = JPanelControlThing.getThing();
		if(this.thing!=null){
//			System.out.println(String.format("(%d,%d)", e.getX(),e.getY()));
			this.thing.x = e.getX();
			this.thing.y = e.getY();
//			System.out.println(String.format("<%d,%d>", this.thing.x,this.thing.y));
		}
	}

	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int mods = e.getModifiers();
		//鼠标左键
		if((mods & InputEvent.BUTTON3_MASK) == 0){
			if(this.thing!=null){
				if(this.thing.getType()==TANKGAME_CODE.thingType_OBSTACLE){
					origin[0] = e.getX();
					origin[1] = e.getY();
					System.out.println("origin");
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int mods = e.getModifiers();
		if ((mods & InputEvent.BUTTON3_MASK) != 0) {
			//鼠标右键
			JPanelControlThing jpct = JPanelControlThing.getJPanelControlThing();
			jpct.setOrigin(e.getX(), e.getY());
		}else if((mods & InputEvent.BUTTON3_MASK) == 0){
			//鼠标左键
			if(this.thing!=null){
				if(JPanelControlThing.isCanGetThing){
					if(this.thing.getType()==TANKGAME_CODE.thingType_TANK){
						Tank tempTank = (Tank)this.thing;
						Data_NowTank dnt = new Data_NowTank();
						if(dnt.checkVector(tempTank)==TANKGAME_CODE.failCode){
							tempTank.setName("tank_" + (tempI++));
							dnt.add(tempTank);
							System.out.println("Data_NowTank.tankVector.size():" + Data_NowTank.tankVector.size());
						}
					}else if(this.thing.getType()==TANKGAME_CODE.thingType_OBSTACLE){
						endLocation[0] = e.getX();
						endLocation[1] = e.getY();
						Data_NowObstacle dno = new Data_NowObstacle();
						Obstacle obstacle = (Obstacle)this.thing;
						Obstacle temp = obstacle;
						
						if(endLocation[0] != origin[0] && endLocation[1] != origin[1]){
							int x,y,width,height;
							if(endLocation[0] < origin[0] && endLocation[1] < origin[1]){
								x = endLocation[0];
								y = endLocation[1];
							}else{
								x = origin[0];
								y = origin[1];
							}
							System.out.println(String.format("(%d,%d)", x,y));
							width = Math.abs(endLocation[0]-origin[0]);
							height = Math.abs(endLocation[1]-origin[1]);
							this.thing.x = x;
							this.thing.y = y;
							
							int number = width/this.thing.width;//注意不要除反了
							
							boolean isCols = false;
							if(number==0){
								number = height/this.thing.height;
								isCols = true;
							}
							
								int tempX = obstacle.x,tempY = obstacle.y;
								System.out.println("number:" + number);
								for(int i = 0;i < number;i++){
									if(isCols){
										tempY += this.thing.height;
										temp.y = tempY;
									}else{
										tempX += this.thing.width;
										temp.x = tempX;
									}
									System.out.println(dno.checkVector(temp));
									if(dno.checkVector(temp)==TANKGAME_CODE.failCode){
										Obstacle tempObstacle = (Obstacle) temp.clone();
										tempObstacle.setName("obstacle_" + (tempI++));
										dno.add(tempObstacle);
									}
								}
						}else{
							if(dno.checkVector(temp)==TANKGAME_CODE.failCode){
								Obstacle tempObstacle = (Obstacle) temp.clone();
								tempObstacle.setName("obstacle_" + (tempI++));
								dno.add(tempObstacle);
							}
						}
					}
					
					this.repaint();
					JPanelControlThing jpct = JPanelControlThing.getJPanelControlThing();
					jpct.initialize();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		me.addMouseMotionListener(this);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		me.removeMouseMotionListener(this);
	}


}



