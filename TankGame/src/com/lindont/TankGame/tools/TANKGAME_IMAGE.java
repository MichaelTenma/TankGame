package com.lindont.TankGame.tools;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class TANKGAME_IMAGE {
	public static Image imageBomb[] = new Image[3];
	public TANKGAME_IMAGE(){
		String link = "./src/image/";
		imageBomb[0] = this.getImage(link + "bomb_1.gif");
		imageBomb[1] = this.getImage(link + "bomb_2.gif");
		imageBomb[2] = this.getImage(link + "bomb_3.gif");
	}
	
	//bomb_1.gif"
//	public void setImageBomb(Image imageBomb[]){
//		TANKGAME_IMAGE.imageBomb = imageBomb;
//	}
	
	public Image getImage(String link){
		Image image = null;
		try {
			image = ImageIO.read(new File(link));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;
	}
}
