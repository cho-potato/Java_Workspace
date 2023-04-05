package game.shooting;

import java.awt.Graphics2D;

public class BgObject extends GameObject {

	public BgObject(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage("res/game_bg.png", width, height);
	}

	public void tick() {
		this.x += this.velX;
		if (x <= -width) {
			x=0;
		} 
	}

	public void render(Graphics2D g) {
		g.drawImage(image, x, y, width, height, null);
		g.drawImage(image, x+width, y, width, height, null);
		
//		if(x == 0-width) {
//			x = 0;
//		}
	}

}
