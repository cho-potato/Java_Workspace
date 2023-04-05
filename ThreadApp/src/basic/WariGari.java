package basic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WariGari extends JFrame {
	JPanel p;
	Thread thread;
	int x; // 원의 x축 좌표
	int y = 250;// 원의 y축 좌표
	Boolean flag=true;

	public WariGari() {
		p = new JPanel() {
//			AWT에서는 paint()를 사용하고, Swing에서는 paintComponent를 사용한다.
//			Swing에서도 paint() 메서드 호출은 가능하지만, Swing에 최적화되어 있지 않다
			public void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g;
//				 기존 그림 삭제
				 g2.clearRect(0, 0, 500, 600);
//				새로 그리기
				g2.setColor(Color.RED);
				g2.fillOval(x, y, 30, 30);
			}
		};
		// Thread 정의
		thread = new Thread() {
			public void run() {
				while (true) {
					moveCircle();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		thread.start();
		 
		add(p); // BorderLayout.CENTER에 붙음
		
		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}

	public void moveCircle() {
//		x+=2;
		int velX =  (flag)? 2: -2;
		x+=velX;
		if(x>=500 || x<=0) {
			flag=!flag;
		}
		
		p.repaint(); // update() -> paintComponent()
		}

	public static void main(String[] args) {
		new WariGari();
	}

}
