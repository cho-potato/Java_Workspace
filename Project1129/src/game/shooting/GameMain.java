package game.shooting;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameMain extends JFrame {
	GamePanel gamePanel;

	public GameMain() {
		gamePanel = new GamePanel();
		add(gamePanel);

		pack();

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

//		키보드 이벤트(리스너) 연결하기
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				System.out.println("???");
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_LEFT:gamePanel.moveX(-5);break;
				case KeyEvent.VK_RIGHT:gamePanel.moveX(5);break;
				case KeyEvent.VK_UP:gamePanel.moveY(-5);break;
				case KeyEvent.VK_DOWN:gamePanel.moveY(5);break;
				case KeyEvent.VK_SPACE:gamePanel.fire(); break;
				};
			}
//			키보드 뗏을 때 속도 없애기
			public void keyReleased(KeyEvent e) {
				System.out.println("!!!");
				int key = e.getKeyCode();
				switch (key) {
				case KeyEvent.VK_LEFT:gamePanel.moveX(0);break;
				case KeyEvent.VK_RIGHT:gamePanel.moveX(0);break;
				case KeyEvent.VK_UP:gamePanel.moveY(0);break;
				case KeyEvent.VK_DOWN:gamePanel.moveY(0);break;
				};
			}
		});
	}

	public static void main(String[] args) {
		new GameMain();

	}

}
