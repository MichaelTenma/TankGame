package com.lindont.TankGame.v0_4;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import com.lindont.TankGame.tools.JFrameCenteral;
import com.lindont.TankGame.tools.TANKGAME_CODE;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;

public class JPanelControlThing extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private static Thing thing = null;
	static boolean isCanGetThing = false;
	public static final String[] modelStringArray = new String[] {"tank", "wall"};
	public static final String[] directStringArray = new String[] {"Up","Down","Left","Right"};
	public static final int[] directValue = new int[] {TANKGAME_CODE.tankUpCode,TANKGAME_CODE.tankDownCode,TANKGAME_CODE.tankLeftCode,TANKGAME_CODE.tankRightCode};
	
	JTextField locationText;
	JComboBox<String> directText;
	JTextField lifeText;
	JTextField widthText;
	JTextField heightText;
	JTextField bulletText;
	JButton button;
	JButton colorText;
	private JCheckBox isUserCheckBox;
	private JCheckBox isUserOneCheckBox;
	private JCheckBox isCanDieCheckBox;
	
	static JComboBox<String> comboBox;
	PreviewJPanel previewJPanel;

	/**
	 * Create the panel.
	 */
	
	private static JPanelControlThing jpct = null;
	public static synchronized JPanelControlThing getJPanelControlThing(){
		if(JPanelControlThing.jpct==null){
			JPanelControlThing.jpct = new JPanelControlThing();
		}
		return JPanelControlThing.jpct;
	}
	private JPanelControlThing() {
		setLayout(null);
		
		JLabel lblLoca = new JLabel("\u4F4D\u7F6E\uFF1A");
		lblLoca.setFont(new Font("宋体", Font.BOLD, 15));
		lblLoca.setBounds(10, 37, 66, 34);
		add(lblLoca);
		
		locationText = new JTextField();
		locationText.setColumns(10);
		locationText.setBounds(71, 46, 99, 21);
		add(locationText);
		
		JLabel lblType = new JLabel("\u7C7B\u578B\uFF1A");
		lblType.setFont(new Font("宋体", Font.BOLD, 15));
		lblType.setBounds(10, 10, 66, 34);
		add(lblType);
		
		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(modelStringArray));
		comboBox.setSelectedIndex(0);
//		comboBox.setToolTipText(modelStringArray[1]);
		comboBox.setBounds(71, 19, 99, 21);
		add(comboBox);
		
		JLabel lblColor = new JLabel("\u989C\u8272\uFF1A");
		lblColor.setFont(new Font("宋体", Font.BOLD, 15));
		lblColor.setBounds(10, 64, 66, 34);
		add(lblColor);
		
		JLabel lblDire = new JLabel("\u65B9\u5411\uFF1A");
		lblDire.setFont(new Font("宋体", Font.BOLD, 15));
		lblDire.setBounds(10, 92, 66, 34);
		add(lblDire);
		
		directText = new JComboBox<String>();
		directText.setModel(new DefaultComboBoxModel<String>(directStringArray));
		directText.setSelectedIndex(0);
		directText.setBounds(71, 101, 99, 21);
		add(directText);
		
		
		JLabel lblLife = new JLabel("\u751F\u547D\uFF1A");
		lblLife.setFont(new Font("宋体", Font.BOLD, 15));
		lblLife.setBounds(10, 123, 66, 34);
		add(lblLife);
		
		lifeText = new JTextField();
		lifeText.setColumns(10);
		lifeText.setBounds(71, 132, 99, 21);
		add(lifeText);
		
		JLabel label = new JLabel("\u957F\u5EA6\uFF1A");
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setBounds(10, 152, 66, 34);
		add(label);
		
		widthText = new JTextField();
		widthText.setColumns(10);
		widthText.setBounds(71, 161, 99, 21);
		add(widthText);
		
		JLabel label_1 = new JLabel("\u9AD8\u5EA6\uFF1A");
		label_1.setFont(new Font("宋体", Font.BOLD, 15));
		label_1.setBounds(10, 180, 66, 34);
		add(label_1);
		
		heightText = new JTextField();
		heightText.setColumns(10);
		heightText.setBounds(71, 189, 99, 21);
		add(heightText);
		
		JLabel label_2 = new JLabel("\u5B50\u5F39\uFF1A");
		label_2.setFont(new Font("宋体", Font.BOLD, 15));
		label_2.setBounds(10, 208, 66, 34);
		add(label_2);
		
		colorText = new JButton("");
		colorText.setBounds(71, 73, 99, 21);
		add(colorText);
		
		bulletText = new JTextField();
		bulletText.setColumns(10);
		bulletText.setBounds(71, 217, 99, 21);
		add(bulletText);
		
		isUserCheckBox = new JCheckBox("\u662F\u5426\u4E3A\u7528\u6237");
		isUserCheckBox.setFont(new Font("宋体", Font.BOLD, 15));
		isUserCheckBox.setBounds(10, 248, 160, 23);
		add(isUserCheckBox);
		
		isUserOneCheckBox = new JCheckBox("\u662F\u5426\u4E3A\u7528\u6237 1");
		isUserOneCheckBox.setFont(new Font("宋体", Font.BOLD, 15));
		isUserOneCheckBox.setBounds(10, 273, 160, 23);
		add(isUserOneCheckBox);
		
		isCanDieCheckBox = new JCheckBox("\u662F\u5426\u80FD\u6B7B");
		isCanDieCheckBox.setFont(new Font("宋体", Font.BOLD, 15));
		isCanDieCheckBox.setBounds(10, 298, 160, 23);
		add(isCanDieCheckBox);
		
		previewJPanel = PreviewJPanel.getPreviewJPanel();
		previewJPanel.setBounds(20, 327, 150, 133);
		add(previewJPanel);
		new Thread(previewJPanel).start();
		
		button = new JButton("\u786E\u5B9A");
		button.setFont(new Font("宋体", Font.BOLD, 20));
		button.setBounds(20, 470, 150, 40);
		add(button);
		
		button.addActionListener(this);
		button.setActionCommand("ok");
		
		comboBox.addActionListener(this);
		comboBox.setActionCommand("comboBoxClicked");
		
		colorText.addActionListener(this);
		colorText.setActionCommand("colorTextClicked");
		
		directText.addActionListener(this);
		directText.setActionCommand("directTextClicked");
		
	}
	
	
	
	boolean isCanDie(){
		return isCanDieCheckBox.isSelected();
	}
	
	boolean isUser(){
		return isUserCheckBox.isSelected();
	}
	
	boolean isUserOne(){
		return isUserOneCheckBox.isSelected();
	}
	
	static Thing getThing() {
		return thing!=null?(Thing)thing.clone():null;
	}
	static void setThing(Thing thing) {
		JPanelControlThing.thing = thing;
	}
	static String getType(){
		return comboBox.getSelectedItem().toString();
	}


	private void makeThing(){
		PreviewJPanel jp = PreviewJPanel.getPreviewJPanel();
		jp.thing = JPanelControlThing.getThing();
		jp.thing.x = this.getX();
		jp.thing.y = this.getY();
		jp.isShow = true;
//		this.initialize();
		System.out.println("ok");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("ok")){
			if(JPanelControlThing.isCanGetThing){
				makeThing();
			}else{
				//判断各输入框的情况
				String type = getType();
				if(this.checkEmpty(type)){
					System.out.println("aa");
					JPanelControlThing.isCanGetThing = true;
					//这里只是做一个模板thing，加到游戏中的thing以此为模板，仅此而已
					//真正的thing在MapEditor中制作
					Thing thing = null;
					if(type.equals(JPanelControlThing.modelStringArray[0])){
						//tank
						Tank tank = new Tank();
						tank.setX(x);
						tank.setY(y);
						tank.setColor(color);
						tank.setName("tempTank");
						tank.setDirect(direct);
						tank.setLife(life);
						tank.setBulletSize(bulletSize);
						tank.setUser(isUser());
						tank.setUserOne(isUserOne());
						thing = tank;
						
//						Data_NowTank dnt = new Data_NowTank();
//						dnt.add(tank);
					}else if(type.equals(JPanelControlThing.modelStringArray[1])){
						//wall
						Wall wall = new Wall(new Obstacle("tempWall", x, y, width, height, true, color, isCanDie(), TANKGAME_CODE.obstacleType_Wall));
						thing = wall;
						
						
					}
					
					//小心使得万年船
					if(thing!=null){
						
						setThing(thing);
						makeThing();
					}
				}else{
					System.out.println("为空");
				}
			}
		}else if(e.getActionCommand().equals("comboBoxClicked")){
			comboBoxOnClicked();
		}else if(e.getActionCommand().equals("colorTextClicked")){
			getSelectColor();
		}else if(e.getActionCommand().equals("directTextClicked")){
			setDirect();
			System.out.println("Direct:" + this.direct);
		}
	}
	
	private int x = TANKGAME_CODE.failCode,y = TANKGAME_CODE.failCode;
	private Color color = null;
	private int direct = TANKGAME_CODE.failCode;
	private int life = TANKGAME_CODE.failCode;
	private int width = TANKGAME_CODE.failCode,height = TANKGAME_CODE.failCode;
	private int bulletSize = TANKGAME_CODE.failCode;
	
	void initialize(){
		color = null;
		direct = TANKGAME_CODE.failCode;
		life = TANKGAME_CODE.failCode;
		width = TANKGAME_CODE.failCode;
		height = TANKGAME_CODE.failCode;
		bulletSize = TANKGAME_CODE.failCode;
		
		isUserCheckBox.setSelected(false);
		isUserOneCheckBox.setSelected(false);
		isCanDieCheckBox.setSelected(false);
		JPanelControlThing.isCanGetThing = false;
	}
	
	public void setOrigin(int x,int y){
		this.x = x;this.y = y;
		String location = String.format("(%d,%d)", x,y);
		locationText.setText(location);
	}
	
	private void setColor(){
		try {
			this.color = new Color(Integer.parseInt(colorText.getText()));
		} catch (NumberFormatException e) {
			getSelectColor();
		}
	}
	
	private void getSelectColor(){
		Color color = null;
		color = JColorChooser.showDialog(null,"请选择你喜欢的颜色",color);
		if(color!=null){
			this.color = color;
			colorText.setText(this.color.getRGB() + "");
		}
	}
	
	private void setDirect(){
		this.direct = JPanelControlThing.directValue[directText.getSelectedIndex()];
	}
	
	private boolean checkEmpty(String type){
		boolean pass = false;
		if(x!=TANKGAME_CODE.failCode&&y!=TANKGAME_CODE.failCode&&color!=null){
			if(type.equals(JPanelControlThing.modelStringArray[0])){
				setLifeAndBulletSize();
				setDirect();
				if(direct!=TANKGAME_CODE.failCode&&life!=TANKGAME_CODE.failCode&&bulletSize!=TANKGAME_CODE.failCode){
					pass=true;
				}
			}else if(type.equals(JPanelControlThing.modelStringArray[1])){
				setWidthAndHeight();
				setDirect();
				if(width!=TANKGAME_CODE.failCode&&height!=TANKGAME_CODE.failCode){
					pass=true;
				}
			}
		}
		
		if(!pass){
			setColor();
			if(type.equals(JPanelControlThing.modelStringArray[0])){
				setLifeAndBulletSize();
			}else if(type.equals(JPanelControlThing.modelStringArray[1])){
				setWidthAndHeight();
			}
			
			if(x!=TANKGAME_CODE.failCode&&y!=TANKGAME_CODE.failCode&&life!=TANKGAME_CODE.failCode&&bulletSize!=TANKGAME_CODE.failCode){
				pass = checkEmpty(comboBox.getSelectedItem().toString());
			}else{
				System.out.println("不递归");
				System.out.printf("\n(%d,%d)",x,y);
				System.out.printf("\n%d %d",life,bulletSize);
			}
			
		}
		return pass;
	}
	
	private void setLifeAndBulletSize(){
		try {
			life = Integer.parseInt(lifeText.getText());
			bulletSize = Integer.parseInt(bulletText.getText());
		} catch (NumberFormatException e) {
			lifeText.setText(TANKGAME_CODE.failCode+"");
			bulletText.setText(TANKGAME_CODE.failCode+"");
			System.out.println("输入有误,请重试");
		}	
	}
	
	private void setWidthAndHeight(){
		try{			
			width = Integer.parseInt(widthText.getText());
			height = Integer.parseInt(heightText.getText());
		}catch(NumberFormatException e){
			widthText.setText(TANKGAME_CODE.failCode+"");
			heightText.setText(TANKGAME_CODE.failCode+"");
			System.out.println("输入有误,请重试");
		}
	}
	
	void comboBoxOnClicked(){
		widthText.setEditable(true);
		heightText.setEditable(true);
		isCanDieCheckBox.setEnabled(true);
		
		directText.setEnabled(true);
		lifeText.setEditable(true);
		bulletText.setEditable(true);
		isUserCheckBox.setEnabled(true);
		isUserOneCheckBox.setEnabled(true);
		
		if(getType().equals(JPanelControlThing.modelStringArray[0])){
			widthText.setEditable(false);
			heightText.setEditable(false);
			isCanDieCheckBox.setEnabled(false);
		}else if(getType().equals(JPanelControlThing.modelStringArray[1])){
			directText.setEnabled(false);
			lifeText.setEditable(false);
			bulletText.setEditable(false);
			isUserCheckBox.setEnabled(false);
			isUserOneCheckBox.setEnabled(false);
		}
	}
}

class PreviewJPanel extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private boolean isRun = true;
	boolean isShow = false;
	Thing thing = null;
	
	private static PreviewJPanel jp = null;
	
	private PreviewJPanel(){
	}
	
	public synchronized static PreviewJPanel getPreviewJPanel(){
		if(PreviewJPanel.jp==null){
			PreviewJPanel.jp=new PreviewJPanel();
		}
		return PreviewJPanel.jp;
	}
	
	public void paint(Graphics g){
		super.paint(g);
		this.setBackground(Color.gray);
		if(this.thing!=null&&this.isShow){
			JFrameCenteral jfc = JFrameCenteral.getJFrameCenteral();
			int location[] = jfc.middleLocaltion(thing.width, thing.height, this.getWidth(), this.getHeight());
			thing.x = location[0];
			thing.y = location[1];
			if(this.thing.getType()==TANKGAME_CODE.thingType_TANK){
				Tank tank = (Tank)thing;
//				System.out.println(tank.toString());
				tank.drawTank(g);
			}else if(this.thing.getType()==TANKGAME_CODE.thingType_OBSTACLE){
				Obstacle obstacle = (Obstacle)thing;
				if(obstacle.obstacleType==TANKGAME_CODE.obstacleType_Wall){
					Wall wall = (Wall)obstacle;
					wall.drawObstacle(g);
				}
			}
		}
	}

	@Override
	public void run() {
		while(this.isRun){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.repaint();
		}
	}

	
}

