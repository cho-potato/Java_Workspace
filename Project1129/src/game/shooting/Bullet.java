package game.shooting;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

// 총알 정의

public class Bullet extends GameObject {
	
	public Bullet(GamePanel gamePanel, int x, int y, int width, int height, int velX, int velY) {
		super(gamePanel, x, y, width, height, velX, velY);
		image = imageManager.getImage("res/bulletggg.png", width, height);
	}

//	총알에 맞는 물리량 변화코드(부모의 메서드를 재정의함)
	public void tick() {
		this.x += this.velX;
		rect.setLocation(x, y);
		
//		총알과 적군이 교차하는지 검증
		collisionCheck();
	}
	public void collisionCheck() {
//		나와 적군들 교차여부
		for (int i = 0; i<gamePanel.enemyList.size(); i++) {
			Enemy enemy = gamePanel.enemyList.get(i);
			boolean result = this.rect.intersects(enemy.rect);
//			컬렉션에서 제거하기
//			나죽고
			if(result) {
				
			int myIndex = gamePanel.bulletList.indexOf(this);
			gamePanel.bulletList.remove(myIndex);
//			너죽고
			int yourIndex = gamePanel.enemyList.indexOf(enemy);
			gamePanel.enemyList.remove(yourIndex);
			//gamePanel.enemyList.remove(i);
			}
			
		}
	}
	public void render(Graphics2D g) {
//		g.setColor(Color.WHITE);
//		g.fillOval(x, y, width, height);
		g.drawImage(image, x, y, width, height, null);
	}

}
