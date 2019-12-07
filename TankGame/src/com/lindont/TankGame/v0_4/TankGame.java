package com.lindont.TankGame.v0_4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import com.lindont.TankGame.tools.JFrameCenteral;

public class TankGame extends JFrame implements ActionListener{
	static GameJPanel jp = null;
	private JMenuBar jmb = null;
	private JMenu jm[] = null;
	private JMenuItem[] jmi = null;
	private String[] jmiStringArray = {"Start a game","Save a game","Make a map"};
	public static Thread gameThread = null;
	
	public static void main(String[] args) {
//		new TANKGAME_IMAGE();//初始化图片
		TankGame.getTankGame();
	}

	private static final long serialVersionUID = -6419851625569185559L;

	public static int width=(int) (618*1.5),height=(int) (382*1.5);
	
	private static TankGame tempTankGame = null;
	private TankGame(){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run() {
				tempTankGame.creatAndShowGUI();
			}
			
		});
	}
	
	public static TankGame getTankGame(){
		if(tempTankGame==null){
			tempTankGame = new TankGame();
		}
		return tempTankGame;
		
	}
	
	protected void creatAndShowGUI(){
		this.setTitle("TankGame");
		this.setSize(TankGame.width, TankGame.height);
		int location[] = JFrameCenteral.getJFrameCenteral().getLocation(TankGame.width, TankGame.height, this);
		this.setLocation(location[0], location[1]);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		jp = GameJPanel.getGameJPanel();
		jmb = new JMenuBar();
		
		jm = new JMenu[1];
		String[] jmStringArray = {"File"};
		for(int i = 0;i < jm.length;i++){
			jm[i] = new JMenu(jmStringArray[i]);
			jmb.add(jm[i]);
		}
		
		jmi = new JMenuItem[jmiStringArray.length];
		for(int i = 0;i < jmi.length;i++){
			jmi[i] = new JMenuItem(jmiStringArray[i]);
			jmi[i].addActionListener(this);
			jmi[i].setActionCommand(jmi[i].getText());
			jm[0].add(jmi[i]);
		}
		
		this.setJMenuBar(jmb);
		this.addKeyListener(jp);
		//启动刷新线程,定时刷新面板
		gameThread = new Thread(jp);
		this.add(TankGame.jp);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String s = e.getActionCommand();
		if(s.equals(jmiStringArray[0])){
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("请选择游戏地图...");
			jfc.setVisible(true);
			jfc.showOpenDialog(null);
			File file = jfc.getSelectedFile();
			if(file!=null){
				String link = file.getAbsolutePath();
				if(!link.equals("")){
					new Map().readMap(link);
				}
			}
		}else if(s.equals(jmiStringArray[1])){
			JFileChooser jfc = new JFileChooser(".");
			jfc.setDialogTitle("另存为...");
			jfc.setVisible(true);
			jfc.showSaveDialog(null);
			File file = jfc.getSelectedFile();
			if(file!=null){
				String link = file.getAbsolutePath();
				if(!link.equals("")){
					if(Data_NowTank.tankVector.size()>1){
						new Map().writeMap(link);
					}else{
						System.out.println("请先开始一个游戏");
					}
				}
			}

		}else if(s.equals(jmiStringArray[2])){
			
			final MapEditer me=  new MapEditer();
			SwingUtilities.invokeLater(new Runnable(){
				@Override
				public void run() {
					me.creatAndShowGUI();
				}
				
			});
//			System.out.println(jmiStringArray[2]);
		}
	}
	
}

