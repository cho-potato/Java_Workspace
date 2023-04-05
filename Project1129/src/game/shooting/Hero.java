package game.shooting;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;

import util.ImageManager;

public class Hero extends GameObject {

	public Hero(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage("res/wraith.png", width, height);
	}

	public void tick() { // 좌우로만 변화시킬예정
		this.x += this.velX;
		this.y += this.velY;

	}

//	Hero는 Component가 아니므로 그려질 수 없지만, JPanel인 GamePanel의 Graphics2D를 넘겨받았으므로 
//	Hero에서 그려지는 모든 그래픽 처리는 모두 GamePanel에 표현된다.
//	또한, JAVA의 모든 게임 오브젝트들은 GamePanel 위에서 그려진다.
	public void render(Graphics2D g) {
//		g.setColor(Color.BLACK);
//		g.fillRect(x, y, width, height);

		g.drawImage(image, x, y, width, height, null);

	}
}
